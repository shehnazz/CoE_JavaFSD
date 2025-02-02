package task7;

import java.util.*;


public class Anagram {
	public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) return result;

        int[] pCount = new int[26];
        int[] sCount = new int[26];

        
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        int pLen = p.length();

        
        for (int i = 0; i < s.length(); i++) {
            
            sCount[s.charAt(i) - 'a']++;

            
            if (i >= pLen) {
                sCount[s.charAt(i - pLen) - 'a']--;
            }

            
            if (Arrays.equals(sCount, pCount)) {
                result.add(i - pLen + 1);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Anagram finder = new Anagram();
        String s = "mnolmnonmlo";
        String p = "mno";

        System.out.println("Anagram start indices: " + finder.findAnagrams(s, p));
    }

}
