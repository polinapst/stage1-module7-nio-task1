package com.epam.mjc.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        String filePath = file.getPath();
        String s;

        try (RandomAccessFile aFile = new RandomAccessFile(filePath, "r")) {
            FileChannel inChannel = aFile.getChannel();

            long fileSize = inChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            s = new String(buffer.array());
            return new Profile(extractName(s), extractAge(s), extractEmail(s), extractPhone(s));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Profile();
    }

    private String extractInfo(String input, String infoType) {
        String[] sArray = input.split("\n");

        for (String st: sArray) {
            if (st.toLowerCase().startsWith(infoType.toLowerCase())) {
                return st.split(" ")[1];
            }
        }
        return "Not found";
    }

    private String extractName(String input) {
        return extractInfo(input, "Name").trim();
    }

    private Integer extractAge(String input) {
        try {
            return Integer.parseInt(extractInfo(input, "Age").trim());
        }
        catch (IllegalArgumentException e) {
            return -1;
        }
    }

    private String extractEmail(String input) {
        return extractInfo(input, "Email").trim();
    }

    private Long extractPhone(String input) {
        try {
            return Long.parseLong(extractInfo(input, "Phone").trim());
        }
        catch (IllegalArgumentException e) {
            return -1L;
        }
    }
}
