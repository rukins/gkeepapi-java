package io.github.rukins.gkeepapi.utils;

import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;
import io.github.rukins.gkeepapi.model.image.ImageData;
import io.github.rukins.gkeepapi.model.image.ImageSize;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static void createImageFile(ImageData imageData, String pathToDirectory) {
        File file = new File(pathToDirectory + imageData.getFileName());

        try {
            if (file.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(imageData.getBytes());

                outputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageData getImageData(File image) {
        byte[] bytes = new byte[(int) image.length()];

        try(FileInputStream inputStream = new FileInputStream(image)) {
            inputStream.read(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageData(bytes, image.getName(), MimeType.getByFileExtension(image.getName()), getImageSize(image));
    }

    public static ImageSize getImageSize(File image) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageSize(bufferedImage.getWidth(), bufferedImage.getHeight());
    }
}
