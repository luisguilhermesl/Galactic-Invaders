package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Asteroid {

    public double x;
    public double y;
    public int width;
    public int height;
    private double speed;
    private double rotAngle = 0;
    private double rotSpeed;

    public boolean dead = false;

    // cor ligeiramente variada para dar diversidade visual
    private Color color;

    public Asteroid() {
        // tamanho aleatório entre 24 e 56
        int size = 24 + Game.rand.nextInt(33);
        width = size;
        height = size;

        x = Game.rand.nextInt(Game.width * Game.scale - width);
        y = -height;

        speed = 1.5 + Game.rand.nextDouble() * 2.5;
        rotSpeed = (Game.rand.nextDouble() * 0.08) * (Game.rand.nextBoolean() ? 1 : -1);

        int base = 100 + Game.rand.nextInt(60);
        color = new Color(base, base - 20, base - 40);
    }

    public void logic() {
        y += speed;
        rotAngle += rotSpeed;

        if (y > Game.height * Game.scale) {
            dead = true;
            return;
        }

        // colisão com player — causa dano e some
        Rectangle rA = new Rectangle((int) x, (int) y, width, height);
        Rectangle rP = new Rectangle(Game.player.x, Game.player.y,
                                     Game.player.width, Game.player.height);
        if (rA.intersects(rP)) {
            Game.player.takeDamage(1);
            Game.explosions.add(new Explosion(x, y));
            dead = true;
        }
    }

    public void render(Graphics g) {
        // polígono irregular simulando asteroide usando um octógono rotacionado
        int cx = (int) x + width / 2;
        int cy = (int) y + height / 2;
        int r = width / 2;

        int sides = 8;
        int[] px = new int[sides];
        int[] py = new int[sides];

        // pontos irregulares: raio varia entre 60%–100% do raio base
        double[] radii = {1.0, 0.75, 0.9, 0.65, 1.0, 0.8, 0.7, 0.85};

        for (int i = 0; i < sides; i++) {
            double angle = rotAngle + (2 * Math.PI / sides) * i;
            px[i] = cx + (int) (Math.cos(angle) * r * radii[i]);
            py[i] = cy + (int) (Math.sin(angle) * r * radii[i]);
        }

        g.setColor(color);
        g.fillPolygon(px, py, sides);

        g.setColor(color.darker());
        g.drawPolygon(px, py, sides);

        // detalhe de cratera central
        g.setColor(color.darker().darker());
        int craterR = r / 4;
        g.fillOval(cx - craterR, cy - craterR, craterR * 2, craterR * 2);
    }
}
