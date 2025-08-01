public class Boot implements SystemComponent {
    @Override
    public void displayInfo() throws Exception{
        Process timProcess = Runtime.getRuntime().exec("wmic os get LastBootUpTime");
        SysUtil.bootTime(timProcess);
        timProcess.waitFor();
    }
    
}
