public class GPU implements SystemComponent{
    @Override
    public void displayInfo() throws Exception{
        Process gpu=Runtime.getRuntime().exec("wmic path win32_VideoController get name");
            SysUtil.readIO(gpu, "GPU",true);
            gpu.waitFor();
    }
    
}
