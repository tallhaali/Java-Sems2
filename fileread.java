import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fileread {
    public static void main(String[] args) {
        try{
 File myFile= new File("sales_data.txt");
 Scanner FileScanner= new Scanner(myFile);

while(FileScanner.hasNextLine()){
    String line = FileScanner.nextLine();
    System.out.println(line); 
}
FileScanner.close();
Launchfile("sales_data.txt");
} catch(FileNotFoundException e){
    System.out.println("File not found");


}



    }
    public static void Launchfile(String path){
       try{
        File file = new File(path);
        Desktop.getDesktop().open(file);
    }
    catch (Exception e){
        System.out.println("File not found");
    }
    }
}
