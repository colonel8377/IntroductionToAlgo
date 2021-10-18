package MatchString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMP {
    public static void main(String[] args) {
//        System.out.println(indexOf("abaabaacaba", "aac"));
//        getNext03("abaabaacaba".toCharArray());
        int q = -1;
        List<Integer> res = new ArrayList<>();
        char[] t = "abaabaacaba".toCharArray();
        char[] p = "aac".toCharArray();
        int[] next = new int[]{-1, 0, -1};
        for (int i = 0; i < 9; i++) {
            while (q >= 0 &&  p[q + 1] != t[i]) {
                q = next[q];
            }
            if(p[q + 1] == t[i]){
                q = q + 1;
            }
            if(q == 2){
                res.add(i - 2 + 1);
                q = next[q];
            }
        }
    }

    public static int indexOf(String source, String pattern) {
        int i = 0, j = 0;
        char[] src = source.toCharArray();
        char[] ptn = pattern.toCharArray();
        int sLen = src.length;
        int pLen = ptn.length;
        int[] next = getNext01(ptn);
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

    public static void getNext04(char[] p) {
        int[] next = new int[p.length + 1];
        for (int i = 1; i < p.length; i++) {
            int j = next[i + 1];

            while (j > 0 && p[j] != p[i]) {
                j = next[j];
            }

            if (j > 0 || p[j] == p[i]) {
                next[i + 1] = j + 1;
            }
        }

    }

    public static void getNext03(char[] p) {
        int n = p.length;
        int[] pi = new int[n];
        pi[0] = 0;
        int q = 1;
        int k = -1;
        for (int i = 1; i < n; i++) {
            while (q > 0 && p[q] != p[i]) {
                q = pi[q - 1];
            }

            if (p[q] == p[i]) {
                q++;
            }

            pi[i] = q;
        }
        for (int ne : pi) {
            System.out.print(" " + ne);
        }
    }

    public static int[] getNext02(char[] p) {
        int mLength = p.length;
        int[] next = new int[p.length];
        next[0] = -1;
        int pre = -1; //next[current-1]
        int current = 0;
        int count = 1;
        while (current < mLength - 1) { //pre表示前缀,current表示后缀
            if (pre == -1 || p[current] == p[pre]) { //若p[k]=p[j],则 next[j+1]=next[j]+1;
                ++pre;
                ++current;
                next[current] = pre;

            } else {
                pre = next[pre];
            }
            System.out.print("Loops " + count + " : ");
            for (int ne : next) {
                System.out.print(" " + ne);
            }
            System.out.println();
            count++;
        }

        return next;
    }

    public static int[] getNext01(char[] p) {
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
            for (int ne : next) {
                System.out.print(" " + ne);
            }
            System.out.println();
            count++;
        }
        return next;
    }
}