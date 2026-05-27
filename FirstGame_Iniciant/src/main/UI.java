package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class UI {
	
	public void render(Graphics g) {
		
		if(Game.gameState == "Normal") {
			//SCORE
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial",Font.BOLD,14));
		g.drawString("SCORE: "+Game.player.score, Game.width * Game.scale-80, 35);
					//vai ler essa parte como uma String
	
		//TIME
		g.setColor(Color.red);
		g.setFont(new Font("Arial",Font.BOLD,18));
		g.drawString(Game.player.hour+":"+Game.player.minut,44, 25);
	
		//LIVE
		g.setColor(Color.white);
		g.fillRect(20, 35, 80, 15);
		
		g.setColor(Color.red);
		g.fillRect(21, 36, (int)((Game.player.life / Game.player.maxLife) * 79), 14);
		}
		else if(Game.gameState == "GameOver") {
		// game over
		g.setColor(Color.gray);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Game Over",(Game.width * Game.scale)/2-82, (Game.height * Game.scale)/2-11);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Game Over",(Game.width * Game.scale)/2-80, (Game.height * Game.scale)/2-10);
		
		g.setColor(Color.yellow);
		g.setFont(new Font("Arial",Font.BOLD,18));
		g.drawString("SCORE: "+Game.player.score, (Game.width * Game.scale) /2-40,(Game.height * Game.scale)/2+20);	
			
		}
		
		
		
		
		
	}

}
