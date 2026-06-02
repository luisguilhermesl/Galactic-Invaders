package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	
	public int width = 48;
	public int height = 48;

	public int x = (Game.width * Game.scale) / 2; // largura da tela
	public int y = Game.height * Game.scale - width; // altura da tela
	
	public boolean shoot;
	
	private int speed = 3;
	
	public boolean left, right;
	
	private BufferedImage sprite = Game.spriteSheet.getSprite(0, 0, 16, 16);
	
	//PONTUAÇÃO E VIDA
	public double life = 7;
	public int score = 0;
	public double maxLife = 7;
	
	//SISTEMA DE TEMPO
	public int seconds = 0;
	public int maxSecond = 60;
	public int minut = 0;
	public int maxMinut = 60;
	public int hour = 0;
	
	
	
	public void logic() {
		if(left) { //ir para a esquerda
			x-=speed; 
		}else if(right) { //ir para a direita
			x += speed;
		}	
		
		fire();
		countTime();
	}
	
	private void countTime() {
		seconds++;
		
		if(seconds >= maxSecond) {
			minut++;
			seconds=0;
			if(minut >= maxMinut) {
				hour++;
				minut=0;
				seconds=0;
			}
		}
		
	}

	public void fire() {
		if(shoot) {
			Game.bullets.add(new Bullets());
			Sound.shoot.play();
			shoot = false;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, width, height, null);
//		g.setColor(Color.white);
//		g.fillRect(x, y, width, height);
	}
	
	public void setShipStyle(int type) {
		if(type == 0) {
			sprite = Game.spriteSheet.getSprite(0, 0, 16, 16);
		}else if(type == 1) {
			sprite = Game.spriteSheet.getSprite(16, 0, 16, 16);
		}else if(type == 2) {
			sprite = Game.spriteSheet.getSprite(32, 0, 16, 16);
	}
	}
}