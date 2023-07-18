import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
class Tenant {
    String honorific, firstName, lastName, meterNumber;
    int currentReading, previousReading;
}

class Flat {
    String street, buildingMeter;
    int buildingNumber, currentReading, previousReading;
    List<String> tenantMeters;
}

public class tenantBillz {
    private static final String FLATS_FILE = "prod_Flat.txt";//flats file
    private static final String METERS_FILE = "prod_Meter.txt";//meters file
    private static final double RATE = 0.205;//the rate is always constant

    public  void oneten() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Compute Adjusted bill for one Block of flats");
        System.out.print("Enter street Number: ");
        int streetNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter street Name: ");
        String streetName = scanner.nextLine();

        List<Flat> flats = readFlatsFile(FLATS_FILE);
        List<Tenant> tenants = readMetersFile(METERS_FILE);
        Flat requestedFlat = findFlat(flats, streetNumber, streetName);

        if (requestedFlat != null) {
            calculateAndPrintBill(requestedFlat, tenants);
        } else {
            System.out.println("Flat not found");
        }
    }

    private static List<Flat> readFlatsFile(String fileName) {
        List<Flat> flats = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/")) continue;

                String[] fields = line.split(",");
                if (fields.length < 8) continue;

                Flat flat = new Flat();
                flat.street = fields[0].trim();
                flat.buildingNumber = Integer.parseInt(fields[1].trim());
                flat.buildingMeter = fields[2].trim();
                flat.currentReading = Integer.parseInt(fields[3].trim());
                flat.previousReading = Integer.parseInt(fields[5].trim());
                
                flat.tenantMeters = new ArrayList<>();
                for (int i = 7; i < fields.length; i++) {
                    if (!fields[i].trim().isEmpty()) {
                        flat.tenantMeters.add(fields[i].trim());
                    }
                }

                flats.add(flat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flats;
    }

    private static List<Tenant> readMetersFile(String fileName) {
        List<Tenant> tenants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/")) continue;

                String[] fields = line.split(",");
                if (fields.length != 9) continue;

                Tenant tenant = new Tenant();
                tenant.honorific = fields[0].trim();
                tenant.firstName = fields[1].trim();
                tenant.lastName = fields[2].trim();
                tenant.meterNumber = fields[4].trim();
                tenant.currentReading = Integer.parseInt(fields[5].trim());
                tenant.previousReading = Integer.parseInt(fields[7].trim());
                
                
                tenants.add(tenant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tenants;
    }

    private static Flat findFlat(List<Flat> flats, int streetNumber, String streetName) {
        for (Flat flat : flats) {
            if (flat.buildingNumber == streetNumber && flat.street.equalsIgnoreCase(streetName)) {
                return flat;
            }
        }
        return null;
    }

    private static void calculateAndPrintBill(Flat flat, List<Tenant> tenants) {
        int totalUsage = flat.currentReading - flat.previousReading;
        double billUsage = totalUsage * RATE;

        List<Tenant> flatTenants = new ArrayList<>(); 

        for (String meter : flat.tenantMeters) {
            for (Tenant tenant : tenants) {
                if (tenant.meterNumber.equals(meter)) {
                    flatTenants.add(tenant); }}}
                    
        double totalTenantUsage = 0;
    for (Tenant tenant : flatTenants) {
        int tenantUsage = tenant.currentReading - tenant.previousReading;
        totalTenantUsage += tenantUsage;
    }
    double adjustedBill = billUsage - (totalTenantUsage * RATE); 
    System.out.println("");
    System.out.println("Showing bill for " + flat.buildingNumber +" "+ flat.street ); 
    System.out.println("-----------------------------------------------------------------");
    System.out.println("Current meter reading: "+flat.currentReading); 
    System.out.println("Previous meter reading: "+flat.previousReading);
    
    System.out.printf("Total usage: %d%n", totalUsage); 
    System.out.println("Rate: "+ RATE); 
    System.out.printf("Bill Usage:  $%.2f%n", billUsage); 
    System.out.println("");

    System.out.println("Tenant           meter   curr   prev usage  pcnt%  $base  $adj  $total" +"\n"+ "---------------------------------------------------------------------------------");
      
    for (String meter : flat.tenantMeters) {
            for (Tenant tenant : tenants) {
                if (tenant.meterNumber.equals(meter)) { 
                  int uses = tenant.currentReading - tenant.previousReading; 
                  double billz = uses * RATE; 
                  double percent = (billz/(totalTenantUsage * RATE))*100; 
                  double adj = (adjustedBill/100)*percent; 
                  double billz2 = billz + adj;
                
if (adjustedBill < 0) {               
System.out.printf("%s %s %s %s %d %d %d %.2f %.2f $0.00 %.2f%n", tenant.honorific, tenant.firstName, tenant.lastName, tenant.meterNumber, tenant.currentReading, tenant.previousReading, uses, percent, billz, billz);}
else{
System.out.printf("%s %s %s %s %d %d %d %.2f %.2f %.2f %.2f%n", tenant.honorific, tenant.firstName, tenant.lastName, tenant.meterNumber, tenant.currentReading,tenant.previousReading, uses, percent, billz, adj, billz2);
}
                } } }
    
    System.out.println("-----------------------------------------------------------------");
    System.out.printf("Total tenant bills metered %.2f%n", totalTenantUsage * RATE);
    System.out.printf("Total tenant bills diff: %.2f%n", adjustedBill); 
    if (adjustedBill < 0) {
    System.out.printf("Total tenant bills adjusted: %.2f%n",totalTenantUsage * RATE);
    } 
    else {System.out.printf("Total tenant bills adjusted: %.2f%n", billUsage);}
    
} 
}