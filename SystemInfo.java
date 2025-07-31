
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemInfo {

    public static void main(String[] args) throws Exception {
        // Example 1: Getting the Operating System name
        String osName = System.getProperty("os.name");
        System.out.println("Operating System: " + osName);
        String osarch= System.getProperty("os.arch");
        System.out.println("OS arch: "+osarch);
        String java=System.getProperty("java.version");
        System.out.println("Java version is: "+java);
        int cores=Runtime.getRuntime().availableProcessors();
        System.out.println("Available cores: "+cores);
        long  totalMem=Runtime.getRuntime().totalMemory();
        System.out.println("Total JVM Memory (MB): " + (totalMem / (1024 * 1024)));
        long freeMem=Runtime.getRuntime().freeMemory();
        System.out.println("Free available Memory (MB): "+ (freeMem/(1024*1024)));
        Process process=Runtime.getRuntime().exec("wmic cpu get Name");
        process.getInputStream();
        BufferedReader input= new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while((line=input.readLine())!=null){
            System.out.print(" "+line.trim());
        }  
        input.close();
        System.out.println();

        Process gpu=Runtime.getRuntime().exec("wmic path win32_VideoController get name");
        gpu.getInputStream();
        BufferedReader gpureader= new BufferedReader(new InputStreamReader(gpu.getInputStream()));
        
        while((line=gpureader.readLine())!=null){
            System.out.print(" "+line.trim());
        }     
        System.out.println();

       Process motherboard = Runtime.getRuntime().exec("wmic baseboard get Manufacturer, Product");
       BufferedReader mothReader = new BufferedReader(new InputStreamReader(motherboard.getInputStream()));    
       while ((line = mothReader.readLine()) != null) {
       if (!line.toLowerCase().contains("manufacturer") && !line.trim().isEmpty()) {
        String[] parts = line.trim().split("\\s{2,}"); // split by 2+ spaces
        if (parts.length >= 2) {
            String manufacturer = parts[0];
            String product = parts[1];
            System.out.println("Motherboard Manufacturer: " + manufacturer);
            System.out.println("Motherboard Product: " + product);
        }
    }
    }
    mothReader.close();
         Process ramreader= Runtime.getRuntime().exec("wmic computersystem get TotalPhysicalMemory");
         BufferedReader ramR=new BufferedReader(new InputStreamReader(ramreader.getInputStream()));
         while ((line = ramR.readLine()) != null) {
    line = line.trim();
    if (!line.isEmpty() && line.matches("\\d+")) { // only parse lines with digits
        long ramBytes = Long.parseLong(line);
        System.out.println("Total Physical RAM (GB): " + (ramBytes / (1024 * 1024 * 1024)));
    }
}
ramR.close();

Process bootTime = Runtime.getRuntime().exec("wmic os get LastBootUpTime");
BufferedReader bootReader = new BufferedReader(new InputStreamReader(bootTime.getInputStream()));
while ((line = bootReader.readLine()) != null) {
    line = line.trim();
    if (!line.isEmpty() && line.matches("\\d+\\.\\d+\\+\\d+")) {
        // Parse WMIC time format: YYYYMMDDHHMMSS
        String bootTimeRaw = line.split("\\.")[0]; // Remove fractional seconds
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
    bootTime.waitFor();
        process.waitFor();
        gpu.waitFor();
        ramreader.waitFor();
        motherboard.waitFor();





}
}
