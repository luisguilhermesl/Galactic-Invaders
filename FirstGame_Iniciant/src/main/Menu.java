package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {

	public boolean isShipSelection = false;
	public int currentOption = 0;
	public int maxOption = 2;

	public int frames = 0, maxFrames = 30;
	private boolean showText = true;

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
				g.drawString("Aperte ENTER para começar", 75,250);
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
		}

	}

}
