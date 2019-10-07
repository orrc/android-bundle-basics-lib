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
package com.android.tools.build.bundletool.xml;

import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNamespace;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


/**
 * A {@link NamespaceContext} that extracts the prefix/URI mapping from the namespace declarations
 * it finds in the given XML.
 */
public final class XmlNamespaceContext implements NamespaceContext {

  private final Map<String, String> prefixToNamespaceUri;

  public XmlNamespaceContext(XmlProtoNode manifestProto) {
    this.prefixToNamespaceUri =
        collectNamespaces(manifestProto.getElement())
            // get rid of other proto fields
            .map(xmlProto -> XmlProtoNamespace.create(xmlProto.getPrefix(), xmlProto.getUri()))
            .distinct()
            .collect(toMap(XmlProtoNamespace::getPrefix, XmlProtoNamespace::getUri));
  }

  /** Recursively collect all the namespace declarations in the given element and its children. */
  private static Stream<XmlProtoNamespace> collectNamespaces(XmlProtoElement protoElement) {
    return Stream.concat(
        protoElement.getNamespaceDeclarations(),
        protoElement.getChildrenElements().flatMap(XmlNamespaceContext::collectNamespaces));
  }

  @Override
  public String getNamespaceURI(String prefix) {
    String namespaceUri = prefixToNamespaceUri.get(prefix);
    if (namespaceUri == null) {
      return "";
    }
    return namespaceUri;
  }

  @Override
  public String getPrefix(String namespaceURI) {
    // Unnecessary for XPath evaluation.
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<String> getPrefixes(String namespaceURI) {
    // Unnecessary for XPath evaluation.
    throw new UnsupportedOperationException();
  }
}
