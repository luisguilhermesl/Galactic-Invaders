package main;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	private Clip clip;
	
	public static final Sound shoot = new Sound ("laserShoot.wav");
	public static final Sound exp_enemys = new Sound ("explosion_enemys.wav");
	public static final Sound exp_boss = new Sound ("explosion_boss.wav");
	public static final Sound theme_music = new Sound ("theme-8bit-techno.wav");
	
	private Sound(String name) {
		try {
			File file = new File("res/"+name);
			//URL url = Sound.class.getResource(name);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			
		} catch (Exception e) {
			System.out.println("ERRO FATAL NO SOM "+name);
			e.printStackTrace();
		}
	}
	
	public void play() {
		try {
			if(clip != null) {
				new Thread() {
					public void run() {
						clip.setFramePosition(0);
						clip.start();
					}
				}.start();
			}
		}catch(Exception e) {}
	}
}
