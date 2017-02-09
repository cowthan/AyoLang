package org.ayo.lang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import junit.framework.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/12/18.
 */

public class Bitmaps {



    private static int calculateInSampleSize(int originWidth, int originHeight, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = originWidth;
        final int width = originHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }

    // 获取需要进行缩放的比例，即options.inSampleSize
    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        return initialSize;
    }

    public static final int UNCONSTRAINED = -1;
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        // 获得图片的宽和高
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math
                .ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math
                .min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == UNCONSTRAINED)
                && (minSideLength == UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 获得设置信息
    public static BitmapFactory.Options getOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只描边，不读取数据
        options.inJustDecodeBounds = true;
        // 加载到内存
        BitmapFactory.decodeFile(path, options);
        return options;
    }

    public static int[] getImageSize(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只描边，不读取数据
        options.inJustDecodeBounds = true;
        // 加载到内存
        BitmapFactory.decodeFile(path, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static int[] getImageSize(Context context, int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);

        return new int[]{options.outWidth, options.outHeight};
    }

    // 获得图像
    public static Bitmap getBitmapByPath(String path, int viewWidth, int  viewHeight) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream inputStream = null;
        inputStream = new FileInputStream(file);
        BitmapFactory.Options options = getOptions(path);
        if (options != null) {
            Rect r = new Rect(0, 0, viewWidth, viewHeight);
            // 取得图片的宽和高
            int w = r.width();
            int h = r.height();
            int maxSize = w > h ? w : h;
            // 计算缩放比例
            int inSimpleSize = computeSampleSize(options, maxSize, w * h);
            // 设置缩放比例
            options.inSampleSize = inSimpleSize;
            options.inJustDecodeBounds = false;
        }

        // 加载压缩后的图片
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 文件大小转换
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    public static Bitmap createThumbNail(final String path, final int height, final int width, final boolean crop) {
        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                //WLog.e(BitmapUtils.class, "bitmap decode failed");
                return null;
            }

            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            //WLog.e(BitmapUtils.class, "decode bitmap failed: " + e.getMessage());
            options = null;
        }

        return null;
    }



    public static Bitmap getBitmapByResource(Context ctx, int id, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(ctx.getResources(), id, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        // Decode bitmap with inSampleSize set
        return BitmapFactory.decodeResource(ctx.getResources(), id, options);
    }
	

}
