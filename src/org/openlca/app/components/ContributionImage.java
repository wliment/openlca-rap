package org.openlca.app.components;

import org.eclipse.swt.SWT;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.openlca.app.FaviColor;
import org.openlca.app.util.Colors;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;


/**
 * Generates contribution images for UI items. As this class manages an image
 * registry you have to call dispose in order to free native resources.
 */
public class ContributionImage {
	private Image image;

	private ImageRegistry imageRegistry = new ImageRegistry();
	private Display display;

	public ContributionImage(Display display) {
		this.display = display;
	}

	public void dispose() {
		imageRegistry.dispose();
	}

	private Canvas createCanvas(Composite parent) {
		Canvas result = new Canvas(parent, SWT.NONE);
		result.setBackground(result.getDisplay()
				.getSystemColor(SWT.COLOR_WHITE));
//		result.addMouseListener(new CanvasMouseListener());
//		result.addPaintListener(new CanvasPaintListener());
		return result;
	}

	/**
	 * Get an contribution image for table label providers. Returns the maximum
	 * image (for +1 or -1) if the contribution is lower than -1 or greater than
	 * 1.
	 * 
	 * @param contribution
	 *            the contribution value between -1 and 1
	 */
	public Image getForTable(double contribution) {
		double c = contribution;
		if (c < -1)
			c = -1;
		if (c > 1)
			c = 1;
		int contributionInt = (int) (50d * c);
		String key = Integer.toString(contributionInt);
		Image image = imageRegistry.get(key);
		if (image == null) {
//			image = new Image(display, 60, 15);
//			GC gc = new GC((Drawable) image);
//			RGB color = FaviColor.getForContribution(c);
//			gc.setBackground(Colors.getColor(color));
//			int width = Math.abs(contributionInt);
//			gc.fillRectangle(5, 5, width, 5);
//			gc.dispose();
//			imageRegistry.put(key, image);
//			RGB rgb = null;
//		    public Color(int r, int g, int b) {

			RGB rgb = FaviColor.getForContribution(c);


			
			Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
				  BufferedImage image2 = new BufferedImage( 60, 15, BufferedImage.TYPE_INT_ARGB);
				  
				  Graphics2D gr2d = image2.createGraphics();
				  // draw the image
//				  gr2d.setColor( new 111,232,123 );
				  gr2d.setBackground(Color.WHITE);
				  
				  gr2d.setColor(new Color(rgb.red,rgb.green,rgb.blue));
//				  gr2d.drawRect( 5, 5, 25 , 25 );
					int width = Math.abs(contributionInt);

				  gr2d.fillRect(5, 5, width , 5);
				  
				  ByteArrayOutputStream out= new ByteArrayOutputStream();
					 try {
						ImageIO.write(image2, "png", out);

						  ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
					        baos=(ByteArrayOutputStream) out;
					        ByteArrayInputStream intput = new ByteArrayInputStream(baos.toByteArray());
							 image = new Image( Display.getCurrent(), intput);

					        
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


			   
		    

				imageRegistry.put(key, image);
		}
		return image;

	}


	// helping classes
	// ////////////////



	/**/

}
