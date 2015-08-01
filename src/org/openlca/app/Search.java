package org.openlca.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openlca.app.util.Labels;
import org.openlca.core.database.ActorDao;
import org.openlca.core.database.FlowDao;
import org.openlca.core.database.FlowPropertyDao;
import org.openlca.core.database.IDatabase;
import org.openlca.core.database.ImpactMethodDao;
import org.openlca.core.database.ProcessDao;
import org.openlca.core.database.ProductSystemDao;
import org.openlca.core.database.ProjectDao;
import org.openlca.core.database.RootEntityDao;
import org.openlca.core.database.SourceDao;
import org.openlca.core.database.UnitGroupDao;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Search implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	private IDatabase database;
	private String rawTerm;
	private String[] terms;
	private List<BaseDescriptor> result = new ArrayList<>();

	public Search(IDatabase database, String term) {
		this.database = database;
		this.rawTerm = term == null ? "" : term.toLowerCase().trim();
		terms = rawTerm.split(" ");
		for (int i = 0; i < terms.length; i++)
			terms[i] = terms[i].trim();
	}

	public List<BaseDescriptor> getResult() {
		return result;
	}

	@Override
	public void run() {
		result.clear();
		log.trace("run search with term {}", rawTerm);
		if (rawTerm.isEmpty())
			return;
		RootEntityDao<?, ?>[] daos = { new ProjectDao(database),
				new ProductSystemDao(database), new ImpactMethodDao(database),
				new ProcessDao(database), new FlowDao(database),
				new FlowPropertyDao(database), new UnitGroupDao(database),
				new SourceDao(database), new ActorDao(database) };
		for (RootEntityDao<?, ?> dao : daos) {
			List<?> descriptors = dao.getDescriptors();
			fetchResults(descriptors);
		}
		Collections.sort(result, new ResultComparator());
		log.trace("{} results fetched and ranked", result.size());
	}

	private void fetchResults(List<?> descriptors) {
		for (Object obj : descriptors) {
			if (!(obj instanceof BaseDescriptor))
				continue;
			BaseDescriptor descriptor = (BaseDescriptor) obj;
			if (match(descriptor))
				result.add(descriptor);
		}
	}

	private boolean match(BaseDescriptor descriptor) {
		String label = Labels.getDisplayName(descriptor);
		if (terms == null || label == null)
			return false;
		String feed = label.toLowerCase();
		for (String term : terms) {
			if (!feed.contains(term))
				return false;
		}
		return true;
	}

	private class ResultComparator implements Comparator<BaseDescriptor> {
		@Override
		public int compare(BaseDescriptor o1, BaseDescriptor o2) {
			String label1 = Labels.getDisplayName(o1).toLowerCase();
			String label2 = Labels.getDisplayName(o2).toLowerCase();
			for (String term : terms) {
				int idx1 = label1.indexOf(term);
				int idx2 = label2.indexOf(term);
				int diff = idx1 - idx2;
				if (diff != 0)
					return diff;
			}
			return label1.compareToIgnoreCase(label2);
		}
	}
}
