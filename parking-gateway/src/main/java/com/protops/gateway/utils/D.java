package com.protops.gateway.utils;


import java.util.*;

public class D {

    public static int[][] testGpSpeed(int size) {

        System.out.println("--- gp speed " + size + " times ---");

//		int[][] css = new int[size][0x5f];

        System.out.println(new Date());
        for (int i = 0; i < size; i++) {
//			css[i] = gp();
            gp();
        }
        System.out.println(new Date());

//		return css;
        return null;
    }

    public static void testEnSpeed(int size, String msg, int[] p) {

        System.out.println("--- en speed " + size + " times ---");

        System.out.println(new Date());
        for (int i = 0; i < size; i++) {
            en(msg, p);
        }
        System.out.println(new Date());
    }

    public static void testDeSpeed(int size, String msg, int[] p) {

        System.out.println("--- de speed " + size + " times ---");

        System.out.println(new Date());
        for (int i = 0; i < size; i++) {
            de(msg, p);
        }
        System.out.println(new Date());
    }

    public static String p2s(int[] p) {
        StringBuilder ret = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 'c' + 'h'; i++) {
            ret.append(r.nextInt(0x5f)).append(',');
        }
        for (int i : p) {
            ret.append(i).append(',');
        }
        for (int i = 0; i < 'd' + 'i' + 'n' + 'g'; i++) {
            ret.append(r.nextInt(0x5f)).append(',');
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static List<Integer> p2p(int[] p) {
        List<Integer> iList = new ArrayList<Integer>();
        Random r = new Random();
        for (int i = 0; i < 'c' + 'h'; i++) {
            iList.add(r.nextInt(0x5f));
        }
        for (int i : p) {
            iList.add(i);
        }
        for (int i = 0; i < 'd' + 'i' + 'n' + 'g'; i++) {
            iList.add(r.nextInt(0x5f));
        }
        return iList;
    }

    public static int[] gp() {

        int[] cs = new int[0x5f];
        for (int i = 0; i < 0x5f; i++) {
            cs[i] = i;
        }
//		p(cs);

        Random r = new Random();
        for (int i = 0; i < 0x5f; i++) {
            int ri = r.nextInt(0x5f);
            int t = cs[i];
            cs[i] = cs[ri];
            cs[ri] = t;
        }
//		sp(cs);

        return cs;
    }

    public static String en(String src, int[] p) {
        String t = r(src, rp(p));
        StringBuilder ret = new StringBuilder(src.length());
        for (int i = 0; i < t.length(); i++) {
            ret.append((char) ((t.charAt(i) - 0x20 + i * (i + 'd' * 'c' * 'h' + i) * i) % 0x5f + 0x20));
        }
        return ret.toString();
    }

    public static String de(String src, int[] p) {
        StringBuilder t = new StringBuilder(src.length());
        for (int i = 0; i < src.length(); i++) {
            t.append((char) (((src.charAt(i) - 0x20 - i * (i + 'd' * 'c' * 'h' + i) * i) % 0x5f + 0x5f) % 0x5f + 0x20));
        }
        return r(t.toString(), p);
    }

    public static int[] rp(int[] p) {
        int[] rp = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            rp[p[i]] = i;
        }
        return rp;
    }

    public static String r(String src, int[] p) {
        StringBuilder ret = new StringBuilder(src.length());
        for (int i = 0; i < src.length(); i++) {
            ret.append((char) ((p[src.charAt(i) - 0x20]) + 0x20));
        }
        return ret.toString();
    }

    public static void p(int[] p) {
        for (int i : p) {
            System.out.print((char) (i + 0x20));
        }
        System.out.println();
    }

    public static void sp(int[] p) {

        List<Character> l = new ArrayList<Character>();
        for (int i : p) {
            l.add((char) (i + 0x20));
        }
        Collections.sort(l);
        for (Character c : l) {
            System.out.print(c);
        }
        System.out.println();
    }
}
