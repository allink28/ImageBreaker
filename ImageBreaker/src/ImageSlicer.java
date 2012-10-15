import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 
 * @author Allen
 *
 */
public class ImageSlicer {
		
	private BufferedImage img;
	private String parentFile;
	private String[] fileInfo;	
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * @param width Horizontal size of sub-images
	 * @param height Vertical size of sub-images
	 */
	public ImageSlicer(int width, int height){
		this.width = width;
		this.height = height;
		getImage();
		slice();
	}	

	/**
	 * Breaks img up and saves the sub images to the same folder img came from.
	 */
	public void slice(){		
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
				//---------------------------------- Multithread getSubimage?
				images[i][j] = img.getSubimage(i*width,j*height, w, h);
				
				try {
					//Saves to parent file of original image.
					outputfile = new File(parentFile+ "\\"+fileInfo[0]+"_"+i+"-"+j+"."+fileInfo[fileInfo.length-1]);
					//This will save to user's desktop. Normally it defaults to where the JAR is saved.
				    //outputfile = new File(System.getProperty("user.home") + "/Desktop\\"+fileInfo[0]+"_"+i+"-"+j+"."+fileInfo[fileInfo.length-1]);					
				    ImageIO.write(images[i][j], fileInfo[fileInfo.length-1], outputfile);//Returns true if save succesful			    
				} catch (IOException e) {
					infoBox("Save failed!", "Error");
				}
				//----------------------------------
			}
		}
		try {			
			infoBox("Pictures saved to: "+outputfile.getCanonicalPath(), "Saved");
		} catch (IOException e) {
			infoBox("Pictures may not have been saved", "Saved");
		}
	}
	

	/**
	 * Uses a filechooser for the user to select an image.
	 */
	private void getImage(){
		JFileChooser fc = new JFileChooser();
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			try {
			    img = ImageIO.read(fc.getSelectedFile());
			} catch (IOException e) {
				System.exit(1);
			}
		}
		parentFile = fc.getSelectedFile().getParent();
		fileInfo = fc.getName(fc.getSelectedFile()).split("\\.");
	}
	
	/**
	 * A pop up infobox
	 * @param infoMessage
	 * @param title
	 */
	private void infoBox(String infoMessage, String title){
        JOptionPane.showMessageDialog(null, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
