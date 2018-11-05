package com.project.presentation.server.base64;

import com.project.presentation.server.qr.QRCode;
import com.project.presentation.server.util.Base64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by john on 03.10.2015.
 */
public class Base64Converter {

    /**
     * Convert file to base64
     * @param filename
     * @return
     */
    public static String convert(String filename){
        final File file = new File(filename);

        final BufferedImage img;
        try {
            img = ImageIO.read(file);
            final String[] split = file.getName().split("\\.");
            final String prefix = split[split.length - 1];

            if (file.getName().endsWith(prefix)) {
                return Base64Decoder.encodeToString(img, prefix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
