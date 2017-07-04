package ru.otus.l4GC.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LogParser {
    public static void main(String[] args) {
        LogParser parser = new LogParser();
        try {
            parser.parse(parser.readPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String pathToLogs) throws IOException {
        final int[] oldTimes = {0};
        final int[] yongTimes = {0};
        final double[] oldMillis = {0};
        final double[] yongMillis = {0};
        Path path = Paths.get(pathToLogs);
        Files.lines(path).forEach(l->{
            if (l.contains("[GC")) {
                yongTimes[0]++;
                String[] arr = l.split(" ");
                double millis = Double.parseDouble(arr[8]);
                //System.out.println("yong millis = " +millis);
                yongMillis[0] += millis;
            } else if (l.contains("[Full GC")){
                oldTimes[0]++;
                String[] arr = l.split(" ");
                double millis = Double.parseDouble(arr[12]);
                //System.out.println("old millis = " +millis);
                oldMillis[0] +=millis;
            }
        });

        System.out.println("Young GC was worked "+ yongTimes[0]+" times, "+yongMillis[0]+" seconds");
        System.out.println("Old GC was worked "+ oldTimes[0]+" times, "+oldMillis[0]+" seconds");
    }

    private String readPath()
    {
        System.out.println("Enter path to log: ");

        Scanner scanIn = new Scanner(System.in);
        String inputString = scanIn.nextLine();

        scanIn.close();

        return inputString;
    }
}
