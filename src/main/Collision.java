package main;

import java.awt.Graphics;

public class Collision {

	private Ball ball = new Ball();
	private Player player = new Player();
	private Brick brick = new Brick();
	private int score;
	int ballY;
	int playerY;

	boolean gameOver;

	private SoundPlayer soundPlayer = new SoundPlayer();

	public Collision() {
		gameOver = false;
	}

	public void update() {
		cfcBallPlayer();
		cfcBallLost();
	}

	public void cfcBallPlayer() {
		if (!ball.isStationary()) {
			if (ball.getX() >= player.getX()
					&& ball.getX() <= player.getX() + 50)
				if (ball.getY() + 6 >= player.getY()
						&& ball.getY() + 6 <= player.getY() + 15) {
					soundPlayer.play(0);
					if (ball.getX() + 6 >= player.getX()
							&& ball.getX() + 6 <= player.getX() + 6) {
						ball.setDx(-7);
						ball.setDy(4);
					}
					if (ball.getX() + 6 >= player.getX() + 7
							&& ball.getX() + 6 <= player.getX() + 13) {
						ball.setDx(-5);
						ball.setDy(6);
					}
					if (ball.getX() + 6 >= player.getX() + 14
							&& ball.getX() + 6 <= player.getX() + 20) {
						ball.setDx(-3);
						ball.setDy(8);
					}
					if (ball.getX() + 6 >= player.getX() + 21
							&& ball.getX() + 6 <= player.getX() + 28) {
						if (ball.getDx() < 0) {
							ball.setDx(-1);
						} else {
							ball.setDx(1);
						}
						ball.setDy(10);
					}
					if (ball.getX() + 6 >= player.getX() + 29
							&& ball.getX() + 6 <= player.getX() + 35) {
						ball.setDx(3);
						ball.setDy(8);
					}
					if (ball.getX() + 6 >= player.getX() + 36
							&& ball.getX() + 6 <= player.getX() + 42) {
						ball.setDx(5);
						ball.setDy(6);
					}
					if (ball.getX() + 6 >= player.getX() + 43
							&& ball.getX() + 6 <= player.getX() + 50) {
						ball.setDx(7);
						ball.setDy(4);
					}
					ball.setDy(ball.getDy() * -1);
				}
		}
	}

	public void cfcBallBrick() {
		soundPlayer.play(1);
		ball.setDy(ball.getDy() * -1);
	}

	public void cfcBallLost() {
		if (ball.getY() > player.getY() + 20) {
			gameOver = true;
			ball.setX(-100);
			ball.setY(-100);
			
		}

	}

	public boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
