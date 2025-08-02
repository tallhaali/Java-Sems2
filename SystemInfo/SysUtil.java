import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SysUtil {
    public static BufferedReader getreader(Process process)throws Exception{
    return new BufferedReader(new InputStreamReader(process.getInputStream()));

    }
    public static void readIO(Process process, String label, boolean skipHeader) throws Exception {
        BufferedReader reader= SysUtil.getreader(process);
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
public static void readDisk(Process process) throws Exception { 
    BufferedReader reader = SysUtil.getreader(process);
    String line; 
    boolean headerSkipped = false;

    while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) continue;

        if (!headerSkipped) {
            // Skip header
            headerSkipped = true;
            continue;
        }

        // Split using 2 or more spaces
        String[] parts = line.split("\\s{2,}");

        if (parts.length >= 4) {
            String interfaceType = parts[0];
            String mediaType = parts[1];
            String model = parts[2];
            String sizeStr = parts[3];

                long sizeBytes;
                try {
                    sizeBytes = Long.parseLong(sizeStr);
                } catch (NumberFormatException e) {
                    sizeBytes = 0;
                }

            double sizeGB = sizeBytes / (1024.0 * 1024 * 1024);

            System.out.println("Model         : " + model);
            System.out.println("Interface     : " + interfaceType);
            System.out.println("Media Type    : " + mediaType);
            System.out.printf("Size (GB)     : %.2f GB\n", sizeGB);
            System.out.println("-----------------------------");
        }
    }

    reader.close();
}
 
 public static void readMotherboard(Process process) throws Exception{ 
    BufferedReader reader =  SysUtil.getreader(process);
      String line; 
       while ((line = reader.readLine()) != null) {
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
    reader.close();
    

 }
 public static void readRAM(Process process) throws Exception{
    BufferedReader reader =SysUtil.getreader(process);
    String line;    
         while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && line.matches("\\d+")) { 
        long ramBytes = Long.parseLong(line);
        System.out.println("Total Physical RAM (GB): " + (ramBytes / (1024 * 1024 * 1024)));
    }
}
reader.close();
 }
 public static void bootTime(Process process)throws Exception{
    
        BufferedReader reader =SysUtil.getreader(process);
        String line;
    while ((line = reader.readLine()) != null) {
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
        reader.close();
        
 }
 

    }
