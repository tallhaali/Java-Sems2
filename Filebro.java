import java.awt.Desktop;
import java.io.File;

public class Filebro {
    public static void main(String[] args) {
        // try{
 File myFile= new File("output.html");
Launchfile("output.html");

    }



    
    public static void Launchfile(String path){
       try{
        File file = new File(path);
        Desktop.getDesktop().browse(file.toURI());
    }
    catch (Exception e){
        System.out.println("File not found");
    }
    }
}