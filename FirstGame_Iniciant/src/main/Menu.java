package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu {

	public boolean isShipSelection = false;
	public int currentOption = 0;
	public int maxOption = 2;

	public int frames = 0, maxFrames = 30;
	private boolean showText = true;

	private BufferedImage[] shipSprites;

	public Menu() {
		shipSprites = new BufferedImage[3];
		shipSprites[0] = Game.spriteSheet.getSprite(0, 0, 16, 16);
		shipSprites[1] = Game.spriteSheet.getSprite(16, 0, 16, 16);
		shipSprites[2] = Game.spriteSheet.getSprite(0, 16, 16, 16); // Millennium Falcon
	}

	public void logic() {
		frames++;
		if (frames >= maxFrames) {
			showText = !showText;
			frames = 0;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);

		if (!isShipSelection) {
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString("SPACE SHOOTER", 30, 100);

			if (showText) {
				g.setFont(new Font("Arial", Font.BOLD, 16));
				g.drawString("Aperte ENTER para começar", 75, 250);
			}
		} else {
			g.setFont(new Font("Arial", Font.BOLD, 28));
			g.drawString("Escolha sua Nave", 60, 150);

			g.setFont(new Font("Arial", Font.BOLD, 24));

			// opções na tela
			g.drawString("Nave 1", 150, 210);
			g.drawString("Nave 2", 150, 250);
			g.drawString("Nave 3", 150, 290);

			g.setColor(Color.yellow);
			if (currentOption == 0)
				g.drawString(">", 100, 210);
			if (currentOption == 1)
				g.drawString(">", 100, 250);
			if (currentOption == 2)
				g.drawString(">", 100, 290);

			// preview das naves na parte de baixo
			int previewSize = 64;
			int spacing = (Game.width * Game.scale) / 3; // 120px por slot
			int previewY = 360;

			for (int i = 0; i < 3; i++) {
				int previewX = i * spacing + (spacing - previewSize) / 2;

				// caixa de destaque para a nave selecionada
				if (i == currentOption) {
					g.setColor(Color.yellow);
					g.drawRect(previewX - 4, previewY - 4, previewSize + 8, previewSize + 8);
				} else {
					g.setColor(new Color(80, 80, 80));
					g.drawRect(previewX - 4, previewY - 4, previewSize + 8, previewSize + 8);
				}

				g.drawImage(shipSprites[i], previewX, previewY, previewSize, previewSize, null);

				// label abaixo de cada preview
				g.setColor(i == currentOption ? Color.yellow : Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 14));
				g.drawString("Nave " + (i + 1), previewX + 8, previewY + previewSize + 18);
			}
		}

	}

}
