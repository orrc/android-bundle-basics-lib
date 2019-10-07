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

import com.android.aapt.Resources.XmlAttributeOrBuilder;
import com.android.aapt.Resources.XmlElementOrBuilder;
import com.android.aapt.Resources.XmlNodeOrBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Internal interface ensuring that {@link XmlProtoElement} and {@link XmlProtoElementBuilder} have
 * the same getters.
 */
abstract class XmlProtoElementOrBuilder<
    NodeProtoT extends XmlNodeOrBuilder,
    NodeWrapperT extends XmlProtoNodeOrBuilder<?, ElementWrapperT, ?>,
    ElementProtoT extends XmlElementOrBuilder,
    ElementWrapperT extends
            XmlProtoElementOrBuilder<NodeProtoT, ?, ElementProtoT, ElementWrapperT, ?, ?>,
    AttributeProtoT extends XmlAttributeOrBuilder,
    AttributeWrapperT extends XmlProtoAttributeOrBuilder<AttributeProtoT>> {

  public abstract ElementProtoT getProto();

  protected abstract List<AttributeProtoT> getProtoAttributesList();

  protected abstract List<NodeProtoT> getProtoChildrenList();

  protected abstract NodeWrapperT newNode(NodeProtoT node);

  protected abstract AttributeWrapperT newAttribute(AttributeProtoT attribute);

  public final String getName() {
    return getProto().getName();
  }

  public final String getNamespaceUri() {
    return getProto().getNamespaceUri();
  }

  public final Stream<AttributeWrapperT> getAttributes() {
    return getProtoAttributesList().stream().map(this::newAttribute);
  }

  public final Stream<NodeWrapperT> getChildren() {
    return getProtoChildrenList().stream().map(this::newNode);
  }

  public final Stream<XmlProtoNamespace> getNamespaceDeclarations() {
    return getProto().getNamespaceDeclarationList().stream().map(XmlProtoNamespace::new);
  }

  @Deprecated // Don't ignore the namespace!
  public final Optional<AttributeWrapperT> getAttributeIgnoringNamespace(String name) {
    return getAttributes().filter(attr -> attr.getName().equals(name)).findFirst();
  }

  public final Stream<ElementWrapperT> getChildrenElements() {
    return getChildren().filter(NodeWrapperT::isElement).map(NodeWrapperT::getElement);
  }

  @Override
  public String toString() {
    return getProto().toString();
  }
}
