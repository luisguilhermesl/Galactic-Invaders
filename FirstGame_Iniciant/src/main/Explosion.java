package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Explosion {
	public int x,y;
	public int width = 48, height = 48;
	
	private BufferedImage[] explosionSprites;
	
	private int frames = 0;
	private int targetFrames = 4;
	private int maxAnimation = 2;
	private int  curAnimation = 0;
	
	public Explosion(double x2, double y2) {
		this.x=(int) x2;
		this.y=(int) y2;
		
		explosionSprites = new BufferedImage[3];
		
		explosionSprites[0] = Game.spriteSheet.getSprite(16*5, 0, 16,16);
		explosionSprites[1] = Game.spriteSheet.getSprite(16*6, 0, 16,16);
		explosionSprites[2] = Game.spriteSheet.getSprite(16*7, 0, 16,16);
	}
	
	public void logic() {
		frames++;
		
		if(frames >= targetFrames) {
			frames = 0;
			curAnimation++;
			
			if(curAnimation > maxAnimation) {
				Game.explosions.remove(this);
			}
		}
	}
	
	public void render(Graphics g) {
		if(curAnimation <= maxAnimation) {
			g.drawImage(explosionSprites[curAnimation],x, y, width, height, null);
		}
	}

}
