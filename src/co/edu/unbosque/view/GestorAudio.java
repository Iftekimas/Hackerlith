package co.edu.unbosque.view;

import javax.sound.sampled.*;
import java.net.URL;

public class GestorAudio {

    private Clip clipActual;

    public void reproducir(String ruta, boolean loop) {
        detener();
        try {
            URL url = getClass().getResource(ruta);
            if (url == null)
                return;
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            clipActual = AudioSystem.getClip();
            clipActual.open(ais);

            if (clipActual.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gain = (FloatControl) clipActual.getControl(FloatControl.Type.MASTER_GAIN);
                gain.setValue(-10.0f);
            }

            clipActual.setFramePosition(0);
            if (loop)
                clipActual.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clipActual.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        if (clipActual != null && clipActual.isRunning()) {
            clipActual.stop();
            clipActual.close();
        }
    }

}
