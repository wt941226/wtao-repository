package com.wt.commons.file;


import java.io.*;
import java.util.*;

/**
 * 读取16进制文件 工具类（转为int数字）
 *
 * @author wtao
 * @date 2019-10-29 17:57
 */
public class Read16BFileUtils {


    public List<Integer> readFile(String filePath) throws IOException {

        BufferedInputStream bis = null;
        bis = new BufferedInputStream(new FileInputStream(filePath));

        int len = bis.available();
        byte[] b = new byte[len];

        bis.read(b);

        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < b.length; i++) {

            if (i % 2 == 0) {
                // 转为 10进制
                //System.out.println(b[i] & 0X0FF);


                // 转为16进制字符串
                String x = Integer.toHexString(b[i] & 0X0FF);
                if (x.length() == 1) {
                    x = "0" + x;
                }
                String y = Integer.toHexString(b[i + 1] & 0X0FF);
                if (y.length() == 1) {
                    y = "0" + y;
                }

                String data = y + x;

                int value = Integer.parseInt(data, 16);
                values.add(value);
            }
        }

        /*Map<Integer, List<Integer>> map = new TreeMap<>();
        for (int i = 0; i < values.size(); i += 11) {
            if (i % 11 == 0) {
                List<Integer> list = new ArrayList<>();
                for (int j = i + 1; j < i + 11; j++) {
                    if (j == i + 1 || j == i + 3) {
                        continue;
                    }
                    list.add(values.get(j));
                }
                map.put(values.get(i), list);
            }
        }
        System.out.println(map.size());

        for (Integer s : map.keySet()) {

            System.out.println(s + ", " + map.get(s).toString().replace("[", "").replace("]", ""));
        }*/

        return values;


    }


    public static int getUnsignedByte(byte data) {      //将data字节型数据转换为0~255 (0xFF 即BYTE)。
        return data & 0x0FF;
    }
}
