package com.athena.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    private static ArrayList<byte[]> arrlist = new ArrayList<>();

    public static ArrayList<byte[]> formatFileBytes(byte[] b) {
        int high, low = 0;

        for (int i = 0; i < b.length; i++) {
            if (b[i] == 0) {
                return arrlist;
            }

            if (b[i] == 13) {
                high = i;
                arrlist.add(Arrays.copyOfRange(b, low, high));
                low = high + 2;
            }

            if (i == b.length - 1) {
                high = i + 1;
                if (low > high) {
                    low = high;
                }
                arrlist.add(Arrays.copyOfRange(b, low, high));
            }
        }
        return arrlist;
    }

    public static byte[] stripList(List<byte[]> list) {
        int length = 0;
        for (byte[] b : list) {
            length += b.length;
        }

        byte[] result = Arrays.copyOf(list.get(0), length);
        int offset = list.get(0).length;

        for (int i = 1; i < list.size(); i++) {
            System.arraycopy(list.get(i), 0, result, offset, list.get(i).length);
            offset += list.get(i).length;
        }
        return result;
    }
}