package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy {
	public double x;
	public double y = 0;
	public int width = 48;
	public int height = 48;
	public double speed;

	// 0 = normal, 1 = blue (tanky+fast), 2 = yellow (zigzag)
	private int type = 0;

	private BufferedImage sprite = Game.spriteSheet.getSprite(16 * 2, 0, 16, 16);
	private BufferedImage spriteWhite = Game.spriteSheet.getSprite(16 * 4, 0, 16, 16);

	private int life;
	private int maxLife;
	private boolean recieveDamage;
	private int damageCount = 0;
	private int maxDamageCount = 5;

	// zigzag state
	private double zigzagSpeed = 2.5;
	private int zigzagDir = 1;
	private int zigzagTimer = 0;
	private int zigzagInterval = 30; // frames por direção

	public Enemy() {
		x = Game.rand.nextInt(Game.width * Game.scale - width);

		double roll = Game.rand.nextDouble();

		if (Game.wave >= 3 && roll < 0.15) {
			// yellow zigzag — 15% de chance a partir da onda 3
			type = 2;
			life = 1;
			maxLife = 1;
			speed = 1 + Game.rand.nextDouble() * 1.5;
		} else if (Game.wave >= 2 && roll < 0.15 + 0.30) {
			// blue tanky — 30% de chance a partir da onda 2 (roll 0.15–0.45 na onda 3+, 0–0.30 na onda 2)
			type = 1;
			life = 2;
			maxLife = 2;
			speed = 2 + Game.rand.nextDouble() * 2;
		} else {
			// normal
			type = 0;
			life = 1;
			maxLife = 1;
			speed = 1 + Game.rand.nextDouble() * 2;
		}
	}

	public void logic() {
		y += speed;

		if (type == 2) {
			zigzagTimer++;
			if (zigzagTimer >= zigzagInterval) {
				zigzagDir = -zigzagDir;
				zigzagTimer = 0;
			}
			x += zigzagDir * zigzagSpeed;
			if (x < 0) { x = 0; zigzagDir = 1; }
			if (x > Game.width * Game.scale - width) { x = Game.width * Game.scale - width; zigzagDir = -1; }
		}

		if (y > Game.height * Game.scale) {
			Game.player.takeDamage(1);
			Game.enemys.remove(this);
			//System.out.println("INIMIGO FOI DELETADO AO SAIR DA TELA - TESTE");
		}

		collisionWhithBullets();
		damage();
	}

	private void collisionWhithBullets() {
		for (int i = 0; i < Game.bullets.size(); i++) {
			Bullets bu = Game.bullets.get(i);

			if (this.checkCollision(this, bu)) {
				Game.bullets.remove(i);
				life--;
				recieveDamage = true;

				if (life <= 0) {
					Game.explosions.add(new Explosion(x,y));
					Sound.exp_enemys.play();
					Game.enemys.remove(this);
					Game.player.score++;
					if (Game.rand.nextDouble() < 0.10) {
						Game.powerUps.add(new PowerUp(x, y));
					}
				}
				break;
			}
		}

	}

	private void damage() {
		if (recieveDamage) {
			damageCount++;
			if (damageCount >= maxDamageCount) {
				recieveDamage = false;
				//System.out.println("Inimigo tomou dano "+recieveDamage);
				damageCount = 0;
			}
		}

	}

	public boolean checkCollision(Enemy en, Bullets bu) {
		Rectangle r1 = new Rectangle((int) en.x, (int) en.y, en.width, en.height);
		Rectangle r2 = new Rectangle(bu.x, bu.y, bu.width, bu.height);
		return r1.intersects(r2);
	}

	public void render(Graphics g) {
		if (recieveDamage) {
			g.drawImage(spriteWhite, (int) x, (int) y, width, height, null);
		} else {
			g.drawImage(sprite, (int) x, (int) y, width, height, null);
		}

		if (!recieveDamage && type != 0) {
			Graphics2D g2 = (Graphics2D) g;
			Composite original = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.45f));
			if (type == 1) {
				g2.setColor(new Color(0, 120, 255)); // azul
			} else {
				g2.setColor(new Color(255, 220, 0)); // amarelo
			}
			g2.fillRect((int) x, (int) y, width, height);
			g2.setComposite(original);
		}
	}
}
