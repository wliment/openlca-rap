package org.openlca.app.util.tables;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.openlca.app.components.IModelDropHandler;
import org.openlca.app.components.ModelTransfer;
import org.openlca.app.util.UI;
import org.openlca.core.model.descriptors.BaseDescriptor;

/**
 * A helper class for creating tables, table viewers and related resources.
 */
public class Tables {

	/**
	 * Creates a default table viewer with the given properties. The properties
	 * are also used to create columns where each column label is the respective
	 * property of this column. The viewer is configured in the following way:
	 * <ul>
	 * <li>content provider = {@link ArrayContentProvider}
	 * <li>lines and header are visible
	 * <li>grid data with horizontal and vertical fill
	 * 
	 */
	public static TableViewer createViewer(Composite parent,
			String... properties) {
		TableViewer viewer = new TableViewer(parent, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setColumnProperties(properties);
		Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		createColumns(table, properties);
		GridData data = UI.gridData(table, true, true);
		data.minimumHeight = 150;
		return viewer;
	}

	public static void createColumns(Table table, String[] labels) {
		for (String label : labels) {
			TableColumn c = new TableColumn(table, SWT.NULL);
			c.setText(label);
		}
		for (TableColumn c : table.getColumns())
			c.pack();
	}

	public static void addDropSupport(TableViewer table,
			final IModelDropHandler handler) {
		final Transfer transfer = ModelTransfer.getInstance();
		DropTarget dropTarget = new DropTarget(table.getTable(), DND.DROP_COPY
				| DND.DROP_MOVE | DND.DROP_DEFAULT);
		dropTarget.setTransfer(new Transfer[] { transfer });
		dropTarget.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (!transfer.isSupportedType(event.currentDataType))
					return;
				List<BaseDescriptor> list = ModelTransfer
						.getBaseDescriptors(event.data);
				handler.handleDrop(list);
			}
		});
	}

	public static void bindColumnWidths(TableViewer viewer,
			double... percents) {
		bindColumnWidths(viewer.getTable(), percents);
	}

	/**
	 * Binds the given percentage values (values between 0 and 1) to the column
	 * widths of the given table
	 */
	public static void bindColumnWidths(final Table table,
			final double... percents) {
		if (table == null || percents == null)
			return;
		table.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				double width = table.getSize().x - 25;
				if (width < 50)
					return;
				TableColumn[] columns = table.getColumns();
				for (int i = 0; i < columns.length; i++) {
					if (i >= percents.length)
						break;
					double colWidth = percents[i] * width;
					columns[i].setWidth((int) colWidth);
				}
			}
		});
	}

	public static <T> void sortByString(TableViewer viewer,
			Function<T, String> fn, int col) {
		StringSorter<T> s = new StringSorter<>(col, fn);
		addSorter(viewer, s);
	}

	public static <T> void sortByDouble(TableViewer viewer,
			Function<T, Double> fn, int col) {
		DoubleSorter<T> s = new DoubleSorter<>(col, fn);
		addSorter(viewer, s);
	}

	public static <T> void sortByLabels(TableViewer viewer,
			ITableLabelProvider labelProvider, int... cols) {
		for (int i = 0; i < cols.length; i++) {
			LabelSorter<T> s = new LabelSorter<>(cols[i], labelProvider);
			addSorter(viewer, s);
		}
	}

	private static void addSorter(TableViewer viewer, Sorter<?> sorter) {
		Table table = viewer.getTable();
		if (sorter.column >= table.getColumnCount())
			return;
		TableColumn column = table.getColumn(sorter.column);
		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableColumn current = table.getSortColumn();
				if (column == current)
					sorter.ascending = !sorter.ascending;
				else
					sorter.ascending = true;
				int direction = sorter.ascending ? SWT.UP : SWT.DOWN;
				table.setSortDirection(direction);
				table.setSortColumn(column);
				viewer.setSorter(sorter);
				viewer.refresh();
			}
		});
	}

	/** Add an event handler for double clicks on the given table viewer. */
	public static void onDoubleClick(TableViewer viewer,
			Consumer<MouseEvent> handler) {
		if (viewer == null || viewer.getTable() == null || handler == null)
			return;
		viewer.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				handler.accept(e);
			}
		});
	}

	/**
	 * Get the table item where the given event occurred. Returns null if the
	 * event occurred in the empty table area.
	 */
	public static TableItem getItem(TableViewer viewer, MouseEvent event) {
		if (viewer == null || event == null)
			return null;
		Table table = viewer.getTable();
		if (table == null)
			return null;
		return table.getItem(new Point(event.x, event.y));
	}

	public static void onDeletePressed(TableViewer viewer,
			Consumer<Event> handler) {
		if (viewer == null || viewer.getTable() == null || handler == null)
			return;
		viewer.getTable().addListener(SWT.KeyUp, (event) -> {
			if (event.keyCode == SWT.DEL) {
				handler.accept(event);
			}
		});
	}
}
