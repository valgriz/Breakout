package main;

import java.applet.Applet;
import java.applet.AudioClip;

public class SoundPlayer {
	private AudioClip clipA, clipB, clipC, clipD;

	public SoundPlayer() {
		try {
			clipA = Applet.newAudioClip(this.getClass().getResource(
					"/raw/sounds/sound_a.wav"));
			clipB = Applet.newAudioClip(this.getClass().getResource(
					"/raw/sounds/sound_b.wav"));
			clipC = Applet.newAudioClip(this.getClass().getResource(
					"/raw/sounds/sound_c.wav"));
			clipD = Applet.newAudioClip(this.getClass().getResource(
					"/raw/sounds/sound_d.wav"));
		} catch (Exception e) {
		}
	}

	public void play(final int i) {
		try {
			new Thread() {
				public void run() {
					switch (i) {
					case 0:
						clipA.play();
						break;
					case 1:
						clipB.play();
						break;
					case 3:
						clipC.play();
						break;
					case 4:
						clipD.play();
						break;
					}
				}
			}.start();
		} catch (Exception e) {
		}
	}
}
