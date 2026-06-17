package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BossBullet {
	public int x, y, width = 15, height = 15, speed = 4;
	
	public BossBullet(int startX, int startY) {
		this.x = startX;
		this.y = startY;
	}
	
	public void logic() {
		y += speed;
		if(y >= Game.height * Game.scale) {
			Game.bossBullets.remove(this);
		}
		
		Rectangle rBullet = new Rectangle(x, y, width, height);
		Rectangle rPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		
		if(rBullet.intersects(rPlayer)) {
			Game.player.takeDamage(1);
			Game.bossBullets.remove(this);
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.magenta);
		g.fillOval(x,y,width,height);
	}
	
	
	
	

}
