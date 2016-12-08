package com.pfi.advent;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    private static final String INPUT = "iwrupvqb";

    public static String byteArrayToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getHashForBytes(byte[] input, String digestString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(digestString);
        return byteArrayToHex(md.digest(input));
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000000000; i++) {
            if (i % 100000 == 0) System.out.println(i);
            String attempt = String.format("%s%d", INPUT, i);
            String md5 = getHashForBytes(attempt.getBytes(), "MD5");
            if (md5.startsWith("000000")) {
                System.out.println("Winner: " + i);
                break;
            }
        }
    }
}
