package com.wikedapps.spider.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileListWriter {

    static String finalA = "";

    public static void writeListToFile() throws IOException {
        String testEntry = "Student ID#  First Name Last Name  DOB         address\n";
        String testEntryData = "123          Tim        Smith      19960215    777 Heavens way\n" +
                "160          Diablo      Ho         19900420    666 Hells Gate\n" +
                "999          Tom         Doe        19860215    999 Never Mind\n" +
                "100          Buffy       Slayer     19960314    456 Seven\n" +
                "225          Lerui       Jackson    20001114    123 Right Here Alley\n" +
                "849          Bruce       Lee        19560215    333 Enter the Dragon\n";


        finalA = testEntry.concat(testEntryData);
        FileUtils.writeStringToFile(new File("mustio.txt"), finalA);
    }

    public static int countNumberOfOccurences(char val, String body) {
        String pattern = "" + val;
        int counter = 0;

        do {
            body = body.replaceFirst(pattern,"1");
            System.out.println(body);
            counter++;
        } while (body.contains(pattern));

        return counter;
    }


    public static void main(String[] args) {
        try {
            writeListToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numberOfOccurence = countNumberOfOccurences('v', finalA);

        System.out.println("The character v  occurs " + numberOfOccurence + " times");
    }
}
