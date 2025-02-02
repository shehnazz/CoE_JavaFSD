package task9;

import java.io.*;
import java.util.*;

public class FileAnalyzer {
	private List<String> keywords;

    public FileAnalyzer(List<String> keywords) {
        this.keywords = keywords;
    }

    public void analyzeLogFile(String inputFile, String outputFile) {
        Map<String, Integer> keywordCounts = new HashMap<>();
        List<String> matchingLines = new ArrayList<>();

       
        for (String keyword : keywords) {
            keywordCounts.put(keyword, 0);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                for (String keyword : keywords) {
                    if (line.contains(keyword)) {
                        keywordCounts.put(keyword, keywordCounts.get(keyword) + 1);
                        matchingLines.add(line);
                    }
                }
            }

           
            writer.write("Log Analysis Report:\n");
            writer.write("====================\n");
            for (String keyword : keywords) {
                writer.write("Occurrences of \"" + keyword + "\": " + keywordCounts.get(keyword) + "\n");
            }
            writer.write("\nMatching Log Entries:\n");
            writer.write("----------------------\n");
            for (String entry : matchingLines) {
                writer.write(entry + "\n");
            }

            System.out.println("Log analysis completed. Report saved to: " + outputFile);

        } catch (IOException e) {
            System.out.println("Error processing log file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<String> keywords = Arrays.asList("ERROR", "WARNING");  
        FileAnalyzer analyzer = new FileAnalyzer(keywords);

        String inputFile = "logfile.txt";  
        String outputFile = "log_report.txt";  

        analyzer.analyzeLogFile(inputFile, outputFile);
    }

}
