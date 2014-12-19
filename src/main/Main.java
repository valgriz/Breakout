package main;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements Runnable, KeyListener {

	private final static int WIDTH = 300;
	private final static int HEIGHT = 400;
	private final int ARRAY_SIZE = 8;

	private Player player = new Player();
	private Ball ball;
	private Brick[][] brickArray = new Brick[ARRAY_SIZE][14];
	private Brick brick = new Brick();
	private boolean[][] visibleArray = new boolean[ARRAY_SIZE][ARRAY_SIZE * 2];
	private Collision collision = new Collision();
	private Thread thread = new Thread(this);

	private boolean isLeft;
	private boolean isRight;
	private boolean animFinished;
	private boolean isPaused;
	private boolean hsView;

	private static boolean gameOver;

	private static double tTime;
	private static int gosPos;
	private static int gosWait;
	private static int score;
	private static int sleepTime;
	private Thread timer;

	private SoundPlayer soundPlayer = new SoundPlayer();
	JFrame hs = new JFrame();

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new Main());
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Breakout Time Challenge");
		frame.setVisible(true);

	}

	public Main() {
		ball = new Ball();
		player = newPlayer();
		addKeyListener(this);
		thread.start();
		setDoubleBuffered(true);
		setFocusable(true);
		setBackground(new Color(137, 251, 255));
		player.setY(HEIGHT - 50);
		firstTime();
	}

	public void firstTime() {
		gameOver = true;
		gosPos = 0;
		animFinished = true;
		init();
	}

	public void init() {
		ball.setStationary(true);
		isLeft = false;
		isRight = false;
		for (int x = 0; x < ARRAY_SIZE; x++) {
			for (int y = 0; y < 14; y++) {
				visibleArray[x][y] = true;
			}
		}
		score = 0;
		tTime = 30.00;
		collision.setGameOver(false);
		isPaused = false;
		sleepTime = 30;
		gosWait = 0;
		timeThis();
		timer.start();
		hsView = false;
	}

	public Player newPlayer() {
		player = new Player();
		return player;
	}

	public void timeThis() {
		timer = new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(10);
					} catch (Exception e) {
					}
					if (!(ball.isStationary())) {
						tTime -= 0.01;
					}
					if (tTime <= 0.00) {
						soundPlayer.play(3);
						tTime = 0.00;
						gameOver = true;
					}
				}
			}
		};
	}

	public void update() {
		collision.setScore(score);
		player.update();
		ball.update();
		collision.update();
		if (collision.getGameOver()) {
			gameOver = true;
		}
		switch (score) {
		case 10:
			sleepTime = 29;
			break;
		case 20:
			sleepTime = 28;
			break;
		case 30:
			sleepTime = 27;
			break;
		case 40:
			sleepTime = 26;
			break;
		case 50:
			sleepTime = 25;
			break;
		case 60:
			sleepTime = 24;
			break;
		case 70:
			sleepTime = 23;
			break;
		case 80:
			sleepTime = 22;
			break;
		case 90:
			sleepTime = 21;
			break;
		case 100:
			sleepTime = 20;
			break;
		}
	}

	public void newHighScoreFrame() {
		Dimension screenDimensions = Toolkit.getDefaultToolkit()
				.getScreenSize();
		int sW = screenDimensions.width;
		int sH = screenDimensions.height;
		if (hsView) {
			hs.setSize(300, 290);
			hs.setVisible(true);
			hs.setLocationRelativeTo(null);
			hs.setTitle("High Scores");
			hs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		hsView = false;
	}

	public void paint(Graphics g) {
		super.paint(g);
		paintPaddle(g);
		paintBall(g);
		paintBrick(g);
		paintScore(g);
		if (gameOver) {
			paintGameOver(g);
		}
		if (isPaused) {
			paintGamePaused(g);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();

	}

	public void paintGamePaused(Graphics g) {
		Image gamePausedScreen = new ImageIcon(this.getClass().getResource(
				"/raw/images/pausedScreen.png")).getImage();
		g.drawImage(gamePausedScreen, 0, 0, this);
	}

	public void paintGameOver(Graphics g) {
		timer.stop();
		ball.setDx(0);
		ball.setDy(0);
		g.fillRect(gosPos, 30, WIDTH, 300);
		Image gameOverScreen = new ImageIcon(this.getClass().getResource(
				"/raw/images/gameOverScreen.png")).getImage();
		g.drawImage(gameOverScreen, gosPos, 0, this);
		animFinished = false;
		if (gosWait > 29) {
			if (score > 0) {

			}
			if (gosPos < 0) {
				gosPos += 5;
			} else {
				animFinished = true;
			}
		} else {
			gosWait++;
		}

		if (gosWait == 15 && score > 0 && tTime != 0) {
			hsView = true;
			soundPlayer.play(4);
			// newHighScoreFrame();
		}
	}

	public void paintPaddle(Graphics g) {
		g.drawImage(player.getImage(), player.getX(), player.getY(), this);
	}

	public void paintBall(Graphics g) {
		g.drawImage(ball.getImage(), ball.getX() - 6, ball.getY() - 6, this);
	}

	public void paintBrick(Graphics g) {
		for (int x = 0; x < ARRAY_SIZE; x++) {
			for (int y = 0; y < 14; y++) {
				if (visibleArray[x][y]) {
					g.drawImage(brick.getImage(), 4 + (x * (33 + 3)),
							3 + 30 + (y * (13 + 3)), this);
					if (ball.getRect().intersects(
							brick.getRect(4 + (x * (33 + 3)),
									3 + 30 + (y * (13 + 3))))) {
						visibleArray[x][y] = false;
						score++;
						collision.cfcBallBrick();
					}
				}
			}
		}
	}

	public void paintScore(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(new Color(11, 86, 180));
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g2d.drawString("SCORE: " + score, 4, 25);
		g.fillRect(146, 3, 2, 28);
		g.drawString("TIME: " + tTime, 155, 25);
		g.setColor(new Color(137, 251, 255));
		g.fillRect(288, 2, 10, 28);
	}

	@Override
	public void run() {
		while (true) {
			try {
				thread.sleep(sleepTime);
			} catch (Exception e) {
			}
			update();
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			player.setdX(player.getStoredDx() * -1);
			isLeft = true;
		} else if (key == KeyEvent.VK_RIGHT) {
			player.setdX(player.getStoredDx());
			isRight = true;
		} else if (key == KeyEvent.VK_SPACE) {
			if (gameOver) {
				if (animFinished) {
					init();
					gameOver = false;
					gosPos = -300;
				}
			} else {
				ball.setStationary(false);
				if (tTime != 0 && tTime != 30) {
					if (!isPaused) {
						thread.suspend();
						timer.suspend();
						isPaused = true;
						repaint();

					} else {
						thread.resume();
						timer.resume();
						isPaused = false;
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			isLeft = false;
			if (!isRight) {
				player.setdX(0);
			}
		} else if (key == KeyEvent.VK_RIGHT) {
			isRight = false;
			if (!isLeft) {
				player.setdX(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
