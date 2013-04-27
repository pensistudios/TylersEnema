//Should Extend Actor but was not working for some reason..

import java.awt.*;

public class ThrobbingGSpot
{
	int frame1;
	int frame2;
	
	ResourceManager rm;
	
	public Rectangle boundingBox;
	
	public int x,y;
	protected int width,height;
	
	private int framecount;
	
	private final int frameTimer = 30;
	
	public ThrobbingGSpot(int i1, int i2, ResourceManager rm_)
	{
		frame1 = i1;
		frame2 = i2;
		rm = rm_;
		width = rm.getImage(frame1).getWidth(); //assumes frame1 and 2 have same sizes
		height = rm.getImage(frame1).getHeight();
		boundingBox = new Rectangle(0,0,20,20);
	}
	
	public void update(int xx,int yy)
	{
		x=xx;
		y=yy;
		boundingBox.setLocation(x+27,y+27);
		
	}
	
	public void paint(Graphics g)
	{
		if(framecount<frameTimer)
		{
			g.drawImage(rm.getImage(frame1),x,y,null);
		}
		else
		{
			g.drawImage(rm.getImage(frame2),x,y,null);
		}
		if(framecount == frameTimer*2)
			framecount = 0;
		
		
		framecount++;
	}
	
	
}