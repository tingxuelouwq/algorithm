package com.kevin.algorithm.greedy.huffman;

import java.util.Map;

/**
 * @Author kevin
 * @Date 2016/10/24 19:18
 */
public class EncodeResult {
    private Map<Character, String> characterEncodings;
    private String encode;

    public EncodeResult(Map<Character, String> characterEncodings, String encode) {
        this.characterEncodings = characterEncodings;
        this.encode = encode;
    }

    public Map<Character, String> getCharacterEncodings() {
        return characterEncodings;
    }

    public void setCharacterEncodings(Map<Character, String> characterEncodings) {
        this.characterEncodings = characterEncodings;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }
}
