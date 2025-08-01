public class CPU implements SystemComponent{
    @Override
    public void displayInfo() throws Exception{
    Process cpu=Runtime.getRuntime().exec("wmic cpu get Name");
            SysUtil.readIO(cpu, "CPU",true);
            int cores=Runtime.getRuntime().availableProcessors();
            System.out.println("Available cores: "+cores);
            cpu.waitFor();
    }
}
