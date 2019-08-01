
public class DotThread extends Thread{
	
	private int interval;
	private boolean stopNow;
	
	public DotThread(int interval){
		this.interval = interval;
	}
	
	public DotThread(){
		this(1000);
	}
	
	public void run(){
		stopNow = false;
		while (!stopNow){	
			try {
				Thread.sleep(interval);
				System.out.print(".");
			}
			catch (InterruptedException e){
				System.out.println("Thread interrupted");
			}
		}
	}
	
	// setStopNow():  Sets stopNow to be true
	//
	public void setStopNow(){
		stopNow = true;
	}
	
}
