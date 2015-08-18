package org.openlca.app.wizards.io;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;

public class StringLableProvider extends LabelProvider {
	
	@Override
	public String getText(Object element) {
		String text = null;
		if (element instanceof String) {
			 text = (String) element;
			 String [] arry = text.split("/");
			 text = arry[arry.length-1];
		}
		return text;
	}

}
