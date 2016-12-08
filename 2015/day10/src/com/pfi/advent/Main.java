package com.pfi.advent;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String INPUT = "1113122113";
    public static void main(String[] args) {
        String thisInput = INPUT;
        for (int i = 0; i < 50; i++) {
            String prev = thisInput;
            thisInput = lookAndSay(thisInput);
        }

        System.out.println("Length: " + thisInput.length());
    }

    private static String lookAndSay(String thisInput) {
        StringBuilder output = new StringBuilder();
        int nCur = 0;
        Character cur = null;
        for (int i = 0; i < thisInput.length(); i++) {
            char c = thisInput.charAt(i);
            if (cur == null) {
                cur = c;
                nCur = 1;
            } else {
                if (cur != c) {
                    // add output
                    output.append(nCur).append(cur);
                    // reset cur
                    cur = c;
                    // reset nCur
                    nCur = 1;
                } else {
                    nCur ++;
                }

                if (i == (thisInput.length() - 1)) {
                    // add output
                    output.append(nCur).append(cur);
                }
            }
        }

        return output.toString();
    }
}
