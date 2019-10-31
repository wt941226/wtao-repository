package com.wt.test;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

/**
 * 读取二进制文件 测试
 *
 * @author wtao
 * @date 2019/10/29 14:02
 **/
@SpringBootTest
public class TestReadBinary {


    public TestReadBinary() throws IOException {
    }

    @Test
    public void testRead() throws IOException {

        BufferedInputStream bis = null;

        bis = new BufferedInputStream(new FileInputStream("D://BAY01_0051_20170727_104023_380.dat"));

        int len = bis.available();
        byte[] b = new byte[len];

       /* Integer a = 2045;
        String s2 = Integer.toHexString(a).toUpperCase();
        System.out.println(s1);
        System.out.println(s2);*/
        bis.read(b, 0, len);
       /* System.out.println(b.length / 9);
        String s = new String(b.toString().getBytes("ascii"), "UTF-8");
        System.out.println(s);*/
        System.out.println(byte2short(b));
        for (byte b1 : b) {
            System.out.println(b1);

            //将data字节型数据转换为0~255 (0xFF 即BYTE)。
            int data = getUnsignedByte(b1);
            System.out.println(data);
            System.out.println(b1 & 0XFF);

           /* int a = b1>>8;
            System.out.println(a);
            System.out.println(getUnsignedInt(b1));
            System.out.println(getUnsignedByte1(b1));*/

        }
        //String s3 = Integer.toBinaryString(Integer.valueOf(Byte.toString(b1)));
        //String s4 = Integer.toHexString(Integer.valueOf(s3, 2));
        //String s5 = binaryString2hexString(s3);*/
/*
            System.out.println(s3);
            //System.out.println(s4);
            System.out.println(s5);*/
        /* int y = Integer.parseInt(Byte.toString(b1),8);*/
        //System.out.println(y);


      /*  DataInputStream is = new DataInputStream(
                new BufferedInputStream(new FileInputStream(
                        "D://BAY01_0051_20170727_104023_380.dat")));
        String readString = is.readUTF();
        System.out.println(readString);*/
    }


       /* String filePath = "D://BAY01_0051_20170727_104023_380.dat";
        byte[] bytes = ReadBinaryFile.getBytes4File(filePath);

        int toInt = Case10.byteToInt(bytes);
        System.out.println(toInt);*/
/*
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }*/


    public static int getUnsignedByte(byte data) {      //将data字节型数据转换为0~255 (0xFF 即BYTE)。
        return data & 0x0FF;
    }

    public static long getUnsignedInt(int data) {     //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFF;
    }

    public static int getUnsignedByte1(short data) {      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
        return data & 0x0FFFF;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            stringBuilder.append(i + ":");

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + ";");
        }
        return stringBuilder.toString();
    }


    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * byte[]转int
     *
     * @param bytes 需要转换成int的数组
     * @return int值
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    public static short byte2short(byte[] b) {
        short l = 0;
        for (int i = 0; i < 2; i++) {
            l <<= 8; //<<=和我们的 +=是一样的，意思就是 l = l << 8
            l |= (b[i] & 0xff); //和上面也是一样的  l = l | (b[i]&0xff)
        }
        return l;
    }
}
