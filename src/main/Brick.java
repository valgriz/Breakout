package main;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Brick {

	Ball ball = new Ball();

	private final static int WIDTH = 33;
	private final static int HEIGHT = 13;
	private Image image = new ImageIcon(this.getClass().getResource(
			"/raw/images/brick.png")).getImage();

	private static int x;
	private static int y;

	public Brick() {
		x = 10;
		y = 10;
	}

	public void update() {

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

	Rectangle rect;

	public Rectangle getRect(int x, int y) {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

}
