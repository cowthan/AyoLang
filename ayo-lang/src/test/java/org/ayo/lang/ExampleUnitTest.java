package org.ayo.lang;

import android.util.Log;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void testLang() throws Exception {

        ///
        Object o = null;
        String s = null;
        List<String> list = null;
        String[] arr = null;
        Set<String> set = null;
        Map<String, String> map = null;

        ///---1 判空系列
        assertTrue(Lang.isEmpty(o));
        assertTrue(Lang.isEmpty(s));
        assertTrue(Lang.isEmpty(list));
        assertTrue(Lang.isEmpty(arr));
        assertTrue(Lang.isEmpty(set));
        assertTrue(Lang.isEmpty(map));

        assertFalse(Lang.isNotEmpty(o));
        assertFalse(Lang.isNotEmpty(s));
        assertFalse(Lang.isNotEmpty(list));
        assertFalse(Lang.isNotEmpty(arr));
        assertFalse(Lang.isNotEmpty(set));
        assertFalse(Lang.isNotEmpty(map));

        ///---1 count系列
        assertEquals(0, Lang.count(s));
        assertEquals(0, Lang.count(list));
        assertEquals(0, Lang.count(arr));
        assertEquals(0, Lang.count(set));
        assertEquals(0, Lang.count(map));

        ///---1 equals系列
        String s1 = "11";
        String s2 = null;
        assertFalse(Lang.equals(s1, s2));

        s1 = "11";
        s2 = "11";
        assertTrue(Lang.equals(s1, s2));

        s1 = null;
        s2 = null;
        assertFalse(Lang.equals(s1, s2));

        s1 = "abc";
        s2 = "AbC";
        assertTrue(Lang.equalsIgnoreCase(s1, s2));

        //rstring, rcolor, rdimen
        //toString
        //snull

        ///---1 集合创建系列
        s = "abc";
        list = Lang.collection.newArrayList("1", "dd", "3");
        set = Lang.collection.newHashSet("1", "dd", "3");
        arr = new String[]{"1", "dd", "3"};
        arr = Lang.array("1", "dd", "3");
        map = Lang.collection.newHashMap("1", "a", "2", "b", "3", "c", "4", "d");
        assertEquals(3, Lang.count(s));
        assertEquals(3, Lang.count(list));
        assertEquals(3, Lang.count(arr));
        assertEquals(3, Lang.count(set));
        assertEquals(4, Lang.count(map));

        ///---1 集合包含系列
        assertTrue(Lang.contains(list, "dd"));
        assertFalse(Lang.contains(list, "adf"));
        assertTrue(Lang.contains(arr, "dd"));
        assertTrue(Lang.contains(set, "dd"));
        assertTrue(Lang.containsKey(map, "1"));
        assertTrue(Lang.containsValue(map, "a"));

        ///---1 字符串转数值
        assertEquals(1, Lang.toInt("1"));
        assertEquals(0, Lang.toInt("0x1"));
        assertEquals(0, Lang.toInt("1sdf"));
        assertEquals(-1, Lang.toInt("1sdf", -1));

        assertEquals(2.1, Lang.toDouble("2.1"));

        assertEquals(2.1f, Lang.toFloat("2.1"));
        assertEquals(2.1f, Lang.toFloat("2.1f"));

        ///---1 秒转格式化时间
        /*
         y 年
          M 月
          d 日
          h 时 在上午或下午 (1~12)
          H 时 在一天中 (0~23)
          m 分
          s 秒
          S 毫秒
          E 星期
          D 一年中的第几天
          F 一月中第几个星期几
          w 一年中第几个星期
          W 一月中第几个星期
          a 上午 / 下午 标记符
          k 时 在一天中 (1~24)
          K 时 在上午或下午 (0~11)
          z 时区
         */
        System.out.print("dddd");
        Log.e("dd", "dfasf");
        s = "Tue May 31 17:46:55 +0800 2011";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
        Date date = new Date(s); //sdf.parse(s);

        String formatStr = Lang.tryToDate(s, "yyyy-MM-dd");
        assertEquals("2011-05-31", formatStr);

        s1 = Lang.toDate("yyy-MM-dd", date);
        assertEquals("2011-05-31", s1);

        long secondes = date.getTime()/1000;
        assertEquals("2011-05-31", Lang.toDate("yyy-MM-dd", secondes));

        assertEquals("2011-05-31", Lang.toDate("yyy-MM-dd", secondes+""));

        try{
            String dd = null;
            dd.toString();
        }catch (Exception e){
            RuntimeException ex = new RuntimeException("还是出问题了", e);
            System.out.print(Lang.readThrowable(ex));
        }
        ///---

    }

}