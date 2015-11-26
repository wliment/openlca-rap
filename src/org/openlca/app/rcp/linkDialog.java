package org.openlca.app.rcp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;



public class linkDialog extends Dialog {

  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

  private Text another_link;
  public Text passText;
  private Label mesgLabel;
  private final String title;
  private final String message;


  public linkDialog( Shell parent, String title, String message ) {
    super( parent );
    this.title = title;
    this.message = message;
  }



  @Override
  protected void configureShell( Shell shell ) {
    super.configureShell( shell );
   

    
  }
  
  @Override
  protected void setShellStyle(int arg0){
	    //Use the following not to show the default close X button in the title bar
	    super.setShellStyle(SWT.MODELESS);
	}


  @Override
	public boolean close() {
		return false;
	}
  
  
  //get index page from file
  public static String get_static_index(String file){
	  
	  
	  return "";
	  
  }
 
  @Override
  protected Control createDialogArea( Composite parent ) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(1, false));

		
		
	

	    Label index_link = new Label( composite, SWT.NONE );
	    
   
	    String link_string = null;
		try {
			link_string = common_unitl.getTxtContent("resources/index_file.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    index_link.setText( link_string);
	    index_link.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
//	    index_link.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
//	    this.set_font_size(index_link, 23);
	    index_link.setFont( new Font( parent.getDisplay(), "Arial", 38, SWT.BOLD ) );



    return composite;
  }



//  
  @Override
  protected void createButtonsForButtonBar( Composite parent ) {
//    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
//    createButton( parent, LOGIN_ID, "Login", true );
  }

  @Override
  protected Button createButton(Composite parent, int id,
	        String label, boolean defaultButton) {
	    if (id == IDialogConstants.CANCEL_ID) return null;
	    return super.createButton(parent, id, label, defaultButton);
	}


  @Override
  protected Point getInitialLocation(final Point initialSize)
  {
    Display display = getShell().getDisplay();
    Monitor monitor = display.getPrimaryMonitor();
    Rectangle monitorRect = monitor.getBounds();
    int x = monitorRect.x + (monitorRect.width - 600) / 2;
    int y = monitorRect.y + (monitorRect.height - 360) / 2;

    return new Point(x, y);
  }
  public void setFocus(){
	  
	  another_link.setFocus();

  }

}