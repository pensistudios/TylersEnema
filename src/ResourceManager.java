import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ResourceManager
{
	ArrayList<GameImage> images;
	
	private int CURRENTKEY = 0;
	
	public ResourceManager()
	{
		images = new ArrayList<GameImage>();
	}
	
	public int loadImage(String file)
	{
		images.add(new GameImage());
		int t = CURRENTKEY;
		CURRENTKEY++;
		images.get(t).loadImage(file);
		return t;
	}
	
	public BufferedImage getImage(int i)
	{
		return images.get(i).image;
	}
}