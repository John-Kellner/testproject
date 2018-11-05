package com.project.presentation.server.md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 15.04.2016.
 */
public class MD5Generator {

    public static String createMD5Key(String textToEncode){
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(textToEncode.getBytes(), 0, textToEncode.length());
            return new BigInteger(1, mdEnc.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
