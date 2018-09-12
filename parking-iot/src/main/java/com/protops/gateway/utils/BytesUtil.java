package com.protops.gateway.utils;

import com.protops.gateway.util.DateUtil;
import com.protops.gateway.util.JsonUtil;
import com.protops.gateway.util.Page;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;


public class BytesUtil {

    public static void main(String[] args) throws IOException {

        Date startDate = DateUtil.parseDate("20160320", DateUtil.DATE_FMT_DISPLAY1);
        Date endDate = DateUtil.parseDate("20160620", DateUtil.DATE_FMT_DISPLAY1);

        List<String> ret;

        while(startDate.before(endDate) || startDate.equals(endDate)){

            try {
                String fileName = "d:\\1\\protops-gateway.log." + DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY1);

                System.out.println(fileName);
                ret = FileUtils.readLines(new File(fileName));

            }catch(FileNotFoundException e){
                startDate = DateUtil.getAddedTime(startDate, Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            List<String> result = new ArrayList<String>();

            File file = new File("d:\\result\\" + DateUtil.getDate(startDate, DateUtil.DATE_FMT_DISPLAY1));

            for (String line : ret) {

                try {

                    Integer idx = line.indexOf("VihicleRequest");

                    if (idx == -1) {
                        continue;
                    }

                    String tmp = line.substring(idx + 49, line.length() - 1);

                    Map<String, Object> r = JsonUtil.fromJson(tmp, Map.class);

                    Map<String, Object> r1 = (Map<String, Object>) r.get("result");

                    String inTime = (String) r1.get("inTime");
                    if (StringUtils.isEmpty(inTime)) {
                        continue;
                    }

                    String remainingParkNum = (String) r1.get("remainingParkNum");

                    if (StringUtils.isEmpty(remainingParkNum)) {
                        continue;
                    }

                    Date date = DateUtil.parseDate(inTime, "yyyyMMddHHmmss");

                    String inTimeStr = DateUtil.getDate(date, DateUtil.DATE_FMT_DISPLAY);

                    Double percent = Double.valueOf(remainingParkNum) / 1000 * 100;

                    DecimalFormat df = new DecimalFormat("######0.00");

                    result.add(df.format(percent) + "|" + inTimeStr);

                }catch(Exception e){
                    continue;
                }

            }

            FileUtils.writeLines(file, result);

            startDate = DateUtil.getAddedTime(startDate, Calendar.DAY_OF_YEAR, 1);

        }


    }

    /**
     * Base64编码
     *
     * @param bstr
     * @return String
     */
    public static String base64Encode(byte[] bstr) {
        return Base64.encodeBase64String(bstr);
    }

    /**
     * Base64解码
     *
     * @param str
     * @return string
     */
    public static byte[] base64Decode(String str) {
        return Base64.decodeBase64(str);
    }

    /**
     * 将16进制的字符串转换成bytes
     *
     * @param hex
     * @return 转化后的byte数组
     */
    public static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
    }

    /**
     * 将16进制的字符数组转换成byte数组
     *
     * @param hex
     * @return 转换后的byte数组
     */
    public static byte[] hexToBytes(char[] hex) {
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127)
                value -= 256;
            raw[i] = (byte) value;
        }
        return raw;
    }

    /**
     * 将byte数组转换成16进制字符串
     *
     * @param bytes
     * @return 16进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        String hexArray = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            int bi = b & 0xff;
            sb.append(hexArray.charAt(bi >> 4));
            sb.append(hexArray.charAt(bi & 0xf));
        }
        return sb.toString();
    }

    /**
     * Int转字节数组
     *
     * @param i
     * @return
     * @throws Exception
     */
    public static byte[] intToByteArray(int i) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeInt(i);
        byte[] b = buf.toByteArray();
        dos.close();
        buf.close();
        return b;
    }

    /**
     * 字节数组转Int
     *
     * @param b
     * @return
     * @throws Exception
     */
    public static int byteArrayToInt(byte b[]) throws Exception {
        ByteArrayInputStream buf = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(buf);
        return dis.readInt();
    }
}
