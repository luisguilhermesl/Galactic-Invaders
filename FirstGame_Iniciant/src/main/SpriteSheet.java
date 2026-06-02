package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public BufferedImage image;
	
	public SpriteSheet(String path) {
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Não foi possivel encontrar a imagem neste caminho:");
			System.out.println(new File(path).getAbsolutePath());
			e.printStackTrace();
		}
	}
	
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}

}
