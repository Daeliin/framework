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
import java.util.Objects;

public final class JpgImage {

    private final File source;

    private final ImageFormat format;

    public JpgImage(File source, ImageFormat format) {
        this.source = Objects.requireNonNull(source);
        this.format = Objects.requireNonNull(format);
    }

    public void compressTo(File target) throws IOException {
        BufferedImage resizedImage = resize();
        FileImageOutputStream targetOutputStream = new FileImageOutputStream(target);
        IIOImage iioImage = new IIOImage(resizedImage, null, null);

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(format.extension()).next();
        ImageWriteParam imageWriteParams = imageWriter.getDefaultWriteParam();
        imageWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParams.setCompressionQuality(format.compression());
        imageWriter.setOutput(targetOutputStream);
        imageWriter.write(null, iioImage, imageWriteParams);

        imageWriter.dispose();
        targetOutputStream.close();
        resizedImage.flush();
        resizedImage.flush();
    }

    private BufferedImage resize() throws IOException {
        BufferedImage originalImage = ImageIO.read(source);
        return Scalr.resize(originalImage, Scalr.Mode.FIT_EXACT, format.width(), format.height());
    }
}
