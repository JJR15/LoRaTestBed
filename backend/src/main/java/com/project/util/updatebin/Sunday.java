package com.project.util.updatebin;

import java.util.ArrayList;

/**
 * Created by DongBaishun on 2017/7/31.
 */
public class Sunday {

    public int Sunday(String dest, String pattern, ArrayList<Integer> posList) {
        int count = 0;

        char[] destchars = dest.toCharArray();
        char[] patternchars = pattern.toCharArray();

        int i = 0;
        int j = 0;

        while (i <= (dest.length() - pattern.length() + j)) {
            if (destchars[i] != patternchars[j]) {
                if (i == (dest.length() - pattern.length() + j)) {
                    break;
                }
                int pos = contains(patternchars, destchars[i + pattern.length() - j]);
                if (pos == -1) {
                    i = i + pattern.length() + 1 - j;
                    j = 0;
                } else {
                    i = i + pattern.length() - pos - j;
                    j = 0;
                }
            } else {
                if (j == (pattern.length() - 1)) {
                    if (count > 1) {
                        break;
                    }
                    posList.clear();
                    posList.add(i - j);
                    posList.add(i);
                    count++;
                    i = i - j + 1;
                    j = 0;
                } else {
                    i++;
                    j++;
                }
            }
        }
        return count;
    }

    private int contains(char[] chars, char target) {
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
