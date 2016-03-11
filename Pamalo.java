package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Pamalo implements ImageObserver
{

	public int pamaloNumber;

	public int x, y, width = 50, height = 250;

	public int score;
	
	public Image pamalo;

	
	public Pamalo(Pong pong, int pamaloNumber)
	{
		
		
		ImageIcon img = new ImageIcon("C:/Users/Oyah/workspace/Pong Mendoza/Images/paddle1.png");
		pamalo = img.getImage();
		
		
		this.pamaloNumber = pamaloNumber;

		if (pamaloNumber == 1)
		{
			x = 0;
		}

		if (pamaloNumber == 2)
		{
			x = pong.width - width;
		}

		y = pong.height / 2 - this.height / 2;
	}

	public void render(Graphics g)
	{
		
		
		g.setColor(Color.WHITE);
		g.drawImage(pamalo, x, y, this);
	}

	public void move(boolean up)
	{
		int speed = 15;

		if (up)
		{
			if (y - speed > 0)
			{
				y -= speed;
			}
			else
			{
				y = 0;
			}
		}
		else
		{
			if (y + height + speed < Pong.pong.height)
			{
				y += speed;
			}
			else
			{
				y = Pong.pong.height - height;
			}
		}
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

}