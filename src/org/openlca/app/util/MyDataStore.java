package org.openlca.app.util;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class MyDataStore {
	public static String tmp_path = System.getProperty("java.io.tmpdir");
	public static byte[] getByteArrayData(String fileName) {

		byte[] bytes = null;
		File file = new File(tmp_path+fileName);
		try {
			bytes = Files.toByteArray(file);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return bytes;
	}

}
