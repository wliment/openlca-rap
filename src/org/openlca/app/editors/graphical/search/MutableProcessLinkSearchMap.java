package org.openlca.app.editors.graphical.search;

import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.Collection;

import org.openlca.core.model.ProcessLink;

public class MutableProcessLinkSearchMap extends ProcessLinkSearchMap {

	public MutableProcessLinkSearchMap(Collection<ProcessLink> links) {
		super(links);
	}

	public void put(ProcessLink link) {
		int index = remove(link);
		if (index == -1)
			index = getAvailableIndex();
		if (index < data.size())
			data.set(index, link);
		else
			data.add(link);
		index(link.getProviderId(), index, providerIndex);
		index(link.getRecipientId(), index, recipientIndex);
	}

	private int getAvailableIndex() {
		for (int index = 0; index < data.size(); index++)
			if (data.get(index) == null) // previously removed link
				return index;
		return data.size();
	}

	public void removeAll(Collection<ProcessLink> links) {
		for (ProcessLink link : links)
			remove(link);
	}

	public int remove(ProcessLink link) {
		int index = data.indexOf(link);
		if (index < 0)
			return -1;
		data.set(index, null);
		remove(link.getProviderId(), index, providerIndex);
		remove(link.getRecipientId(), index, recipientIndex);
		return index;
	}

	private void remove(long id, int index,
			TLongObjectHashMap<TIntArrayList> map) {
		TIntArrayList list = map.get(id);
		if (list == null)
			return;
		list.remove(index);
		if (list.size() == 0)
			map.remove(id);
	}

}
