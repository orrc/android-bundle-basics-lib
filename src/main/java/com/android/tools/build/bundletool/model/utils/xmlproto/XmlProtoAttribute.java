/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources.XmlAttribute;

/** Wrapper around the {@link XmlAttribute} proto, providing a fluent API. */
public final class XmlProtoAttribute extends XmlProtoAttributeOrBuilder<XmlAttribute> {

  private final XmlAttribute attribute;

  XmlProtoAttribute(XmlAttribute attribute) {
    this.attribute = attribute;
  }

  @Override
  public XmlAttribute getProto() {
    return attribute;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof XmlProtoAttribute)) {
      return false;
    }
    return attribute.equals(((XmlProtoAttribute) o).getProto());
  }

  @Override
  public int hashCode() {
    return attribute.hashCode();
  }
}
