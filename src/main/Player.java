package main;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	private final static int WIDTH = 50;
	private final static int HEIGHT = 15;
	private Image image = new ImageIcon(this.getClass().getResource(
			"/raw/images/player.png")).getImage();

	private static int x;
	private static int y;
	private int dX;
	private int dY;
	private int storedDx;
	private int storedDy;

	public Player() {
		y = 0;
		x = 98;
		storedDx = 12;

	}

	public void update() {

		if (x + WIDTH + 5 > 300) {
			x = 300 - WIDTH - 5;
		} else if (x < 0) {
			x = 0;
		}
		x += dX;
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

	public int getdX() {
		return dX;
	}

	public void setdX(int dX) {
		this.dX = dX;
	}

	public int getdY() {
		return dY;
	}

	public void setdY(int dY) {
		this.dY = dY;
	}

	public void setStoredDx(int storedDx) {
		this.storedDx = storedDx;
	}

	public int getStoredDx() {
		return storedDx;
	}

}
