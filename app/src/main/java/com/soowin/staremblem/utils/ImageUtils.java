package com.soowin.staremblem.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by hxt on 2018/4/8 0008.
 * 图片操作类
 */

public class ImageUtils {
    private static String filePath;
    private static Bitmap mBitmap;
    private static String mFileName = "";
    private static String mSaveMessage;
    private final static String TAG = "ImageUtils";
    private static Context context;

    private static DonwloadImgLinener mdonwloadImgLinener;


    /**
     * 下载图片
     *
     * @param contexts
     * @param filePaths
     */
    public static void donwloadImg(Context contexts, String filePaths,
                                   String FileName, DonwloadImgLinener donwloadImgLinener) {
        context = contexts;
        filePath = filePaths;
        mFileName = FileName;
        mdonwloadImgLinener = donwloadImgLinener;
        try {
            byte[] data = getImage(filePath);
            if (data != null) {
                mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
            } else {
                mdonwloadImgLinener.onDonwloadImgLinener(false);
            }
            saveFile(mBitmap, mFileName);
            mdonwloadImgLinener.onDonwloadImgLinener(true);
        } catch (IOException e) {
            mdonwloadImgLinener.onDonwloadImgLinener(false);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }


    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        fileName = fileName + ".png";
        File jia = new File(Environment.getExternalStorageDirectory().getPath() + "/StarEmblem/Images");
        if (!jia.exists()) {   //判断文件夹是否存在，不存在则创建
            jia.mkdirs();
        }
        File myCaptureFile = new File(jia + "/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();

        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public interface DonwloadImgLinener {
        void onDonwloadImgLinener(boolean isOk);
    }
}
