package com.blebail.components.core.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class SimpleImageFormatTest {

    @Test
    public void shouldBuildAPictureFormat() {
        ImageFormat pictureFormat = new SimpleImageFormat.Builder()
            .withWidth(128)
            .withHeight(85)
            .withExtension("jpg")
            .withPrefix("image-")
            .withSuffix("-ld")
            .withCompression(0.90f)
            .build();

        assertThat(pictureFormat.width()).isEqualTo(128);
        assertThat(pictureFormat.height()).isEqualTo(85);
        assertThat(pictureFormat.extension()).isEqualTo("jpg");
        assertThat(pictureFormat.prefix()).isEqualTo("image-");
        assertThat(pictureFormat.suffix()).isEqualTo("-ld");
        assertThat(pictureFormat.compression()).isEqualTo(0.90f);
    }
}