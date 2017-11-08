package com.kevin.test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author kevin
 * @Date 2017/2/14 14:36
 */
public class Main3 {
    public static void main(String[] args) {
        String s = "raaeaedere";
        char[] ch = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        int i, j;
        for (i = 0; i < ch.length; i++) {
            if (!map.containsKey(ch[i])) {
                map.put(ch[i], 1);
            } else {
                map.put(ch[i], map.get(ch[i]) + 1);
            }
        }

        Tuple[] tuples = new Tuple[map.size()];
        i = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            tuples[i++] = new Tuple(entry.getKey(), entry.getValue());
        }

        Arrays.sort(tuples);

        for (i = 0; i < tuples.length; i++) {
            j = 0;
            for (j = 0; j < tuples[i].cnt; j++) {
                System.out.print(tuples[i].c);
            }
        }
    }

    private static class Tuple implements Comparable<Tuple> {
        private char c;
        private int cnt;

        public Tuple(char c, int cnt) {
            this.c = c;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Tuple o) {
            return o.cnt - cnt;
        }
    }
}
