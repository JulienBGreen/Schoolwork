package gui;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundManager {
	
	private int volume = -20;
	
	public void setRelVolume(int volume){
		this.volume = volume;
	}
	
	public void sound(String soundFileName){
		this.makesSound(soundFileName);
	}
	
	private void makesSound(String soundFileName){
		try {
	         URL url = this.getClass().getClassLoader().getResource(soundFileName);
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         Clip clip = AudioSystem.getClip();
	         clip.open(audioIn);
	         
	         FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		     gainControl.setValue(volume);
	         
	         clip.start();
	   } catch(Exception e){}
	}

}
