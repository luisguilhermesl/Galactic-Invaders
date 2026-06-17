package main;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {

    private Clip clip;

    // controle global de áudio (usado pela tela de opções)
    public static boolean musicEnabled = true;
    public static boolean effectsEnabled = true;

    public static final Sound shoot       = new Sound("laserShoot.wav");
    public static final Sound exp_enemys  = new Sound("explosion_enemys.wav");
    public static final Sound exp_boss    = new Sound("explosion_boss.wav");
    public static final Sound theme_music = new Sound("theme-8bit-techno.wav");

    private Sound(String fileName) {
        File file = findFile(fileName);
        if (file == null) {
            System.out.println("[Som] Arquivo não encontrado: " + fileName);
            return;
        }
        try {
            AudioInputStream rawStream = AudioSystem.getAudioInputStream(file);
            AudioFormat base = rawStream.getFormat();

            // Converte para PCM_SIGNED 16-bit (compatível com todos os sistemas)
            AudioFormat target = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(), 16,
                base.getChannels(),
                base.getChannels() * 2,
                base.getSampleRate(), false
            );

            AudioInputStream pcmStream = AudioSystem.getAudioInputStream(target, rawStream);
            clip = AudioSystem.getClip();
            clip.open(pcmStream);

        } catch (Exception e) {
            System.out.println("[Som] Erro ao carregar " + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    /** Procura o arquivo .wav em vários locais possíveis. */
    private static File findFile(String fileName) {
        String[] candidates = {
            "res/" + fileName,                   // rodando da raiz do projeto
            "../res/" + fileName,                // rodando de uma subpasta
            buildPathFromClassLocation(fileName) // detecta via localização das classes
        };
        for (String path : candidates) {
            if (path == null) continue;
            File f = new File(path);
            if (f.exists()) return f;
        }
        return null;
    }

    /** Navega da pasta de classes compiladas (bin/) até a raiz do projeto. */
    private static String buildPathFromClassLocation(String fileName) {
        try {
            // Ex: .../FirstGame_Iniciant/bin/
            File classesDir = new File(
                Sound.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            );
            // bin/ -> raiz do projeto
            File projectRoot = classesDir.getParentFile();
            File inRes = new File(projectRoot, "res/" + fileName);
            if (inRes.exists()) return inRes.getAbsolutePath();
            return new File(projectRoot, fileName).getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    /** Liga/desliga a música de fundo. */
    public static void toggleMusic() {
        musicEnabled = !musicEnabled;
        if (musicEnabled) theme_music.loop();
        else theme_music.stop();
    }

    /** Liga/desliga os efeitos sonoros. */
    public static void toggleEffects() {
        effectsEnabled = !effectsEnabled;
    }

    /** Toca o som uma vez (efeitos sonoros). */
    public void play() {
        if (clip == null) return;
        if (!effectsEnabled) return; // efeitos desligados
        new Thread(() -> {
            try {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("[Som] Erro ao reproduzir: " + e.getMessage());
            }
        }).start();
    }

    /** Toca em loop contínuo (música de fundo). */
    public void loop() {
        if (clip == null) return;
        new Thread(() -> {
            try {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.out.println("[Som] Erro no loop: " + e.getMessage());
            }
        }).start();
    }

    public void stop() {
        if (clip != null) clip.stop();
    }
}
