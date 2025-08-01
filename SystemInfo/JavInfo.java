public class JavInfo implements SystemComponent {
   @Override
   public void displayInfo(){
    String java=System.getProperty("java.version");
    long  totalMem=Runtime.getRuntime().totalMemory();
    long freeMem=Runtime.getRuntime().freeMemory();
    System.out.println("Java version is: "+java);   
    System.out.println("Total JVM Memory (MB): " + (totalMem / (1024 * 1024)));   
    System.out.println("Free available Memory (MB): "+ (freeMem/(1024*1024)));
   } 
    
}
