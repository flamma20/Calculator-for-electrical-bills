import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class electricz {
    private static final String FLATS_FILE = "Prod_Flat.txt";
    private static final double RATE = 0.205;

    public void bcbill() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input a street number: ");
        int streetNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Input a street name: ");
        String streetName = scanner.nextLine().trim().toLowerCase();

        try (BufferedReader br = new BufferedReader(new FileReader(FLATS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.isBlank()) {
                    String[] fields = line.split(",");
                    if (fields.length >= 8) {
                        String flatStreetName = fields[0].trim().toLowerCase();
                        int flatStreetNumber = Integer.parseInt(fields[1].trim());
                        if (flatStreetName.equals(streetName) && flatStreetNumber == streetNumber) {
                            int currentReading = Integer.parseInt(fields[3].trim());
                            int previousReading = Integer.parseInt(fields[5].trim());
                            int usage = currentReading - previousReading;
                            double bill = usage * RATE;

                            System.out.println("Showing bill for " + streetNumber + " " + streetName);
                            System.out.println("-----------------------------------------------------------");
                            System.out.println("Current meter reading   " + currentReading + "    " + fields[4].trim());
                            System.out.println("Previous meter reading  " + previousReading + "   " + fields[6].trim());
                            System.out.println("Usage                                 " + usage);
                            System.out.println("Rate                                    $" + RATE + "/kwh");
                            System.out.printf("Bill Usage                               $%,.2f%n", bill);

                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading flats file: " + e.getMessage());
        }

        scanner.close();
    }

    public void allBcbills() {
        int recordsProcessed = 0;
        double total = 0;

        try (Scanner scanner = new Scanner(new File(FLATS_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.startsWith("/") && !line.isBlank()) {
                    String[] fields = line.split(",");
                    if (fields.length >= 8) {
                        int currentReading = Integer.parseInt(fields[3].trim());
                        int previousReading = Integer.parseInt(fields[5].trim());
                        int consumption = currentReading - previousReading;
                        double bill = consumption * RATE;
                        total += bill;
                        recordsProcessed++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FLATS_FILE);
            System.exit(1);
        }

        System.out.println("Total for all flats");
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("Total: $%,.2f%n", total);
        System.out.println("Records processed: " + recordsProcessed);
    }
}