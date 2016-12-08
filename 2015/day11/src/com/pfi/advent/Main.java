package com.pfi.advent;

public class Main {
    private static final String INPUT = "vzbxkghb";

    public static void main(String[] args) {
	    String thisPassword = INPUT;
        do {
            thisPassword = nextPassword(thisPassword);
        } while (!validPassword(thisPassword));

        System.out.println("Next valid password: " + thisPassword);

        do {
            thisPassword = nextPassword(thisPassword);
        } while (!validPassword(thisPassword));

        System.out.println("Next valid password: " + thisPassword);
    }

    private static String nextPassword(String oldPassword) {
        char chars[] = oldPassword.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            char thisChar = chars[i];
            if (thisChar < 'a' || thisChar > 'z') throw new IllegalArgumentException("Unexpected letter input " + thisChar);
            char newChar = (thisChar == 'z') ? 'a' : ++thisChar;
            chars[i] = newChar;
            if (newChar != 'a') {
                break;
            }
        }

        return new String(chars);
    }

    private static boolean validPassword(String password) {
        //System.out.println("Checking " + password);
        boolean ok = false;

        // check for invalid letter
        ok = !password.contains("i") && !password.contains("o") && !password.contains("l");
        if (!ok) return false;

        // check for three-letter-sequence (abc, ghi)
        ok = false;
        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i) + 1 == password.charAt(i+1) && password.charAt(i) + 2 == password.charAt(i+2)) {
                ok = true;
                break;
            }
        }
        if (!ok) return false;

        // check for repeated chars
        ok = false;
        boolean one = false;
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                if (!one) {
                    one = true;
                    i++;
                } else {
                    ok = true;
                    break;
                }
            }
        }
        if (!ok) return false;

        return true;
    }

}
