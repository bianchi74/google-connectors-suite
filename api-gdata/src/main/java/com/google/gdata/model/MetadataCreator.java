/**
 * Mule Google Api Commons
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */


package com.google.gdata.model;

import com.google.gdata.model.Metadata.VirtualValue;

/**
 * Shared interface for metadata creators.  Extended by {@link AttributeCreator}
 * and {@link ElementCreator}, this is used to create both elements and
 * attributes, which we refer to as properties.
 *
 * 
 */
public interface MetadataCreator {

  /**
   * Sets the name of the property.  This is used on parsing to decide which
   * field to place a property in, and during generation to choose the display
   * name of the property.  This should only be set to override the default
   * name, which is the ID specified in the {@link MetadataKey}.
   *
   * @param name the new name to use for the property.
   * @return this metadata creator for chaining.
   */
  MetadataCreator setName(QName name);

  /**
   * Sets the requiredness of this property.  If set to true, this property
   * must appear in both the input and output or a validation error will occur.
   * If set to false, this property is optional.
   *
   * @param required true to set the property to required, false to set it
   *     to optional (the default).
   * @return this metadata creator for chaining.
   */
  MetadataCreator setRequired(boolean required);

  /**
   * Sets whether this property is visible.  If the property is not visible
   * then it will not be included in the output.  This can be used to hide an
   * property in particular contexts (such as RSS or JSON output).  It can
   * also be used to explicitly set a property to visible that may be hidden
   * by other metadata rules.
   *
   * @param visible true to make the property visible (the default), false to
   *     hide it from the output.
   * @return this metadata creator for chaining.
   */
  MetadataCreator setVisible(boolean visible);

  /**
   * Sets the virtual value for the property.  This is used as the value
   * of the property during parsing and generation.
   */
  MetadataCreator setVirtualValue(VirtualValue virtualValue);
}
