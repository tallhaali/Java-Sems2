public class DiskDrive implements SystemComponent{
    @Override
    public void displayInfo()throws Exception{
    Process Disk=Runtime.getRuntime().exec("wmic diskdrive get Model,Size,InterfaceType,MediaType");
    SysUtil.readDisk(Disk);
    Disk.waitFor();
    }
    
}
