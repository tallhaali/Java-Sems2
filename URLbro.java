import java.awt.Desktop;
import java.io.*;
import java.net.*;

public class URLbro{
    public static void main(String[] args) throws IOException{
        URL url = new URL("https://www.dawn.com");

        BufferedReader reader = new BufferedReader(new InputStreamReader((url.openStream())));

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.html"));

        String line;
        while((line = reader.readLine())!=null)
        {
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();
        System.out.println("Webpage content saved at output.html");

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
    