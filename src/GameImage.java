import java.awt.image.*;
import java.io.File;

public class GameImage
{
	public BufferedImage image;
	
	
	public GameImage()
	{
	
	}
	
	public void loadImage(String path)
	{
		System.out.println(path);
		try { image = javax.imageio.ImageIO.read(new File(path)); }
		catch (Exception e) { System.out.println("Image \""+path+"\" could not be loaded..."); }
	}
}