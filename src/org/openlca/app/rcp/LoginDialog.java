package org.openlca.app.rcp;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Charsets;
import com.google.common.io.Files;


public class LoginDialog extends Dialog {

  private static final int LOGIN_ID = IDialogConstants.CLIENT_ID + 1;

  private Text userText;
  private Text passText;
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

  @Override
  protected Control createDialogArea( Composite parent ) {
    Composite composite = ( Composite )super.createDialogArea( parent );
    composite.setLayout( new GridLayout( 2, false ) );
    mesgLabel = new Label( composite, SWT.NONE );
    GridData messageData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    messageData.horizontalSpan = 2;
    mesgLabel.setLayoutData( messageData );
    Label userLabel = new Label( composite, SWT.NONE );
    userLabel.setText( "Username:" );
    userText = new Text( composite, SWT.BORDER );
    userText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    Label passLabel = new Label( composite, SWT.NONE );
    passLabel.setText( "Password:" );
    passText = new Text( composite, SWT.BORDER | SWT.PASSWORD );
    passText.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    initilizeDialogArea();
    return composite;
  }

  @Override
  protected void createButtonsForButtonBar( Composite parent ) {
    createButton( parent, IDialogConstants.CANCEL_ID, "Cancel", false );
    createButton( parent, LOGIN_ID, "Login", true );
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

  private String pass_file = "/var/openlca_user.txt";
  private  Map get_passfile(){
	  Map user_info = new HashMap();
	  File testFile = new File(this.pass_file);
      try {
		List<String> lines = Files.readLines(testFile,Charsets.UTF_8);
		 for (String line : lines) {
			   if("".equals(line)) continue;
			 	String [] temp= line.split(":");
			 	user_info.put(temp[0], temp[1]);
	        }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
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
  }

}