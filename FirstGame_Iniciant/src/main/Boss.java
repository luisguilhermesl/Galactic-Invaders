package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Boss {
	
	public int x, y, width = 90, height = 90;
	public int life;
	public int speed = 2;
	private boolean goToRight = true;
	
	private int shootTimer = 0;
	private int spawnTimer = 0;
	
	public Boss() {
		x = (Game.width * Game.scale) / 2 - (width/2);
		y = 20;
		life = 30 * Game.wave; // 30, 60, 90... conforme a onda
	}
	
	public void logic() {
		if(goToRight) {
			x += speed;
			if(x + width >= Game.width * Game.scale) goToRight = false;
		}else {
			x -= speed;
			if(x <= 0) {
				goToRight = true;
			}
		}
		
		shootTimer++;
		if(shootTimer >= 60) {
			Game.bossBullets.add(new BossBullet(x + width/2, y + height));
			shootTimer = 0;
		}
		
		spawnTimer++;
		if(spawnTimer >= 120) {
			Game.enemys.add(new Enemy());
			spawnTimer = 0;
		}
		collisionWithPlayerBullets();
	
	}

	private void collisionWithPlayerBullets() {
		for(int i=0; i < Game.bullets.size(); i++) {
			Bullets bu = Game.bullets.get(i);
			Rectangle rBoss = new Rectangle(x,y,width,height);
			Rectangle rBullet = new Rectangle(bu.x,bu.y,bu.width,bu.height);
			
			if(rBoss.intersects(rBullet)) {
				Game.bullets.remove(bu);
				life--;
				
				if(life <= 0) {
					//Game.boss = null;
					Sound.exp_boss.play();
					Game.player.score += 50;
				}
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int cx = x + width / 2;

		// --- asa esquerda ---
		int[] wxL = { x,          x + 25,      x + 30,      x      };
		int[] wyL = { y + height, y + height/2, y + height,  y + height };
		g2.setColor(new Color(80, 0, 120));
		g2.fillPolygon(wxL, wyL, 4);
		g2.setColor(new Color(160, 0, 220));
		g2.drawPolygon(wxL, wyL, 4);

		// --- asa direita ---
		int[] wxR = { x + width,      x + width - 25, x + width - 30, x + width      };
		int[] wyR = { y + height,      y + height/2,   y + height,     y + height };
		g2.setColor(new Color(80, 0, 120));
		g2.fillPolygon(wxR, wyR, 4);
		g2.setColor(new Color(160, 0, 220));
		g2.drawPolygon(wxR, wyR, 4);

		// --- corpo central ---
		g2.setColor(new Color(40, 0, 70));
		g2.fillOval(x + 15, y + 10, width - 30, height - 15);
		// borda do corpo
		g2.setColor(new Color(180, 0, 255));
		g2.setStroke(new BasicStroke(2f));
		g2.drawOval(x + 15, y + 10, width - 30, height - 15);
		g2.setStroke(new BasicStroke(1f));

		// --- canhão central (baixo) ---
		g2.setColor(new Color(200, 50, 50));
		g2.fillRect(cx - 5, y + height - 18, 10, 20);
		g2.setColor(new Color(255, 100, 100));
		g2.drawRect(cx - 5, y + height - 18, 10, 20);

		// --- olho / núcleo de energia ---
		g2.setColor(new Color(255, 0, 80));
		g2.fillOval(cx - 12, y + height/2 - 12, 24, 24);
		g2.setColor(new Color(255, 150, 180));
		g2.fillOval(cx - 6,  y + height/2 - 6,  12, 12);
		g2.setColor(Color.WHITE);
		g2.fillOval(cx - 3,  y + height/2 - 3,  6,  6);

		// --- detalhe de parafusos / plating ---
		g2.setColor(new Color(220, 180, 255));
		g2.fillOval(x + 20, y + 20, 6, 6);
		g2.fillOval(x + width - 26, y + 20, 6, 6);
		g2.fillOval(cx - 3, y + 14, 6, 6);

		// --- barra de vida acima do boss ---
		int barW = width;
		int barH = 6;
		int maxLife = 30 * Game.wave;
		int currentLifeW = (int) ((life / (double) maxLife) * barW);
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(x, y - 12, barW, barH);
		g2.setColor(life > maxLife * 0.5 ? new Color(0, 210, 80) : new Color(220, 60, 60));
		g2.fillRect(x, y - 12, currentLifeW, barH);
		g2.setColor(Color.WHITE);
		g2.drawRect(x, y - 12, barW, barH);
	}
}
