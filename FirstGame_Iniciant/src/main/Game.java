package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

	// altura, largura, escala
	public static Menu menu;
	public static String gameState = "Menu";
	public static int width = 120;
	public static int height = 160;
	public static int scale = 3;
	public JFrame frame;

	private BufferedImage image; // permite dar imagem ao projeto
	public static SpriteSheet spriteSheet;
	private BufferedImage backGround;

	private boolean isRunning = true;
	private Thread thread;
	public static Player player;
	private Enemy en;
	public static Boss boss;

	public static ArrayList<BossBullet> bossBullets;
	public static ArrayList<Enemy> enemys;
	public static ArrayList<Bullets> bullets;
	public static ArrayList<Explosion> explosions;
	public static boolean bossCompleted = false;

	public Spawner spawner;
	public static Random rand;

	private UI ui;

	// public static String gameState = "Normal"; -> aqui era onde o jogo começava,
	// agora vai para "menu"

	public Game() {
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(width * scale, height * scale));
		createScreen();

		image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_BGR);
		spriteSheet = new SpriteSheet("spritesheet.png");
		backGround = spriteSheet.getSprite(48, 0, 16, 16); // 16*3

		thread = new Thread(this);
		rand = new Random();

		player = new Player();
		ui = new UI();
		en = new Enemy();
		enemys = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullets>();
		spawner = new Spawner();
		bossBullets = new ArrayList<BossBullet>();
		explosions = new ArrayList<Explosion>();

		Sound.theme_music.play();

		menu = new Menu();

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void createScreen() { // criando a tela

		frame = new JFrame();
		frame.add(this); // estou usando essa linha
		frame.pack(); // pegando tamanho da tela

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // abrir a tela no meio
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void logic() { // trabalha com lógica do game
		if (gameState == "Normal") {
			player.logic();
			// spawner.logic();

			if (player.score >= 15 && !bossCompleted && boss == null) {
				boss = new Boss();
			}

			if (boss != null) {
				boss.logic();
				if (boss.life <= 0) {
					bossCompleted = true;
					boss = null;
				}
			} else {
				spawner.logic();
			}

			for (int i = 0; i < bullets.size(); i++) {
				Bullets bu = bullets.get(i);
				bu.logic();
			}

			for (int i = 0; i < bossBullets.size(); i++) {
				bossBullets.get(i).logic();
			}

			for (int i = 0; i < enemys.size(); i++) {
				Enemy en = enemys.get(i);
				en.logic();
			}

			for (int i = 0; i < explosions.size(); i++) {
				Explosion ex = explosions.get(i);
				ex.logic();
			}
		} else if (gameState == "Menu")
			menu.logic();
	}

	public void render() { // renderiza imagens e graficos
		BufferStrategy bs = this.getBufferStrategy(); // corrige erro de tela piscando

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, width * scale, height * scale);

		for (int h = 0; h < 25; h++) {
			for (int v = 0; v < 30; v++) {
				g.drawImage(backGround, h * 16, v * 16, 16, 16, null);
			}

		}

		if (gameState == "Normal") {
			player.render(g);
			// en.render(g);

			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).render(g);
			}

			for (int i = 0; i < enemys.size(); i++) {
				enemys.get(i).render(g);
			}

			for (int i = 0; i < explosions.size(); i++) {
				explosions.get(i).render(g);
			}

			if (boss != null) {
				boss.render(g);
			}

			for (int i = 0; i < bossBullets.size(); i++) {
				bossBullets.get(i).render(g);
			}

			ui.render(g);

		} else if (gameState == "Menu") {
			menu.render(g);
		}

		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		bs.show();

	}

	@Override
	public void run() {

		while (isRunning) {
			logic();
			render();
			this.requestFocus();

			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) { // pressionar teclas

		if (gameState == "Normal") {

			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				player.shoot = true;
			}
		} else if (gameState == "Menu") {

			if (!menu.isShipSelection) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					menu.isShipSelection = true;
				}
			} else {
				
				// RESOLVER: SELEÇÃO PARA CIMA NÃO FUNCIONA
				if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					menu.currentOption++;
					if (menu.currentOption > menu.maxOption)
						menu.currentOption = 0;
				}if (e.getKeyCode() == KeyEvent.VK_W|| e.getKeyCode() == KeyEvent.VK_UP) {
					menu.currentOption++;
					if (menu.currentOption > menu.maxOption)
						menu.currentOption = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					gameState = "Normal";
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { // solto a tecla
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}

	}

}
