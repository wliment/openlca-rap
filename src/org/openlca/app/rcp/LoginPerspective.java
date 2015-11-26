package org.openlca.app.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;



public class LoginPerspective  implements IPerspectiveFactory{

	
	
	
	@Override
	  public void createInitialLayout(final IPageLayout layout)
	  {
	    String editorArea = layout.getEditorArea();
	    layout.setEditorAreaVisible(false);

	    layout.addStandaloneView(LoginView.id, false, IPageLayout.RIGHT,0.5f,
	      editorArea);
	    
//	    IViewPart viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView( LoginView.id );
//	    viewPart.setFocus();
	  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
