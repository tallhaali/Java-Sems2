import java.util.Scanner;
public class SystemInfo {
public static void main(String[] args) throws Exception {
String[] options = {"CPU","GPU","RAM","Motherboard","OS Info","Java Info","Boot Time","All","Exit"};
Scanner inputusr = new Scanner(System.in);
while (true) {
System.out.println("Available components are:");
for (int i = 0; i < options.length; i++) {
System.out.println((i + 1) + ". " + options[i]);
    }
System.out.println("Enter your choice:");
String choice = inputusr.nextLine().trim();
SystemComponent component=null;
switch(choice){
    case "1":
    case "CPU":
    component = new CPU();
    break;
    case "2":
    case "GPU":
    component =new GPU();
    break;
    case "3":
    case "RAM":
    component =new RAM();
    break;
    case "4":
    case "Motherboard":
    component = new Motherboard();
    break;
    case "5":
    case "OS Info":
    component = new OsInfo();
    break;
    case "6":
    case "Java Info":
    component =new JavInfo();
    break;
    case "7":
    case "Boot Time":
    component = new Boot();
    break;
    case "8":
    case "All":
    component = new All();
    break;
    case "9":
    case "Exit":
    System.out.println("Exiting the program...");
    System.exit(0);
    break;
    default:
    System.out.println("Invalid choice");
    break;
}
if(component!=null){
    component.displayInfo();
    System.out.println();
}
}
    }
}
