package com.protops.gateway.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CipherUtil {

    private final static char[] HEX = "0123456789abcdef".toCharArray();

    public static byte[] encrypt(byte[] key, byte[] bys) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey sk = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        return cipher.doFinal(bys);
    }

    public static byte[] decrypt(byte[] key, byte[] bys) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey sk = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        // Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sk);
        return cipher.doFinal(bys);

    }

    public static String bytes2Hex2(byte[] bys) {
        char[] chs = new char[bys.length * 2];
        for (int i = 0; i < bys.length; i++) {
            chs[2 * i] = HEX[bys[i] >> 4 & 0xf];
            chs[2 * i + 1] = HEX[bys[i] & 0xf];
        }
        return new String(chs);
    }

    /**
     * 按银行提供的规则生成新的key
     *
     * @return
     */
    public static String newKeyProduct(String seedStr, String roundStr) {
        // step1
        // seed从末尾第二位截取secureKey长度的位数(0000ef12)（如果截取的长度不足8位，则左补零补齐）
        String newSeedStr = "";
        if (seedStr.length() > 8) {
            newSeedStr = seedStr.substring(seedStr.length() - 9, seedStr.length() - 1);
        } else if (seedStr.length() < 9 && seedStr.length() > 0) {
            newSeedStr = seedStr.substring(0, seedStr.length() - 1);
        } else {
            newSeedStr = "00000000";
        }
        int len = newSeedStr.length();
        // 没有8位的用零补齐
        if (newSeedStr.length() < 8) {
            for (int i = 0; i < 8 - len; i++) {
                newSeedStr = "0" + newSeedStr;
            }
        }
        // step2
        // 将STEP1的secureKey(abcd1234)和截取的seed (0000ef12)分别奇偶位排序->secureKey
        // (ac13bd24), seed (00e100f2)
        roundStr = newStr(roundStr); // 奇偶排序
        newSeedStr = newStr(newSeedStr);// 奇偶排序
        // step3
        // 将STEP2的secureKey(ac13bd24), seed(00e100f2)按位串联->newKey
        // (a0c01e31b0d02f42)

        // 最后要返回的字符串
        String finalStr = step3(roundStr, newSeedStr);
        // step4
        // STEP4:将STEP3的newKey截取8位: a0c01e31
        finalStr = finalStr.substring(0, 8);
        return finalStr;
    }

    /**
     * 步骤三 按位串联
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String step3(String str1, String str2) {
        String newStr = "";
        for (int i = 0; i < 8; i++) {
            newStr = newStr + str1.substring(i, i + 1) + str2.substring(i, i + 1);
        }
        return newStr;
    }

    /**
     * 将传送过来的字符串奇偶排序(字符串长度为8)
     *
     * @param str 要排序的参数
     * @return
     */
    public static String newStr(String str) {
        String returnStr = "";
        String[] array = new String[8];
        for (int i = 0; i < 8; i++) {
            array[i] = str.substring(i, i + 1);
        }
        returnStr = array[0] + array[2] + array[4] + array[6] + array[1] + array[3] + array[5] + array[7];
        return returnStr;
    }

    /**
     * 生成长度为8的随机数 同时包含字母和数字
     *
     * @return 生成的随机数
     */
    public static String randStr() {
        String returnStr = "";
        String[] strArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z",};
        for (int k = 0; k < 8; k++) {
            java.util.Random r = new java.util.Random();
            returnStr = returnStr + strArray[r.nextInt(36)];
        }
        return returnStr;
    }

    /**
     * 获得新密钥
     *
     * @param loginName 登录名
     * @return 新密钥
     */
    public static String getNewKey(String loginName) {
        String key = randStr(); // 八位随机数
        String newKey = newKeyProduct(loginName, key); // 根据规则生成新的key

        return newKey;
    }

}
