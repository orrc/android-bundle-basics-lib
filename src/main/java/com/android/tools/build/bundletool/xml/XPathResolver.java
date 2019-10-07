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

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.StringJoiner;

/** Resolver of XPath expressions against an XML node. */
public final class XPathResolver {

  public static XPathResult resolve(Node node, XPathExpression xPathExpression) {
    try {
      NodeList nodeList = (NodeList) xPathExpression.evaluate(node, XPathConstants.NODESET);
      return new XPathResult(nodeList);
    } catch (XPathExpressionException e) {
      throw new RuntimeException("Error evaluating the XPath expression.", e);
    }
  }

  /** Result of an XPath evaluation. */
  public static final class XPathResult {

    private final NodeList nodeList;

    private XPathResult(NodeList nodeList) {
      this.nodeList = nodeList;
    }

    /**
     * String representation of the XPath result.
     *
     * <p>If multiple values were matched, each value will be printed on a new line.
     */
    @Override
    public String toString() {
      StringJoiner output = new StringJoiner(System.lineSeparator());

      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
          output.add(((Attr) node).getValue());
        }
      }

      return output.toString();
    }
  }
}
