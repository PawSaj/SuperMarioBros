package com.project.mario.resource_loaders;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Klasa odpowiadaj�ca za odczytanie dzwi�ku oraz jego zarz�dzanie.
 *
 */
public class Sound {
	private Clip clip;

	public Sound(String path) {
		try {
			BufferedInputStream myStream = new BufferedInputStream(new FileInputStream(new File(path)));
			AudioInputStream ais = AudioSystem.getAudioInputStream(myStream);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("odczytywanie dzwieku");
		}
	}

	/**
	 * Metoda w��czaj�ca odtwarzanie dzwi�ku
	 */
	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	/**
	 * Metoda zamykaj�ca otwarty plik dzwieku
	 */
	public void close() {
		stop();
		clip.close();
	}

	/**
	 * Metoda zatrzymuj�ca odtwarzanie dzwi�ku
	 */
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	/**
	 * Metoda zwracajaca czy dzwiek jest odtwarzany
	 * 
	 * @return Informacja czy dzwi�k jest obecnie odtwarzany
	 */
	public boolean isRunning() {
		return clip.isRunning();
	}
}
