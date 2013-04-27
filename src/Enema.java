/**
 * @(#)Enema.java
 *
 * Sample Applet application
 *
 * @author 
 * @version 1.00 07/06/12
 */
 
import java.awt.*;
import java.applet.*;
import javax.swing.JApplet;
import java.awt.event.*;
import java.util.*;

public class Enema extends Applet implements Runnable
{
	Thread appThread = null;
	
	private int width;
	private int height;
	
	private boolean rightKeyPressed = false;
	private boolean leftKeyPressed = false;
	private boolean upKeyPressed = false;
	private boolean downKeyPressed = false;
	private boolean spaceKeyPressed = false;
	private boolean yesKeyPressed = false;
	private boolean noKeyPressed = false;
	
	private boolean titleState = false;
	private boolean instructState = false;
	private boolean gameState = false;
	private boolean winState = false;
	private boolean loseState = false;
	private boolean noState = false;
	
	private int spawntimer = 0;
	
	Random rand;
	
	private Graphics back;
	Image backImage;
	
	GameImage background;
	GameImage slice;
	
	GameImage title;
	GameImage instruct;
	GameImage win;
	GameImage lose;
	GameImage noscreen;
	
	Actor fingers;
	ThrobbingGSpot gspot;
	
	ResourceManager rm;
	
	LinkedList<Actor> spawnQueue;
	LinkedList<Actor> inMap;
	
	//ArrayList<Integer> commonSpawnIndex;
	//ArrayList<Integer> rareSpawnIndex;
	
	int commonSpawnIndex[];
	int rareSpawnIndex[];
	
	public void init() 
	{
		rm = new ResourceManager();
		
		spawnQueue = new LinkedList<Actor>();
		inMap = new LinkedList<Actor>();
		
		rand = new Random();
		
		width = (int)getSize().getWidth();
		height = (int)getSize().getHeight();
		
		addKeyListener( new KeyAdapter( ) {
        public void keyPressed(KeyEvent e)
        { processKey(e); }
        public void keyReleased(KeyEvent e)
         { processKeyRelease(e); }
      	});
      	
      	backImage =  createImage(width,height);
      	back = backImage.getGraphics();
      	
      	fingers = new Actor(rm.loadImage("data\\Fingers.png"),rm);
      	fingers.update(10,rand.nextInt(600)+50);
      	fingers.setSpeed(2);
      	fingers.resizeBox(.7);
      	
      	gspot = new ThrobbingGSpot(rm.loadImage("data\\gspot\\GspotA.png"),
      								rm.loadImage("data\\gspot\\GspotB.png"),rm);
      	gspot.update(860,rand.nextInt(654)+38);
      	
     	background = new GameImage();
     	background.loadImage("data\\Background.jpg");
     	slice = new GameImage();
     	slice.loadImage("data\\Slice.png");
     	
     	title = new GameImage();
     	title.loadImage("data\\screens\\splash.jpg");
     	instruct = new GameImage();
     	instruct.loadImage("data\\screens\\instructions.jpg");
     	noscreen = new GameImage();
     	noscreen.loadImage("data\\screens\\noscreen.jpg");
     	
     	win = new GameImage();
     	lose = new GameImage();
     	loadWinLose();
      	
      	//Load images into RM
      	
      	commonSpawnIndex = new int[8];
      	rareSpawnIndex = new int[1];
      	
      	commonSpawnIndex[0]=rm.loadImage("data\\enemies\\18Wheeler.png");
      	commonSpawnIndex[1]=rm.loadImage("data\\enemies\\BlueTruck.png");
      	commonSpawnIndex[2]=rm.loadImage("data\\enemies\\bus.png");
      	commonSpawnIndex[3]=rm.loadImage("data\\enemies\\YellowCar.png");
      	commonSpawnIndex[4]=rm.loadImage("data\\enemies\\Firetruck.png");
      	commonSpawnIndex[5]=rm.loadImage("data\\enemies\\train.png");
      	commonSpawnIndex[6]=rm.loadImage("data\\enemies\\truck.png");
      	commonSpawnIndex[7]=rm.loadImage("data\\enemies\\Dumper.png");
      	
      	rareSpawnIndex[0]=rm.loadImage("data\\enemies\\Ufo.png");
      	   	
      	titleState = true;
      	
	}
	
	public void start()
	{
		if( appThread == null)
		{
			appThread = new Thread(this);
			appThread.start();
		}
	}
	
	public void stop()
	{
		appThread = null;
	}
	
	public void paint(Graphics g) 
	{
		if(titleState)
		{
			g.drawImage(title.image,0,0,null);
		}
		if(instructState)
		{
			g.drawImage(instruct.image,0,0,null);
		}
		
		if(gameState)
		{
			g.drawImage(background.image,0,0,null);
			
		
			for(Actor a : inMap)
			{
				a.paint(g);
			}
			
			g.drawImage(slice.image,863,0,null);
			
			gspot.paint(g);
			
			fingers.paint(g);
			
		}
		
		if(winState)
		{
			g.drawImage(win.image,0,0,null);
		}
		
		if(loseState)
		{
			g.drawImage(lose.image,0,0,null);
		}
		if(noState)
		{
			g.drawImage(noscreen.image,0,0,null);
		}
	}
	
	public void update(Graphics g) 
    { 
		back.setColor (getBackground ()); 
		back.fillRect (0, 0, width, height); 

		back.setColor (getForeground()); 
		paint(back); 

		g.drawImage (backImage, 0, 0, this); 
    	 
    } 

