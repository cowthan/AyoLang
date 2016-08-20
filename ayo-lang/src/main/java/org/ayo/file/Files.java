package org.ayo.file;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;

import org.ayo.Ayo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Files {

	public static final String PATH_SEP = "/";
	
	
	/**
	 * notify the andoid system to refresh the files, because a new file(path) is added
	 */
	public static void notifyFileChanged(String path){
		try {
			Uri uri = Uri.fromFile(new File(path));
			//Uri.parse("file://" + Environment.getExternalStorageDirectory()+"/image/"+ fileName)
			Ayo.context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static class path{
		/** end with /  */
		//public static String SD_ROOT = "";
		/** end with / */
		//public static String CONTEXT_ROOT = "";
		/**
		 * subPath = aa/rr.txt
		 * return /storage/SD.ROOT/aa/rr.txt
		 *
		 */
		public static String getFileInRoot(String subPath){
			String path = Ayo.ROOT;
			if(subPath != null){
				if(subPath.startsWith("/")){
					path = Ayo.ROOT + subPath.substring(1);
				}else{
					path = Ayo.ROOT + subPath;
				}
			}
			//fileop.createFileIfNotExists(path);
			return path;
		}
		
		public static String getPathInRoot(String subPath){
			String path = Ayo.ROOT;
			if(subPath != null){
				if(subPath.startsWith("/")){
					path = Ayo.ROOT + subPath.substring(1);
				}else{
					path = Ayo.ROOT + subPath;
				}
			}
			return path;
		}
		
		public static String getDirInRoot(String subPath){
			String path = Ayo.ROOT;
			if(subPath != null){
				if(subPath.startsWith("/")){
					path = Ayo.ROOT + subPath.substring(1);
				}else{
					path = Ayo.ROOT + subPath;
				}
			}
			fileop.createDirIfNotExists(path);
			return path;
		}
		
		public static String getCameraPath(){
			String s = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
			File f = new File(s);
			if(f.exists() && f.isDirectory()){
				return s;
			}else{
				if(f.mkdirs()){
					return s;
				}else{
					s = Environment.getExternalStorageDirectory().getAbsolutePath();
					return s;
				}
			}
			
		}
		
	}
	
	public static class fileop{
		
		/** full path */
		public static void createFileIfNotExists(String filepath) {

			File f = new File(filepath);
			if(f.exists() && f.isFile()){
				return;
			}
			
			int index = filepath.lastIndexOf(PATH_SEP);
			if(index != -1){
				String dirPath = filepath.substring(0, index);
				createDirIfNotExists(dirPath);
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
			}
		}

		public static void createDirIfNotExists(String dirPath){
			File dir = new File(dirPath);
			if(dir.exists() && dir.isDirectory()){
				return;
			}else{
				dir.mkdirs();
			}
		}
		
		/**
		 * from: full path
		 * toDir: dir path
		 * @return
		 */
		public static File moveFile(String from, String toDir) {

			File f = new File(from);
			File dirTo = new File(toDir);
			if (!dirTo.exists()) {
				dirTo.mkdirs();
			}
			if (f.renameTo(new File(dirTo, f.getName()))) {
				return new File(dirTo, f.getName());
			} else {
				return null;
			}
		}
		
		/**
		 * 
		 * @param from  full path of file
		 * @param toDir dir, if not exist, will be created
		 * @param newFilename 新文件名
		 * @return
		 */
		public static File copyFile(String from, String toDir, String newFilename) {
			
			File f = new File(from);
			if(f.exists() && f.isFile()){
				File dirTo = new File(toDir);
				if (!dirTo.exists()) {
					dirTo.mkdirs();
				}else{
					if(!dirTo.isDirectory()){
						throw new IllegalArgumentException("dirTo is exists, but is not a dir");
					}
				}
				
				File out = new File(dirTo, newFilename);
				try {
					inputstreamtofile(new FileInputStream(f), out);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return out;
				
			}else{
				throw new IllegalArgumentException("source file must be exists, and must be a file--" + from);
			}
			
		}
		
		
		public static boolean copyAssetDb(final Context context, final String dbname, final String path, boolean overrideOldFile) {
			if(!overrideOldFile){
				File file = new File(path, dbname);
				if (file.exists() && file.length() > 0) {
					return true;
				}
			}

			final boolean[] res = {false,false};
			AssetManager am = context.getAssets();
			try {
				InputStream is = am.open(dbname);
				File file = new File(context.getFilesDir(), dbname);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				is.close();
				res[0] = true;
				res[1] = true;
			} catch (Exception e) {
				e.printStackTrace();
				res[0] = false;
				res[1] = true;
			}
			res[1] = true;
			return res[0];
		}
		
		/**
		 * @param assetPath  DocType.properties
		 * @param sdPath     full path
		 * eg
		 *  moveFromAssetToSD("DocType.properties",
		 *                         Environment.getExternalStorageDirectory().getAbsolutePath() + "/boc_fudeng/DocType.properties");
		 */
	    public static boolean moveFromAssetToSD(String assetPath, String sdPath) {
	        try {
	            File file = new File(sdPath);
	            if (!file.exists()) {
	                InputStream in = Ayo.context.getAssets().open(assetPath);
	                file.createNewFile();
	                inputstreamtofile(in, file);
	                in.close();
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
				return false;
	        }
			return true;

	    }
	    private static void inputstreamtofile(InputStream ins, File file) {
	        try {
	            OutputStream os = new FileOutputStream(file);
	            int bytesRead = 0;
	            byte[] buffer = new byte[8192];
	            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	            os.close();
	            ins.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	    }
		
	}///~~~end fileop~~~
	
	public static class file{
		
		/**
		 * 读文件，以字符串返回
		 * @param path sub path
		 * 
		 * @return
		 */
		public static String getContent(String path){
			try {
				return getContent(new FileInputStream(Files.path.getFileInRoot(path)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
		
		public static String getContentFromAssets(String path){
			if(path == null || path.equals("")) return "";
			try {
				return getContent(Ayo.context.getAssets().open(path));
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		
		
		public static void putContent(String absolutePath, String content){
			try {
				File f = new File(absolutePath);
				if(!f.exists()){
					f.createNewFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(new File(absolutePath));   
				out.write(content.getBytes()); 
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			 
		}
		
		public static void appendContent(String absolutePath, String content){
			File file = new File(absolutePath);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
			try {
				FileWriter fw = new FileWriter(absolutePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.append(content);
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public static String getContent(InputStream in) throws IOException{
			StringBuilder sb = new StringBuilder();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = in.read(buffer)) != -1){
				sb.append(new String(buffer, 0, len));
			}
			in.close();
			return sb.toString();
		}
		
		public static void putContent(OutputStream out){
			
		}
	}
}
