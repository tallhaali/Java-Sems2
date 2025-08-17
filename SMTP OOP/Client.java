import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
       Chat client=new Chat("localhost",9090);
       client.start();
    }
}
