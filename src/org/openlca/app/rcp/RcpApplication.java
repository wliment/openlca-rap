package org.openlca.app.rcp;
import org.eclipse.equinox.app.IApplication;

import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.openlca.app.util.Editors;

public class RcpApplication implements IApplication {

	
	public Object start(final IApplicationContext context) throws Exception {
		Object result = null;
		Display display = PlatformUI.createDisplay();
		try {
			final int returnCode = PlatformUI.createAndRunWorkbench(display,
					new RcpWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				result = IApplication.EXIT_RESTART;
			} else {
				result = IApplication.EXIT_OK;
			}
		} finally {
			display.dispose();
			
//			Editors.closeAll();
			
		}
		return result;
	}

	
	public void stop() {
//		final IWorkbench workbench = PlatformUI.getWorkbench();
//		if (workbench == null) {
//			return;
//		}
//		final Display display = workbench.getDisplay();
//		display.syncExec(new Runnable() {
//			@Override
//			public void run() {
//				if (!display.isDisposed()) {
//					workbench.close();
//				}
//			}
//		});
	}
}
