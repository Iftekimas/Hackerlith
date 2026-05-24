package co.edu.unbosque.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Maneja la reproducción de audio del juego.
 */
public class GestorAudio {

    private Clip clipActual;

    /**
     * Reproduce un archivo de audio, deteniendo el anterior si hay uno activo.
     * @param ruta ruta del archivo WAV dentro del classpath
     * @param loop true para reproducir en bucle continuo
     */
    public void reproducir(String ruta, boolean loop) {
        detener();
        try {
            URL url = getClass().getResource(ruta);
            if (url == null)
                return;
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clipActual = AudioSystem.getClip();
            clipActual.open(ais);
            clipActual.setFramePosition(0);
            if (loop)
                clipActual.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clipActual.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Detiene y cierra el audio que se esté reproduciendo actualmente.
     */
    public void detener() {
        if (clipActual != null && clipActual.isRunning()) {
            clipActual.stop();
            clipActual.close();
        }
    }

}
