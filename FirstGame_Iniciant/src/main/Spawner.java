package main;

public class Spawner {

    private int interval = 0;
    private int maxInterval = 80;

    // Chuva de Asteroides: ativa em ondas múltiplas de 3, dura 30s = 1800 frames a 60fps
    private static final int ASTEROID_RAIN_DURATION = 1800;
    private static final int ASTEROID_SPAWN_INTERVAL = 25; // frames entre asteroides

    // rastreia em qual onda o evento já foi disparado (evita reativar na mesma onda)
    private int lastAsteroidWave = -1;

    public boolean asteroidRainActive = false;
    private int asteroidRainTimer = 0;
    private int asteroidInterval = 0;

    public void logic() {
        // verifica se esta onda deve iniciar a chuva (múltiplo de 3, ainda não disparado)
        if (Game.wave % 3 == 0 && Game.wave != lastAsteroidWave && !asteroidRainActive) {
            startAsteroidRain();
        }

        if (asteroidRainActive) {
            tickAsteroidRain();
        } else {
            tickEnemySpawn();
        }
    }

    private void startAsteroidRain() {
        asteroidRainActive = true;
        asteroidRainTimer = 0;
        asteroidInterval = 0;
        lastAsteroidWave = Game.wave;
        Game.asteroids.clear(); // garante lista limpa ao iniciar
    }

    private void tickAsteroidRain() {
        asteroidRainTimer++;
        asteroidInterval++;

        if (asteroidInterval >= ASTEROID_SPAWN_INTERVAL) {
            Game.asteroids.add(new Asteroid());
            asteroidInterval = 0;
        }

        if (asteroidRainTimer >= ASTEROID_RAIN_DURATION) {
            // encerra a chuva — o boss aparecerá normalmente via Game.logic()
            asteroidRainActive = false;
            Game.asteroids.clear();
        }
    }

    private void tickEnemySpawn() {
        // quanto maior a onda, menor o intervalo (mínimo 20 frames)
        maxInterval = Math.max(20, 80 - (Game.wave - 1) * 10);

        interval++;
        if (interval >= maxInterval) {
            Game.enemys.add(new Enemy());
            interval = 0;
        }
    }

    // chamado pelo reset de partida em Game.java
    public void reset() {
        interval = 0;
        asteroidRainActive = false;
        asteroidRainTimer = 0;
        asteroidInterval = 0;
        lastAsteroidWave = -1;
    }
}
