public class All implements SystemComponent {
    @Override
    public void displayInfo() throws Exception{
        new CPU().displayInfo();
        new GPU().displayInfo();
        new RAM().displayInfo();
        new DiskDrive().displayInfo();
        new OsInfo().displayInfo();
        new Boot().displayInfo();
        new JavInfo().displayInfo();
        new Motherboard().displayInfo();
        
    }
    
}
