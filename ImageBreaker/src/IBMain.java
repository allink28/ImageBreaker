import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 * Breaks images into several different 1024x1024 images (as well as any remainder).
 * 
 * @author Allen
 *
 */
public class IBMain implements Runnable{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new IBMain()); //Complying with Swing's threading policy.
		 //http://docs.oracle.com/javase/6/docs/api/javax/swing/package-summary.html#threading
	}

	@Override
	public void run() {
		JFileChooser fc = new JFileChooser();
		ImageSlicer imgSlicer;
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			BufferedImage img = null;
			try {
			    img = ImageIO.read(fc.getSelectedFile());
			} catch (IOException e) {
				System.exit(1);
			}
			imgSlicer = new ImageSlicer(img, fc.getName(fc.getSelectedFile()));			
			imgSlicer.slice(1024, 1024);			
		}
		
	}
}
