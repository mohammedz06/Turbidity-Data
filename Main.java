import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main{
    public static void main(String[] args) {
        String inputFile = "src/main/java/TurbidityTestData.txt";
        String tempFile = "TurbidityTestData_temp.txt";

        // Check if the input file exists before proceeding
        if (!Files.exists(Paths.get(inputFile))) {
            System.err.println("Input file not found: " + inputFile);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;

            while ((line = reader.readLine()) != null) {
                // Replace the first '.' with ','
                int firstDotIndex = line.indexOf('.');
                if (firstDotIndex != -1) {
                    line = line.substring(0, firstDotIndex) + ',' + line.substring(firstDotIndex + 1);
                }

                // Replace the first '..' with ','
                int firstDotsIndex = line.indexOf("..");
                if (firstDotsIndex != -1) {
                    line = line.substring(0, firstDotsIndex) + ',' + line.substring(firstDotsIndex + 2);
                }

                // Write the processed line to the temporary file
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Replace the original file with the processed file
        try {
            Files.move(
                Paths.get(tempFile),
                Paths.get(inputFile),
                StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("File processed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}