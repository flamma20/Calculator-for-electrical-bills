import java.util.ArrayList;  
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class read
{
    
    public void read()
    {
        try {
    File file = new File("prod_Flat.txt"); 
    int metersCount = 0;

    Scanner scanner = new Scanner(file);
    ArrayList<String[]> flatData = new ArrayList<>(); // create an ArrayList to store file data
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        // Ignore lines starting with /
        if (line.startsWith("/")) {
            continue;
        }
        // Ignore lines with only whitespace
        if (line.isBlank()) {
            continue;
        }
        // Ignore lines with trailing commas
        if (line.endsWith(",")) {
            line = line.substring(0, line.length() - 1);
        }
        // Split the line by commas
        String[] fields = line.split(",");
        // Check if the line has at least 8 fields
        if (fields.length >= 8) {
            flatData.add(fields); // add fields to ArrayList
        } 
        
        for (int i = 7; i < fields.length; i++) {
                    if (fields[i].startsWith("m")){
                        metersCount++;//count how many meters in flats file
                    }
                }
    } 
    
    
            

    
    System.out.println("Number of Flats read in: " + flatData.size());
    System.out.println("Number of meters read in: " + metersCount);
    scanner.close();
} catch (FileNotFoundException e) {
    System.out.println("File not found.");
}

    }

    public void read2()
    {
        try {
    File file = new File("prod_Meter.txt");
    Scanner scanner = new Scanner(file);
    ArrayList<String[]> meterData = new ArrayList<>(); // create an ArrayList to store file data 
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        // Ignore lines starting with /
        if (line.startsWith("/")) {
            continue;
        }
        // Ignore lines with only whitespace
        if (line.isBlank()) {
            continue;
        }
        // Ignore lines with trailing commas
        if (line.endsWith(",")) {
            line = line.substring(0, line.length() - 1);
        }
        // Split the line by commas
        String[] fields = line.split(",");
        // Check if the line has 9 fields
        if (fields.length == 9) {
            meterData.add(fields); // add fields to ArrayList
        } 
        
        
        
    }
    System.out.println("Number of Meters read in: " + meterData.size());
    scanner.close();
} catch (FileNotFoundException e) {
    System.out.println("File not found.");
}

    }
}
