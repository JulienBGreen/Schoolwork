package gui;

public class Audio {
	
	private static final Music DEFAULT_TUNE = Music.Default;
	
	private SoundManager soundMan;
	private MusicManager musicMan;
	
	/** Creates new Audio object */
	public Audio(){
		this.soundMan = new SoundManager();
		this.musicMan = new MusicManager();
	}
	
	/** Plays the given sound */
	public void triggerSound(Sound sound){
		soundMan.sound(sound.getPath());
	};
	
	/** Starts Playing the given Music */
	public void playMusic(Music music){
		musicMan.music(music.path);
	}
	
	/** Starts Playing the Default Music */
	public void playMusic(){
		playMusic(DEFAULT_TUNE);
	}
	
	/** Sets the Sound Volume to volume, volume should be between 0 and 1, will be disregarded otherwise */
	public void setSoundVolume(double volume){
		
	}
	
	/** Sets the Music Volume to volume, volume should be between 0 and 1, will be disregarded otherwise */
	public void setMusicVolume(double volume){
		
	}
	
	public static enum Sound{
		Move("testSound.wav");
		private String path;
		private Sound(String path){
			this.path = path;
		}
		public String getPath(){
			return path;
		}
	}
	
	public static enum Music{
		Default("testMusic.wav");
		private String path;
		private Music(String path){
			this.path = path;
		}
		public String getPath(){
			return path;
		}
	}
	
	/** Returns whether the state of this object is okay */
	protected boolean repOk(){
		return true;
	}
	
}
