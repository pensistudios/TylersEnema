import java.awt.*;

public class Actor
{
	public Rectangle boundingBox;
	protected int imageCode;
	ResourceManager rm;
	
	//shit java
	int movespeed;
	
	public int x,y;
	protected int width,height;
	
	public Actor()
	{
		boundingBox = new Rectangle();
	}
	
	public Actor(int imagecode, ResourceManager rm_)
	{
		rm = rm_;
		imageCode = imagecode;
		width = rm.getImage(imagecode).getWidth();
		height = rm.getImage(imagecode).getHeight();
		boundingBox = new Rectangle(0,0,width,height);	
	}
	
	public void update(int xx,int yy)
	{
		x=xx;
		y=yy;
		boundingBox.setLocation(x,y);
		
	}
	
	public void resizeBox(double percent)
	{
		int nWidth = (int)((double)width*percent);
		int nHeight= (int)((double)height*percent);
		System.out.println(width + " " + height + " / " + percent + " " + nWidth + " " + nHeight);
		boundingBox.setSize(nWidth,nHeight);
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(rm.getImage(imageCode),x,y,null);
	}
	
	public void setSpeed(int m)
	{
		movespeed = m;
	}
	
	public int speed()
	{
		return movespeed;
	}
}