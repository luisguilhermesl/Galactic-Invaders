package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Boss {
	
	public int x, y, width = 90, height = 90;
	public int life = 30;
	public int speed = 2;
	private boolean goToRight = true;
	
	private int shootTimer = 0;
	private int spawnTimer = 0;
	
	public Boss() {
		x = (Game.width * Game.scale) / 2 - (width/2);
		y = 20;
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
		g.setColor(Color.magenta);
		g.fillRect(x, y, width, height);
	}
}
