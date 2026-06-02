package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bullets {
	
	public int x, y, width = 48,height = 48,speed = 3;
	
	private BufferedImage sprite = Game.spriteSheet.getSprite(16, 0, 16, 16);
	
	public Bullets() {
		x = Game.player.x;
		y = Game.player.y;
//		width = 8;
//		height = 8;
	}
	
	public void logic() {
		y -= speed;
		
		if(y + height <= 0) {
			Game.bullets.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y,width, height, null);
//		g.setColor(Color.white);
//		g.fillRect(x, y, width, height);
	}
}
