package org.openlca.app.rcp;
import javax.servlet.http.HttpSession;
import javax.swing.event.HyperlinkEvent;

import org.eclipse.jface.window.Window;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.client.service.StartupParameters;
import org.eclipse.rap.rwt.widgets.DialogCallback;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.dialogs.MessageDialog;

public class LoginView extends ViewPart{
	public static String id="views.login";
	private LoginDialog loginDialog ;
//	private Hyperlink link;

    public static IWorkbenchWindowConfigurer windowConfigurer;
	public LoginView() {
		// TODO Auto-generated constructor stub
	}
	
	public void createPartControl(Composite parent){
	
		String message = "";
		parent.setLayout(new GridLayout(4, false));
	    loginDialog = new LoginDialog( parent.getShell(), "Welcome to openLCA Web Version", message ) {
	    	
	      @Override
	      public boolean close() {
	        boolean result = super.close();
	        int returnCode = getReturnCode();
//	        String resultText = "Result: " + getReturnCodeText( returnCode );
	        String resultText = "1111";
	        if( returnCode == Window.OK ) {
	          String pwInfo = getPassword() == null ? "n/a" : getPassword().length() + " chars";
	          resultText += ", user: " + getUsername() + ", password: " + pwInfo;
	        }
//	        showResult( resultText );
	        System.out.print(resultText);
	      if (getUsername() != null){
	    	  HttpSession session = RWT.getUISession().getHttpSession();
	    	  session.setAttribute("login", "maomi");
	    	  StartupParameters service = RWT.getClient().getService( StartupParameters.class );
	    	  String index = service.getParameter( "index" );
//	    		  loadApplicationPerspective();
//	    	  showMessageDialogInfo(parent.getShell());
	    	  if( !"true".equals(index)){
	    		  	linkDialog ld= new linkDialog(parent.getShell(), "Welcome to openLCA Web Version", message);
	  	    		ld.open();
	  	    		parent.getShell().setSize(1000,400);

	    	  }
	    		  }
	        return result;
	      }
	    };
//	    loginDialog.setUsername( "john" );
	    HttpSession session = RWT.getUISession().getHttpSession();
	    
	    StartupParameters service = RWT.getClient().getService( StartupParameters.class );
  	  String index = service.getParameter( "index" );
  	  if ( session.getAttribute("login") != null && !"true".equals(index)){
		    Shell shell2 = this.windowConfigurer.getWindow().getShell();

		    shell2.setFullScreen(true);
//
		    shell2.setMaximized( true );
	  		linkDialog ld= new linkDialog(parent.getShell(), "Welcome to openLCA Web Version", message);
	  	    ld.open();
	  	}
  	  else if ( session.getAttribute("login") != null && "true".equals(index)){
		  loadApplicationPerspective();

	  	}
	  	else{
	  		loginDialog.setBlockOnOpen(false);
	    	loginDialog.open();
	  	}
        System.out.println("111111111111zzz");

	    loginDialog.setFocus();
	   

		
	}
	
	private void showMessageDialogInfo(Shell shell) {
	    String title = "Information";
	    String message = "This is a RAP MessageDialog.";
	    DialogCallback callback = new DialogCallback() {
	      public void dialogClosed( int returnCode ) {
//	        showResult( "Result: none" );
	      }
	    };
	    MessageDialogUtil.openInformation( shell, title, message, callback );
	  }


	void loadApplicationPerspective()
	  {
//        windowConfigurer.setShowCoolBar(true);
	  
        windowConfigurer.setShowMenuBar(true);
	    IPerspectiveDescriptor pers = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("perspectives.standard");
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(pers);
	    windowConfigurer.getWindow().getShell().setMaximized( true );
        windowConfigurer.setShowCoolBar(false);
       
   

	  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		loginDialog.setFocus();
		
//		link.setFocus();
	}

}
