
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SystemInfo {
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


    public static void main(String[] args) throws Exception {
        String line;
        String[] options = {
    "CPU","GPU","Motherboard","RAM","OS Info","Java Info","Boot Time","All","Exit"
};
        
        
        String java=System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osarch= System.getProperty("os.arch");
        long  totalMem=Runtime.getRuntime().totalMemory();
        long freeMem=Runtime.getRuntime().freeMemory();
        Process bootTime = Runtime.getRuntime().exec("wmic os get LastBootUpTime");
        BufferedReader bootReader = new BufferedReader(new InputStreamReader(bootTime.getInputStream()));
        Scanner inputusr = new Scanner(System.in);

        while (true) {
        System.out.println("Available components are:");
        for (int i = 0; i < options.length; i++) {
        System.out.println((i + 1) + ". " + options[i]);
    }

        System.out.println("Enter your choice:");
        String choice = inputusr.nextLine().trim();
        switch(choice){
            case "1":
            case "CPU":
            Process cpu=Runtime.getRuntime().exec("wmic cpu get Name");
            readIO(cpu, "CPU",true);
            int cores=Runtime.getRuntime().availableProcessors();
            System.out.println("Available cores: "+cores);
            cpu.waitFor();
            break;
            case "2":
            case "GPU":
            Process gpu=Runtime.getRuntime().exec("wmic path win32_VideoController get name");
            readIO(gpu, "GPU",true);
            gpu.waitFor();
            break;
            case "3":
            case "Motherboard":
            Process motherboard = Runtime.getRuntime().exec("wmic baseboard get Manufacturer, Product");
       BufferedReader mothReader = new BufferedReader(new InputStreamReader(motherboard.getInputStream()));    
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
        motherboard.waitFor();
        break;
         
        case "4":
        case "RAM":
        Process ramreader= Runtime.getRuntime().exec("wmic computersystem get TotalPhysicalMemory");
         BufferedReader ramR=new BufferedReader(new InputStreamReader(ramreader.getInputStream()));
         while ((line = ramR.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && line.matches("\\d+")) { 
        long ramBytes = Long.parseLong(line);
        System.out.println("Total Physical RAM (GB): " + (ramBytes / (1024 * 1024 * 1024)));
    }
}
        ramR.close();
        ramreader.waitFor();
        break;
        case "5":
        case "OS Info":
         System.out.println("Operating System: " + osName);
         System.out.println("OS arch: "+osarch);
         break;
         case "6":
         case "Java Info":
        System.out.println("Java version is: "+java);
       
        System.out.println("Total JVM Memory (MB): " + (totalMem / (1024 * 1024)));
       
        System.out.println("Free available Memory (MB): "+ (freeMem/(1024*1024)));
        break;
        case "7":
        case "Boot Time":
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
        bootTime.waitFor();
        break;
        case "8":
        case "All":
        Process cpuAll = Runtime.getRuntime().exec("wmic cpu get Name");
        readIO(cpuAll, "CPU", true);
        Process gpuAll = Runtime.getRuntime().exec("wmic path win32_VideoController get name");
        readIO(gpuAll, "GPU", true);

        Process motherboardAll = Runtime.getRuntime().exec("wmic baseboard get Manufacturer, Product");
        BufferedReader mbReader = new BufferedReader(new InputStreamReader(motherboardAll.getInputStream()));
    while ((line = mbReader.readLine()) != null) {
        if (!line.toLowerCase().contains("manufacturer") && !line.trim().isEmpty()) {
            String[] parts = line.trim().split("\\s{2,}");
            if (parts.length >= 2) {
                System.out.println("Motherboard Manufacturer: " + parts[0]);
                System.out.println("Motherboard Product: " + parts[1]);
            }
        }
    }
    mbReader.close();
    motherboardAll.waitFor();
    Process ramAll = Runtime.getRuntime().exec("wmic computersystem get TotalPhysicalMemory");
    BufferedReader ramReader = new BufferedReader(new InputStreamReader(ramAll.getInputStream()));
    while ((line = ramReader.readLine()) != null) {
        line = line.trim();
        if (!line.isEmpty() && line.matches("\\d+")) {
            long ramBytes = Long.parseLong(line);
            System.out.println("Total Physical RAM (GB): " + (ramBytes / (1024 * 1024 * 1024)));
        }
    }
    ramReader.close();
    System.out.println("Operating System: " + osName);
    System.out.println("OS Architecture: " + osarch);
    System.out.println("Java version is: " + java);
    System.out.println("Total JVM Memory (MB): " + (totalMem / (1024 * 1024)));
    System.out.println("Free available Memory (MB): " + (freeMem / (1024 * 1024)));
        
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
        bootTime.waitFor();
        break;
        case "9":
        case "Exit":
        System.out.println("Exiting the program...");
        inputusr.close();
        System.exit(0);
        break;
        default:
                System.out.println("Invalid input. Please try again.");






        }
        System.out.println();
}
    

}

}
