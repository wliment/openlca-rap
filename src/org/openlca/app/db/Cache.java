package org.openlca.app.db;

import org.openlca.core.database.EntityCache;
import org.openlca.core.database.IDatabase;
import org.openlca.core.matrix.cache.MatrixCache;
import org.openlca.core.model.ModelType;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains the cache management of the application.
 */
public final class Cache {

	private static Logger log = LoggerFactory.getLogger(Cache.class);
	private static AppCache appCache = new AppCache();
	private static EntityCache entityCache;
	private static MatrixCache matrixCache;

	private Cache() {
	}

	public static EntityCache getEntityCache() {
		return entityCache;
	}

	public static MatrixCache getMatrixCache() {
		return matrixCache;
	}

	public static void close() {
		log.trace("close cache");
		evictAll();
		entityCache = null;
		matrixCache = null;
		appCache = null;
	}

	/**
	 * Initializes the caches for the given database. Old cache instances are
	 * closed.
	 */
	public static void create(IDatabase database) {
		log.trace("create cache");
		close();
		entityCache = EntityCache.create(database);
		matrixCache = MatrixCache.createLazy(database);
		appCache = new AppCache();
	}

	public static AppCache getAppCache() {
		return appCache;
	}

	public static void evict(BaseDescriptor descriptor) {
		if (descriptor == null)
			return;
		log.trace("evict {} with ID {}", descriptor.getClass(),
				descriptor.getId());
		if (descriptor.getModelType() == null)
			evictAll(); // to be on the save side
		else if (shouldEvictAll(descriptor.getModelType())) {
			if (entityCache != null)
				entityCache.invalidateAll();
			evictFromMatrices(descriptor);
		} else {
			evictEntity(descriptor);
			evictFromMatrices(descriptor);
		}
	}

	private static boolean shouldEvictAll(ModelType type) {
		return type == ModelType.UNIT || type == ModelType.UNIT_GROUP
				|| type == ModelType.FLOW || type == ModelType.FLOW_PROPERTY
				|| type == ModelType.CATEGORY;
	}

	public static void evictAll() {
		log.trace("evict all from caches");
		if (entityCache != null)
			entityCache.invalidateAll();
		if (matrixCache != null)
			matrixCache.evictAll();
	}

	private static void evictEntity(BaseDescriptor descriptor) {
		if (entityCache == null)
			return;
		long id = descriptor.getId();
		Class<?> clazz = descriptor.getClass();
		log.trace("evict from entity cache {} with id={}", clazz, id);
		entityCache.invalidate(clazz, id);
		if (descriptor.getModelType() == null)
			return;
		clazz = descriptor.getModelType().getModelClass();
		log.trace("evict from entity cache {} with id={}", clazz, id);
		entityCache.invalidate(clazz, id);
	}

	private static void evictFromMatrices(BaseDescriptor descriptor) {
		if (matrixCache == null)
			return;
		matrixCache.evict(descriptor.getModelType(), descriptor.getId());
	}

	public static void registerNew(BaseDescriptor descriptor) {
		if (matrixCache == null)
			return;
		log.trace("register new model {}", descriptor);
		matrixCache.registerNew(descriptor.getModelType(), descriptor.getId());
	}

}
