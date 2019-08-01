/* Timer class by Julien Green, utilizing the timer from Java.util this creates and runs a basic timer 
 * last modified 10/8/15
 */

package timer;
import java.util.*;

public class Timer{
	
	private long initialTime; //intial time that the timer is created to count down from and to 0
	private boolean running; //if the timer is currently counting down
	private long timeLeft;
	private TimerTask task;
	private java.util.Timer globalTimer;
	
	/** Constructs Timer with initial time of initialTime, must be in milliseconds */
	public Timer(long initialTime){
		this.initialTime = initialTime;
		this.timeLeft = initialTime;
		globalTimer = new java.util.Timer();
		running = false;
		 
		task = new TimerTask(){ 
			@Override
			public void run()
			{
				if(running){
					timeLeft--;
					if(timeLeft == 0)
						System.out.println("Timer action!");
				}
			}
		};
		
		globalTimer.schedule(task, 0, 1);
	}

	/** Method called by the String executing this Timer */
	public void run() {
		running = true;
	}

	/** Method that returns the time that the timer started at*/
	public long getinitialTime(){
		return this.initialTime;
	}

	/** Returns the time that is still remaining */
	public long getTimeRemaining(){
		return timeLeft;
	}
	
	/** Pauses Timer */
	public void pause(){
		running = false;
		
	}
	
	/** Returns whether the timer has reached 0 */
	public boolean timeOut(){
		return timeLeft == 0;
	}
	
	/** Resets the timer to be counting down from time milliseconds to 0 */
	public void resetTimer(long time){
		this.initialTime = time;
		this.timeLeft = time;
		globalTimer = new java.util.Timer();
		running = false;
	}
	
	/** Stop the Timer */
	public void stop() {
		running =  false;
	}
	
	/** Returns whether the state of this object is okay */
	protected boolean repOk(){
		return(globalTimer != null && timeLeft >= 0 && initialTime >= 0);
	}
}

