package org.openlca.app.viewers;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.openlca.core.model.Category;
import org.openlca.core.model.Exchange;
import org.openlca.core.model.ImpactCategory;
import org.openlca.core.model.Parameter;
import org.openlca.core.model.RootEntity;
import org.openlca.core.model.descriptors.BaseDescriptor;

/**
 * Sorts objects by their respective names.
 */
public class BaseNameSorter extends ViewerSorter {

	@Override
	public int compare(final Viewer viewer, final Object e1, final Object e2) {
		String s1 = null;
		String s2 = null;
		if (e1 instanceof RootEntity && e2 instanceof RootEntity) {
			s1 = ((RootEntity) e1).getName();
			s2 = ((RootEntity) e2).getName();
		} else if (e1 instanceof Exchange && e2 instanceof Exchange) {
			s1 = ((Exchange) e1).getFlow().getName();
			s2 = ((Exchange) e2).getFlow().getName();
		} else if (e1 instanceof Category && e2 instanceof Category) {
			s1 = ((Category) e1).getName();
			s2 = ((Category) e2).getName();
		} else if (e1 instanceof Parameter && e2 instanceof Parameter) {
			s1 = ((Parameter) e1).getName();
			s2 = ((Parameter) e2).getName();
		} else if (e1 instanceof ImpactCategory && e2 instanceof ImpactCategory) {
			s1 = ((ImpactCategory) e1).getName();
			s2 = ((ImpactCategory) e2).getName();
		} else if (e1 instanceof BaseDescriptor && e2 instanceof BaseDescriptor) {
			s1 = ((BaseDescriptor) e1).getName();
			s2 = ((BaseDescriptor) e2).getName();
		}
		return s1 != null && s2 != null ? s1.compareToIgnoreCase(s2) : 0;
	}
}
