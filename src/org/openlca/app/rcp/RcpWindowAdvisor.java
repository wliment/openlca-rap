package org.openlca.app.rcp;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.SingletonUtil;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.openlca.app.Config;
import org.openlca.app.editors.StartPage;
import org.openlca.app.util.Editors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RcpWindowAdvisor extends WorkbenchWindowAdvisor {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public RcpWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
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
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowProgressIndicator(true);
		configurer.setShowMenuBar(true);
//		configurer.setTitle(Config.APPLICATION_NAME + " " + Config.VERSION);
		configurer.setShellStyle( SWT.NO_TRIM );
//		   getWindowConfigurer().setShowMenuBar( false );
	}

	@Override
	public void postWindowOpen() {
		if (Config.isBrowserEnabled())
			StartPage.open();
		   Shell shell = getWindowConfigurer().getWindow().getShell();
		   shell.setMaximized( true );
		   shell.setFullScreen(true);
		   shell.setTouchEnabled(true);
		   
//		   shell.addMouseListener(listener);
		   
		   
		   RWT.getUISession().addUISessionListener( new UISessionListener() {
			   public void beforeDestroy( UISessionEvent event ) {
				   log.trace("close all editors");
//					preWindowShellClose();
			   }
			 } );
	}

	@Override
	public boolean preWindowShellClose() {
		log.trace("close all editors");
		Editors.closeAll();
		

//		configurer.getWindow().getWorkbench().close();
//
//		configurer.getWindow().close();

		

		return super.preWindowShellClose();
	}

}
