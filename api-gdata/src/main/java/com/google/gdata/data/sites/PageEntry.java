/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.data.sites;

import com.google.gdata.data.BaseEntry;

/**
 * An entry representing a page in the site. These entries have namesand
 * correspond to non-anonymous nodes in jotspot.
 *
 * 
 */
public class PageEntry extends BasePageEntry<PageEntry> {

  /**
   * Default mutable constructor.
   */
  public PageEntry() {
    super();
  }

  /**
   * Constructs a new instance by doing a shallow copy of data from an existing
   * {@link BaseEntry} instance.
   *
   * @param sourceEntry source entry
   */
  public PageEntry(BaseEntry<?> sourceEntry) {
    super(sourceEntry);
  }

  @Override
  public String toString() {
    return "{PageEntry " + super.toString() + "}";
  }

}

