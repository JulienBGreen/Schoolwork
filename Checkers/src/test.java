import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class test {
	public void main(String[] args){
		Server.main(null);
		try {
			new ClientThread(new Socket("64.89.144.100", 32007));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
