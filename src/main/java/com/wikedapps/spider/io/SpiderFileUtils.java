package com.wikedapps.spider.io;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class SpiderFileUtils {

    public static String decodeByteArrayToString(byte[] byteData){
        byte[] decodedData = Base64.getDecoder().decode(byteData);
        return new String(decodedData);
    }

    public static String readFile(File targetFile) throws IOException {
        PDDocument document = PDDocument.load(targetFile);
        PDFTextStripper textStripper = new PDFTextStripper();
        String content = textStripper.getText(document);
        return content;
    }

    public static HashMap<String,String> readFileInDir(File dir) throws IOException {
        HashMap<String,String> content = new HashMap<>();
        for (File file : dir.listFiles()) {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper textStripper = new PDFTextStripper();
            String contVal = textStripper.getText(document);
            content.put(file.getName(),contVal);
        }

        return content;
    }

    public static String cleanExtractedContent(String rawText){
        String finalCleanText = new String();
        finalCleanText = rawText.replace("'","");
        finalCleanText = finalCleanText.replace(" \" ","");
        finalCleanText = finalCleanText.replace("<","&lt;");
        finalCleanText = finalCleanText.replace(">","&gt;");
        finalCleanText = finalCleanText.replaceAll("([\\[\\d*\\]])","");
        finalCleanText = finalCleanText.replace("//","\n");
        finalCleanText = finalCleanText.replaceAll("See also","replace*");
        finalCleanText = finalCleanText.replaceAll("https","replace*");
        finalCleanText = finalCleanText.replaceAll("\""," ");
        return finalCleanText;
    }

    public static void main(String args[]) throws IOException {

        File pdfFile = new File("/home/muzima/Desktop/Biochemistry - Wikipedia.pdf");
        String content = readFile(pdfFile);
        String cleanText = cleanExtractedContent(content);
        System.out.print(cleanText);
    }
}
