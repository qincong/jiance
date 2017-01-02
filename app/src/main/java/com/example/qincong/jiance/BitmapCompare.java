package com.example.qincong.jiance;

import java.text.DecimalFormat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCompare
{
    private static final String DIFFERENT_SIZE = "differentSize";
    private static final String RESULT_FORMAT = "00.0%";

    public static Double similarity(String url1, String url2) {
        Bitmap bm1 = BitmapFactory.decodeFile(url1);
        Bitmap bm2 = BitmapFactory.decodeFile(url2);
        return similarity(bm1, bm2);
    }

    public static Double similarity(Bitmap bm1, Bitmap bm2) {
        final int bm1Width = bm1.getWidth();
        final int bm2Width = bm2.getWidth();
        final int bmHeight = bm1.getHeight();

        if (bmHeight != bm2.getHeight() || bm1Width != bm2Width)
            return 0.0;

        int[] pixels1 = new int[bm1Width];
        int[] pixels2 = new int[bm2Width];

        reset();
        for (int i = 0; i < bmHeight; i++) {
            bm1.getPixels(pixels1, 0, bm1Width, 0, i, bm1Width, 1);
            bm2.getPixels(pixels2, 0, bm2Width, 0, i, bm2Width, 1);

            comparePixels(pixels1, pixels2, bm1Width);
        }

        return percent(Count.sT, Count.sF + Count.sT);
    }

    private static void comparePixels(int[] pixels1, int[] pixels2, int length) {
        for (int i = 0; i < length; i++) {
            if ((pixels1[i] - pixels2[i])>=-1000&&(pixels1[i] - pixels2[i])<=1000) {
                Count.sT++;
            } else {
                Count.sF++;
            }
        }
    }

    private static Double percent(int divisor, int dividend) {
        final double value = divisor * 1.0 / dividend*100;
        DecimalFormat df = new DecimalFormat(RESULT_FORMAT);
        return value;
    }

    private static void reset() {
        Count.sT = 0;
        Count.sF = 0;
    }

    private static class Count {
        private static int sT;
        private static int sF;
    }
}