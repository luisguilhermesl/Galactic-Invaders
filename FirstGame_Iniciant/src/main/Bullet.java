package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

//39
public class Bullet {
	//52.2							//45
	public int x, y,width,height,speed=3;
	private BufferedImage sprite = Game.spriteSheet.getSprite(16, 0, 16, 16);
	
	//45
	public Bullet() {
		x = Game.player.x; //47 //alinhando os tiros no player
	/*45*/y = Game.player.y;
		width=48;
		height=48;	
	}

	public void tick() {
		y -= speed; //vai fazer a bala subir - sempre que uma bala for criada ela vai se mover para cima
		
		if(y + height <= 0) { // 47 limitando e removendo os tiros após certa distancia | COMEÇAR COM 50 PARA PREVIEW
			Game.bullets.remove(this);
		}
	}

	public void render(Graphics g) {
//		g.setColor(Color.white);
//		g.fillOval(x, y, width, height); 
		g.drawImage(sprite,x, y, width, height,null); 
	}

}

// * agora temos que criar várias balas, mas ao mesmo tempo, elimina-las após serem usadas, 
// para não termos vários objetos de bala, por isso, vamos criar listas
