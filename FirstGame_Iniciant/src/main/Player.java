package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player { // a lógica do jogo
	public int width = 48;
	public int height = 48;

	// precisamos da posição dos elementos
	/* 44 */public int x = (Game.width * Game.scale) / 2;
	// 31 //largura da janela
	/* 44 */public int y = Game.height * Game.scale - width; // 31 //altura da janela
	// 34 centralizando ele no meio a baixo na tela

	/* 42.2 */public boolean shot;

	private int speed = 5; // é o que vai dar a sensação de movimento/velocidade
	public boolean left, right;

	// score
	public int score = 0;

	//time
	public int seconds = 0;
	public int maxSecond = 60;
	public int minut = 0;
	public int maxMinut = 60;
	public int hour = 0;
	
	//life
	public float life = 7;
	public float maxLife = 7;

	/* 58 */private BufferedImage sprite = Game.spriteSheet.getSprite(0, 0, 16, 16);
	// aqui vamos pegar a imagem e colocar as coordenadas da qual sprite queremos

	/* 30 */
	public void tick() {
		if (left) { // como se fosse left == true
			x -= speed; // x = x - speed cada vez que o jogo for atualizado, a nossa var x vai receber
						// ela mesma - a velocidade. lógica de movimento
		} else if (right) {
			x = x + speed; // acima, ir para direita, agora, ir para esuerda
		}
		/* 46 */fire();
		countTime();

	}

	// 42.1 sempre quando der um tiro ...
	public void fire() {
		if (shot) {
			Game.bullets.add(new Bullet());
			/* 46 */shot = false; // temos que parar o tiro para ele nao ficar shotando sem parar
		}
	}

	public void countTime() {
		seconds++;
		if (seconds >= maxSecond) {
			minut++;
			seconds = 0;
			if (minut >= maxMinut) {
				hour++;
				minut = 0;
				seconds = 0;
			}
		}

	}

	public void render(Graphics g) {
		// g.setColor(Color.white); 59. inicialmente usamos assim, mas depois vamos
		// mudar para usar a imagem
		/* 60 */g.drawImage(sprite, x, y, width, height, null); // 31 // 60.colocando imagem

	}

}
