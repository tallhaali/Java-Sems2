public class RAM implements SystemComponent {
    @Override
    public void displayInfo() throws Exception{
        Process ramreader= Runtime.getRuntime().exec("wmic computersystem get TotalPhysicalMemory");
        SysUtil.readRAM(ramreader);
        ramreader.waitFor();
        
    }
    
}
