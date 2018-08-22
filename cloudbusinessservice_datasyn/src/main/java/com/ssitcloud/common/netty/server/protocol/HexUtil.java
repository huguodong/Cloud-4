
package com.ssitcloud.common.netty.server.protocol;


/**
 * 十六进制转换
 * @author yeyalin
 *
 */
public class HexUtil
{

    public HexUtil()
    {
    }

    public static long hexString2long(String hexString)
    {
        byte bts[] = hexString2Bytes(hexString);
        return byteTolong(bts);
    }

    public static long byteTolong(byte b[])
    {
        int mask = 255;
        int temp = 0;
        long n = 0L;
        int length = b.length;
        for(int i = 0; i < length; i++)
        {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }

        return n;
    }

    public static byte[] hexString2Bytes(String src)
    {
        byte ret[] = new byte[src.length() / 2];
        byte tmp[] = src.getBytes();
        for(int i = 0; i < src.length() / 2; i++)
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);

        return ret;
    }

    public static String int2HexString(int src, int len)
    {
        String hexStr;
        for(hexStr = Integer.toHexString(src).toUpperCase(); hexStr.length() < len; hexStr = (new StringBuilder("0")).append(hexStr).toString());
        return hexStr;
    }

    public static byte uniteBytes(byte src0, byte src1)
    {
        byte _b0 = Byte.decode((new StringBuilder("0x")).append(new String(new byte[] {
            src0
        })).toString()).byteValue();
        _b0 <<= 4;
        byte _b1 = Byte.decode((new StringBuilder("0x")).append(new String(new byte[] {
            src1
        })).toString()).byteValue();
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }

    public static String printHexString(byte b[])
    {
        StringBuffer returnValue = new StringBuffer();
        for(int i = 0; i < b.length; i++)
        {
            String hex = Integer.toHexString(b[i] & 255);
            if(hex.length() == 1)
                hex = (new StringBuilder(String.valueOf('0'))).append(hex).toString();
            returnValue.append(hex.toUpperCase());
        }

        return returnValue.toString();
    }

    public static String printHexString(byte b[], int len)
    {
        StringBuffer returnValue = new StringBuffer();
        for(int i = 0; i < len; i++)
        {
            String hex = Integer.toHexString(b[i] & 255);
            if(hex.length() == 1)
                hex = (new StringBuilder(String.valueOf('0'))).append(hex).toString();
            returnValue.append(hex.toUpperCase());
        }

        return returnValue.toString();
    }

    public static String checkSum(String src)
    {
        int length = src.length();
        String sumString = null;
        for(int i = 0; i < length; i += 2)
            sumString = sumHex(sumString, src.substring(i, i + 2));

        return sumString.substring(sumString.length() - 2, sumString.length()).toUpperCase();
    }

    private static String sumHex(String src1, String src2)
    {
        if(src1 == null)
        {
            return src2;
        } else
        {
            src1 = src1.replace("/", "");
            src2 = src2.replace("/", "");
            long x = Long.parseLong(src1, 16);
            long y = Long.parseLong(src2, 16);
            return Long.toHexString(x + y);
        }
    }

    public static String increase(String src)
    {
        if(Long.parseLong(src, 16) >= 100L)
            return src;
        long x = Long.parseLong(src, 16);
        String tmp = Long.toHexString(x + 1L).toUpperCase();
        if(tmp.length() == 1)
            return (new StringBuilder("0")).append(tmp).toString();
        else
            return tmp.substring(tmp.length() - 2, tmp.length()).toUpperCase();
    }

    public static String reduce(String src)
    {
        if(Long.parseLong(src, 16) <= 0L)
            return src;
        long x = Long.parseLong(src, 16);
        String tmp = Long.toHexString(x - 1L).toUpperCase();
        if(tmp.length() == 1)
            return (new StringBuilder("0")).append(tmp).toString();
        else
            return tmp.substring(tmp.length() - 2, tmp.length()).toUpperCase();
    }
}
