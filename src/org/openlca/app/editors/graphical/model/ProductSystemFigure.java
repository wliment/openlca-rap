package org.openlca.app.editors.graphical.model;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.openlca.app.editors.graphical.layout.GraphLayoutManager;
import org.openlca.app.editors.graphical.layout.GraphLayoutType;
import org.openlca.app.editors.graphical.layout.NodeLayoutStore;
import org.openlca.app.util.Colors;

class ProductSystemFigure extends Figure {

	private boolean firstTime = true;
	private ProductSystemNode node;

	ProductSystemFigure(ProductSystemNode node) {
		setForegroundColor(Colors.getBlack());
//		setBorder(new LineBorder(Colors.getBlack(),1));
		node.setFigure(this);
		this.node = node;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProcessFigure> getChildren() {
		return super.getChildren();
	}

	@Override
	public GraphLayoutManager getLayoutManager() {
		return (GraphLayoutManager) super.getLayoutManager();
	}
	
	@Override
	public void paint(Graphics graphics) {
//		if(graphics.)
//		SWTGraphics g = (SWTGraphics)graphics;
//		graphics.setBackgroundColor(Colors.getBlack());
//		graphics.setForegroundColor(Colors.getWhite());
		Color black_color = new Color(Display.getCurrent(),new RGB(234,234,124));
		Color white_color = new Color(Display.getCurrent(),new RGB(112,234,224));
//
//		setBackgroundColor(black_color);
//		setForegroundColor(white_color);
//		super.setBackgroundColor(black_color);
//		super.setForegroundColor(white_color);
		super.bgColor=black_color;
		super.fgColor=black_color;
		this.bgColor=black_color;
		this.fgColor=black_color;
		
		super.paint(graphics);
		
		
		
		if (!firstTime)
			return;
		firstTime = false;
		boolean layoutLoaded = false;
		if (!node.getEditor().isInitialized()) {
			node.getEditor().setInitialized(true);
			layoutLoaded = NodeLayoutStore.loadLayout(node);
		}
		if (layoutLoaded)
			return;
		long refId = node.getProductSystem().getReferenceProcess().getId();
		node.getProcessNode(refId).expandLeft();
		getLayoutManager().layout(this, GraphLayoutType.TREE_LAYOUT);
	}

}
