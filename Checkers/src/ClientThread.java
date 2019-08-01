	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.swing.JOptionPane;

	/**
	 * Trivial client for the date server.
	 */
	public class ClientThread extends Thread {
		Socket link;
		public ClientThread(Socket link){
			this.link = link;
		}
		
		public void run(){
	        try {
	            System.out.println("Connection from " +  
	                    link.getInetAddress().toString() );
	            
	            BufferedReader in = 
	            		new BufferedReader(
	            				new InputStreamReader(link.getInputStream()));
	            PrintWriter outgoing;   // Stream for sending data.
	            outgoing = new PrintWriter(link.getOutputStream(), true);
	            outgoing.flush();  // Make sure the data is actually sent!
	            String opt = in.readLine();
	            System.out.println("got here");
	            if(opt != null){
	            	outgoing.println("hi!");
	            }
	            outgoing.flush();  // Make sure the data is actually sent!
	            link.close();
	        }
	        catch (Exception e){
	            System.out.println("Error: " + e);
	        }
	    }
	}

