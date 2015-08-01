package org.eclipse.nebula.jface.tablecomboviewer;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

public class TableComboViewer  extends StructuredViewer{

	public TableComboViewer(TableCombo combo) {
		// TODO Auto-generated constructor stub
	}

	public void setContentProvider(ArrayContentProvider instance) {
		// TODO Auto-generated method stub
		
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		// TODO Auto-generated method stub
		
	}

	public void setSorter(ViewerSorter sorter) {
		// TODO Auto-generated method stub
		
	}

	public Control getControl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Widget doFindInputItem(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Widget doFindItem(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List getSelectionFromWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void internalRefresh(Object element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reveal(Object element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setSelectionToWidget(List l, boolean reveal) {
		// TODO Auto-generated method stub
		
	}

}
