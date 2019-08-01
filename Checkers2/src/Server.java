import java.io.*;
import java.net.*;


public class Server implements Runnable{
	
	public static final int PORT = 31000;
	
	public Server(Socket ss) {
		
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket listener;
		Socket connection;
		
		try {
			listener = new ServerSocket(PORT);
			while(true) {
				connection = listener.accept();
				new ServerHandler(connection).start();
				
			}
		}
		catch (Exception e) {
			System.out.println("Sorry, the server has shut down.");
			System.out.println("Error: " + e);
		}
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
