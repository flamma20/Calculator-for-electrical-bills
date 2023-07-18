import java.util.Scanner;
import java.io.IOException;

public class Menu
{
    public static void main(String[] args) throws Exception {

    {
        checksum check = new checksum(); 
        read reader = new read(); 
        electricz allBc = new electricz();
        electricz bcCalculator = new electricz(); 
        sortz sorted = new sortz(); 
        MeterSearch searches = new MeterSearch();
        tenantBillz tenantCalc = new tenantBillz(); 
        Fullbillz allTen = new Fullbillz();
        String option =""; 
        System.out.println("ST2 Assignment One by Ahmed Saeed"); 
        Scanner scanner = new Scanner(System.in); 
        
        while (!option.equalsIgnoreCase("E")) { //while loop will execute as long as option is not E
            System.out.println("Select an option:");
            System.out.println("Option E - Exit program");
            System.out.println("Option M – Read meter file(Task 1)");
            System.out.println("Option F – Read flats file (Task 1)");
            System.out.println("Option C – Compute BC bill for 1 flat (Task 2)");
            System.out.println("Option A – Compute BC bill for all flats (Task 2)"); 
            System.out.println("Option S – Sort meter file into meter order (Task 3)");
            System.out.println("Option P – Prove meter file sort and find (Task 3)");
            System.out.println("Option O – Compute full bill for one flat(Task 4)");
            System.out.println("Option Z – Compute full bill for all flats(Task 5)");
            
            option = scanner.nextLine().toUpperCase();
            
            switch(option) {
                case "E":
                    System.out.println("Exiting program...");
                    break;
                case "M":
                    System.out.println("Reading meter file..."); 
                  reader.read2();  
                  check.checksum();
                break;
                case "F": 
                    System.out.println("Reading Flats file..."); 
                    reader.read();
                    check.checksum2();
                    break;
                case "C":
                    System.out.println("Computing BC bill for 1 flat...");
                    bcCalculator.bcbill();
                    break;
                case "A":
                    System.out.println("Computing BC bill for all flats (total)...");
                    allBc.allBcbills();
                    break; 
                case "S":
                    System.out.println("Sorting meter file into meter order...");
                    sorted.sorter();
                    break; 
                case "P":
                    System.out.println("proving meter file sort and find...");
                    searches.search();
                    break;
                case "O":
                    System.out.println("Computing full bill for one flat...");
                    tenantCalc.oneten();
                    break;
                case "Z":
                    System.out.println("adjusted bill for all block of flats...");
                    allTen.everybill();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
            
            System.out.println(); 
        }
    
    }
} 
}
