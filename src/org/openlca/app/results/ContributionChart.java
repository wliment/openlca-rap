package org.openlca.app.results;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.graphics.Graphics;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.openlca.app.FaviColor;
import org.openlca.app.Messages;
import org.openlca.app.components.charts.ChartCanvas;
import org.openlca.app.util.Colors;
import org.openlca.app.util.Labels;
import org.openlca.app.util.Numbers;
import org.openlca.app.util.UI;
import org.openlca.core.model.RootEntity;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.openlca.core.results.ContributionItem;
import org.openlca.core.results.ProcessGrouping;
import javax.imageio.ImageIO;


/**
 * A pie chart for showing a set of result contributions.
 */
public class ContributionChart {

	private ImageRegistry imageRegistry = new ImageRegistry();
	private Stack<ImageHyperlink> createdLinks = new Stack<>();
	private ChartCanvas chartCanvas;
	private Composite linkComposite;
	private String unit;

	public ContributionChart(Composite parent, FormToolkit toolkit) {
		parent.addDisposeListener(new Dispose());
		initContent(parent, toolkit);
	}

	private void initContent(Composite parent, FormToolkit toolkit) {
		parent.setLayout(new FillLayout());
		Composite composite = toolkit.createComposite(parent);
		UI.gridLayout(composite, 2);
		chartCanvas = new ChartCanvas(composite, SWT.NONE);
		GridData gridData = UI.gridData(chartCanvas, false, false);
		gridData.heightHint = 250;
		gridData.widthHint = 300;
		linkComposite = toolkit.createComposite(composite);
		UI.gridData(linkComposite, true, true);
		UI.gridLayout(linkComposite, 1).verticalSpacing = 0;
	}

	public void setData(List<ContributionItem<?>> data, String unit) {
		this.unit = unit;
		setData(data);
	}

	public void setData(List<ContributionItem<?>> data) {
		if (data == null)
			return;
		while (!createdLinks.isEmpty())
			createdLinks.pop().dispose();
		UI.gridLayout(linkComposite, 1);
		boolean hasRest = hasRest(data);
		createChart(data, hasRest);
		createLinks(data);
		linkComposite.layout(true);
	}

	private boolean hasRest(List<ContributionItem<?>> data) {
		for (ContributionItem<?> item : data) {
			if (item.isRest())
				return true;
		}
		return false;
	}

	private void createChart(List<ContributionItem<?>> data, boolean withRest) {
		List<Double> vals = new ArrayList<>();
		for (ContributionItem<?> item : data)
			vals.add(item.getAmount());
		Chart chart = new ContributionChartCreator(vals).createChart(withRest);
		chartCanvas.setChart(chart);
		chartCanvas.updateChart_public();
	}

	private void createLinks(List<ContributionItem<?>> data) {
		int colorIndex = 0;
		for (ContributionItem<?> item : data) {
			ImageHyperlink link = new ImageHyperlink(linkComposite, SWT.TOP);
			link.setText(getLinkText(item));

			if (item.isRest())
				link.setImage(getLinkImage(-1));
			else
				link.setImage(getLinkImage(colorIndex++));
			createdLinks.push(link);
		}
	}

	private String getLinkText(ContributionItem<?> item) {
		String number = Numbers.format(item.getAmount(), 3);
		if (unit != null)
			number += " " + unit;
		String text = "";
		Object content = item.getItem();
		// TODO: it would be better if a label provider could be set here
		if (content instanceof BaseDescriptor)
			text = Labels.getDisplayName((BaseDescriptor) content);
		else if (content instanceof RootEntity)
			text = Labels.getDisplayName((RootEntity) content);
		else if (content instanceof ProcessGrouping)
			text = ((ProcessGrouping) content).getName();
		else if (item.isRest())
			text = Messages.Other;
		return number + ": " + text;
	}
	private   final class CanvasPaintListener implements PaintListener{
		private Image image;
		
		public  CanvasPaintListener(Image image){
			this.image = image;
		}
		  private static final long serialVersionUID = 1L;
			public void paintControl(PaintEvent event) {
				GC gc = event.gc;
				gc.drawImage(this.image, 0, 0);
				int width = 4;
				gc.setLineWidth(width);
				gc.setForeground(event.widget.getDisplay().getSystemColor(
						SWT.COLOR_GREEN));
				
				
			}
	  }
	private Image getLinkImage(int index) {
		String key = Integer.toString(index);
		Image image = imageRegistry.get(key);
		if (image == null) {
//		
		RGB rgb = null;
//	    public Color(int r, int g, int b) {

		if (index != -1)
				rgb =FaviColor.getRgbForChart(index);
		else
			    rgb =Colors.getGray().getRGB();

		
		Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
			  BufferedImage image2 = new BufferedImage( 30, 15, BufferedImage.TYPE_INT_ARGB);
			  
			  Graphics2D gr2d = image2.createGraphics();
			  // draw the image
//			  gr2d.setColor( new 111,232,123 );
			  gr2d.setBackground(Color.WHITE);
			  
			  gr2d.setColor(new Color(rgb.red,rgb.green,rgb.blue));
//			  gr2d.drawRect( 5, 5, 25 , 25 );
			  gr2d.fillRect(5, 5, 25 , 25);
			  
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
		
		//返回图片
//		return 	 Display.getCurrent().getSystemImage(SWT.ICON_QUESTION);

	}


	private class Dispose implements DisposeListener {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			imageRegistry.dispose();
		}
	}

}
