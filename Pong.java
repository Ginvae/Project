package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener
{

	public static Pong pong;
	public int width = 700, height = 700;
	public Renderer renderer;
	public Pamalo player1;
	public Pamalo player2;
	public Bola bola;
	public boolean w, s, up, down;
	public int gameStatus = 0, scoreLimit = 5, playerWon; //0 = Menu, 1 = Paused, 2 = Playing, 3 = Over
	public int botDifficulty, botMoves, botCooldown = 0;
	public Random random;
	public JFrame jframe;

	public Pong()
	{
		Timer timer = new Timer(20, this);

		jframe = new JFrame("Pong");

		renderer = new Renderer();

		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);

		timer.start();
	}

	public void start()
	{
		gameStatus = 2;
		player1 = new Pamalo(this, 1);
		player2 = new Pamalo(this, 2);
		bola = new Bola(this);
	}

	public void update()
	{
		if (player1.score >= scoreLimit)
		{
			playerWon = 1;
			gameStatus = 3;
		}

		if (player2.score >= scoreLimit)
		{
			gameStatus = 3;
			playerWon = 2;
		}

		if (w)
		{
			player1.move(true);
		}
		if (s)
		{
			player1.move(false);
		}

		
		if (up)
		{
				
			player2.move(true);
		}
		if (down)
		{
			player2.move(false);
		}
		
		bola.update(player1, player2);
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (gameStatus == 0)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PONG", width / 2 - 75, 50);
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 75);
			g.drawString("Press Escape to Exit", width / 2 - 150, height / 2 - 25);
			g.drawString("<< Score Limit: " + scoreLimit + " >>", width / 2 - 150, height / 2 + 75);
			
			g.setFont(new Font("Arial", 1, 30));
			g.drawString("Controls", width / 2 - 330, height / 2 + 190);
			
			
			
			g.setFont(new Font("Arial", 1, 20));
			g.drawString("Player 1 Controls: W = up, S = down.", width / 2 - 330, height / 2 + 225);
			g.drawString("Player 2 Controls: ArrowUpKey = up, ArrowDownKey = down. ", width / 2 - 330, height / 2 + 250);
			g.drawString(" ArrowLeftKey = decrease score limit.", width / 2 - 335, height / 2 + 275);
			g.drawString(" ArrowRightKey = increase score limit.", width / 2 - 335, height / 2 + 300);
			
			
			
			
			}

		if (gameStatus == 1)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2 - 25);
		}

		if (gameStatus == 1 || gameStatus == 2)
		{
			g.setColor(Color.WHITE);

			g.setStroke(new BasicStroke(5f));

			g.drawLine(width / 2, 0, width / 2, height);

			g.setStroke(new BasicStroke(2f));

			g.drawOval(width / 2 - 150, height / 2 - 150, 300, 300);

			g.setFont(new Font("Arial", 1, 50));

			g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
			g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);

			player1.render(g);
			player2.render(g);
			bola.render(g);
		}

		if (gameStatus == 3)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			if (playerWon == 1)
			{
				g.drawString("Player " + playerWon + " Wins!", width / 2 - 165, 200);
			}

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
			g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (gameStatus == 2)
		{
			update();
		}

		renderer.repaint();
	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = true;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		else if (id == KeyEvent.VK_RIGHT)
		{
			
			 if (gameStatus == 0)
			{
				scoreLimit++;
			}
		}
		else if (id == KeyEvent.VK_LEFT)
		{
			 if (gameStatus == 0 && scoreLimit > 1)
			{
				scoreLimit--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3))
		{
			gameStatus = 0;
		}
		
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 0))
		{
	      System.exit(0);
		}
		
		else if (id == KeyEvent.VK_SPACE)
		{
			if (gameStatus == 0 || gameStatus == 3)
			{
				start();
			}
			else if (gameStatus == 1)
			{
				gameStatus = 2;
			}
			else if (gameStatus == 2)
			{
				gameStatus = 1;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
			
			
	}
