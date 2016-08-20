package org.ayo.file;

import org.ayo.Ayo;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.Context;

public class FileVisitor {

	private static final String CHARSET = "utf-8";

	/**
	 * @return
	 */
	public static String getCotentOfRaw(int rawId) {
		InputStream in = Ayo.context.getResources().openRawResource(rawId);
		return readInputStream(in);
	}

	/**
	 * @return
	 */
	public static String getContentOfAssets(String fileName) {
		InputStream in;
		try {
			in = Ayo.context.getResources().getAssets().open(fileName);
			return readInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * access files in /data/data/<pkgName>
	 *
	 * @param fileName
	 * @return
	 */
	public static String getContentOfContextFile(String fileName) {
		FileInputStream fin;
		try {
			fin = Ayo.context.openFileInput(fileName);
			return readInputStream(fin);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "";
		}
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static String getContentOfSdcard(String fileName) {
		try {
			FileInputStream fin = new FileInputStream(fileName);
			return readInputStream(fin);
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 */
	public void writeToFileOfContext(String fileName, String content) {
		if (content == null) {
			return;
		}
		try {
			FileOutputStream fout = Ayo.context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			writeOutputStream(fout, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void appendToFileOfContext(String fileName, String content) {
		if (content == null) {
			return;
		}
		try {
			FileOutputStream fout = Ayo.context.openFileOutput(fileName,
					Context.MODE_APPEND);
			writeOutputStream(fout, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 */
	public void writeToFileOfSdcard(String filepath, String content) {
		if (content == null) {
			return;
		}
		try {
			FileOutputStream fout = new FileOutputStream(filepath);
			writeOutputStream(fout, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void appendToFileOfSdcard(String fileName, String content) {
		if (content == null) {
			return;
		}
		try {
			//FileSystem.createFileIfNotExistsOfSdcard(fileName);
			FileOutputStream fout = new FileOutputStream(fileName, true);
			writeOutputStream(fout, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readInputStream(InputStream in) {

		String jsonStr = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;

//		String res = "";
//		try {
//			int length = in.available();
//			byte[] buffer = new byte[length];
//			in.read(buffer);
//			res = IOUtil.readStr(buffer, CHARSET); //EncodingUtils.getString(buffer, CHARSET);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (Exception e2) {
//				}
//			}
//		}
//		return res;
	}

	public static void writeOutputStream(OutputStream out, String content) {
		try {
			byte[] bytes = content.getBytes();
			out.write(bytes);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void appentToFile(String path, String content){
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		try {
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(content);
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
