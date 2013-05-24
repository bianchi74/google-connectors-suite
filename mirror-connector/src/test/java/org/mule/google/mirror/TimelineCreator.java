/*
Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com

The software in this package is published under the terms of the CPAL v1.0
license, a copy of which has been included with this distribution in the
LICENSE.txt file.
 */
package org.mule.google.mirror;

import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.annotations.param.Payload;
import org.mule.api.store.ObjectStore;

import com.google.api.services.mirror.model.TimelineItem;

import java.util.Map;

/**
 * TODO
 */
public class TimelineCreator
{
    @Lookup(value = "myStore")
    private ObjectStore store;

    public Object onCall(@Payload Map<String, String> payload) throws Exception
    {
//        store.store("themuleman@gmail.com", "4/JwpBKHh8cdvIt_XCgeopstaX2rwW.kq9lYmob93ceMqTmHjyTFGM0tmKcfQI");
//        String token = (String)store.retrieve("themuleman@gmail.com");
//        event.getMessage().setInvocationProperty("tokenId", "themuleman@gmail.com");

        TimelineItem item = new TimelineItem().setText(payload.get("message"));

        return item;
    }
}
