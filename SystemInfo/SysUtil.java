import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SysUtil {
    public static void readIO(Process process, String label, boolean skipHeader) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    boolean headerSkipped = !skipHeader; // if skipHeader is true, then we haven't skipped yet

    while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty()) {
            if (!headerSkipped) {
                // Skip the first valid line
                headerSkipped = true;
                continue;
            }
            System.out.println(label + ": " + line);
        }
    }

    reader.close();
}
 public static void readMotherboard(Process process) throws Exception{
    BufferedReader mothReader = new BufferedReader(new InputStreamReader(process.getInputStream())); 
      String line; 
       while ((line = mothReader.readLine()) != null) {
       if (!line.toLowerCase().contains("manufacturer") && !line.trim().isEmpty()) {
        String[] parts = line.trim().split("\\s{2,}"); 
        if (parts.length >= 2) {
            String manufacturer = parts[0];
            String product = parts[1];
            System.out.println("Motherboard Manufacturer: " + manufacturer);
            System.out.println("Motherboard Product: " + product);
             
        }
    }
    }
    mothReader.close();
    

 }
 public static void readRAM(Process process) throws Exception{
    BufferedReader ramR=new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;    
         while ((line = ramR.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && line.matches("\\d+")) { 
        long ramBytes = Long.parseLong(line);
        System.out.println("Total Physical RAM (GB): " + (ramBytes / (1024 * 1024 * 1024)));
    }
}
ramR.close();
 }
 public static void bootTime(Process process)throws Exception{
    
        BufferedReader bootReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
    while ((line = bootReader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && line.matches("\\d+\\.\\d+\\+\\d+")) {
        String bootTimeRaw = line.split("\\.")[0];
        String year = bootTimeRaw.substring(0, 4);
        String month = bootTimeRaw.substring(4, 6);
        String day = bootTimeRaw.substring(6, 8);
        String hour = bootTimeRaw.substring(8, 10);
        String min = bootTimeRaw.substring(10, 12);
        String sec = bootTimeRaw.substring(12, 14);
        System.out.println("Last Boot Time: " + year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec);
    }
}
        bootReader.close();
        
 }
 

    }
