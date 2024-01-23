package GiocoBomberman;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 * La classe AudioManager gestisce la riproduzione della musica di sottofondo e degli effetti sonori nel gioco Bomberman.
 * Implementa il pattern Singleton per assicurare che esista una sola istanza di AudioManager all'interno dell'applicazione.
 * Fornisce metodi per riprodurre, fermare e gestire il volume della musica e degli effetti sonori.
 *
 * @author Davide Vittucci
 */
public class AudioManager {
	 /** Istanza unica di AudioManager, parte del pattern Singleton. */
    private static AudioManager instance;

    /** Clip audio per la musica di sottofondo. */
    private Clip backgroundMusicClip;

    /** Clip audio per gli effetti sonori. */
    private Clip soundEffectClip;
    /**
     * Ottiene l'istanza singleton di AudioManager. Se non esiste, viene creata.
     *
     * @return L'istanza singleton di AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    /** Costruttore privato per impedire l'istanziazione diretta. */
    private AudioManager() {
    }
    /**
     * Riproduce la musica di sottofondo.
     *
     * @param filename Il percorso del file audio da riprodurre.
     * @param loop Se true, la musica viene riprodotta in loop.
     * @param volume Il volume della musica (0.0 a 1.0).
     */
    public void playBackgroundMusic(String filename, boolean loop,float volume) {
        try {
            if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
                backgroundMusicClip.stop(); // Stoppa la musica corrente se in esecuzione
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            if (backgroundMusicClip != null) {
            if (loop) {
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                backgroundMusicClip.start();
            }
            setVolume(volume, backgroundMusicClip);}
            else {
            	
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(new File(filename));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream1);
            if (backgroundMusicClip != null) {
                if (loop) {
                    backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    backgroundMusicClip.start();
                }
                setVolume(volume, backgroundMusicClip);}}
           
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
           
        }
    }
    /**
     * Imposta il volume di un Clip audio.
     *
     * @param volume Il volume desiderato (0.0 a 1.0).
     * @param clip Il Clip audio di cui impostare il volume.
     */
    private void setVolume(float volume, Clip clip) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
    /**
     * Riproduce un effetto sonoro.
     *
     * @param filename Il percorso del file audio dell'effetto sonoro.
     * @param volume Il volume dell'effetto sonoro (0.0 a 1.0).
     */
    public void play(String filename,float volume) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename));
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(audioStream);
            soundEffectClip.start();
            setVolume(volume, soundEffectClip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Riproduce un effetto sonoro con volume predefinito.
     *
     * @param filename Il percorso del file audio dell'effetto sonoro.
     */
    public void play(String filename) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filename));
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(audioStream);
            soundEffectClip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /** Ferma la riproduzione della musica di sottofondo e rilascia le risorse. */
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
            backgroundMusicClip = null;
        }
    }
    /** Ferma la riproduzione dell'effetto sonoro e rilascia le risorse. */
    public void stopSoundEffect() {
        if (soundEffectClip != null) {
            soundEffectClip.stop();
            soundEffectClip.close();
            soundEffectClip = null;
        }
    }
}