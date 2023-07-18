import java.io.BufferedReader;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileReader;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

class Meter {
    String honorific, firstName, secondName, tenantMeterNumber;
    int currentMeterReading, previousMeterReading;
    Date currentMeterReadingDate, previousMeterReadingDate;

    public Meter(String[] domain) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        honorific = domain[0].trim();
        firstName = domain[1].trim();
        secondName = domain[2].trim();
        tenantMeterNumber = domain[4].trim();
        currentMeterReading = Integer.parseInt(domain[5].trim());
        currentMeterReadingDate = sdf.parse(domain[6].trim());
        previousMeterReading = Integer.parseInt(domain[7].trim());
        previousMeterReadingDate = sdf.parse(domain[8].trim());
    }

    public String toString() {
        return String.format("%s %s %s %d %s",
                firstName, secondName, tenantMeterNumber, currentMeterReading, currentMeterReadingDate);
    }
}

public class MeterSearch {
    public void search() {
        MeterSearch main = new MeterSearch();
        List<Meter> meters = main.loadMeters();
        main.sortMeters(meters);

        // Generate 10,000 random meter numbers
        List<String> meterNumbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            meterNumbers.add("m" + (random.nextInt(999999) + 100000));
        }
        System.out.println("Check [0]"+meters.get(0));
        System.out.println("Check [9]"+meters.get(9));
        System.out.println("Last []"+meters.get(meters.size() - 1)); 
        System.out.println("------------------------------------------------------------------------------------"); 
        
        long startTime = System.currentTimeMillis();
        for (String meterNumber : meterNumbers) {
            main.findMeterSequential(meters, meterNumber);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Find sequential: " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        for (String meterNumber : meterNumbers) {
            main.findMeterBinary(meters, meterNumber);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Find Binary: " + (endTime - startTime) + " milliseconds");

    }

    public List<Meter> loadMeters() {
        List<Meter> meters = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Prod_Meter.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/")) continue;
                String[] domain = line.split(",");
                if (domain.length == 9) {
                    meters.add(new Meter(domain));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return meters;
    }

    public void sortMeters(List<Meter> meters) {//sorting algorithm
        for (int i = 0; i < meters.size() - 1; i++) {
            for (int j = i + 1; j < meters.size(); j++) {
                if (meters.get(i).tenantMeterNumber.compareTo(meters.get(j).tenantMeterNumber) > 0) {
                    Meter temp = meters.get(i);
                    meters.set(i, meters.get(j));
                    meters.set(j, temp);
                }
            }
        }
    }

    public Meter findMeterSequential(List<Meter> meters, String meterNumber) {//sequential search
        for (Meter meter : meters) {
            if (meter.tenantMeterNumber.equals(meterNumber)) {
                return meter;
            }
        }
        return null;
    }

    public Meter findMeterBinary(List<Meter> meters, String meterNumber) {//binary search
        int left = 0;
        int right = meters.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = meters.get(mid).tenantMeterNumber.compareTo(meterNumber);
            if (comparison == 0) {
                return meters.get(mid);
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}