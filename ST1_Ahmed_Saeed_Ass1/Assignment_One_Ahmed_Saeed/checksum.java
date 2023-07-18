import java.io.*; 
import java.util.zip.*; 
import java.text.DecimalFormat;




public class checksum  
{
    
    public void checksum() throws Exception
    {
      String fileName = "prod_Meter.txt";
    String line;
    int currentReadingsTotal = 0;

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      while ((line = bufferedReader.readLine()) != null) { 
        String[] fields = line.split(",");
        if  (!line.startsWith("/") && !line.trim().isEmpty() && fields.length == 9) {
          
          currentReadingsTotal += Integer.parseInt(fields[5].trim());
        }
      }
      System.out.println("Checksum: " + currentReadingsTotal);
    } catch (IOException e) {
      System.err.format("IOException: %s%n", e);
    }
      DecimalFormat formatter = new DecimalFormat("0.#######E0");
      String scientificNotation = formatter.format(currentReadingsTotal);
      System.out.println("Checksum: "+scientificNotation);//print in scientific notation
       
} 

 public void checksum2() throws Exception
    {
      String fileName = "prod_Flat.txt";
      String line;
    int currentReadingsTotal = 0;

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      while ((line = bufferedReader.readLine()) != null) { 
        String[] fields = line.split(",");
        if  (!line.startsWith("/") && !line.trim().isEmpty() && fields.length >= 8) {
          
          currentReadingsTotal += Integer.parseInt(fields[3].trim());
        }
      }
      System.out.println("Checksum: " + currentReadingsTotal);
    } catch (IOException e) {
      System.err.format("IOException: %s%n", e);
    }
      
      DecimalFormat formatter = new DecimalFormat("0.#######E0");
      String scientificNotation = formatter.format(currentReadingsTotal);
      System.out.println("Checksum: "+scientificNotation);//print in scientific notation
      
}
}