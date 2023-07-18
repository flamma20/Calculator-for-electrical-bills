import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Flat {
    String street;
    int buildingNumber;
    String buildingMeter;
    int currentReading;
    int previousReading;
    List<String> tenantMeters;
}

class Tenant {
    String honorific;
    String firstName;
    String secondName;
    String tenantMeter;
    int currentReading;
    int previousReading;
}

public class Fullbillz {
    private static final double RATE = 0.205;//the rate is always constant

    public void everybill() throws IOException {
        List<Flat> flats = readFlatsFile("prod_Flat.txt");
        List<Tenant> tenants = readMetersFile("prod_Meter.txt");

        Map<String, Tenant> tenantMap = new HashMap<>();
        for (Tenant tenant : tenants) {
            tenantMap.put(tenant.tenantMeter, tenant);
        }

        printAdjustedBills(flats, tenantMap);
    }

    private static List<Flat> readFlatsFile(String filePath) throws IOException {
        List<Flat> flats = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/") || line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 8) {
                    continue;
                }

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
        }
        return flats;
    }

    private static List<Tenant> readMetersFile(String filePath) throws IOException {
        List<Tenant> tenants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/") || line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length != 9) {
                    continue;
                }

                Tenant tenant = new Tenant();
                tenant.honorific = fields[0].trim();
                tenant.firstName = fields[1].trim();
                tenant.secondName = fields[2].trim();
                tenant.tenantMeter = fields[4].trim();
                tenant.currentReading = Integer.parseInt(fields[5].trim());
               
                tenant.previousReading = Integer.parseInt(fields[7].trim());

                tenants.add(tenant);
            }
        }
        return tenants;
    }

    private static void printAdjustedBills(List<Flat> flats, Map<String, Tenant> tenantMap) {
    double totalBcBill = 0;
    double totalDifference = 0;
    double totalDiffAdj = 0;
    double totalTenantBill = 0;

    System.out.println("Flat Address                   BC Bill     Difference    DiffAdj    Tenant Bill");
    System.out.println("--------------------------------------------------------------------------------------");

    for (Flat flat : flats) {
        int totalUsage = flat.currentReading - flat.previousReading;
        double billUsage = totalUsage * RATE;
        double tenantTotalUsage = 0;

        for (String tenantMeter : flat.tenantMeters) {
            Tenant tenant = tenantMap.get(tenantMeter);
            if (tenant != null) {
                int tenantUsage = tenant.currentReading - tenant.previousReading;
                tenantTotalUsage += tenantUsage;
            }
        }

        double adjustedBill = billUsage - (tenantTotalUsage * RATE);
        double flatTotalBillz2 = 0;
        double flatTotalAdj = 0;

        for (String tenantMeter : flat.tenantMeters) {
            Tenant tenant = tenantMap.get(tenantMeter);
            if (tenant != null) {
                int uses = tenant.currentReading - tenant.previousReading;
                double billz = uses * RATE;
                double percent = (billz / (tenantTotalUsage * RATE)) * 100;
                double adj = (adjustedBill / 100) * percent;
                double billz2 = billz + adj;

                flatTotalBillz2 += billz2;
                flatTotalAdj += adj;
            }
        }
        double sub = 0.00; 
        if (adjustedBill < 0){        System.out.printf("%-30s  %.2f$     %.2f$      %.2f$       %.2f$%n", flat.buildingNumber + " " + flat.street, billUsage, sub, flatTotalAdj, flatTotalBillz2);
}
else{        System.out.printf("%-30s  %.2f$     %.2f$      %.2f$       %.2f$%n", flat.buildingNumber + " " + flat.street, billUsage, adjustedBill, flatTotalAdj, flatTotalBillz2);
}

        totalBcBill += billUsage;
        totalDifference += adjustedBill;
        totalDiffAdj += flatTotalAdj;
        totalTenantBill += flatTotalBillz2; 
        
    } 
    

    System.out.println("--------------------------------------------------------------------------------------");
    System.out.printf("                                %.2f$     %.2f$       %.2f$        %.2f$%n", totalBcBill, totalDifference, totalDiffAdj, totalTenantBill);
}
}
