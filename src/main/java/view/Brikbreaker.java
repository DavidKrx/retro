package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

import javafx.event.ActionEvent;

public class Brikbreaker extends JPanel implements KeyListener, ActionListener{

	private boolean play =false;
	private int score=0;
	private int totalBricks=21;
	private Timer timer;
	private int delay=8;
	private int playerx=310;
	private int ballposX=120;
	private int ballposY=350;
	private int balldirX=-1;
	private int balldirY=-2;
	
	private MapGeneretaed map;
	
	public Brikbreaker() {
	map=new MapGeneretaed(3, 7);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	timer=new Timer(delay,this);
	timer.start();
	}
	public void paint(Graphics gc) {
		//background
		gc.setColor(Color.black);
		gc.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)gc);
		//scores
		gc.setColor(Color.white);
		gc.setFont(new Font("serif", Font.BOLD, 25));
		gc.drawString(""+score, 590, 30);
		
		//borders
		gc.setColor(Color.yellow);
		gc.fillRect(0, 0, 3, 592);
		gc.fillRect(0, 0, 692, 3);
		gc.fillRect(692, 0, 3, 592);
		
		//paddle
		gc.setColor(Color.green);
		gc.fillRect(playerx, 550, 100, 8);
		//ball
		gc.setColor(Color.yellow);
		gc.fillOval(ballposX, ballposY, 20, 20);
		
		if(ballposY >570) {
			play=false;
			balldirX=0;
			balldirY=0;
			gc.setColor(Color.red);
			gc.setFont(new Font("serif",Font.BOLD,20));
			gc.drawString("Game Over, Scores ", 190,300);
			
			gc.setFont(new Font("serif",Font.BOLD,20));
			gc.drawString("Press enter to restart ", 230,350);
		}
		
		gc.dispose();
	}
	
	
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerx,550,100,8))){
				balldirY=-balldirY;
			}
			
			A:for(int i=0; i<map.map.length;i++) {
				for(int j=0; j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j* map.brickWidth+80;
						int brickY=i* map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							if(ballposX+19 <= brickRect.x || ballposX+1 >= brickRect.x + brickRect.width) {
								balldirX=-balldirX;
								
							}else {
								balldirY=-balldirY;
							}
							break A;
						
						}
					}
				}
			}
			ballposX +=balldirX;
			ballposY +=balldirY;
			if(ballposX<0) {
				balldirX=-balldirX;
			}
			if(ballposY<0) {
				balldirY=-balldirY;
			}
			if(ballposX>670) {
				balldirX=-balldirX;
			}
		}
		repaint();
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerx >=600) {
				playerx=600;
			} else {
				moveRight();
			}
			
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerx <10) {
				playerx=10;
			} else {
				moveLeft();
			}
			
			
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				balldirX=-1;
				balldirY=-2;
				playerx=310;
				score=0;
				totalBricks=21;
				map=new MapGeneretaed(3, 7);
				repaint();
			}
		}
	}
	private void moveLeft() {
		play=true;
		playerx-=20;
		
	}
	private void moveRight() {
		play=true;
		playerx+=20;
		
	}
	
	
	
	
}
