package com.blebail.components.core.image;

public interface ImageFormat {

    int width();

    int height();

    String extension();

    String prefix();

    String suffix();

    float compression();
}
