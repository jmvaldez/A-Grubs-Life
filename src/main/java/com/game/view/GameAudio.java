package com.game.view;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;


public class GameAudio {
    public static void PlayWelcomeAudio() {
        SetAudio("src/main/resources/audio/Welcome.wav");

    }


    public static void PlayEatAudio() {
        SetAudio("src/main/resources/audio/Eat.wav");

    }

    public static void PlayGodAudio() {
        SetAudio("src/main/resources/audio/God.wav");

    }

    public static void PlayGOAudio() {
        SetAudio("src/main/resources/audio/GO.wav");

    }

    public static void PlayAttackAudio() {
        SetAudio("src/main/resources/audio/Attack.wav");

    }
    public static void PlayHelpAudio() {
        SetAudio("src/main/resources/audio/Help.wav");

    }
    public static void PlayHideAudio() {
        SetAudio("src/main/resources/audio/HIDE.wav");
    }
    public static void PlayNORTHAudio() {
        SetAudio("src/main/resources/audio/NORTH.wav");
    }
    public static void PlaySOUTHAudio() {
        SetAudio("src/main/resources/audio/SOUTH.wav");
    }
    public static void PlayEASTAudio() {
        SetAudio("src/main/resources/audio/EAST.wav");
    }
    public static void PlayWESTAudio() {
        SetAudio("src/main/resources/audio/WEST.wav");
    }
    public static void PlayYOUADEADAudio() {
        SetAudio("src/main/resources/audio/YOUADEAD.wav");
    }
    public static void PlayYOUCANTAudio() {
        SetAudio("src/main/resources/audio/YOUCANT.wav");
    }
    public static void PlayCONGRATSAudio() {
        SetAudio("src/main/resources/audio/CONGRATS.wav");
    }

    public static void PlayRunAudio() {
        SetAudio("src/main/resources/audio/Run.wav");

    }
    public static void PlaySadAudio() {
        SetAudio("src/main/resources/audio/Sad.wav");

    }
    public static void PlayLeaveAudio() {
        SetAudio("src/main/resources/audio/Leave.wav");
    }
    public static void PlayDeadAudio() {
        SetAudio("src/main/resources/audio/Dead.wav");
    }
    public static void PlayDefeatedAudio() {
        SetAudio("src/main/resources/audio/Defeated.wav");
    }
    public static void PlayICANTAudio() {
        SetAudio("src/main/resources/audio/ICANT.wav");
    }
    public static void PlayMAXLEVELAudio() {
        SetAudio("src/main/resources/audio/MAXLEVEL.wav");
    }
    public static void PlayBUTTERFLYAudio() {
        SetAudio("src/main/resources/audio/BUTTERFLY.wav");
    }
    public static void PlayLEVEL2Audio() {
        SetAudio("src/main/resources/audio/LEVEL2.wav");
    }





    private static void SetAudio(String s)  {

        try {
            Scanner scanner = new Scanner(System.in);

            File file = new File(s);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = null;
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }


}
