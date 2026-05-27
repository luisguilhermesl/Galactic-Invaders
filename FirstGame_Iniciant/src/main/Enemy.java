package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//35
public class Enemy {

	public int x;//52.3 removi o 32
	
	public int y = 0; // os inimigos vao começar lá de cima
	public int width = 48;//37
	public int height = 48;//37
	public int speed = Game.rand.nextInt(5-1)+1;//estava 2; //37
	/*61*/private BufferedImage sprite = Game.spriteSheet.getSprite(16*2, 0, 16, 16); //as posições da imagem são 0,16,32...
	private BufferedImage spriteWhite = Game.spriteSheet.getSprite(16*4, 0, 16, 16);
	
	private int life = 4;
	private int maxLife = 4;
	private boolean recieveDamage;
	private int damageCount = 0;
	private int maxDamageCount = 5;
	
	
	//52.3--------------------------------------- vai "chover" inimigos aleatoriamente
	public Enemy() {
		x = Game.rand.nextInt(Game.width * Game.scale - width); // assim para estar na tela inteira e '- width' para nao ficar fora da tela
	}
	//52.3---------------------------------------
	
	public void tick() {
		y += speed; // e vai aumentando a altura para eles virem ao nosso encontro
		if(y > Game.height * Game.scale) {
			Game.player.life--;
			if(Game.player.life <= 0) {
				Game.gameState = "GameOver";
			}
			Game.enemys.remove(this);
			//System.out.println("DELETED"); DEBUG MANUAL PARA SABER SE OS ENEMYS ESTÃO SENDO REMOVIDOS AO SAIR DA TELA
			
		}
		
		
		this.collisionWithBullets();
		this.damage();
	}
	
	//52.3---------------------------------------
	public void collisionWithBullets() {
		for(int i=0; i < Game.bullets.size();i++) { // temos que acessar e modificar a lista de bullets
			Bullet bu = Game.bullets.get(i); //recebe cada elemento da lista
			if(this.checkCollision(this, bu)) { // se eles estiverem colidindo
				Game.bullets.remove(i); //vai remover o inimigo
				life--;
				recieveDamage = true;
				
				if(life <= 0) {
					Game.enemys.remove(this);
					Game.player.score++;
				}
//				Game.bullets.remove(i);
//				Game.player.score++;
			}
		}
	}
	
	public void damage() {
		if(recieveDamage) {
			damageCount++;
			if(damageCount >= maxDamageCount){
				recieveDamage = false;
				System.out.println("Countou"+recieveDamage);
				damageCount = 0;
			}
		}
		
	}
	
	public boolean checkCollision(Enemy en, Bullet bu) {
		Rectangle r1 = new Rectangle(en.x, en.y,en.width, en.height);
		Rectangle r2 = new Rectangle(bu.x,bu.y,bu.width,bu.height);
		return r1.intersects(r2); // se estiver r1 colidindo com r2, ele vai retornar true ou false
	}
	//52.3---------------------------------------
	
	public void render(Graphics g) {
		
		if(recieveDamage) {
			g.drawImage(spriteWhite,x, y, width, height,null); 
		}else {
			g.drawImage(sprite,x, y, width, height,null); 
		}
		
		
		//g.setColor(Color.red);
		//g.fillRect(x, y, width, height); 
		//g.fillOval(x, y, width, height); se quiser o inimigo redondo
		
	//	/*62*/g.drawImage(sprite,x, y, width, height,null); 
	}
}
