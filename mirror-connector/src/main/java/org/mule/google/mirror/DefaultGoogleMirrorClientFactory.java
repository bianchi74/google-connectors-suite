/*
Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com

The software in this package is published under the terms of the CPAL v1.0
license, a copy of which has been included with this distribution in the
LICENSE.txt file.
 */
package org.mule.google.mirror;

import org.mule.modules.google.oauth.invalidation.InvalidationAwareCredential;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.mirror.Mirror;

/**
 * TODO
 */
public class DefaultGoogleMirrorClientFactory implements GoogleMirrorClientFactory
{
    public Mirror newClient(String accessToken, String applicationName)
    {
        Credential credential = new InvalidationAwareCredential(BearerToken.authorizationHeaderAccessMethod());
        credential.setAccessToken(accessToken);

        return new Mirror.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                .setApplicationName(applicationName)
                .build();
    }
}
