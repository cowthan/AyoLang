package org.ayo.lang;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class Strings {

	/**
	 * check ip is，192.168.0.213
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isIPAdress(String str) {
		Pattern pattern = Pattern
				.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
		return pattern.matcher(str).matches();
	}
	 
    public static boolean isMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile); 
    } 
	
    public static boolean isPhone(String str) {
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    } 
    
    
    /**
	 * */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * check if contains chinese
	 * 
	 * */
	public static final boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	private static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	/**
	 * is shenfenzheng
	 */
	public static boolean isCard(String s_aStr) {
		String str = "\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z]";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(s_aStr);
		return m.matches();
	}
	
	/** can just has shuzi, zimu, xiahuaxian */
	public static boolean isName(String s_aStr) {
		String str = "[\u4e00-\u9fa5\\w]+";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(s_aStr);
		return m.matches();
	}
	
	/**only digital */
	public static boolean isDigital(String s_aStr) {
		String str = "^-?\\d+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(s_aStr);
		return m.matches();
	}
	
	
	/**
     * is int
     */ 
    public static boolean isDigit2(String digit) { 
        String regex = "\\-?[1-9]\\d+"; 
        return Pattern.matches(regex,digit); 
    } 
    
	public static String getUrl(String src) {
		if (TextUtils.isEmpty(src)) {
			return null;
		}
		Pattern p = Pattern.compile("http://[\\w\\.\\-/:]+",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(src);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
	
	/**
     * is int or float
     */ 
    public static boolean isDecimals(String decimals) { 
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?"; 
        return Pattern.matches(regex,decimals); 
    }  
    
    /**
     * is \t \n \r \f \x0B space
     */ 
    public static boolean isBlankSpace(String blankSpace) { 
        String regex = "\\s+"; 
        return Pattern.matches(regex,blankSpace); 
    } 

	public static boolean pattern(String pattern, String content) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(content);
		return m.matches();
	}

	/**
	 *
	 * @param inputStream
	 * @return
	 */
	public static String fromStream(InputStream inputStream) {
		String jsonStr = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			jsonStr = new String(out.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * Joiner
	 * @param list
	 * @param delemeter
	 * @param ignoreNull if true, null will be ignored, or null is ""
	 * @return
	 */
	public static <T> String fromList(List<T> list, String delemeter, boolean ignoreNull){
		if(Lang.isEmpty(list)) return "";
		String res = "";
		for (int i = 0; i < list.size(); i++) {
			if(ignoreNull && list.get(i) == null) continue; 
			res += Lang.snull(list.get(i)) + (i == list.size() - 1 ? "" : delemeter);
		}
		return res;
	}
	
	/**
	 * Joiner
	 * @param list
	 * @param delemeter
	 * @param ignoreNull  if true, null will be ignored, or null is ""
	 * @return list = null, return ""
	 */
	public static <T> String fromArray(T[] list, String delemeter, boolean ignoreNull){
		if(Lang.isEmpty(list)) return "";
		String res = "";
		for (int i = 0; i < list.length; i++) {
			if(ignoreNull && list[i] == null) continue; 
			res += Lang.snull(list[i]) + (i == list.length - 1 ? "" : delemeter);
		}
		return res;
	}
	
	/**
	 * @param delemeter
	 * @param ignoreNull if true, null will be ignored, or null is ""
	 * @return
	 */
	public static <T> String fromSet(Set<T> set, String delemeter, boolean ignoreNull){
		if(Lang.isEmpty(set)) return "";
		String res = "";
		int i = 0;
		for (T str : set) {  
			if(ignoreNull && str == null) {
				i++;
				continue; 
			}
			res += Lang.snull(str) + (i == set.size() - 1 ? "" : delemeter);
			i++;
		} 
		return res;
	}

	public static String[] split(String s, String delemeter){
		if(s == null) return null;
		return s.split(delemeter);
	}

	/**
	 * split the string, every element will be limited by emelemtLength
	 * @param s
	 * @param elementLength
	 * @return
	 */
	public static List<String> split(String s, int elementLength){
		
		List<String> list = new ArrayList<String>();
		
		if(s == null){
			return list;
		}
		if(s.length() <= elementLength){
			list.add(s);
		}else{
			
			int start = 0;
			
			while(start < s.length()){
				int end = start + elementLength;
				if(end > s.length()){
					end = s.length();
				}
				//System.out.println("" + start + ", " + end);
				String element = s.substring(start, end);
				start = end;
				list.add(element);
			}
			
			
		}
		
		return list;
		
	}
	
	/**
     */
    public static boolean isChineseCharacter(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     */
    public static boolean isFullWidthCharacter(char c) {
        // 全角空格为12288，半角空格为32
        // 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
        // 全角空格 || 其他全角字符
        if (c == 12288 || (c > 65280 && c < 65375)) {
            return true;
        }
        // 中文全部是全角
        if (isChineseCharacter(c)) {
            return true;
        }
        // 日文判断
        // 全角平假名 u3040 - u309F
        // 全角片假名 u30A0 - u30FF
        if (c >= '\u3040' && c <= '\u30FF') {
            return true;
        }
        return false;
    }

    /**
     */
    public static char toHalfWidthCharacter(char c) {
        if (c == 12288) {
            return (char) 32;
        } else if (c > 65280 && c < 65375) {
            return (char) (c - 65248);
        }
        return c;
    }

    /**
     */
    public static String toHalfWidthString(CharSequence str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(toHalfWidthCharacter(str.charAt(i)));
        }
        return sb.toString();
    }

    /**
     */
    public static boolean isFullWidthString(CharSequence str) {
        return charLength(str) == str.length() * 2;
    }

    /**
     */
    public static boolean isHalfWidthString(CharSequence str) {
        return charLength(str) == str.length();
    }

    /**
     */
    public static int charLength(CharSequence str) {
        int clength = 0;
        for (int i = 0; i < str.length(); i++) {
            clength += isFullWidthCharacter(str.charAt(i)) ? 2 : 1;
        }
        return clength;
    }

    
    /**
	 * raw = 10000, return 10,000
	 * */
    public static String getCurrency(String raw){
		if(raw == null || raw.equals("")) return "0";
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		return currencyFormat.format(Double.parseDouble(raw));
	}
    
    
    /**
     * copy c by num times
     */
    public static String dup(char c, int num) {
        if (c == 0 || num < 1)
            return "";
        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < num; i++)
            sb.append(c);
        return sb.toString();
    }
    
    /**
     * if s = aaa
	 * width = 5
	 * c = 1
	 *
	 * then return aaa11
     */
    public static String alignRight(String s, int width, char c) {
        if (null == s)
            return null;
        int len = s.length();
        if (len >= width)
            return s;
        return new StringBuilder().append(dup(c, width - len)).append(s).toString();
    }

    /**
	 * if s = aaa
	 * width = 5
	 * c = 1
	 *
	 * then return 11aaa
     */
    public static String alignLeft(Object o, int width, char c) {
        if (null == o)
            return null;
        String s = o.toString();
        int length = s.length();
        if (length >= width)
            return s;
        return new StringBuilder().append(s).append(dup(c, width - length)).toString();
    }


	//----------

}
