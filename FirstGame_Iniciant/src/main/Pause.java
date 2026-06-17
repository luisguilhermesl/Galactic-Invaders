package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Pause {

	// false = menu principal de pause | true = submenu de opções
	public boolean inOptions = false;

	// menu principal: 0 = Voltar ao Jogo, 1 = Opções, 2 = Sair do Jogo
	public int currentOption = 0;
	public int mainMax = 2;

	// submenu opções: 0 = Música, 1 = Efeitos, 2 = Voltar
	public int optionsOption = 0;
	public int optionsMax = 2;

	/** Reinicia o estado da tela de pause (chamado ao pausar). */
	public void reset() {
		inOptions = false;
		currentOption = 0;
		optionsOption = 0;
	}

	public void render(Graphics g) {
		int W = Game.width * Game.scale;
		int H = Game.height * Game.scale;

		// fundo escuro translúcido por cima do jogo congelado
		g.setColor(new Color(0, 0, 0, 175));
		g.fillRect(0, 0, W, H);

		if (!inOptions) {
			drawTitle(g, "JOGO PAUSADO", 120);

			String[] opts = { "Voltar ao Jogo", "Opções", "Sair do Jogo" };
			int startY = 220;
			int gap = 48;
			for (int i = 0; i < opts.length; i++) {
				drawOption(g, opts[i], startY + i * gap, i == currentOption);
			}
		} else {
			drawTitle(g, "OPÇÕES", 120);

			String musica = Sound.musicEnabled ? "Desligar Música" : "Ligar Música";
			String efeitos = Sound.effectsEnabled ? "Desligar Efeitos Sonoros" : "Ligar Efeitos Sonoros";
			String[] opts = { musica, efeitos, "Voltar" };

			int startY = 210;
			int gap = 48;
			for (int i = 0; i < opts.length; i++) {
				drawOption(g, opts[i], startY + i * gap, i == optionsOption);
			}
		}
	}

	private void drawTitle(Graphics g, String text, int y) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		FontMetrics fm = g.getFontMetrics();
		int x = (Game.width * Game.scale - fm.stringWidth(text)) / 2;
		g.drawString(text, x, y);
	}

	private void drawOption(Graphics g, String text, int y, boolean selected) {
		g.setFont(new Font("Arial", Font.BOLD, 22));
		FontMetrics fm = g.getFontMetrics();
		int W = Game.width * Game.scale;
		int x = (W - fm.stringWidth(text)) / 2;

		if (selected) {
			g.setColor(Color.yellow);
			g.drawString(">", x - 25, y);
			g.drawString(text, x, y);
		} else {
			g.setColor(Color.white);
			g.drawString(text, x, y);
		}
	}
}
