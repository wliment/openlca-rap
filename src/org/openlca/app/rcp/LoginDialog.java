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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;

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



public class LoginDialog extends Dialog {

  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

  private Text userText;
  public Text passText;
  private Label mesgLabel;
  private final String title;
  private final String message;
  private String username;
  private String password;

  public LoginDialog( Shell parent, String title, String message ) {
    super( parent );
    this.title = title;
    this.message = message;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername( String username ) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  protected void configureShell( Shell shell ) {
    super.configureShell( shell );
    if( title != null ) {
      shell.setText( title );
    }
  }

  private void set_font_size(Label label,int font_size){
		FontData[] fD = label.getFont().getFontData();
		fD[0].setHeight(font_size);
		label.setFont( new Font(Display.getCurrent(),fD[0]));
	  
  }
  @Override
  protected Control createDialogArea( Composite parent ) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(2, false));
		// new TableLayout();
//		Composite version_composite = new Composite(composite, SWT.NO_FOCUS);
//		
//		GridLayout version_layout =  new GridLayout(1, false);
//		version_layout.numColumns=2;
		
//		version_composite.setLayout(version_layout);
//		GridData version_grid_data= new GridData(SWT.CENTER, SWT.CENTER, true, false);
//		version_grid_data.horizontalSpan=2;
//		version_composite.setLayoutData(version_grid_data);
//		Label welcome_lable =new Label(version_composite, SWT.CENTER);
//		welcome_lable.setText("Welcome to openLCA Web Version");
//		this.set_font_size(welcome_lable,20);
//
//		GridData welcome_data = new GridData(SWT.FILL, SWT.CENTER, true, false);
//		welcome_data.horizontalSpan=2;
//		welcome_lable.setLayoutData(welcome_data);
//		
//		Label version_lable =new Label(version_composite, SWT.CENTER);
//		version_lable.setText("(Beta 1.3)\n\n\n");
//		GridData version_data = new GridData(SWT.FILL, SWT.CENTER, true, false);
//		version_data.horizontalSpan=2;
//		version_data.verticalSpan=3;
//		version_lable.setLayoutData(version_data);
//		
//			
//		Label info_lable =new Label(version_composite, SWT.RIGHT);
//		info_lable.setText("Contributed by Advanced Design and Manufactuing Engineering Centre");
//		this.set_font_size(info_lable,7);
//		GridData info_data = new GridData(SWT.RIGHT, SWT.RIGHT, true, false);
////		info_data.verticalSpan = 5;
//		info_data.horizontalSpan = 10;
//		info_lable.setLayoutData(info_data);
//		
		
		
		Label logon_Label = new Label(composite, SWT.NONE);
		logon_Label.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		String logo_path="";
		try{
			logo_path = common_unitl.getImageURL("resources/openlca_logo.jpg");
		}
		catch (Exception e){
			
		}
		logon_Label.setText("<img src='" + logo_path + "' width='" + 330 + "' height='" + 200 + "'/>");

		Composite login_composite = new Composite(composite, SWT.NO_FOCUS);
		login_composite.setLayout(new GridLayout(2, false));
		
		
		login_composite.forceFocus();

		mesgLabel = new Label(login_composite, SWT.NONE);
		GridData messageData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		messageData.horizontalSpan = 2;
		mesgLabel.setLayoutData(messageData);

		Label userLabel = new Label(login_composite, SWT.NONE);
		userLabel.setText("Username:");
		userText = new Text(login_composite, SWT.BORDER);
		GridData userData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		userData.horizontalSpan=0;
		userText.setLayoutData(userData);
		Label passLabel = new Label(login_composite, SWT.NONE);
		passLabel.setText("Password:");
		passText = new Text(login_composite, SWT.BORDER | SWT.PASSWORD);
		passText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		Label userLabel2 = new Label(login_composite, SWT.NONE);
		userLabel2.setText("");	

		Composite button_composite =(Composite) createButtonBar(login_composite);
		button_composite.setLayout(new GridLayout(2, false));
		button_composite.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false));
		createButton(button_composite, IDialogConstants.CANCEL_ID, "Cancel", false);
		createButton(button_composite, LOGIN_ID, "Login", true);

//    Label userLabel = new Label( composite, SWT.NONE );
//    userLabel.setText( "Username:" );
//    userText = new Text( composite, SWT.BORDER );
//    userText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
//    Label passLabel = new Label( composite, SWT.NONE );
//    passLabel.setText( "Password:" );
//    passText = new Text( composite, SWT.BORDER | SWT.PASSWORD );
//    passText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
	
    
    
    initilizeDialogArea();
//    passText.forceFocus();
    return composite;
  }

  @Override
  protected void createButtonsForButtonBar( Composite parent ) {
//    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
//    createButton( parent, LOGIN_ID, "Login", true );
  }

  @Override
  protected void buttonPressed( int buttonId ) {
    if( buttonId == LOGIN_ID ) {
      username = userText.getText();
      password = passText.getText();
      setReturnCode( OK );
      if(this.verify_passwd(username, password))
       close();
      else{
      	mesgLabel.setText("wrong username or passwd");
		final IWorkbench workbench = PlatformUI.getWorkbench();

      	final Display display = workbench.getDisplay();
      	mesgLabel.setForeground(new org.eclipse.swt.graphics.Color(display, 255, 0, 0));

      }
        

    } else {
      password = null;
    }

    super.buttonPressed( buttonId );
  }

  private String pass_file = "resources/openlca_user.txt";
  private  Map get_passfile(){
	  Map user_info = new HashMap();
	  String passtext="";
	try {
		passtext = common_unitl.getTxtContent(this.pass_file);
		System.out.println(passtext);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      String lines[] = passtext.split("\\r?\\n");
	  for (String line : lines) {
		   if("".equals(line)) continue;
		 	String [] temp= line.split(":");
		 	user_info.put(temp[0], temp[1]);
	    }
	 
	return user_info;
	  
  }
  private  Boolean verify_passwd(String username,String passwd){
	  if("".equals(username) || null == username) return false;
	  
	  Map user_info = this.get_passfile();
	  
	  if( user_info.get(username) != null  && user_info.get(username).equals(passwd)) 
		  return true;
	  else 
		  return false;
		  
  }
  private void initilizeDialogArea() {
    if( message != null ) {
      mesgLabel.setText( message );
    }
    if( username != null ) {
      userText.setText( username );
    }
    userText.setFocus();
//	  passText.setFocus();

    
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
	  
	  userText.setFocus();

  }

}