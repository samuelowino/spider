package com.wikedapps.spider.io;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class SpiderGlossaryFileUtils {

    public static String decodeByteArrayToString(byte[] byteData) {
        byte[] decodedData = Base64.getDecoder().decode(byteData);
        return new String(decodedData);
    }

    public static String readFile(File targetFile) throws IOException {
        PDDocument document = PDDocument.load(targetFile);
        PDFTextStripper textStripper = new PDFTextStripper();
        String content = textStripper.getText(document);
        return content;
    }

    public static HashMap<String, String> readFileInDir(File dir) throws IOException {
        HashMap<String, String> content = new HashMap<>();
        for (File file : dir.listFiles()) {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setEndBookmark(new PDOutlineItem());
            String contVal = textStripper.getText(document);

            System.err.println(contVal);

            String cleanContent = cleanExtractedContent(contVal);
            content.put(file.getName(), cleanContent);

            document.close();
        }

        return content;
    }

    public static String cleanExtractedContent(String rawText) {
        String finalCleanText = new String();
        finalCleanText = rawText.replace("'", "");
        finalCleanText = finalCleanText.replaceAll("\\n\\n","\n\n\n");
        finalCleanText = finalCleanText.replaceAll("\\n","\n\n");
        finalCleanText = finalCleanText.replace(" \" ", "");
        finalCleanText = finalCleanText.replace("“"," ");
        finalCleanText = finalCleanText.replace("”"," ");
        finalCleanText = finalCleanText.replace("()","");
        finalCleanText = finalCleanText.replace("<", "&lt;");
        finalCleanText = finalCleanText.replace(">", "&gt;");
        finalCleanText = finalCleanText.replaceAll("([\\[\\d*\\]])", "");
        finalCleanText = finalCleanText.replace("See also", "");
        finalCleanText = finalCleanText.replaceAll("\"", " ");
        finalCleanText = finalCleanText.replace("wikipedia", " ");
        finalCleanText = finalCleanText.replace("Wikipedia", " ");
        finalCleanText = finalCleanText.replace("WIKIPEDIA", " ");
        finalCleanText = finalCleanText.replace("org/wiki/", " ");
        finalCleanText = finalCleanText.replaceAll("&","and");
        return finalCleanText;
    }

    public static void main(String args[]) throws IOException {

        File pdfFile = new File("/home/muzima/Desktop/content II");
        HashMap<String, String> content = readFileInDir(pdfFile);

        for (String fileName : content.keySet()) {
            File newFile = new File("/home/muzima/Desktop/R/" + fileName + ".txt");
            boolean isFileReady = newFile.createNewFile();
            if (isFileReady)
                FileUtils.writeStringToFile(newFile, content.get(fileName));
            else
                throw new AssertionError("Unable to write to file, File was not created or is not ready");
        }
    }
}
