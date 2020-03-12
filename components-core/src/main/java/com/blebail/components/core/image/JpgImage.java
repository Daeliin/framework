package com.blebail.components.core.image;

import org.imgscalr.Scalr;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class JpgImage {

    private final File source;

    private final ImageFormat format;

    public JpgImage(File source, ImageFormat format) {
        this.source = Objects.requireNonNull(source);
        this.format = Objects.requireNonNull(format);
    }

    public void compressTo(File target) throws IOException {
        FileImageOutputStream targetOutputStream = new FileImageOutputStream(target);
        BufferedImage resizedImage = resize();

        ImageWriter writer = getWriter();
        ImageWriteParam writerSettings = getWriterSettings(writer);

        try {
            writer.setOutput(targetOutputStream);
            writer.write(null, new IIOImage(resizedImage, null, null), writerSettings);
        } finally {
            writer.dispose();
            targetOutputStream.close();
            resizedImage.flush();
        }
    }

    private BufferedImage resize() throws IOException {
        BufferedImage sourceImage = ImageIO.read(source);

        return Scalr.resize(sourceImage, Scalr.Mode.FIT_EXACT, format.width(), format.height());
    }

    private ImageWriter getWriter() {
        Iterator<ImageWriter> imageWritersIterator = ImageIO.getImageWritersByFormatName(format.extension());

        if (!imageWritersIterator.hasNext()) {
            throw new NoSuchElementException(String.format("Could not find an image writer for %s format", format.extension()));
        }

        return imageWritersIterator.next();
    }

    private ImageWriteParam getWriterSettings(ImageWriter imageWriter) {
        ImageWriteParam imageWriteParams = imageWriter.getDefaultWriteParam();

        imageWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParams.setCompressionQuality(format.compression());

        return imageWriteParams;
    }
}
