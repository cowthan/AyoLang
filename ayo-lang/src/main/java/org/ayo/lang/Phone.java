package org.ayo.lang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import org.ayo.Ayo;
import org.ayo.Configer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

public class Phone {
	
	 private static final String TAG = "Phone";
	
    public static boolean isEmulator(Context context){
        String androidId= Settings.System.getString(context.getContentResolver(), "android_id");
        if(TextUtils.isEmpty(androidId)
                || Build.MODEL.equals("sdk")
                || Build.MODEL.equals("google_sdk")){
            return true;
        }
        return false;
    }

    /**
     * get the phone number, always fail
     */
    public static String getPhoneNumber() {
        TelephonyManager tManager = (TelephonyManager) Ayo.context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        Log.e(TAG, "the phone number is: " + number);
        return number;
    }
    
    /**
     */
    protected static String getCpuInfo() {
        String str = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader("/proc/cpuinfo");
            if (localFileReader != null)
                try {
                    localBufferedReader = new BufferedReader(localFileReader, 1024);
                    str = localBufferedReader.readLine();
                    localBufferedReader.close();
                    localFileReader.close();
                } catch (IOException localIOException) {
                    Log.e(TAG, "Could not read from file /proc/cpuinfo", localIOException);
                }
        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e(TAG, "Could not open file /proc/cpuinfo", localFileNotFoundException);
        }
        if (str != null) {
            int i = str.indexOf(58) + 1;
            str = str.substring(i);
        }
        return str.trim();
    }
    
    
    @SuppressLint("NewApi")
	public static String getDeviceId() {
    	String KEY = "deviceId-genius";
		String deviceId = Configer.getInstance().get(KEY, "");
		if (Lang.isNotEmpty(deviceId)) {
			return deviceId;
		}

		TelephonyManager tm = (TelephonyManager) Ayo.context.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		if (Lang.isEmpty(deviceId)) {
			if (Build.VERSION.SDK_INT >= 10) {
				deviceId = Build.SERIAL;
			}
		}
		if (Lang.isEmpty(deviceId) || "0123456789ABCDE".equals(deviceId)) {
			deviceId = Secure.getString(Ayo.context.getContentResolver(),
					Secure.ANDROID_ID);
			if ("9774d56d682e549c".equals(deviceId)
					|| Lang.isEmpty(deviceId)) {
				try {
					deviceId = Configer.getInstance().get(KEY, "");
					if (Lang.isNotEmpty(deviceId)) {

					} else {
						deviceId = UUID.randomUUID().toString();
						Configer.getInstance().put(KEY, deviceId);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		if (Lang.isEmpty(deviceId)) {
			deviceId = UUID.randomUUID().toString();
		}
		Configer.getInstance().put(KEY, deviceId);
		return deviceId;
	}
	
	/**
     *
     * @param mContext
     */
    public static String[] getAccesstype(Context mContext) {
        // TODO Auto-generated method stub
        String[] arrayOfString = {
                "Unknown", "Unknown"
        };
        PackageManager localPackageManager = mContext.getPackageManager();
        if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE",
                mContext.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }
        ConnectivityManager localConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE); //"connectivity");
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }
        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
        if (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "Wi-Fi";
            return arrayOfString;
        }
        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
        if (localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2G/3G";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }
        return arrayOfString;
    }
	
	/**
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     */
    public static String getMac() {
        String mac = null;
        try {
            WifiManager localWifiManager = (WifiManager) Ayo.context.getSystemService(Context.WIFI_SERVICE); //"wifi");
            WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
            mac = localWifiInfo.getMacAddress();
        } catch (Exception localException) {
            Log.i("Mac", "Could not read MAC, forget to include ACCESS_WIFI_STATE permission?",
                    localException);
        }
        return mac;
    }
    
	public static String getLocAddress() {

		String ipaddress = "";

		try {
			Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface networks = en.nextElement();
				Enumeration<InetAddress> address = networks
						.getInetAddresses();
				while (address.hasMoreElements()) {
					InetAddress ip = address.nextElement();
					System.out.println("+++: " + ip.getHostAddress());
					if (!ip.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ip
									.getHostAddress())
							&& !ip.getHostAddress().equals("10.0.2.15")
					) {
						ipaddress = ip.getHostAddress();
					}
				}
			}
			// ipaddress = "192.168.0.106";
		} catch (SocketException e) {
			e.printStackTrace();
		}

		System.out.println("IP is:" + ipaddress);

		return ipaddress;

	}
	

}
