import java.net.Socket;
import java.util.Date;
import java.io.*;

public class ThreadedDateHandler extends Thread {
    Socket connection;
    
    public ThreadedDateHandler(Socket s) {
        connection = s;
    }
    
    public void run() {
        sendDate(connection);
    }
    
       /**
     * The parameter, client, is a socket that is already connected to another 
     * program.  Get an output stream for the connection, send the current time, 
     * and close the connection.
     */
    private static void sendDate(Socket client) {
        try {
            System.out.println("Connection from " +  
                    client.getInetAddress().toString() );
            Date now = new Date();  // The current date and time.
            
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter outgoing;   // Stream for sending data.
            outgoing = new PrintWriter(client.getOutputStream(), true );
            
            outgoing.println("Standard format?");
            outgoing.flush();  // Make sure the data is actually sent!
            String opt = in.readLine();
                
            if (opt.startsWith("y")) {
                outgoing.println( now.toString() );
            } else {
                outgoing.println("TOMORROW");
            }
            outgoing.flush();  // Make sure the data is actually sent!
            client.close();
        }
        catch (Exception e){
            System.out.println("Error: " + e);
        }
    } // end sendDate()
}