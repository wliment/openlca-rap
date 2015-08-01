package org.openlca.app.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openlca.app.db.Database;
import org.openlca.core.database.CategorizedEntityDao;
import org.openlca.core.model.Category;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

/**
 * Represents categories in the navigation tree.
 */
public class CategoryElement extends NavigationElement<Category> {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public CategoryElement(INavigationElement<?> parent, Category category) {
		super(parent, category);
	}

	@Override
	protected List<INavigationElement<?>> queryChilds() {
		Category category = getContent();
		log.trace("add category childs for {}", category);
		if (category == null) {
			return Collections.emptyList();
		}
		List<INavigationElement<?>> list = new ArrayList<>();
		for (Category child : category.getChildCategories()) {
			list.add(new CategoryElement(this, child));
		}
		addModelElements(category, list);
		return list;
	}

	private void addModelElements(Category category,
			List<INavigationElement<?>> list) {
		try {
			CategorizedEntityDao<?, ?> dao = Database.createRootDao(category
					.getModelType());
			if (dao == null)
				return;
			Optional<Category> optional = Optional.fromNullable(category);
			for (BaseDescriptor descriptor : dao.getDescriptors(optional)) {
				list.add(new ModelElement(this, descriptor));
			}
		} catch (Exception e) {
			log.error("failed to get model elements: " + category, e);
		}
	}

}
