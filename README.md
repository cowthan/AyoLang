# AyoSdk
常用工具类，java函数糖，安卓函数糖

没整到jcenter上，暂时就用aar包吧，也挺省劲：[下载地址](https://github.com/cowthan/AyoLang/blob/master/ayo-lang.aar?raw=true)  
下载下来文件名是：ayo-lang.aar，拷到libs目录里  
然后在build.gradle里引入：
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

compile(name:'ayo-lang', ext:'aar')
```

* 第三方依赖：
    * fastjson：compile 'com.alibaba:fastjson:1.2.8'，请注意版本

## 1 初始化
初始化涉及到的类是Ayo，在Application.onCreate()里

```java
package com.cowthan.sample;

import android.app.Application;

import org.ayo.Ayo;
import org.ayo.CrashHandler;
import org.ayo.Logger;
import org.ayo.VanGogh;

/**
 * Created by cowthan on 2016/1/24.
 */
public class App extends Application{

    public static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //初始化AyoSDK
        Ayo.init(this, "ayo", true, true);
        Ayo.debug = true;

    }
}
```

要访问全局上下文，可以使用`App.app`或`Ayo.context`


## 2 常用工具类

* 常用工具类
    * Lang
    * 集合工具类 Lists
    * 字符串工具类 Strings
    * 安卓系统Intent
    * 手机信息访问
    * App信息访问
    * 日期工具类
    * IP地址

java语法糖，函数糖，一般都在org.ayo.lang中


### 1 判空系列

```java
Lang.isEmpty(String)
Lang.isEmpty(Collection<?>)
Lang.isNull(Object)

还有对应的isNotEmpty, isNotNull
```

### 2 长度系列

```java
Lang.count(List或者数组，或者String)
```

### 3 equals和toString
```java
Lang.equals(o1, o2)
Lang.toString(o1)
Lang.equalsIgnoreCase(String, String)
```
### 4 snull方法：String为null时的替换

```java
String s = Lang.snull("可能为null的字符串", "如果为null，则返回这个");
```

### 5 数组和集合操作：Lang和Lists, Maps

```java
//数组和List的创建
String[] ss = Lang.array("A","B","C");
List<Object> list =  Lang.list("true", "23", "ABC");
List<Object> list =  Lists.newArrayList("true", "23", "ABC");
Set<Object> set= Lang.set("true", "23", "ABC");

//contains
contains：针对数组和List，Set，数组
containsKey和containsValue：针对map

//元素访问，无则给null
Object ele = Lists.lastElement(list);  //最后一个元素
Object ele = Lists.firstElement(list); //第一个元素

//元素筛选
List<T> list = Lists.killTraitors(List<T> ours, List<T> traitors); //从ours中删掉traitor包含的元素，基于contains和equals方法

//列表合并
List<T> list = Lists.combine(List<T> list1, List<T> list2); //list1和list2合并为一个list

//遍历
Lists.each(list, new OnWalk<String>() {
    @Override
    public boolean process(int index, String s, int total) {
        //index是下标， s是当前元素，total是一共几个
    }
});

//克隆：返回一个新list，但元素还是相同的引用
List<T> list = Lists.combine(List<T> list1)
List<T> list = Lists.combine(T[] arr)

//遍历并删除元素：删除为null的字符串
//注意，内部实现是复制了一份list，然后遍历新list，在原list基础上做remove
List<Object> list =  Lang.list("true", "23", "ABC", null);
Lists.remove(list, new OnWalk<String>() {
    @Override
    public boolean process(int index, String s, int total) {
        if(s == null) return true;
        return false;
    }
});

```

### 6 转换

```java
//类型转换，如果出现异常，则返回对应类型的0
int i = Lang.toInt("23");
long l = Lang.toLong("23")
double d = Lang.toDouble("23.3");

