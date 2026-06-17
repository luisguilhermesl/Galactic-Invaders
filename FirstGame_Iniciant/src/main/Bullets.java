package main;

import java.awt.Color;
import java.awt.Graphics;

public class Bullets {

    public int x, y, width, height, speed;

    // 0 = padrão (nave 1), 1 = pesado (nave 2), 2 = laser (nave 3)
    private int shotType;

    // construtor padrão — mantém compatibilidade com tiro duplo do power-up
    public Bullets(int startX, int startY) {
        this(startX, startY, Game.player.shotType);
    }

    public Bullets(int startX, int startY, int shotType) {
        this.x = startX;
        this.y = startY;
        this.shotType = shotType;

        switch (shotType) {
            case 1: // pesado
                width  = 20;
                height = 20;
                speed  = 5;
                break;
            case 2: // laser
                width  = 6;
                height = 36;
                speed  = 12;
                break;
            default: // padrão
                width  = 12;
                height = 12;
                speed  = 8;
                break;
        }
    }

    public void logic() {
        y -= speed;

        if (y + height <= 0) {
            Game.bullets.remove(this);
        }
    }

    public void render(Graphics g) {
        switch (shotType) {
            case 1: // pesado — retângulo vermelho com borda escura
                g.setColor(new Color(180, 0, 0));
                g.fillRect(x, y, width, height);
                g.setColor(new Color(255, 80, 80));
                g.drawRect(x, y, width, height);
                break;
            case 2: // laser — retângulo azul fino com brilho central
                g.setColor(new Color(0, 180, 255));
                g.fillRect(x, y, width, height);
                g.setColor(Color.WHITE);
                g.fillRect(x + width / 2 - 1, y, 2, height); // linha de brilho
                break;
            default: // padrão — oval amarela
                g.setColor(new Color(255, 220, 0));
                g.fillOval(x, y, width, height);
                g.setColor(Color.WHITE);
                g.fillOval(x + 3, y + 3, width - 6, height - 6);
                break;
        }
    }
}
