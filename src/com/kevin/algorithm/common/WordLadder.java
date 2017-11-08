package com.kevin.algorithm.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 词梯游戏：在一个词梯中，每个单词均由前面的单词改变一个字母而得到。
 * @Author kevin
 * @Date 2016/10/7 17:02
 */
public class WordLadder {
    /**
     * 读取输入的单词
     */
    public static List<String> readWords(BufferedReader in) throws IOException {
        String oneLine;
        List<String> lst = new ArrayList<>();

        while((oneLine = in.readLine()) != null)
            lst.add(oneLine);

        return lst;
    }

    /**
     * 检测两个单词是否只在一个字母上不同
     */
    private static boolean oneCharOff(String word1, String word2) {
        if(word1.length() != word2.length())
            return false;

        int diffs = 0;

        for(int i = 0; i < word1.length(); i++)
            if(word1.charAt(i) != word2.charAt(i))
                if(++diffs > 1)
                    return false;

        return diffs == 1;
    }

    /**
     * 更新词梯
     */
    private static <KeyType> void update(Map<KeyType, List<String>> m, KeyType key, String value) {
        List<String> lst = m.get(key);
        if (lst == null) {
            lst = new ArrayList<>();
            m.put(key, lst);
        }

        lst.add(value);
    }

    /**
     * 计算词梯：第一种解法：穷举法，时间复杂度O(N^2)
     */
    public static Map<String,List<String>> computeAdjacentWordsSlow( List<String> theWords )
    {
        Map<String,List<String>> adjWords = new HashMap<>( );

        String [ ] words = new String[ theWords.size( ) ];

        theWords.toArray( words );
        for( int i = 0; i < words.length; i++ )
            for( int j = i + 1; j < words.length; j++ )
                if( oneCharOff( words[ i ], words[ j ] ) )
                {
                    update( adjWords, words[ i ], words[ j ] );
                    update( adjWords, words[ j ], words[ i ] );
                }

        return adjWords;
    }

    /**
     * 计算词梯：第二种解法：按单词长度先将单词分组，再对每组单词进行穷举
     */
    public static Map<String,List<String>> computeAdjacentWordsMedium( List<String> theWords )
    {
        Map<String,List<String>> adjWords = new HashMap<>( );
        Map<Integer,List<String>> wordsByLength = new HashMap<>( );

        // Group the words by their length
        for( String w : theWords )
            update( wordsByLength, w.length( ), w );

        // Work on each group separately
        for( List<String> groupsWords : wordsByLength.values( ) )
        {
            String [ ] words = new String[ groupsWords.size( ) ];

            groupsWords.toArray( words );
            for( int i = 0; i < words.length; i++ )
                for( int j = i + 1; j < words.length; j++ )
                    if( oneCharOff( words[ i ], words[ j ] ) )
                    {
                        update( adjWords, words[ i ], words[ j ] );
                        update( adjWords, words[ j ], words[ i ] );
                    }
        }

        return adjWords;
    }

    /**
     * 计算词梯：第三种解法：按单词长度先将单词分组，对每组单词，删除每个位置的字符得到一个子串，则子串相同的字符串构成词梯
     * 时间复杂度：
     * O(NlogN) -- TreeMap
     * O(N) -- HashMap
     */
    public static Map<String,List<String>> computeAdjacentWords( List<String> words )
    {
        Map<String,List<String>> adjWords = new TreeMap<>( );
        Map<Integer,List<String>> wordsByLength = new TreeMap<>( );

        // Group the words by their length
        for( String w : words )
            update( wordsByLength, w.length( ), w );

        // Work on each group separately
        for( Map.Entry<Integer,List<String>> entry : wordsByLength.entrySet( ) )
        {
            List<String> groupsWords = entry.getValue( );
            int groupNum = entry.getKey( );

            // Work on each position in each group
            for( int i = 0; i < groupNum; i++ )
            {
                // Remove one character in specified position, computing representative.
                // Words with same representative are adjacent, so first popular
                // a map
                Map<String,List<String>> repToWord = new HashMap<>( );

                for( String str : groupsWords )
                {
                    String rep = str.substring( 0, i ) + str.substring( i + 1 );
                    update( repToWord, rep, str );
                }

                // and then look for map values with more than one string
                for( List<String> wordClique : repToWord.values( ) )
                    if( wordClique.size( ) >= 2 )
                        for( String s1 : wordClique )
                            for( String s2 : wordClique )
                                if( s1 != s2 )  // must be same string; equals not needed
                                    update( adjWords, s1, s2 );
            }
        }

        return adjWords;
    }

