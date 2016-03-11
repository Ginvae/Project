package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.swing.ImageIcon;

public class Bola implements ImageObserver {

	public int x, y, width = 25, height = 25;

	public int xDirection, yDirection;

	public Random random;

	private Pong pong;

	public int IncreaseBallSpeed;
	
	Image ball;

	public Bola(Pong pong) {
		this.pong = pong;

		this.random = new Random();

		Respawn();
		
		ImageIcon img = new ImageIcon("C:/Users/Oyah/workspace/Pong Mendoza/Images/ball.png");
		ball = img.getImage();
	}

	public void update(Pamalo pamalo1, Pamalo pamalo2) {
		System.out.print(this.y);
		int speed = 5;

		x += xDirection * speed;
		y += yDirection * speed;

		if (this.y + height - yDirection > pong.height || this.y + yDirection < 0) {
			if (this.yDirection < 0) {
				this.y = 0;
				this.yDirection = random.nextInt(4);

				if (yDirection == 0) {
					yDirection = 1;
				}
			} else {
				this.yDirection = -random.nextInt(4);
				this.y = pong.height - height;

				if (yDirection == 0) {
					yDirection = -1;
				}
			}
		}

		if (checkCollision(pamalo1) == 1) {
			this.xDirection = 1 + (IncreaseBallSpeed / 2);
			this.yDirection = -2 + random.nextInt(4);

			if (yDirection == 0) {
				yDirection = 1;
			}

			IncreaseBallSpeed++;
		} else if (checkCollision(pamalo2) == 1) {
			this.xDirection = -1 - (IncreaseBallSpeed / 2);
			this.yDirection = -2 + random.nextInt(4);

			if (yDirection == 0) {
				yDirection = 1;
			}

			IncreaseBallSpeed++;
		}

		if (checkCollision(pamalo1) == 2) {
			pamalo2.score++;
			Respawn();
		} else if (checkCollision(pamalo2) == 2) {
			pamalo1.score++;
			Respawn();
		}
	}

	public void Respawn() {
		this.IncreaseBallSpeed = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;

		this.yDirection = -2 + random.nextInt(4);

		if (yDirection == 0) {
			yDirection = 1;
		}

		if (random.nextBoolean()) {
			xDirection = 1;
		} else {
			xDirection = -1;
		}
	}

	public int checkCollision(Pamalo pamalo) {
		if (this.x < pamalo.x + pamalo.width && this.x + width > pamalo.x && this.y < pamalo.y + pamalo.height
				&& this.y + height > pamalo.y) {
			return 1; // bounce
		} else if ((pamalo.x > x && pamalo.pamaloNumber == 1) || (pamalo.x < x - width && pamalo.pamaloNumber == 2)) {
			return 2; // score
		}

		return 0; // nothing
	}

	public void render(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.drawImage(ball, x, y, this);
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

}