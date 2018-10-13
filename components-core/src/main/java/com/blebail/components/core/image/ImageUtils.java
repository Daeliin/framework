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

public final class ImageUtils {

    public static void resize(File originalImageFile, File destinationImageFile, ImageFormat format) throws IOException {
        BufferedImage originalImage = ImageIO.read(originalImageFile);
        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Mode.FIT_EXACT, format.width(), format.height());

        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName(format.extension()).next();
        ImageWriteParam imageWriteParams = imageWriter.getDefaultWriteParam();
        imageWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParams.setCompressionQuality(format.compression());

        FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(destinationImageFile);
        imageWriter.setOutput(fileImageOutputStream);
        IIOImage iioImage = new IIOImage(resizedImage, null, null);
        imageWriter.write(null, iioImage, imageWriteParams);
        imageWriter.dispose();
        fileImageOutputStream.close();

        originalImage.flush();
        resizedImage.flush();
    }
}
