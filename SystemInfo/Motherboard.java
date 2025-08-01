public class Motherboard implements SystemComponent{
    @Override
    public void displayInfo() throws Exception{
         Process motherboard = Runtime.getRuntime().exec("wmic baseboard get Manufacturer, Product");
         SysUtil.readMotherboard(motherboard);
        motherboard.waitFor();
       
}
}
