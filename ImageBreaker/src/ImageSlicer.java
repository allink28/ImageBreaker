import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * 
 * @author Allen
 *
 */
public class ImageSlicer {
	BufferedImage img;
	String[] fileInfo;
	
	public ImageSlicer(BufferedImage i, String filename){
		img = i;
		fileInfo = filename.split("\\.");
	}

	/**
	 * Breaks the given image up and saves the sub images to the user's desktop.
	 * @param width Width of Subimages
	 * @param height Height of Subimages
	 */
	public void slice(int width, int height){		
		int x = img.getWidth()/width;
		int y = img.getHeight()/height;
		
		if(x == 0 && y == 0){
			infoBox("SubImages are larger than original!", "Error");
			System.exit(2);
		}
		if (img.getWidth()%width > 0){
			++x;
		}
		if (img.getHeight()%height > 0){
			++y;
		}
		
		BufferedImage[][] images = new BufferedImage[x][y];
		File outputfile = null;
		for (int j = 0; j < y; ++j){
			for (int i = 0; i < x; ++i){
				int w = width;
				int h = height;
				if (i+1 == x && img.getWidth()%width != 0){
					w = img.getWidth()%width;					
				}
				if (j+1 == y && img.getHeight()%height != 0){
					h = img.getHeight()%height;			
				}
				
				images[i][j] = img.getSubimage(i*width,j*height, w, h);
				
				try {		
					//This will save to user's desktop. Normally it defaults to where the JAR is saved.
				    outputfile = new File(System.getProperty("user.home") + "/Desktop\\"+fileInfo[0]+"_"+i+"-"+j+"."+fileInfo[fileInfo.length-1]);
				    ImageIO.write(images[i][j], fileInfo[fileInfo.length-1], outputfile);//Returns true if save succesful			    
				} catch (IOException e) {
					infoBox("Save failed!", "Error");
				}
			}
		}
		try {			
			infoBox("Pictures saved to: "+outputfile.getCanonicalPath(), "Saved");
		} catch (IOException e) {
			infoBox("Pictures saved", "Saved");
		}
	}
	
	private void infoBox(String infoMessage, String title)
    {
        JOptionPane.showMessageDialog(null, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
