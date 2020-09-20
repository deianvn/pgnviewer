package com.github.deianvn.pgnviewer.core;

import java.util.Objects;

public enum Color {
  WHITE, BLACK;

  public static Color fromString(String color) {
    Objects.requireNonNull(color);
    switch (color) {
      case "w": return Color.WHITE;
      case "b": return Color.BLACK;
      default: throw new IllegalStateException("Color should be w or b");
    }
  }
}
