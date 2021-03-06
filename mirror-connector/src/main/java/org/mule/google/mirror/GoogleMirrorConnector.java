/*
Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com

The software in this package is published under the terms of the CPAL v1.0
license, a copy of which has been included with this distribution in the
LICENSE.txt file.
 */
package org.mule.google.mirror;

import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.oauth.OAuth2;
import org.mule.api.annotations.oauth.OAuthAccessToken;
import org.mule.api.annotations.oauth.OAuthAccessTokenIdentifier;
import org.mule.api.annotations.oauth.OAuthAuthorizationParameter;
import org.mule.api.annotations.oauth.OAuthConsumerKey;
import org.mule.api.annotations.oauth.OAuthConsumerSecret;
import org.mule.api.annotations.oauth.OAuthInvalidateAccessTokenOn;
import org.mule.api.annotations.oauth.OAuthPostAuthorization;
import org.mule.api.annotations.oauth.OAuthProtected;
import org.mule.api.annotations.oauth.OAuthScope;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.modules.google.AbstractGoogleOAuthConnector;
import org.mule.modules.google.AccessType;
import org.mule.modules.google.ForcePrompt;
import org.mule.modules.google.IdentifierPolicy;
import org.mule.modules.google.oauth.invalidation.OAuthTokenExpiredException;

import com.google.api.services.mirror.Mirror;
import com.google.api.services.mirror.model.TimelineItem;
import com.google.api.services.mirror.model.TimelineListResponse;

import java.io.IOException;

/**
 * Google Mirror Cloud connector.
 * This connector covers almost all the Google Mirror API (used for Google Glass) using OAuth2 for authentication.
 *
 * @author ross@mulesoft.com
 */
@Connector(name = "google-mirror", schemaVersion = "1.0", friendlyName = "Google Mirror", minMuleVersion = "3.4", configElementName = "config-with-oauth")
@OAuth2(
        authorizationUrl = "https://accounts.google.com/o/oauth2/auth",
        accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
        accessTokenRegex = "\"access_token\"[ ]*:[ ]*\"([^\\\"]*)\"",
        expirationRegex = "\"expires_in\"[ ]*:[ ]*([\\d]*)",
        refreshTokenRegex = "\"refresh_token\"[ ]*:[ ]*\"([^\\\"]*)\"",
        authorizationParameters = {
                @OAuthAuthorizationParameter(name = "access_type", defaultValue = "online", type = AccessType.class, description = "Indicates if your application needs to access a Google API when the user is not present at the browser. " +
                        " Use offline to get a refresh token and use that when the user is not at the browser. Default is online", optional = true),
                @OAuthAuthorizationParameter(name = "force_prompt", defaultValue = "auto", type = ForcePrompt.class, description = "Indicates if google should remember that an app has been authorized or if each should ask authorization every time. " +
                        " Use force to request authorization every time or auto to only do it the first time. Default is auto", optional = true)
        }
)
public class GoogleMirrorConnector extends AbstractGoogleOAuthConnector
{
    /**
     * The OAuth2 consumer key
     */
    @Configurable
    @OAuthConsumerKey
    private String consumerKey;

    /**
     * The OAuth2 consumer secret
     */
    @Configurable
    @OAuthConsumerSecret
    private String consumerSecret;

    /**
     * Application name registered on Google API console
     */
    @Configurable
    private String applicationName;

    /**
     * The OAuth scopes you want to request
     */
        @OAuthScope
        @Configurable
        @Optional
        @Default(
                USER_PROFILE_SCOPE
                        + " https://www.googleapis.com/auth/glass.timeline")
        private String scope;

    /**
     * This policy represents which id we want to use to represent each google account.
     * <p/>
     * PROFILE means that we want the google profile id. That means, the user's primary key in google's DB.
     * This is a long number represented as a string.
     * <p/>
     * EMAIL means you want to use the account's email address
     */
    @Configurable
    @Optional
    @Default("EMAIL")
    private IdentifierPolicy identifierPolicy = IdentifierPolicy.EMAIL;

    /**
     * Factory to instantiate the underlying google client.
     * Usually you don't need to override this. Most common
     * use case of a custom value here is testing.
     */
    @Configurable
    @Optional
    private GoogleMirrorClientFactory clientFactory;

    @OAuthAccessToken
    private String accessToken;

    /**
     * The google api client
     */
    private Mirror client;

    /**
     * Initializes the connector. if no clientFactory was provided, then a default
     * {@link org.mule.google.mirror.DefaultGoogleMirrorClientFactory}
     * wil be used instead
     */
    @Start
    public void init()
    {
        if (this.clientFactory == null)
        {
            this.clientFactory = new DefaultGoogleMirrorClientFactory();
        }
    }

    @OAuthAccessTokenIdentifier
    public String getAccessTokenId()
    {
        return this.identifierPolicy.getId(this);
    }

    @OAuthPostAuthorization
    public void postAuth()
    {
        this.client = this.clientFactory.newClient(this.getAccessToken(), this.getApplicationName());
    }

    /**
     * When users interacts with their timeline, the main way they receive information is in the form of timeline items, otherwise known as cards. Timeline cards display content from various Glassware and swiping forward and backward on Glass reveals more cards in the past and future.
     * <p/>
     * {@sample.xml ../../../doc/GoogleMirror-connector.xml.sample google-mirror:insert-timeline-item}
     *
     * @param item      The description of the timeline entry to insert in to the users timeline
     * @return the item inserted
     * @throws IOException in case of connection issues
     */
    @Processor
    @OAuthProtected
    @OAuthInvalidateAccessTokenOn(exception = OAuthTokenExpiredException.class)
    public TimelineItem insertTimelineItem(TimelineItem item)
            throws IOException {

        client.timeline().insert(item).execute();
        return item;
    }

    /**
     * Lists the items on the users time
     * <p/>
     * {@sample.xml ../../../doc/GoogleMirror-connector.xml.sample google-mirror:list-timeline}
     *
     * @return the a list of timeline items
     * @throws IOException in case of connection issues
     */
    @Processor
    @OAuthProtected
    @OAuthInvalidateAccessTokenOn(exception = OAuthTokenExpiredException.class)
    public TimelineListResponse listTimeline() throws IOException {
        return client.timeline().list().execute();
    }

    public String getConsumerKey()
    {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey)
    {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret()
    {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret)
    {
        this.consumerSecret = consumerSecret;
    }

    public String getApplicationName()
    {
        return applicationName;
    }

    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }

    public IdentifierPolicy getIdentifierPolicy()
    {
        return identifierPolicy;
    }

    public void setIdentifierPolicy(IdentifierPolicy identifierPolicy)
    {
        this.identifierPolicy = identifierPolicy;
    }

    public GoogleMirrorClientFactory getClientFactory()
    {
        return clientFactory;
    }

    public void setClientFactory(GoogleMirrorClientFactory clientFactory)
    {
        this.clientFactory = clientFactory;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public Mirror getClient()
    {
        return client;
    }

    public void setClient(Mirror client)
    {
        this.client = client;
    }
}