    /**
     * 打印个数不少于minWords个的词梯
     */
    public static void printHighChangeables(Map<String, List<String>> adjWords, int minWords) {
        for (Map.Entry<String, List<String>> entry : adjWords.entrySet()) {
            List<String> words = entry.getValue();

            if (words.size() >= minWords) {
                System.out.print(entry.getKey() + " (");
                System.out.print(words.size() + "):");
                for(String w : words)
                    System.out.print(" " + w);
                System.out.println();
            }
        }
    }

    /**
     * 寻找最大词梯数对应的所有词梯
     */
    public static List<String> findMostChangeable(Map<String, List<String>> adjWords) {
        List<String> mostChangeableWords = new ArrayList<>();   //记录最大词梯数对应的词梯
        int maxNumberOfAdjWords = 0;    //记录最大词梯数

        for (Map.Entry<String, List<String>> entry : adjWords.entrySet()) {
            List<String> changes = entry.getValue();

            if (changes.size() > maxNumberOfAdjWords) { //如果有更大的词梯，则更新词梯数，并清空映射表中的key
                maxNumberOfAdjWords = changes.size();
                mostChangeableWords.clear();
            }
            if(changes.size() == maxNumberOfAdjWords)   //如果是最大的词梯，则将其key加入到list中
                mostChangeableWords.add(entry.getKey());
        }

        return mostChangeableWords;
    }

    /**
     * 打印具有最大词梯数对应的所有词梯
     */
    public static void printMostChangeables(List<String> mostChangeable, Map<String, List<String>> adjacentWords) {
        for (String word : mostChangeable) {
            System.out.print(word + ":");
            List<String> adjWords = adjacentWords.get(word);
            for(String adjWord : adjWords)
                System.out.println(" " + adjWord);
            System.out.println("(" + adjWords.size() + " words)");
        }
    }

    /**
     * 得到最短路径的词梯
     */
    public static List<String> getChainFromPreviousMap(Map<String, String> previousMap, String first, String second) {
        LinkedList<String> result = new LinkedList<>();

        for(String str = second; str != null; str = previousMap.get(str))
            result.addFirst(str);

        return result;
    }

    /**
     * 从邻接表表示的图中计算从first到second的最短路径：采用广度优先遍历
     * 注：每一个单词都是一个顶点，如果两个单词只相差一个字符，则它们之间有边。previousMap中的key是关联边的入端顶点，value是关联边
     * 的出端顶点
     */
    public static List<String> findChain(Map<String, List<String>> adjacentWords, String first, String second) {
        Map<String, String> previoursMap = new HashMap<>();
        Queue<String> q = new LinkedList<>();

        q.offer(first); //将源点入队
        while (!q.isEmpty()) {
            String current = q.poll();  //出队一个顶点
            List<String> adjWords = adjacentWords.get(current); //得到该顶点的所有邻接顶点

            if(adjWords != null)
                for(String adjWord : adjWords)  //遍历邻接顶点
                    if (previoursMap.get(adjWord) == null) {    //如果该邻接顶点未被访问
                        previoursMap.put(adjWord, current);     //则将其标记为已访问
                        q.add(adjWord);                         //并入队
                    }
        }

        previoursMap.put(first, null);  //注意处理源点

        return getChainFromPreviousMap(previoursMap, first, second);
    }

    /**
     * 从词典中计算从first到second的最短路径
     */
    public static List<String> findChain(List<String> words, String first, String second) {
        Map<String, List<String>> adjacentWords = computeAdjacentWords(words);
        return findChain(adjacentWords, first, second);
    }

    public static void main(String[] args) throws IOException {
        long start, end;

        FileReader fin = new FileReader("D:/dict.txt");
        BufferedReader bin = new BufferedReader(fin);
        List<String> words = readWords(bin);
        System.out.println("Read the words..." + words.size());
        Map<String, List<String>> adjacentWords;

        start = System.currentTimeMillis();
        adjacentWords = computeAdjacentWords(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time FAST: " + (end-start));

        start = System.currentTimeMillis();
        adjacentWords = computeAdjacentWordsMedium(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time MEDIUM: " + (end-start));


        start = System.currentTimeMillis() ;
        adjacentWords = computeAdjacentWordsSlow(words);
        end = System.currentTimeMillis();
        System.out.println("Elapsed time SLOW: " + (end-start));

        // printHighChangeables;
        System.out.println("Adjacents computed...");
        List<String> mostChangeable = findMostChangeable(adjacentWords);
        System.out.println("Most changeable computed..." );
        printMostChangeables(mostChangeable, adjacentWords);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        for(;;) {
            System.out.println("Enter two words: ");
            String w1 = in.readLine();
            String w2 = in.readLine();

            List<String> path = findChain(adjacentWords, w1, w2);
            System.out.print(path.size() + "...");
            for(String word : path)
                System.out.print(" " + word);
            System.out.println();
        }
    }

}
