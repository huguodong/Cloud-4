package com.ssitcloud.app_reader.common.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.ssitcloud.app_reader.common.exception.UnDecodeBarcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/14.
 * 条码工具
 */

public class BarcodeUtil {
    /**
     * 生成条形码，默认黑色
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static Bitmap encodeToOneBarcode(String str,int width,int height) throws WriterException{
        return encodeToOneBarcode(str, width, height,0xff000000);
    }

    /**
     * 生成条形码，指定颜色
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @param color 颜色ARGB值，例如0xff000000
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static Bitmap encodeToOneBarcode(String str,int width,int height,int color) throws WriterException {
//        HashMap<EncodeHintType,Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height);

        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();

        int[] pixels = new int[matrixWidth * matrixHeight];
        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * matrixWidth + x] = color;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(matrixWidth, matrixHeight,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, matrixWidth, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成二位码，默认黑色
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static Bitmap createTwoDCode(String str,int width,int height) throws WriterException {
        return createTwoDCode(str,width,height,0xff000000);
    }

    /**
     * 生成二位码，指定颜色
     * @param str 字符串
     * @param width 条形码宽
     * @param height 条形码高
     * @param color 颜色ARGB值，例如0xff000000
     * @return 生成的Bitmap
     * @throws WriterException 字符编码失败
     */
    public static Bitmap createTwoDCode(String str,int width,int height,int color) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, width, height);
        matrix = deleteWhite(matrix);
        width = matrix.getWidth();
        height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = color;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static String decode(Bitmap bitmap) throws UnDecodeBarcode {
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        LuminanceSource source = new RGBLuminanceSource(bitmap);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        try {
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
            return result.getText();
        } catch (NotFoundException e) {
            throw new UnDecodeBarcode();
        }
    }

    private static BitMatrix deleteWhite(BitMatrix matrix){
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}
