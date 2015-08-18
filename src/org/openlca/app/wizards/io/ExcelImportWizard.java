package org.openlca.app.wizards.io;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.openlca.app.Messages;
import org.openlca.app.db.Cache;
import org.openlca.app.db.Database;
import org.openlca.app.navigation.Navigator;
import org.openlca.app.rcp.ImageType;
import org.openlca.io.xls.process.input.ExcelImport;
import org.openlca.io.xls.process.input.ExcelImport_rap;

public class ExcelImportWizard extends Wizard implements IImportWizard {

	private FileImportPage importPage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.ProcessExcelImportDescription);
		setDefaultPageImageDescriptor(ImageType.IMPORT_ZIP_WIZARD
				.getDescriptor());
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		importPage = new FileImportPage(new String[] { "xlsx", "xls" }, true);
		addPage(importPage);
	}

	@Override
	public boolean performFinish() {
		String [] files = importPage.getFiles();
		if (files == null)
			return false;
		try {
			doRun(files);
			return true;
		} catch (final Exception e) {
			return false;
		} finally {
			Navigator.refresh();
			Cache.evictAll();
		}
	}

	private void doRun(final String[] files) throws Exception {
		getContainer().run(true, true, new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask(Messages.Import, files.length);
				for (String file : files) {
					monitor.subTask(file);
					ExcelImport_rap importer = new ExcelImport_rap(file, Database.get());
					importer.run();
					monitor.worked(1);
				}
				monitor.done();
			}
		});

	}
}
