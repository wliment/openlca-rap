package org.openlca.app.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openlca.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * The list of registered databases which is stored in a configuration file.
 */
public class DatabaseList {

	private List<DerbyConfiguration> localDatabases = new ArrayList<>();
	private List<MySQLConfiguration> remoteDatabases = new ArrayList<>();

	public List<DerbyConfiguration> getLocalDatabases() {
		return localDatabases;
	}

	public List<MySQLConfiguration> getRemoteDatabases() {
		return remoteDatabases;
	}



	public static DatabaseList read(File file) {
		Logger log = LoggerFactory.getLogger(DatabaseList.class);
		log.info("read database configurations from {}", file);
		try (FileInputStream in = new FileInputStream(file);
		     Reader reader = new InputStreamReader(in, "utf-8")) {
			Gson gson = new Gson();
			return gson.fromJson(reader, DatabaseList.class);
		} catch (Exception e) {
			log.error("failed to read database configurations from " + file, e);
			return new DatabaseList();
		}
	}

	public void write(File file) {
		Logger log = LoggerFactory.getLogger(DatabaseList.class);
		log.info("write database configurations to {}", file);
		try (FileOutputStream out = new FileOutputStream(file);
		     Writer writer = new OutputStreamWriter(out, "utf-8")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String s = gson.toJson(this);
			writer.write(s);
		} catch (Exception e) {
			log.error("failed to write database configuration to " + file, e);
		}
	}

	public boolean contains(DerbyConfiguration config) {
		return localDatabases.contains(config);
	}

	public boolean contains(MySQLConfiguration config) {
		return remoteDatabases.contains(config);
	}

	/** Returns true if a database with the given name exists. */
	public boolean nameExists(String name) {
		for (IDatabaseConfiguration config : localDatabases) {
			if (Strings.nullOrEqual(config.getName(), name))
				return true;
		}
		for (IDatabaseConfiguration config : remoteDatabases) {
			if (Strings.nullOrEqual(config.getName(), name))
				return true;
		}
		return false;
	}

}
