import java.util.Scanner;
//Class
public class  frac{
    int num1, num2;
    int den1, den2;
    //Constructor
    public frac(int num1, int num2,int den1, int den2){
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
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        //Taking input from user, instead of hardcoded values.
        System.out.println("Please enter the first Numeretor: ");
        int num1=input.nextInt();
        System.out.println("Please enter the first Denominator: ");
        int den1=input.nextInt();
        System.out.println("Please enter the Second Numeretor: ");
        int num2=input.nextInt();
        System.out.println("Please enter the Second Denominator: ");
        int den2=input.nextInt();

        //Object creation for fractions
        frac Fraction=new frac(num1,num2,den1,den2);
        String result=Fraction.multiply();
        System.out.println("The answer is: "+result);
        
    }

}