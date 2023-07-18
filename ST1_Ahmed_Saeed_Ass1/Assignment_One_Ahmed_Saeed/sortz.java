import java.io.BufferedReader; 
import java.util.List; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.FileReader; 
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.Comparator;



class Meter {
    String honorific, firstName, secondName, tenantMeterNumber;
    int currentMeterReading, previousMeterReading;
    Date currentMeterReadingDate, previousMeterReadingDate;

    public Meter(String[] data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        honorific = data[0].trim();
        firstName = data[1].trim();
        secondName = data[2].trim();
        tenantMeterNumber = data[4].trim();
        currentMeterReading = Integer.parseInt(data[5].trim());// string to integer parse
        currentMeterReadingDate = sdf.parse(data[6].trim());//simple date format parse
        previousMeterReading = Integer.parseInt(data[7].trim());
        previousMeterReadingDate = sdf.parse(data[8].trim());
    }
    
    public String toString() {
        return String.format("%s %s %s %d %s",
                firstName,
                secondName,
                tenantMeterNumber,
                currentMeterReading,
                currentMeterReadingDate);
    }
} 

public class sortz {
    public  void sorter() {
        List<Meter> meters = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("prod_Meter.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("/")) continue;
                String[] data = line.split(",");//data split into feilds 
                if (data.length == 9) {
                    meters.add(new Meter(data));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            
        }
        //bubble sort algorithm
        for (int i = 0; i < meters.size() - 1; i++) {
            for (int j = i + 1; j < meters.size(); j++) {
                if (meters.get(i).tenantMeterNumber.compareTo(meters.get(j).tenantMeterNumber) > 0) {
                    Meter temp = meters.get(i);
                    meters.set(i, meters.get(j));
                    meters.set(j, temp);
                }
            }
        }

        
        System.out.println("Check [0]"+meters.get(0));
        System.out.println("Check [9]"+meters.get(9));
        System.out.println("Last []"+meters.get(meters.size() - 1)); 
        System.out.println("------------------------------------------------------------------------------------");
        for (int i = 0; i < meters.size(); i++) {
            System.out.println("Check [" + i + "] " + meters.get(i));//sort and output whole file
        }
        
        
        
        
    } 
    
    
}