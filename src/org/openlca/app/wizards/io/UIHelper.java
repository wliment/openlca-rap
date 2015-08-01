package org.openlca.app.wizards.io;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.openlca.app.rcp.RcpActivator;
import org.slf4j.Logger;

public class UIHelper {

	public static void reportException(Logger log, String message,
			Throwable throwable) {
		log.error(message, throwable);
		Status s = new Status(IStatus.ERROR, RcpActivator.PLUGIN_ID, message,
				throwable);
		StatusManager.getManager().handle(s, StatusManager.BLOCK);
	}

	public static void logException(Logger log, String message,
			Throwable throwable) {
		log.error(message, throwable);
		Status s = new Status(IStatus.ERROR, RcpActivator.PLUGIN_ID, message,
				throwable);
		StatusManager.getManager().handle(s, StatusManager.SHOW);
	}

	public static void logError(Logger log, String message) {
		log.error(message);
		Status s = new Status(IStatus.ERROR, RcpActivator.PLUGIN_ID, message);
		StatusManager.getManager().handle(s, StatusManager.SHOW);
	}

}
