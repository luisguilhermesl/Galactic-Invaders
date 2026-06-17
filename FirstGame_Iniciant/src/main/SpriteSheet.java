package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public BufferedImage image;

	public SpriteSheet(String fileName) {
		File file = findFile(fileName);
		try {
			if (file == null) {
				System.out.println("[Sprite] Não foi possível encontrar a imagem: " + fileName);
				return;
			}
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("[Sprite] Erro ao ler a imagem: " + file.getAbsolutePath());
			e.printStackTrace();
		}
	}

	/** Procura o arquivo em vários locais, independente do diretório de trabalho. */
	private static File findFile(String fileName) {
		String[] candidates = {
			fileName,                              // working dir = raiz do projeto
			"res/" + fileName,                     // dentro de res/
			"../" + fileName,                      // rodando de uma subpasta
			buildPathFromClassLocation(fileName)   // detecta via localização das classes
		};
		for (String path : candidates) {
			if (path == null) continue;
			File f = new File(path);
			if (f.exists()) return f;
		}
		return null;
	}

	/** Navega da pasta de classes compiladas (bin/) até a raiz do projeto. */
	private static String buildPathFromClassLocation(String fileName) {
		try {
			File classesDir = new File(
				SpriteSheet.class.getProtectionDomain().getCodeSource().getLocation().toURI()
			);
			// bin/ -> raiz do projeto
			File projectRoot = classesDir.getParentFile();
			File direct = new File(projectRoot, fileName);
			if (direct.exists()) return direct.getAbsolutePath();
			return new File(projectRoot, "res/" + fileName).getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
	}

	public BufferedImage getSprite(int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}

}
