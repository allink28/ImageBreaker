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
		//System.out.println("0:"+fileInfo[0]+" 1: "+fileInfo[1]);
	}

	/**
	 * Breaks the given image up and saves the sub images.
	 * @param width Width of Subimages
	 * @param height Height of Subimages
	 */
	public void slice(int width, int height){		
		int x = img.getWidth()/width;
		int y = img.getHeight()/height;
		
		if(x == 0 || y == 0){
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
		int index = 0;
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
				    File outputfile = new File(fileInfo[0]+"_"+index+"."+fileInfo[fileInfo.length-1]);
				    ImageIO.write(images[i][j], fileInfo[fileInfo.length-1], outputfile);//Returns true if save succesful
				    	//System.out.println(outputfile.getCanonicalPath());				    
				} catch (IOException e) {
					infoBox("Save failed!", "Error");
				}
				++index;
			}
		}
		try {
			infoBox("Pictures saved to: "+(new File(fileInfo[0]+"_0"+"."+fileInfo[fileInfo.length-1])).getCanonicalPath()
					, "Saved");
		} catch (IOException e) {
			infoBox("Pictures saved", "Saved");
		}
	}
	
	private void infoBox(String infoMessage, String title)
    {
        JOptionPane.showMessageDialog(null, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
