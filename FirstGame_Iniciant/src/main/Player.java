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
	
	public boolean left, right, up, down;
	public int shipType = 0; // 0, 1, 2 — qual nave está em uso
	
	private BufferedImage sprite = Game.spriteSheet.getSprite(0, 0, 16, 16);
	
	//PONTUAÇÃO E VIDA
	public double life = 7;
	public int score = 0;
	public double maxLife = 7;

	// POWER-UPS
	public boolean shielded = false;
	public int doubleShotTimer = 0;

	// tipo de tiro definido pela nave escolhida (0=padrão, 1=pesado, 2=laser)
	public int shotType = 0;
	
	//SISTEMA DE TEMPO
	public int seconds = 0;
	public int maxSecond = 60;
	public int minut = 0;
	public int maxMinut = 60;
	public int hour = 0;
	
	
	
	public void logic() {
		if (shipType == 2) {
			// nave 3: movimento livre nos 8 sentidos
			if (left)  x -= speed;
			if (right) x += speed;
			if (up)    y -= speed;
			if (down)  y += speed;

			// limites da tela
			if (x < 0) x = 0;
			if (x + width  > Game.width  * Game.scale) x = Game.width  * Game.scale - width;
			if (y < 0) y = 0;
			if (y + height > Game.height * Game.scale) y = Game.height * Game.scale - height;
		} else {
			// naves 1 e 2: apenas horizontal
			if (left)  x -= speed;
			if (right) x += speed;
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

	public void takeDamage(double amount) {
		if (shielded) {
			shielded = false;
			return;
		}
		life -= amount;
		if (life <= 0) {
			Game.gameState = "GameOver";
		}
	}

	public void fire() {
		if (shoot) {
			int cx = x + width / 2; // centro horizontal da nave
			if (doubleShotTimer > 0) {
				Game.bullets.add(new Bullets(cx - 16, y, shotType));
				Game.bullets.add(new Bullets(cx + 8,  y, shotType));
			} else {
				Game.bullets.add(new Bullets(cx, y, shotType));
			}
			Sound.shoot.play();
			shoot = false;
		}

		if (doubleShotTimer > 0) doubleShotTimer--;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, width, height, null);
//		g.setColor(Color.white);
//		g.fillRect(x, y, width, height);
	}
	
	public void setShipStyle(int type) {
		shipType = type;
		shotType = type; // cada nave tem seu tipo de tiro
		if (type == 0) {
			sprite = Game.spriteSheet.getSprite(0, 0, 16, 16);
		} else if (type == 1) {
			sprite = Game.spriteSheet.getSprite(16, 0, 16, 16);
		} else if (type == 2) {
			sprite = Game.spriteSheet.getSprite(0, 16, 16, 16); // Millennium Falcon
		}
	}
}