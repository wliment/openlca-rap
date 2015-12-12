package org.openlca.app.rcp;

import javax.servlet.http.HttpSession;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.SingletonUtil;
import org.eclipse.rap.rwt.client.service.StartupParameters;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.handlers.IHandlerService;
import org.openlca.app.Config;
import org.openlca.app.editors.StartPage;
import org.openlca.app.util.Editors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RcpWindowAdvisor extends WorkbenchWindowAdvisor {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public RcpWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
		LoginView.windowConfigurer = configurer;
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new RcpActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
//		configurer.setInitialSize(new Point(1280, 800));
//		configurer.setShowCoolBar(true);
//		configurer.setShowStatusLine(true);
//		configurer.setShowProgressIndicator(true);
//		configurer.setShowMenuBar(true);
//		configurer.setTitle(Config.APPLICATION_NAME + " " + Config.VERSION);
		configurer.setShellStyle( SWT.NO_TRIM );
//		   getWindowConfigurer().setShowMenuBar( false );
//		 IViewPart viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView( LoginView.id );
//		    viewPart.setFocus();
	   
	}

	@Override
	public void postWindowOpen() {
		StartupParameters service = RWT.getClient().getService( StartupParameters.class );
	  	  String index = service.getParameter( "index" );
  	  HttpSession session = RWT.getUISession().getHttpSession();
  	  	if ("true".equals(index) && session.getAttribute("login") != null){
			if (Config.isBrowserEnabled())
				StartPage.open();
			   Shell shell = getWindowConfigurer().getWindow().getShell();
			   shell.setMaximized( true );
			   shell.setFullScreen(true);
			   shell.setTouchEnabled(true);
	}
  	  	else{
		   
//		 close opened perspective
		    IPerspectiveDescriptor pers = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
		    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closePerspective(pers, false, false);

		    // open login perspective
		    pers = PlatformUI.getWorkbench().getPerspectiveRegistry().findPerspectiveWithId("perspectives.login");
		    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(pers);
		    
		    Shell shell2 = getWindowConfigurer().getWindow().getShell();
//		    getWindowConfigurer().setInitialSize(new Point(400, 300));
//		    getWindowConfigurer().setShowCoolBar(false);
		    getWindowConfigurer().setShowStatusLine(false);
		    getWindowConfigurer().setShowPerspectiveBar(false);
		    getWindowConfigurer().setShowFastViewBars(false);
		    getWindowConfigurer().setShowMenuBar(false);

		    shell2.setFullScreen(true);
//
		    shell2.setMaximized( true );
		    
		    System.out.println("sdfsfsd");
		    LoginView l = (LoginView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
		    	    .findView(LoginView.id);
//		    
		    l.setFocus();
  	  	}

		    
		   
	}
	private void hideCoolbar() {
	    try {
	        IHandlerService service = (IHandlerService) PlatformUI
	                .getWorkbench().getActiveWorkbenchWindow()
	                .getService(IHandlerService.class);
	       
	        if (service != null)
	            service.executeCommand("org.eclipse.ui.ToggleMenubarAction",
	                    null);
	    } catch (Exception e) {
	        //handle error
	    }
	}

	@Override
	public boolean preWindowShellClose() {
		log.trace("close all editors");
		Editors.closeAll();
		IPerspectiveDescriptor pers = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
	    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closePerspective(pers, false, false);

//		configurer.getWindow().getWorkbench().close();
//
//		configurer.getWindow().close();

	    Shell shell2 = getWindowConfigurer().getWindow().getShell();
	    shell2.setFullScreen(false);
	    //
	    		    shell2.setMaximized( false );
	    

		return super.preWindowShellClose();
	}

}
