
import java.util.Scanner;


public class first {
    public static void main(String[] args) {
        String t= "talha";
        float [][]sales={
            {530,1035,304,220,258,546},
            {820,945,192,60,0,966},
            {110,1320,568,200,96,840},
            {660,270,392,230,90,1386},
          };
          String[]RepID ={"E011","E012","E013","E014"};
          String[]Products={"Product-A","Product-B","Product-C","Product-D","Product-E","Product-F"};
        System.out.println("Please choose");
        System.out.println("1 RepID");
        System.out.print("2 Products"+":"+" ");
        Scanner inputScanner = new Scanner(System.in); 
        int user=inputScanner.nextInt();
        if (user==1){
            System.out.println("Available RepIDs are:");
            for(int i=0; i<RepID.length;i++){
                System.out.println(i+1 +":"+ " " + RepID[i]);
                
                 }
            System.out.println(" ");
            System.out.print("Please select an ID from above: ");
            int selec=inputScanner.nextInt();
         //looping to find the index of the ID that user has entered!!
        /* int tar=-1;
         for(int i=0; i<RepID.length; i++){
            if(selec.equals(RepID[i])){
                tar= i;
                break;
            }
            
         }*/

         int tar=selec -1;
         int totalsales=0;
         for(int i=0; i<sales[tar].length; i++){

            totalsales += sales[tar][i];
            System.out.println(Products[i]+" "+"$"+sales[tar][i]);


         }

         System.out.println("Total Sales: $" + totalsales);



        }
            else if(user==2){
                System.out.println("Available Products are:");
                for(int i=0; i<Products.length;i++){
                    System.out.println(i + 1 +":" + " " +Products[i]);
                }
                int selec=inputScanner.nextInt();
                int tar=selec -1;
                int totalsales=0;
                for(int i=0; i<sales.length; i++){
                    
    
                    totalsales += sales[i][tar];
                    System.out.println(RepID[i]+" "+"$"+sales[i][tar]);
            }
            System.out.println("Total Sale of "+Products[tar]+ " is "+"$"+totalsales);
        }
            else {
                System.out.println("Invalid choice.");
            }
          
                
             
             

        inputScanner.close();
 
    }
}
