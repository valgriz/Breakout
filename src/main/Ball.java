package main;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Ball {

	private Player player = new Player();

	private final static int WIDTH = 12;
	private final static int HEIGHT = 12;
	private Image image = new ImageIcon(this.getClass().getResource(
			"/raw/images/ball.png")).getImage();

	private static int x;
	private static int y;
	private static int dX;
	private static int dY;
	private static int storedDx;
	private static int storedDy;

	private static boolean stationary;

	public Ball() {
		stationary = true;
		dX = 4;
		dY = -7;

	}

	public void update() {
		move();
	}

	public void move() {
		if (stationary) {
			x = player.getX() + 25;
			y = player.getY() - 5;

		} else {
			if (x >= 288 || x <= 6) {
				dX = -dX;
			} else if (y <= 6) {
				dY = -dY;
			}
			x += dX;
			y += dY;
		}
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDy(int dY) {
		this.dY = dY;
	}

	public int getDy() {
		return dY;
	}

	public void setDx(int dX) {
		this.dX = dX;
	}

	public int getDx() {
		return dX;
	}

	public boolean isStationary() {
		return stationary;
	}

	public void setStationary(boolean stationary) {
		this.stationary = stationary;
	}

	Rectangle rect;

	public Rectangle getRect() {
		return new Rectangle(x - 6, y - 6, WIDTH, HEIGHT);
	}
}
