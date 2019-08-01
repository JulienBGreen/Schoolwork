package gui;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class MusicManager {
	
	private int volume = -30;
	
	private Clip clip;
	
	
	public void setRelVolume(int volume){
		this.volume = volume;
	}
	
	public void music(String musicFileName){
		this.makeMusic(musicFileName);
	}
	
	private void makeMusic(String musicFileName){
		try{
			clip.stop();
		}catch(Exception ex){}
		try{
			URL url = this.getClass().getClassLoader().getResource(musicFileName);
	        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	        clip = AudioSystem.getClip();
	        clip.open(audioIn);
	        
	        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        gainControl.setValue(volume);
	        
	        clip.start();
	        clip.setLoopPoints(0, -1);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception ex){System.out.println(ex);}
	}

}
