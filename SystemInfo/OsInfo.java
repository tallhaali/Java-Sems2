public class OsInfo implements SystemComponent{
    @Override
    public void displayInfo(){
        String osName = System.getProperty("os.name");
        String osarch= System.getProperty("os.arch");
        System.out.println("Operating System: " + osName);
         System.out.println("OS arch: "+osarch);
    }

    
}
