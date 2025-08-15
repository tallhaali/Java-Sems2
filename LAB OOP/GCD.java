import java.util.Scanner; 
public class GCD {
    public static int gcd(int n, int m){
        if( m==0)
        return n;
        else 
        return gcd(m, n%m);

    }

public static void main(String[] args) {
    Scanner input=new Scanner(System.in);
    System.out.println("Please enter two number1: ");
    int n=input.nextInt();
    System.out.println("Enter second number: ");
    int m=input.nextInt();
    System.out.println("GCD is: "+gcd(n,m));
input.close();
}
    
}
