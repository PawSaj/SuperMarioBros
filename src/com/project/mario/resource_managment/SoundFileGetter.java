package com.project.mario.resource_managment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Klasa odpowiadająca za odczytanie dzwięku oraz jego zarządzanie.
 *
 */
public class SoundFileGetter {
	private Clip clip;

	public SoundFileGetter(String path) {
		try {
			BufferedInputStream myStream = new BufferedInputStream(new FileInputStream(new File(path)));
			AudioInputStream ais = AudioSystem.getAudioInputStream(myStream);
			clip = AudioSystem.getClip();
			clip.open(ais);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("blad odczytywania dzwieku");
		}
	}

	/**
	 * Metoda włączająca odtwarzanie dzwięku
	 */
	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	/**
	 * Metoda zamykająca otwarty plik dzwieku
	 */
	public void close() {
		stop();
		clip.close();
	}

	/**
	 * Metoda zatrzymująca odtwarzanie dzwięku
	 */
	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	/**
	 * Metoda zwracajaca czy dzwiek jest odtwarzany
	 * 
	 * @return Informacja czy dzwięk jest obecnie odtwarzany
	 */
	public boolean isRunning() {
		return clip.isRunning();
	}
}
