package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30]; // Mảng chứa đường dẫn các file âm thanh

    public Sound() {
        soundURL[0] = getClass().getResource("/res/sound/bmg.wav");
        soundURL[1] = getClass().getResource("/res/sound/punch.wav");
        soundURL[2] = getClass().getResource("/res/sound/win.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Loi khi mo file am thanh: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
    
}