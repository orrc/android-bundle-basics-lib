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
import com.android.aapt.Resources.XmlElement;
import com.android.aapt.Resources.XmlNode;

import java.util.List;

/** Wrapper around the {@link XmlElement.Builder} proto, providing a fluent API. */
public final class XmlProtoElementBuilder
    extends XmlProtoElementOrBuilder<
        XmlNode.Builder,
        XmlProtoNodeBuilder,
        XmlElement.Builder,
        XmlProtoElementBuilder,
        XmlAttribute.Builder,
        XmlProtoAttributeBuilder> {

  private final XmlElement.Builder element;

  XmlProtoElementBuilder(XmlElement.Builder element) {
    this.element = element;
  }

  public XmlProtoElement build() {
    return new XmlProtoElement(element.build());
  }

  @Override
  public XmlElement.Builder getProto() {
    return element;
  }

  @Override
  protected List<XmlAttribute.Builder> getProtoAttributesList() {
    return element.getAttributeBuilderList();
  }

  @Override
  protected List<XmlNode.Builder> getProtoChildrenList() {
    return element.getChildBuilderList();
  }

  @Override
  protected XmlProtoNodeBuilder newNode(XmlNode.Builder node) {
    return new XmlProtoNodeBuilder(node);
  }

  @Override
  protected XmlProtoAttributeBuilder newAttribute(XmlAttribute.Builder attribute) {
    return new XmlProtoAttributeBuilder(attribute);
  }

}
