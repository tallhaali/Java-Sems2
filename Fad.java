
import java.util.Scanner;
//Class
public class  Fad{
    int num1, num2;
    int den1, den2;
    //Constructor
    public Fad(int num1, int num2,int den1, int den2){
        this.num1=num1;
        this.num2=num2;
        this.den1=den1;
        this.den2=den2;
    }
    //Method that returns a value as fraction through the use of string and+ 
    public String multiply(){
        int numer=num1*num2;
        int deno= den1*den2;
        return (numer+"/"+deno);
    }
    //Additon method
    public String add(){
        int adnum1=num1*den2;
        int adnum2=num2*den1;
        int finalnum= adnum1 + adnum2;
        int finalden=den1*den2;
        return finalnum +"/"+ finalden;

    }
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        //Taking input from user, instead of hardcoded values.
        System.out.println("Please enter the first Numerator: ");
        int num1=input.nextInt();
        System.out.println("Please enter the first Denominator: ");
        int den1=input.nextInt();
        System.out.println("Please enter the Second Numerator: ");
        int num2=input.nextInt();
        System.out.println("Please enter the Second Denominator: ");
        int den2=input.nextInt();
        input.nextLine();
        System.out.println("What would you like to do? Add or multiply?");
        String choice=input.nextLine();
        input.close();
        String result;
        Fad Fraction=new Fad(num1,num2,den1,den2);
        
        switch(choice.toLowerCase()){
        case "add":
        result =Fraction.add();
        break;
        case "multiply":
        result=Fraction.multiply();
        break;
        default:
          result= "invalid Choice";
}
        //Object creation for fractions
        
                System.out.println("The answer is: "+result);

    }

}