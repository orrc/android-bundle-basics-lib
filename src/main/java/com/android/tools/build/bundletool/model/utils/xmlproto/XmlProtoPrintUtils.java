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

import com.android.aapt.Resources.*;
import com.android.aapt.Resources.Reference.Type;

import java.lang.String;
import java.lang.Boolean;

/** Utility class to print debug values from the proto XML. */
public final class XmlProtoPrintUtils {

  static String getRefAsString(Reference ref) {
    // Return the name of the resource if available, else the ID.
    if (!ref.getName().isEmpty()) {
      return (ref.getType().equals(Type.REFERENCE) ? "@" : "?") + ref.getName();
    }
    return String.format("0x%08x", ref.getId());
  }

  /**
   * Returns the value of an {@link Item} as a string regardless of its type.
   *
   * <p>When not a string, the conversion is done on a best-effort basis, and may change in the
   * future.
   */
  public static String getItemValueAsString(Item item) {
    switch (item.getValueCase()) {
      case PRIM:
        return getPrimitiveValueAsString(item.getPrim());
      case FILE:
        return item.getFile().getPath();
      case ID:
        return "<ID>";
      case RAW_STR:
        return item.getRawStr().getValue();
      case REF:
        return getRefAsString(item.getRef());
      case STR:
        return item.getStr().getValue();
      case STYLED_STR:
        return "<unsupported>";
      default:
        // Not supported.
        return "";
    }
  }

  /**
   * Returns the value of an {@link Primitive} as a string regardless of its type.
   *
   * <p>When not a string, the conversion is done on a best-effort basis, and may change in the
   * future.
   */
  public static String getPrimitiveValueAsString(Primitive primitive) {
    switch (primitive.getOneofValueCase()) {
      case NULL_VALUE:
      case EMPTY_VALUE:
        return "";
      case FLOAT_VALUE:
        return Float.toString(primitive.getFloatValue());
      case DIMENSION_VALUE:
        return String.valueOf(primitive.getDimensionValue());
      case FRACTION_VALUE:
        return String.valueOf(primitive.getFractionValue());
      case INT_DECIMAL_VALUE:
        return Integer.toString(primitive.getIntDecimalValue());
      case INT_HEXADECIMAL_VALUE:
        return String.format("0x%08x", primitive.getIntHexadecimalValue());
      case BOOLEAN_VALUE:
        return Boolean.toString(primitive.getBooleanValue());
      case COLOR_ARGB8_VALUE:
        return String.format("#%08x", primitive.getColorArgb8Value());
      case COLOR_RGB8_VALUE:
        // We clear the transparency bits since they're not displayed for an RGB value.
        return String.format("#%06x", primitive.getColorRgb8Value() & 0x00FFFFFF);
      case COLOR_ARGB4_VALUE:
        return String.format("#%08x", primitive.getColorArgb4Value());
      case COLOR_RGB4_VALUE:
        // We clear the transparency bits since they're not displayed for an RGB value.
        return String.format("#%06x", primitive.getColorRgb4Value() & 0x00FFFFFF);
      case FRACTION_VALUE_DEPRECATED:
      case DIMENSION_VALUE_DEPRECATED:
      case ONEOFVALUE_NOT_SET:
    }
    return "";
  }

  private XmlProtoPrintUtils() {}
}
