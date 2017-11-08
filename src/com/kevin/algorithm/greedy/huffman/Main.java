package com.kevin.algorithm.greedy.huffman;

import java.util.*;

/**
 * @Author kevin
 * @Date 2016/10/24 11:34
 */
public class Main {
    public static void main(String[] args) {
        String input = "is\na tie\n";
        EncodeResult result = huffmanEncode(input);
        Map<Character, String> characterEncodings = result.getCharacterEncodings();
        String encode = result.getEncode();

        System.out.println("字符编码表如下：");
        for(Map.Entry<Character, String> e : characterEncodings.entrySet())
            System.out.println(e);

        System.out.println("编码如下");
        System.out.println(encode);

        System.out.println("开始解码：");
        System.out.println(decode(result));
        System.out.println("解码完成");
    }

    /**
     * Huffman编码
     * @param input 待编码的字符串
     * @return 编码后的字符串
     */
    public static EncodeResult huffmanEncode(String input) {
        if(input == null || input.length() == 0)
            return null;

        /**
         * 构建Huffman编码树
         */
        //解析待编码的字符串，得到字符及其频率
        List<Node> list = parseToList(input);
        //按照频率将带编码的字符集存到优先队列中
        PriorityQueue<Node> q = new PriorityQueue<>(list);  // 构建最小堆，时间复杂度O(C)，其中C是优先队列中元素个数
        //每次任意选取两个权值最小的元素T1、T2，并构建以T1和T2为子树的新树，重复执行n-1次即得到Huffman编码树
        Node root = createHuffmanTree(q);

        /**
         * 获取字符编码表，以及编码结果
         */
        Map<Character, String> characterEncodings = parseHuffmanTree(root);
        EncodeResult result = encode(characterEncodings, input);
        return result;
    }

    /**
     * Huffman解码
     * @param encodeResult
     * @return
     */
    public static String decode(EncodeResult encodeResult) {
        StringBuilder decode = new StringBuilder();
        Map<String, Character> characterDecodings = getDecoder(encodeResult.getCharacterEncodings());
        String encode = encodeResult.getEncode();
        String tmp;
        int i = 1;
        while(encode.length() > 0) {
            tmp = encode.substring(0, i);
            if(characterDecodings.containsKey(tmp)) {
                decode.append(characterDecodings.get(tmp));
                encode = encode.substring(i);
                i = 1;
            } else
                i++;
        }
        return decode.toString();
    }

    /**
     * 通过字符编码表得到编码字符表
     * @param characterEncodings
     * @return
     */
    private static Map<String,Character> getDecoder(Map<Character, String> characterEncodings) {
        Map<String, Character> characterDecodings = new HashMap<>();
        for(Map.Entry<Character, String> e : characterEncodings.entrySet()) {
            Character key = e.getKey();
            String value = e.getValue();
            characterDecodings.put(value, key);
        }
        return characterDecodings;
    }

    private static EncodeResult encode(Map<Character, String> characterEncodings, String input) {
        StringBuilder encode = new StringBuilder();
        for(int i = 0; i < input.length(); i++)
            encode.append(characterEncodings.get(input.charAt(i)));
        EncodeResult result = new EncodeResult(characterEncodings, encode.toString());
        return result;
    }

    /**
     * 解析Huffman编码树，得到字符编码表
     * @param root 树的根节点
     * @return
     */
    private static Map<Character,String> parseHuffmanTree(Node root) {
        if(root == null)
            return null;

        Map<Character, String> characterEncodings = new HashMap<>();
        LinkedList<String> trace = new LinkedList<>();
        parseHuffmanTree(root, trace, characterEncodings);
        return characterEncodings;
    }

    /**
     * 递归解析Huffman编码树，得到字符编码表，Huffman编码树有C-1个非叶子节点，共有2(C-1)条，时间复杂度为O(C)
     * @param t
     * @param trace
     * @param characterEncodings
     */
    private static void parseHuffmanTree(Node t, LinkedList<String> trace, Map<Character, String> characterEncodings) {
        if(t.getLeft() == null &&
                t.getRight() == null) {
            StringBuilder encode = new StringBuilder();
            for(String s : trace)
                encode.append(s);
            characterEncodings.put(t.getData().getC(), encode.toString());
            trace.removeLast();
            return;
        }

        trace.addLast("0");
        parseHuffmanTree(t.getLeft(), trace , characterEncodings);
        trace.addLast("1");
        parseHuffmanTree(t.getRight(), trace, characterEncodings);
        trace.clear();
    }

    /**
     * 构建Huffman编码树，需要2(C-1)次deleteMin，C-1次insert，时间复杂度为O(ClogC)
     * @param q
     * @return
     */
    private static Node createHuffmanTree(PriorityQueue<Node> q) {
        while(q.size() >= 2) {
            Node left = q.poll();
            Node right = q.poll();
            Node root = new Node();
            Data data = new Data();
            data.setFrequency(left.getData().getFrequency() +
                right.getData().getFrequency());

            root.setData(data);
            root.setLeft(left);
            root.setRight(right);

            q.offer(root);
        }
        return q.poll();
    }

    /**
     * 将待编码的字符串解析成节点列表，时间复杂度O(n)，其中n是字符串长度
     * @param input
     * @return
     */
    private static List<Node> parseToList(String input) {
        List<Node> list = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();

        for(int i = 0; i < input.length(); i++) {
            Character c = input.charAt(i);
            if(!map.containsKey(c))
                map.put(c, 1);
            else
                map.put(c, map.get(c) + 1);
        }

        for(Map.Entry<Character, Integer> e : map.entrySet()) {
            Data data = new Data(e.getKey(), e.getValue());
            Node node = new Node(data);
            list.add(node);
        }

        return list;
    }
}
