package com.kevin.datastructure.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @Author kevin
 * @Date 2016/10/7 21:23
 */
public class Main2 {
    public static void main(String[] args) {

    }

    public static List<String> readWords(BufferedReader bin) throws IOException {
        String oneLine;
        List<String> words = new ArrayList<>();

        while((oneLine = bin.readLine()) != null)
            words.add(oneLine);

        return words;
    }

    public static Map<String, List<String>> computeAdjacentWords(List<String> words) {
        Map<String, List<String>> adjacentWords = new HashMap<>();
        Map<Integer, List<String>> wordsByLength = new HashMap<>();

        for(String word : words)
            update(wordsByLength, word.length(), word);

        //处理每一组
        for (Map.Entry<Integer, List<String>> entry : wordsByLength.entrySet()) {
            List<String> groupWords = entry.getValue();
            int groupNum = entry.getKey();

            //删除每个位置的字符，得到一个子串，则子串相同的字符串构成词梯
            for(int i = 0; i < groupNum; i++) {
                //子串相同的字符串构成词梯
                Map<String, List<String>> repToWords = new HashMap<>();
                for (String word : groupWords) {
                    String rep = word.substring(0, i) + word.substring(i + 1);
                    update(repToWords, rep, word);
                }

                //构建词梯
                for(List<String> wordClique : repToWords.values())
                    if(wordClique.size() >= 2)
                        for(String s1 : wordClique)
                            for(String s2 : wordClique)
                                if(s1 != s2)
                                    update(adjacentWords, s1, s2);
            }
        }

        return adjacentWords;
    }

    private static <K> void update(Map<K, List<String>> m, K key, String value) {
        List<String> lst = m.get(key);
        if (lst == null) {
            lst = new ArrayList<>();
            m.put(key, lst);
        }
        lst.add(value);
    }

    public static List<String> findChain(Map<String, List<String>> adjacentWords, String first, String second) {
        Map<String, String> previouswords = new HashMap<>();
        Queue<String> q = new LinkedList<>();

        q.offer(first);

        while (!q.isEmpty()) {
            String current = q.poll();
            List<String> adjWords = adjacentWords.get(current);

            if(adjWords != null)
                for(String word : adjWords)
                    if (previouswords.get(word) == null) {
                        previouswords.put(word, current);
                        q.offer(word);
                    }
        }

        previouswords.put(first, null);

        return findChainFromPreviousMap(previouswords, first, second);
    }

    private static List<String> findChainFromPreviousMap(Map<String, String> previouswords, String first, String second) {
        LinkedList<String> path = new LinkedList<>();
        String str = second;

        for(; str != null; str = previouswords.get(str))
            path.addFirst(str);

        return path;
    }
}
