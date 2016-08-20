package org.ayo.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class AyoFiles {
	
	public static class io{
		
		public static void close(InputStream is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(Reader is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(BufferedReader is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(OutputStream is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(Writer is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(BufferedWriter is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class path{
		
		/**
		 * 
		 * @param fullPath  "com/cowthan/csv/contact-91.csv"
		 * @return
		 */
		public static String getAbsolutePathInContext(String fullPath){
			String uri = AyoFiles.class.getClassLoader().getResource("") + fullPath;  //file:/D:/ws0425-jee/JavaTest/bin/
			return uri.replace("file:/", "");
			/*
			 这两个一样
			 System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
			System.out.println(Test1.class.getClassLoader().getResource(""));
			 */
		}
		
	}
	
	public static class steam{
		
		public static String string(InputStream is){
			try {
				InputStreamReader reader = new InputStreamReader(is);
				return string(reader);
			} finally {
				io.close(is);
			}
			
		}
		
		public static String string(Reader is){
			BufferedReader br = new BufferedReader(is);
			String line = "";
			StringBuilder sb = new StringBuilder();
			try {
				while((line = br.readLine()) != null){
					sb.append(line);
				}
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				io.close(br);
				io.close(is);
			}
			return "";
		}
		
		public static byte[] bytes(InputStream is){
				
			return null;
		}
		
		public static String string(String path){
			return string(new File(path));
		}
		
		public static String string(File f){
			try {
				return string(new FileInputStream(f));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		public static byte[] bytes(String path){

			return null;
		}
		
		public static byte[] bytes(File f){

			return null;
		}
		
		public static void output(String content, OutputStream os){
			try {
				OutputStreamWriter writer = new OutputStreamWriter(os);
				output(content, writer);
			} finally {
				io.close(os);
			}
			
		}
		
		public static void output(String content, Writer writer){
			BufferedWriter bw = new BufferedWriter(writer);
			try {
				bw.write(content);
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				io.close(bw);
				io.close(writer);
			}
		}
		
		public static void output(byte[] content, OutputStream os){
			
		}
		
		public static void output(String content, String path, boolean append){
			output(content, new File(path), append);
		}
		
		public static void output(String content, File f, boolean append){

			try {
				FileOutputStream os = new FileOutputStream(f, append);
				output(content, os);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		public static void output(byte[] content, String path, boolean append){
			
		}
		
		public static void output(byte[] content, File f, boolean append){
			
		}	
		
	}
	
	public static class file{
		
		public static void isFile(File f){
			
		}
		
		public static void isDir(File f){
			
		}
		
		public static void isExists(File f){
			
		}
		
		/**
		 * 可删文件，目录，删目录时不要递归
		 * @param f
		 */
		public static void delete(File f){
			
		}
		
		public static void rename(File f, String newName){
			
		}
		
		public static void move(File src, File dest){
			
		}
		
		public static void copy(File src, File dest){
			
		}
		
		public static void touch(File f){
			
		}
		
		
	}

}
