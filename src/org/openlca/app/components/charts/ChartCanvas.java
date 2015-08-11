package org.openlca.app.components.charts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ChartCanvas extends Canvas {

 private Label chartLabel;
 private Chart chart;

 public Chart getChart() {
   return chart;
 }

 public void setChart( Chart chart ) {
   this.chart = chart;
 }

 public ChartCanvas( Composite parent, int style ) {
   super( parent, style );
   setLayout( new FillLayout() );
   chartLabel = new Label( this, SWT.NONE );
   chartLabel.setToolTipText( "Chart" );
   chartLabel.setData( RWT.MARKUP_ENABLED, Boolean.TRUE );
   addControlListener( new SmartControlAdapter() {

     protected void handleControlResized( ControlEvent event ) {
       try {
         updateChart();
       } catch( Exception e ) {
         e.printStackTrace();
       }
     }
   } );
 }
 
 public void updateChart_public(){
	 updateChart();
	 
 }

 private void updateChart() {
   try {
     drawChart( chart );
   } catch( Exception e ) {
     e.printStackTrace();
   }
 }

 private void drawChart( Chart chart ) throws Exception {
	 if(chart == null) return;
   Point size = getSize();
   if( !isCached( chart, size ) ) {
     IDeviceRenderer render = null;
     PluginSettings ps = PluginSettings.instance();
     render = ps.getDevice( "dv.PNG" );
     Bounds bounds = BoundsImpl.create( 0, 0, size.x, size.y );
     int resolution = render.getDisplayServer().getDpiResolution();
     bounds.scale( 72d / resolution );
     GeneratedChartState state;
     Generator gr = Generator.instance();
     state = gr.build( render.getDisplayServer(),
                       chart,
                       bounds,
                       null,
                       null,
                       null );
     File tmpFile = null;
     tmpFile = File.createTempFile( "birt" + chart.hashCode(), "_"
                                                               + size.x
                                                               + "_"
                                                               + size.y );
     render.setProperty( IDeviceRenderer.FILE_IDENTIFIER, tmpFile );
     gr.render( render, state );
     String url = getImageURL( tmpFile );
     chartLabel.setText( "<img src='"+url+"' width='"+size.x +"' height='"+size.y+"'/>");

     //Image img;
     //InputStream inputStream = null;
     //try {
     //  inputStream = new FileInputStream( tmpFile );
     //  img = Graphics.getImage( tmpFile.getName(), inputStream );
     //   tmpFile.delete();
     //} finally {
     //  if( inputStream != null ) {
     //    inputStream.close();
     //  }
     //}
     //if( img != null ) {
     //  chartLabel.setImage( img );
     //} else {
     //  chartLabel.setText( "Chart generation failed!" );
     //}
   }
 }

 private boolean isCached( Chart chart, Point size ) {
   // should be implemented by using a useful cache implementation
   // depending on the use-case it may be enough to just cache images
   // based on the chart object, if the chart is dynamic you need to
   // implement another strategy
   return false;
 }

 private String getImageURL(File imageFile) {
   ResourceManager resourceManager = RWT.getResourceManager();

   String resourceKey = imageFile.getPath();
   if (!resourceManager.isRegistered(resourceKey)) {
     try {
       InputStream inputStream = new FileInputStream(imageFile);
       try {
         resourceManager.register(resourceKey, inputStream);
       } finally {
         inputStream.close();
       }
     } catch (IOException e) {
     }
   }
   return resourceManager.getLocation(resourceKey);
 }
}
