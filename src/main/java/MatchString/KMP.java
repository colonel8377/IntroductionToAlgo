package MatchString;

import java.util.Arrays;

public class KMP {
    public static void main(String[] args) {
        System.out.println(indexOf("abaabaacaba", "abaabaacaba"));
    }
    public static int indexOf(String source, String pattern) {
        int i = 0, j = 0;
        char[] src = source.toCharArray();
        char[] ptn = pattern.toCharArray();
        int sLen = src.length;
        int pLen = ptn.length;
        int[] next = getNext(ptn);
        while (i < sLen && j < pLen) {
            if (j == -1 || src[i] == ptn[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == pLen)
            return i - j;
        return -1;
    }
    public static int[] getNext(char[] p) {
        int pLen = p.length;
        int[] next = new int[pLen];
        Arrays.fill(next, -2);
        int k = -1;
        int j = 0;
        next[0] = -1;
        int count = 1;
        while (j < pLen - 1) {
            if (k == -1 || p[j] == p[k]) {
                k++;
                j++;
                if (p[j] != p[k]) {
                    next[j] = k;
                } else {
                    next[j] = next[k];
                }
            } else {
                k = next[k];
            }
            System.out.print("Loops " + count + " : ");
            for(int ne : next){
                System.out.print(" " + ne);
            }
            System.out.println();
            count ++;
        }
        return next;
    }
}