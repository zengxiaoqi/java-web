package com.eastcom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 从配置文件加载属性配置
 * 
 * @author huangwq
 * 
 *         2013-4-10
 */
public class PropertiesUtil {
	private static Map<String, Long> lastModified = new HashMap<String, Long>();

	/**
	 * 从文件加载属性配置
	 * 
	 * @param file
	 * @return
	 * @exception IOException
	 *                读写文件错误时抛出
	 */
	public static Properties loadProperties(String file) throws IOException {
		return loadProperties(file, new Properties());
	}

	public static Properties loadProperties(String file, Properties defaultProps) throws IOException {
		// 加载配置文件
		InputStream ins = null;

		try {
			ins = new FileInputStream(file);
			defaultProps.load(ins);
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
			}
		}
		return defaultProps;
	}

	/**
	 * 检查文件是否更改过
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isModified(String filename) {
		File file = new File(filename);
		long l = file.lastModified();

		Long t = (Long) lastModified.get(filename);

		long last = t == null ? 0 : t.longValue();

		if (l > last) {
			lastModified.put(filename, new Long(last));
			return true;
		}
		return false;
	}

	public static void writeFile(Properties prop, String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			prop.store(fos, "properties");
		} finally {
			if (fos != null) {
				fos.close();
			}
			fos = null;
		}
	}
}