	public void run()
  	{	
    	while( appThread != null ) 
    	{
    		if(gameState)
    		{
    	   		if(spawntimer == 50)
    	  	 	{
    	   			spawnCar();
    	   			spawntimer=0;
    	   		}
    	   		spawntimer++;
			}
			updateApp();
			
			repaint();
			
    		try { Thread.sleep(10);}  
    		catch(InterruptedException e) { stop(); }
    		
    		
    	}

    }
    
    private void spawnCar()
    {
    	int r = rand.nextInt(100);
    	if(r < 10)
    	{
    		//rarespawn
    		Actor temp = new Actor(rareSpawnIndex[rand.nextInt(1)],rm); //DANGER static coded num of enemies
    		temp.update(865,rand.nextInt(654)+38);
    		temp.setSpeed(rand.nextInt(5)+3);
    		temp.resizeBox(.7);
    		spawnQueue.addLast(temp); 
    	}
    	else
    	{
    		//commonspawn
    		Actor temp = new Actor(commonSpawnIndex[rand.nextInt(8)],rm); //DANGER static coded num of enemies
    		temp.update(865,rand.nextInt(654)+38);
    		temp.setSpeed(rand.nextInt(5)+3);
    		temp.resizeBox(.7);
    		spawnQueue.addLast(temp);
    	}
    }
	
	public void updateApp()
	{
		if(loseState)
		{
			if(yesKeyPressed)
			{
				spawnQueue.clear();
				inMap.clear();
				loadWinLose();
				fingers.update(10,rand.nextInt(600)+50);
				gspot.update(860,rand.nextInt(654)+38);
				gameState=true;
				loseState = false;
			}
			if(noKeyPressed)
			{
				System.out.println("Im here cockforbrains");
				noState = true;
			}	
		}
		
		if(instructState)
		{
			if(spaceKeyPressed)
			{
				instructState=false;
				gameState = true;
			}
		}
		
		if(gameState)
		{
			Actor temp;
			if(spawnQueue.size() > 0)
			{
				temp = spawnQueue.pop();
				inMap.addLast(temp);
			}
		
		
			if(rightKeyPressed)
			{
				fingers.update(fingers.x+fingers.speed(),fingers.y);
			}
			if(leftKeyPressed)
			{
				fingers.update(fingers.x-fingers.speed(),fingers.y);
			}
			if(upKeyPressed)
			{
				fingers.update(fingers.x,fingers.y-fingers.speed());
			}
			if(downKeyPressed)
			{
				fingers.update(fingers.x,fingers.y+fingers.speed()); 
			}
		
			if(fingers.boundingBox.intersects(gspot.boundingBox))
			{
				gameState = false;
				winState = true;
			}
		
			for(int c = 0; c < inMap.size(); c++)
			{
				Actor a = inMap.get(c);
				a.update(a.x-a.speed(),a.y); 
			
				if(a.x < -1000)
				{
					inMap.remove(a);
				}
			
				if(a.boundingBox.intersects(fingers.boundingBox))
				{
					gameState = false;
					loseState = true;
				}
			}
		}
		if(titleState)
		{
			if(spaceKeyPressed)
			{
				titleState=false;
				instructState = true;
				spaceKeyPressed = false;
			}
		}
	}
	
	private void loadWinLose()
	{
		int num = rand.nextInt(5)+1;
		
		switch(num)
		{//COCK
			case 1:
				win.loadImage("data\\screens\\win1.jpg");
				lose.loadImage("data\\screens\\lose1.jpg");
				break;
			case 2:
				win.loadImage("data\\screens\\win2.jpg");
				lose.loadImage("data\\screens\\lose2.jpg");
				break;
			case 3:
				win.loadImage("data\\screens\\win3.jpg");
				lose.loadImage("data\\screens\\lose3.jpg");
				break;
			case 4:
				win.loadImage("data\\screens\\win4.jpg");
				lose.loadImage("data\\screens\\lose4.jpg");
				break;
			case 5:
				win.loadImage("data\\screens\\win5.jpg");
				lose.loadImage("data\\screens\\lose5.jpg");
				break;
			
			
		}
	}
		
	private void processKey(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			rightKeyPressed = true;
		}
		
		if(keyCode == KeyEvent.VK_LEFT)
		{
			leftKeyPressed = true;
		}
		
		if(keyCode == KeyEvent.VK_DOWN)
		{
			downKeyPressed = true;
		}
		
		if(keyCode == KeyEvent.VK_UP)
		{
			upKeyPressed = true;
		}
		if(keyCode == KeyEvent.VK_SPACE)
		{
			spaceKeyPressed = true;
		}
		if(keyCode == KeyEvent.VK_Y)		
		{
			yesKeyPressed = true;
		}
		if(keyCode == KeyEvent.VK_N)
		{
			noKeyPressed = true;
		}
	}
	//IM A CUMEATOR
	private void processKeyRelease(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			rightKeyPressed = false;
		}
		
		if(keyCode == KeyEvent.VK_LEFT)
		{
			leftKeyPressed = false;
		}
		
		if(keyCode == KeyEvent.VK_DOWN)
		{
			downKeyPressed = false;
		}
		
		if(keyCode == KeyEvent.VK_UP)
		{
			upKeyPressed = false;
		}
		if(keyCode == KeyEvent.VK_SPACE)
		{
			spaceKeyPressed = false;
		}
		if(keyCode == KeyEvent.VK_Y)		
		{
			yesKeyPressed = false;
		}
		if(keyCode == KeyEvent.VK_N)
		{
			noKeyPressed = false;
		}
	}
}
//hehe