package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy {
	public double x;
	public double y = 0;
	public int width = 48;
	public int height = 48;
	//public double speed = 0.1 + Game.rand.nextDouble() * 0.2;
	public double speed = 1 + Game.rand.nextDouble() * 2;

	private BufferedImage sprite = Game.spriteSheet.getSprite(16 * 2, 0, 16, 16);
	private BufferedImage spriteWhite = Game.spriteSheet.getSprite(16 * 4, 0, 16, 16);

	private int life = 4;
	private int maxLife = 4;
	private boolean recieveDamage;
	private int damageCount = 0;
	private int maxDamageCount = 5;

	public Enemy() {
		x = Game.rand.nextInt(Game.width * Game.scale - width);
	}

	public void logic() {
		y += speed;

		if (y > Game.height * Game.scale) {
			Game.player.life--;
			if (Game.player.life <= 0) {
				Game.gameState = "GameOver";
			}
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

//		g.setColor(Color.red);
//		g.fillRect((int)x, (int)y, width, height);
	}
}
