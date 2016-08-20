package org.ayo.lang;

import org.ayo.Ayo;
import org.ayo.file.Files;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;


public class SystemIntent {
	
	/**
	 * 
	 * @param activity
	 * @param path  need a full path of jpg file, or you can just use generateRandomFilePath()
	 */
	private static int REQ_CAMERA = 1953;
	public static void openCamera(Activity activity, String path){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        activity.startActivityForResult(intent, REQ_CAMERA);
    }
	
	public static String generateRandomFilePath(){
		return Files.path.getDirInRoot("camera") + "/" + System.currentTimeMillis() + ".jpg";
	}
	
	public static boolean isResultFromCamera(int requestCode){
		return requestCode == REQ_CAMERA;
	}
	
	
	public static void startBrowser(String url){
		Intent intent = new Intent();        
        intent.setAction("android.intent.action.VIEW");    
        Uri content_url = Uri.parse(url);   
        intent.setData(content_url);  
        Ayo.context.startActivity(intent);
	}
	
	
	/**
	 * check if the location service is opened
	 * @param context
	 * @return
	 */
	public static final boolean isGPSOpened(final Context context) { 
        LocationManager locationManager  
                                 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE); 
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位） 
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); 
        if (gps || network) { 
            return true; 
        } 
   
        return false; 
    }
	
	/**
     * force open the GPS
     */
    public static final void openGPS(Activity activity) { 
    	activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
    
    
    
    /**
     * com.android.settings.AccessibilitySettings 辅助功能设置
com.android.settings.ActivityPicker 选择活动
com.android.settings.ApnSettings APN设置
com.android.settings.ApplicationSettings 应用程序设置
com.android.settings.BandMode 设置GSM/UMTS波段
com.android.settings.BatteryInfo 电池信息
com.android.settings.DateTimeSettings 日期和坝上旅游网时间设置
com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
com.android.settings.DeviceAdminSettings 设备管理器
com.android.settings.DeviceInfoSettings 关于手机
com.android.settings.Display 显示——设置显示字体大小及预览
com.android.settings.DisplaySettings 显示设置
com.android.settings.DockSettings 底座设置
com.android.settings.IccLockSettings SIM卡锁定设置
com.android.settings.InstalledAppDetails 语言和键盘设置
com.android.settings.LanguageSettings 语言和键盘设置
com.android.settings.LocalePicker 选择手机语言
com.android.settings.LocalePickerInSetupWizard 选择手机语言
com.android.settings.ManageApplications 已下载（安装）软件列表
com.android.settings.MasterClear 恢复出厂设置
com.android.settings.MediaFormat 格式化手机闪存
com.android.settings.PhysicalKeyboardSettings 设置键盘
com.android.settings.PrivacySettings 隐私设置
com.android.settings.ProxySelector 代理设置
com.android.settings.RadioInfo 手机信息
com.android.settings.RunningServices 正在运行的程序（服务）
com.android.settings.SecuritySettings 位置和安全设置
com.android.settings.Settings 系统设置
com.android.settings.SettingsSafetyLegalActivity 安全信息
com.android.settings.SoundSettings 声音设置
com.android.settings.TestingSettings 测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
com.android.settings.TetherSettings 绑定与便携式热点
com.android.settings.TextToSpeechSettings 文字转语音设置
com.android.settings.UsageStats 使用情况统计
com.android.settings.UserDictionarySettings 用户词典
com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
com.android.settings.WirelessSettings 无线和网络设置

使用下面两句之一可以实现
startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);

 总共可以使用的系统调用如下：
String	ACTION_ACCESSIBILITY_SETTINGS	Activity Action: Show settings for accessibility modules.
String	ACTION_ADD_ACCOUNT	Activity Action: Show add account screen for creating a new account.
String	ACTION_AIRPLANE_MODE_SETTINGS	Activity Action: Show settings to allow entering/exiting airplane mode.
String	ACTION_APN_SETTINGS	Activity Action: Show settings to allow configuration of APNs.
String	ACTION_APPLICATION_DETAILS_SETTINGS	Activity Action: Show screen of details about a particular application.
String	ACTION_APPLICATION_DEVELOPMENT_SETTINGS	Activity Action: Show settings to allow configuration of application development-related settings.
String	ACTION_APPLICATION_SETTINGS	Activity Action: Show settings to allow configuration of application-related settings.
String	ACTION_BLUETOOTH_SETTINGS	Activity Action: Show settings to allow configuration of Bluetooth.
String	ACTION_DATA_ROAMING_SETTINGS	Activity Action: Show settings for selection of 2G/3G.
String	ACTION_DATE_SETTINGS	Activity Action: Show settings to allow configuration of date and time.
String	ACTION_DEVICE_INFO_SETTINGS	Activity Action: Show general device information settings (serial number, software version, phone number, etc.).
String	ACTION_DISPLAY_SETTINGS	Activity Action: Show settings to allow configuration of display.
String	ACTION_DREAM_SETTINGS	Activity Action: Show Daydream settings.
String	ACTION_INPUT_METHOD_SETTINGS	Activity Action: Show settings to configure input methods, in particular allowing the user to enable input methods.
String	ACTION_INPUT_METHOD_SUBTYPE_SETTINGS	Activity Action: Show settings to enable/disable input method subtypes.
String	ACTION_INTERNAL_STORAGE_SETTINGS	Activity Action: Show settings for internal storage.
String	ACTION_LOCALE_SETTINGS	Activity Action: Show settings to allow configuration of locale.
String	ACTION_LOCATION_SOURCE_SETTINGS	Activity Action: Show settings to allow configuration of current location sources.
String	ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS	Activity Action: Show settings to manage all applications.
String	ACTION_MANAGE_APPLICATIONS_SETTINGS	Activity Action: Show settings to manage installed applications.
String	ACTION_MEMORY_CARD_SETTINGS	Activity Action: Show settings for memory card storage.
String	ACTION_NETWORK_OPERATOR_SETTINGS	Activity Action: Show settings for selecting the network operator.
String	ACTION_NFCSHARING_SETTINGS	Activity Action: Show NFC Sharing settings.
String	ACTION_NFC_SETTINGS	Activity Action: Show NFC settings.
String	ACTION_PRIVACY_SETTINGS	Activity Action: Show settings to allow configuration of privacy options.
String	ACTION_QUICK_LAUNCH_SETTINGS	Activity Action: Show settings to allow configuration of quick launch shortcuts.
String	ACTION_SEARCH_SETTINGS	Activity Action: Show settings for global search.
String	ACTION_SECURITY_SETTINGS	Activity Action: Show settings to allow configuration of security and location privacy.
String	ACTION_SETTINGS	Activity Action: Show system settings.
String	ACTION_SOUND_SETTINGS	Activity Action: Show settings to allow configuration of sound and volume.
String	ACTION_SYNC_SETTINGS	Activity Action: Show settings to allow configuration of sync settings.
String	ACTION_USER_DICTIONARY_SETTINGS	Activity Action: Show settings to manage the user input dictionary.
String	ACTION_WIFI_IP_SETTINGS	Activity Action: Show settings to allow configuration of a static IP address for Wi-Fi.
String	ACTION_WIFI_SETTINGS	Activity Action: Show settings to allow configuration of Wi-Fi.
String	ACTION_WIRELESS_SETTINGS	Activity Action: Show settings to allow configuration of wireless controls such as Wi-Fi, Bluetooth and Mobile networks.
String	AUTHORITY	
String	EXTRA_ACCOUNT_TYPES	Activity Extra: Limit available options in launched activity based on the given account types.
String	EXTRA_AUTHORITIES	Activity Extra: Limit available options in launched activity based on the given authority.
String	EXTRA_INPUT_METHOD_ID

     * @param activity
     * @param action
     */
    public static void openSettings(Activity activity, String action){
    	
    }
    
    
    
    @TargetApi(4)
	public static Intent getSMSIntent(List<String> toList, String content) {
		String spc = ",";
		if (Build.MANUFACTURER.contains("HTC")) {
			spc = ";";
		}
		Intent it = new Intent();
		it.setAction(Intent.ACTION_SENDTO);
		StringBuilder sb = new StringBuilder();
		for (String address : toList) {
			if (sb.length() > 0)
				sb.append(spc);
			sb.append(address);
		}
		it.putExtra("address", sb.toString());
		if(!TextUtils.isEmpty(content)) {
			it.putExtra("sms_body", content);
		}
		it.setData(Uri.fromParts("sms", sb.toString(), null));
		return it;
	}
	
	public static Intent getSMSIntent(String content) {
		Intent it = new Intent(Intent.ACTION_VIEW);
		it.putExtra("sms_body", content);
		it.setType("vnd.android-dir/mms-sms");
		return it;
	}
	
	public static Intent getBrowseWebIntent(String urlStr) {
		Intent it = new Intent();
		Uri uri = null;
		if (urlStr == null)
			urlStr = "";
		it.setAction(Intent.ACTION_VIEW);
		// simple checking for http protocol head, should be regular in a
		// tool class
		if (!Pattern.matches("^[\\w-]+://.*$", urlStr)) {
			urlStr = "http://" + urlStr;
		}
		uri = Uri.parse(urlStr);
		it.setData(uri);
		return it;
	}
	
	public static Intent getCallItent(String number) {
	    Intent it = new Intent(Intent.ACTION_CALL);
        it.setData(Uri.fromParts("tel", number, null));
        return it;
	}

    public static Intent getDialItent(String number) {
        Intent it = new Intent(Intent.ACTION_DIAL);
        it.setData(Uri.fromParts("tel", number, null));
        return it;
    }
    
    public static void dial(Context c, String number){
    	 Intent it = new Intent(Intent.ACTION_DIAL);
         it.setData(Uri.fromParts("tel", number, null));
         c.startActivity(it);
    }
    
    public static void call(Context c, String number){
    	 Intent it = new Intent(Intent.ACTION_CALL);
         it.setData(Uri.fromParts("tel", number, null));
         c.startActivity(it);
   }
    
    public static void openSystemAlbun(Activity a, int requestCode){
    	Intent intent = new Intent(Intent.ACTION_PICK, null);

		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		a.startActivityForResult(intent, requestCode);
    }
    
    public static void openCamera(Activity a, File outFile, int requestCode){
    	Intent intent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
		a.startActivityForResult(intent, requestCode);
    }
    
    /**
	 * @param uri
	 */
	public static void openPhotoCrop(Activity a, Uri uri, File outFile, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("output", Uri.fromFile(outFile)); // save path
		a.startActivityForResult(intent, requestCode);
	}
}
