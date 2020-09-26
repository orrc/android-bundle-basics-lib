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

/** Common methods between {@link XmlProtoAttribute} and {@link XmlProtoAttributeBuilder}. */
abstract class XmlProtoAttributeOrBuilder<AttributeProtoT extends XmlAttributeOrBuilder> {

  abstract AttributeProtoT getProto();

  public final String getName() {
    return getProto().getName();
  }

  public final String getNamespaceUri() {
    return getProto().getNamespaceUri();
  }

  public final int getResourceId() {
    return getProto().getResourceId();
  }

  /**
   * Returns the value of the attribute as a string regardless of its type.
   *
   * <p>When not a string, the conversion is done on a best-effort basis, and may change in the
   * future.
   */
  public final String getDebugString() {
    if (!getProto().hasCompiledItem()) {
      return getProto().getValue();
    }

    return XmlProtoPrintUtils.getItemValueAsString(getProto().getCompiledItem());
  }

  @Override
  public String toString() {
    return getProto().toString();
  }
}
