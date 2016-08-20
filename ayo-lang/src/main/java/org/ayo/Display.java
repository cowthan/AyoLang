package org.ayo;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Display {
	
	public static int statusBarHeight = 0;
	public static int screenWidth = 0;
	public static int screenHeight = 0;

	public static void init(Context context) {
		statusBarHeight = getStatusBarHeight(context);
		screenWidth = getScreenWidth((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE));
		screenHeight = getScreenHeight((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE));
	}

	private static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(
					resourceId);
		}
		return result;
	}

	private static int getScreenHeight(WindowManager manager) {
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	private static int getScreenWidth(WindowManager manager) {
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	// /--------------------
	/**
	 *
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {
		final float scale = Ayo.context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 */
	public static int px2dip(float pxValue) {
		final float scale = Ayo.context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 *
	 * @param context
	 * @param value
	 * @return
	 */
	public static float sp2px(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return value * metrics.scaledDensity;
	}

	/**
	 *
	 * @param context
	 * @param value
	 * @return
	 */
	public static float px2sp(Context context, float value) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return value / metrics.scaledDensity;
	}

	// ========================OrientationHelper================//
	public static final int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;
	public static final int PORTRAIT = Configuration.ORIENTATION_PORTRAIT;
	public static final int NOTHING = -100;

	public static Integer userTending(int orientation, int previous) {

		if (previous == PORTRAIT) {
			if (orientation > 85 && orientation < 115) {
				return LANDSCAPE;
			} else if (orientation > 285 && orientation < 300) {
				return LANDSCAPE;
			} else if (orientation > 160 && orientation < 210) {
				return LANDSCAPE;
			}
		} else if (previous == LANDSCAPE) {
			if (orientation > 0 && orientation < 30) {
				return PORTRAIT;
			} else if (orientation > 330 && orientation < 360) {
				return PORTRAIT;
			}
		}
		return NOTHING;
	}
	
}
