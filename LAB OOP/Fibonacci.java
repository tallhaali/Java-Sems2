import java.util.Scanner;

public class Fibonacci{

    public static int fib(int n){
    if (n==0)
        return 0;
        else if(n==1)
        return 1;

        else
            return fib(n-1)+fib(n-2);
    }
        
    public static void main(String[] args) {
        Scanner number = new Scanner(System.in);
        System.out.println("Enter the number");
        int num=number.nextInt();
        System.out.println("You entered: "+num);
        if(num<0)
        System.out.println("Please enter a positive number");
        else{
        for(int i=0; i<num; i++){
        System.out.print(fib(i)+" ");
        }
        System.out.println("");
        System.out.println("The nth Fibonacci is: "+fib(num-1));
    }
        


number.close();
}
}
