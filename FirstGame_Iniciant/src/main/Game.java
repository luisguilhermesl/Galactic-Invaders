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
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;

// 4 -------------				//13 interface própria do java
public class Game extends Canvas implements Runnable, KeyListener { // assim temos acesso a certos elementos gráficos e
																	// a maneiras de manipular o tamanho da tela
//------------------								/*27. adicionando uma nova interface que vai nos permitir interagir com o teclado */				

	// 2. -//33---------------/38---------
	public static int width = 120; // largura da tela
	public static int height = 160; // altura
	public static int scale = 3; // escalocar a tela do jogo, multiplicando os tamanhos 3x, mantendo a qualidade
	public JFrame frame;
	// 2-----------------------------
	private BufferedImage image; // 9. ------- permite dar imagens ao projeto e recebe-las de fora do projeto
	private boolean isRunning = true; // 15
	private Thread thread;
	// 16
	/* 23 */public static Player player; // 43

	/* 56 */public static SpriteSheet spriteSheet;

	/* 36 */ // private Enemy en; 49 apagar este
	/* 49 */private Spawner spawner;
	private UI ui;

	/* 52 */public static Random rand;

	/* 40 */public static ArrayList<Enemy> enemys;
	/* 40 */public static ArrayList<Bullet> bullets;

	private BufferedImage backGround;
	
	public static String gameState="Normal"; //normal, GameOver, Menu

	// 5----------
	public Game() {
		/* 28 */this.addKeyListener(this);
		this.setPreferredSize(new Dimension(width * scale, height * scale));
		image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_BGR); // 10, quero cobrir a tela
																								// inteira do jogo
		criarTela();
		thread = new Thread(this);// 17. o this' quer dizer que essa thread pertence a classe game

		/* 57 */spriteSheet = new SpriteSheet("/spretsheet.png"); // as imagens do jogo tem que ser carregadas antes do
																	// player

		backGround = spriteSheet.getSprite(16 * 3, 0, 16, 16);

		/* 24 */ player = new Player();
		ui = new UI();

		/* 36 */ // en = new Enemy(); 49.apagar este

		/* 40 */enemys = new ArrayList<Enemy>();
		/* 40 */bullets = new ArrayList<Bullet>();

		/* 49 */spawner = new Spawner();

		/* 52 */rand = new Random();

	}
	// -------------------------

	// 1 ------------------
	public static void main(String[] args) {
		Game game = new Game();
		// -----------------------------
		game.start(); // 21. responsavel pelo start e dar inicio a nossa thread

	}

	// 3. -------------------------------
	public void criarTela() {
		frame = new JFrame();

		// 6 -----------------------
		frame.add(this); // estou falando ao meu frame que o que ela deve usar é essa linha de codigo (ln
							// 21)
		frame.pack(); // pegando o tamanho
		// ------------------

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // O programa vai parar de rodar sempre que fecharmos a
																// tela
		frame.setLocationRelativeTo(null); // Para tela abrir a tela do jogo vai abrir no meio da tela do computador
		frame.setResizable(false); // não pode alterar o tamanho da tela
		frame.setVisible(true); // deixar a tela do jogo visivel
	}
	// --------------------------------------

	// 19. começar a thread ----------------------
	public synchronized void start() {
		isRunning = true;
		thread.start();
	} // 19--------------------------------------------

	// 20----------------------------------------------
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// -----------------------------------------------20

	// 7. responsavel por atualizar a lógica do jogo ----------
	public void tick() {
		
		if(gameState == "Normal") {
		/* 25 */player.tick(); // responsável por executar toda a logica e as imagens das outras classes

		/* 36 */// en.tick(); 50 substituir por (abaixo)
		/* 50 */spawner.tick();

		// 41 quero que mostre todas as bullets ue estiverem na
		// lista----------------------------------------
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.tick();
		}

		// 51-----------------------------------------------------
		for (int i = 0; i < enemys.size(); i++) {
			Enemy en = enemys.get(i);
			en.tick();
		}
		// ----------------------------------------------------------
		}
	}
	// ------------------------------------------

	// 8. trabalhar a imagem e renderização do jogo
	public void render() {
		BufferStrategy bs = this.getBufferStrategy(); // permite fazer com que a nossa tela de jogo não pisque
		// se tentarmos colocar elementos jogaveis ai sem ele, a tela vai começar a
		// piscar/falhar

		if (bs == null) { // faço um if pois a primeira vez que ele executa o bs é null, entao fazemos
							// assim
			this.createBufferStrategy(3);
			return; // vai funcionar como um loop, onde quando ele for retornar, ele vai voltar ao
					// inicio do render e executar o bs
		}

		/* 11 */Graphics g = image.getGraphics();// 11. me permite colocar elementos graficos, como quadrados,
													// triangulos...

		// 32 criando um retangulo da mesma cor da nossa tela, para o player nao se
		// estivar mais, mas sim se mover
		g.setColor(Color.black);
		g.fillRect(0, 0, width * scale, height * scale);

		for (int h = 0; h < 25; h++) {
			for (int v = 0; v < 30; v++) {
				g.drawImage(backGround, h * 16, v * 16, 16, 16, null);
			}
		}

		/* 26 */player.render(g); // renderização - agora se executar, teremos o player na tela, não só um
									// retangulo generico
		/* 36 */ // en.render(g); 50 apagar este

		/* 41 */
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.render(g);
		}

//51-----------------------------------------------------
		for (int i = 0; i < enemys.size(); i++) {
			Enemy en = enemys.get(i);
			en.render(g);
		}
//----------------------------------------------------------		
		ui.render(g);
		// g.setColor(Color.black);//12

		// apagar os abaixo depois que criar a classe player
		// g.setColor(Color.white);//22
		// g.fillRect(10,10, 32, 32);//12 - x , y,widght, height

		g = bs.getDrawGraphics(); // 11 desenhando a imagem
		g.drawImage(image, 0, 0, width * scale, height * scale, null);// 11
		bs.show();// 11
	}
	// ----------------

	// 14 é a thread do noss projeto. temos ue colocar os dois metodos tick e render
	// aqui dentro
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) { // 14. para o jogo não rodar tao rápido, temos que definir um limite", qque será
							// 60x por seg ao mesmo tempo
			tick();// 14
			render();// 14
			this.requestFocus();
			// ---------------------------------------18
			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 18------------------------------------
		}

	}

	@Override
	public void keyTyped(KeyEvent e) { // toques na tela
		// TODO Auto-generated method stub

	}

	// 29 adicionando a letra A e D para direita e esquerda -----------
	@Override
	public void keyPressed(KeyEvent e) { // pressionar alguma tecla
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}

		// 47---------------------------------------------------------
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.shot = true; // não preciso fazer um false porque já fizemos o comando para parar de atirar
								// na outra classe
		}
		// ------------------------------------------------------------

	}

	@Override
	public void keyReleased(KeyEvent e) { // soltar alguma tecla
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}

	}// 29 -------------------------------------------------------------------------

}
