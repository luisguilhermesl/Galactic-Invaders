package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//53. Criei um FOLDER chamado 'res', coloquei a spritesheet.png lá e criei essa classe para manipular ela

public class SpriteSheet {
	public BufferedImage image;
	
	//54 método para ler o texto e interpretar como imagem---------------------
	public SpriteSheet(String path) { 
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// 54------------------------------------------------------------------
	
	//55 vai construir sub'imagens a partir do meu png-----------------------------
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return image.getSubimage(x,y,width,height);
	}
	//55 ------------------------------------------------------------------------------

}
