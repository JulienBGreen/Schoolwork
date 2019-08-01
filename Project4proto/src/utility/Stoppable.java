package utility;

public interface Stoppable {
	
	/** Stops the current Object */
	public void stop();
	/** Returns whether the current Object has stopped */
	public boolean hasStopped();
	/** Resets the current Object */
	public void reset();
	
}
