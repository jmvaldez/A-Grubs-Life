package com.game.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GameAudio {
    public static void playAudio(String name) {
        SetAudio(name + ".wav");
    }

    public static void PlayWelcomeAudio() {
        SetAudio("Welcome.wav");

    }

    public static void PlayEatAudio() {
        SetAudio("Eat.wav");

    }

    public static void PlayGodAudio() {
        SetAudio("God.wav");

    }

    public static void PlayGOAudio() {
        SetAudio("GO.wav");

    }

    public static void PlayAttackAudio() {
        SetAudio("Attack.wav");

    }

    public static void PlayHelpAudio() {
        SetAudio("Help.wav");

    }

    public static void PlayHideAudio() {
        SetAudio("HIDE.wav");
    }

    public static void PlayNORTHAudio() {
        SetAudio("NORTH.wav");
    }

    public static void PlaySOUTHAudio() {
        SetAudio("SOUTH.wav");
    }

    public static void PlayEASTAudio() {
        SetAudio("EAST.wav");
    }

    public static void PlayWESTAudio() {
        SetAudio("WEST.wav");
    }

    public static void PlayYOUADEADAudio() {
        SetAudio("YOUADEAD.wav");
    }

    public static void PlayYOUCANTAudio() {
        SetAudio("YOUCANT.wav");
    }

    public static void PlayCONGRATSAudio() {
        SetAudio("CONGRATS.wav");
    }

    public static void PlayRunAudio() {
        SetAudio("Run.wav");

    }

    public static void PlaySadAudio() {
        SetAudio("Sad.wav");

    }

    public static void PlayLeaveAudio() {
        SetAudio("Leave.wav");
    }

    public static void PlayDeadAudio() {
        SetAudio("Dead.wav");
    }

    public static void PlayDefeatedAudio() {
        SetAudio("Defeated.wav");
    }

    public static void PlayICANTAudio() {
        SetAudio("ICANT.wav");
    }

    public static void PlayMAXLEVELAudio() {
        SetAudio("MAXLEVEL.wav");
    }

    public static void PlayBUTTERFLYAudio() {
        SetAudio("BUTTERFLY.wav");
    }

    public static void PlayLEVEL2Audio() {
        SetAudio("LEVEL2.wav");
    }

    private static void SetAudio(String s) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(GameAudio.class.getResource("/audio/" + s));
            Clip clip = null;
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.out.println("Audio Import Error");
        }
        ;
    }
}