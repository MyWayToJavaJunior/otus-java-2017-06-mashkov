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
        final double[] values = {0,0,0,0};
        Path path = Paths.get(pathToLogs);
        Files.lines(path).forEach(l->{
            if (l.contains("[GC")) {
                values[0]++;
                String[] arr = l.split(" ");
                double millis = Double.parseDouble(arr[8]);
                values[1]+=millis;
            } else if (l.contains("[Full GC")){
                values[2]++;
                String[] arr = l.split(" ");
                double millis = Double.parseDouble(arr[12]);
                values[3]+=millis;
            }
        });

        System.out.println("Young GC was worked "+ values[0]+" times, "+values[1]+" seconds");
        System.out.println("Old GC was worked "+ values[2]+" times, "+values[3]+" seconds");
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
