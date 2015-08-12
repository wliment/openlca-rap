package org.openlca.app.rcp;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.service.ServiceHandler;
import org.eclipse.rap.rwt.service.ServiceManager;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.openlca.app.logging.Console;
import org.openlca.app.logging.LoggerPreference;
import org.openlca.app.util.DownloadServiceHandler;

public class RcpWorkbenchAdvisor extends WorkbenchAdvisor {

	/**
	 * The ID of the openLCA perspective
	 */
	private static final String PERSPECTIVE_ID = "perspectives.standard";

	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			final IWorkbenchWindowConfigurer configurer) {
		return new RcpWindowAdvisor(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}

	@Override
	public void initialize(final IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(false);
		
//		ServiceManager manager = RWT.getServiceManager();
//		ServiceHandler handler = new DownloadServiceHandler();
//		manager.registerServiceHandler( "downloadServiceHandler", handler );
	
		if (LoggerPreference.getShowConsole()) {
			Console.show();
		}
	}

	@Override
	public void postStartup() {
		super.postStartup();
	}
}
