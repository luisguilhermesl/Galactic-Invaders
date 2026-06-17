package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PowerUp {

    public double x, y;
    public int width = 32;
    public int height = 32;
    private double speed = 1.5;

    // 0 = Cura, 1 = Escudo, 2 = Tiro Duplo
    public int type;

    private static final Color COLOR_HEAL   = new Color(0, 220, 80);
    private static final Color COLOR_SHIELD = new Color(80, 160, 255);
    private static final Color COLOR_DOUBLE = new Color(255, 200, 0);

    // pulso visual
    private int pulseTimer = 0;

    public PowerUp(double x, double y) {
        this.x = x;
        this.y = y;
        this.type = Game.rand.nextInt(3);
    }

    public void logic() {
        y += speed;
        pulseTimer++;

        if (y > Game.height * Game.scale) {
            Game.powerUps.remove(this);
            return;
        }

        // colisão com o player
        Rectangle rPU = new Rectangle((int) x, (int) y, width, height);
        Rectangle rPL = new Rectangle(Game.player.x, Game.player.y,
                                      Game.player.width, Game.player.height);
        if (rPU.intersects(rPL)) {
            apply();
            Game.powerUps.remove(this);
        }
    }

    private void apply() {
        switch (type) {
            case 0: // Cura
                Game.player.life = Math.min(Game.player.life + 2, Game.player.maxLife);
                break;
            case 1: // Escudo
                Game.player.shielded = true;
                break;
            case 2: // Tiro Duplo
                Game.player.doubleShotTimer = 600; // ~10 segundos a 60fps
                break;
        }
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // fundo colorido pulsante
        float alpha = 0.6f + 0.4f * (float) Math.abs(Math.sin(pulseTimer * 0.1));
        Composite original = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        switch (type) {
            case 0: g2.setColor(COLOR_HEAL);   break;
            case 1: g2.setColor(COLOR_SHIELD); break;
            case 2: g2.setColor(COLOR_DOUBLE); break;
        }
        g2.fillRoundRect((int) x, (int) y, width, height, 8, 8);

        // borda branca
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect((int) x, (int) y, width, height, 8, 8);

        // ícone central em texto
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        String icon = type == 0 ? "+" : type == 1 ? "S" : "2";
        int tw = g2.getFontMetrics().stringWidth(icon);
        g2.drawString(icon, (int) x + (width - tw) / 2, (int) y + height / 2 + 5);

        g2.setComposite(original);
    }
}