```

### 7 日期转换

```java
//日期
String date = Lang.toDate("yyyy-MM-dd", "1547232323"); //参数2是秒数，String类型
String date = Lang.toDate("yyyy-MM-dd", 1547232323); //参数2是秒数，long类型
String date = Lang.toDate("yyyy-MM-dd", new Date());

//时间戳：当前毫秒
long timestamp = Lang.getTimeStamp();
```

### 8 字符串工具类：Strings

#### 从各种来源得到字符串

```java
//读取输入流
String s = Lang.fromStream(InputStream inputStream);

//拼接
List<String> list = Lang.list("true", "23", "ABC", null);
String s = fromList(list, ",", true); //返回true, 23, abc
s = fromList(list, ",", false); //  返回true, 23, abc, ,

fromArray, fromSet也是一个道理

//炸裂
String[] arr = Strings.split(src, delemeter);  //按分隔符炸
List<String> list = String.split(较长字符串, 一个元素最长多少);  //按长度炸
```

#### 各种模式匹配

```java
//比较常用的字符串格式判断


//中英文相关


//全角半角相关

```

#### IP地址相关还有一个类：InetAddressUtils，也是判断各种格式的IP地址

### 9 手机设备幻象：Phone

Phone类代表当前手机，提供了对手机的访问

```java
Phone.isEmulator(); //是否模拟器
Phone.getPhoneNumber(); //得到手机号，一般得不到
Phone.getCpuInfo(); //得到当前cpu信息
Phone.getDeviceId(); //综合多种方式得到一个唯一的设备ID
Phone.getAccesstype(); //得到当前联网方式
Phone.getMac(); //得到Mac地址，需要权限：ACCESS_WIFI_STATE
Phone.getLocAddress(); //得到当前IP地址，可能不是真正的网卡IP
```
### 10 当前APP幻象：TheApp
```java
TheApp类代表当前应用，提供了对应用的访问
TheApp.getAppVersionName(); //得到版本名
TheApp.getAppVersionCode(); //得到版本号
TheApp.isServiceRunning(Service的class); //查看指定service是否在运行
```
__怎么知道当前app是否进入了后台？__

本来看到一个库，说是五种方法判断程序进入前台还是后台，但试了试，怎么这么卡呢
https://github.com/wenmingvs/AndroidProcess

具体代码没看，但鉴于并没有一个receiver机制可以知道这个信息，所以估计是做了大循环，
为了不做大循环，初步估计得记录所有Activity的当前状态才行

这个工作如果要做，就在TheApp里做

### 11 Json工具类：JsonUtils

基于FastJson，注意，没有用type或者TypeToken相关的，怕接口不友好

```java
List<Bean> list = JsonUtils.getBeanList(json, Bean.class);
Bean bean = JsonUtils.getBean(json, Bean.class);
```

###11 安卓提供的Intent和Content，Receiver等： SystemIntent和OS类

打开相机，打开设置，打电话，打开浏览器等，暂时不整理了，因为：
__这个类是要是删除的，因为现在对外接口不够友好，但因为之前代码还有用，所以暂时留着__


要作为代替的类是：OS，表示当前系统，请优先使用这个


### 12 其他

```java
//震动
Lang.i_am_cold();

//读取异常
String s = Lang.readThrowable(throwable);
```

## 3 Bitmaps工具类

提供了解析图片的基本方法


## 4 观察者模式支持

Observable和Observer，以组件形式提供，而非基类形式

```
///发布者
protected Observable observable = new Observable();
public Observable getObservable(){
    return observable;
}

///通知订阅者
getObservable().notifyDataChanged(this, 任意数据);

///订阅者
v.getObservable().addObserver(new Observer() {
    @Override
    public void update(Observable observable, Object src, Object data) {
        setNotify(data.toString());
    }
});
```

## 5 日志

Lang.log系列

Lang.raise


## 6 Json支持

基于fastjson：compile 'com.alibaba:fastjson:1.2.8'

## 7 加密解密

Md5Utils  
DES

## 5 file相关


## 6 Configer


## 7 Display