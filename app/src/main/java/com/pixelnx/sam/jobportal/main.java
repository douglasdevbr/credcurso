package com.pixelnx.sam.jobportal;

public class main {
    public static void main(String[] args) {
        String filename = "image.pdf";
        String filenameArray[] = filename.split("\\.");
        String extension = filenameArray[filenameArray.length-1];
        System.out.println(extension);
    }
}