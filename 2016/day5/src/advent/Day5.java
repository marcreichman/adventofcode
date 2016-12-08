package advent;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/7/2016
 * Time: 11:33 AM
 */
public class Day5 {
//    private static final String INPUT = "abc";
    private static final String INPUT = "uqwqemis";
    private static String getMD5ForIndex(int index) throws Exception {
        final StringBuilder sb = new StringBuilder();

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((INPUT + index).getBytes("UTF-8"));
        byte[] digestBytes = messageDigest.digest();

        for (byte digestByte : digestBytes) {
            String hex = Integer.toHexString(0xFF & digestByte);
            if (hex.length() < 2)
                sb.append("0");
            sb.append(hex);
        }
        return sb.toString();
    }

    public static void main(String... args) throws Exception {
        String step1Password = "";
        int index = 0;
        int lastPrintedLength = 0;
        while (step1Password.length() < 8) {
            String thisMD5 = getMD5ForIndex(index++);
            if (thisMD5.startsWith("00000")) {
                step1Password += thisMD5.charAt(5);
            }

            if (step1Password.length() > lastPrintedLength) {
                lastPrintedLength = step1Password.length();
                System.out.println("Got " + lastPrintedLength + " chars of step 1!");
            }
        }

        System.out.println("Step 1 pw: " + step1Password);

        Character step2PasswordArr[] = { '_','_','_','_','_','_','_','_'};
        index = 0; lastPrintedLength = 0;
        String step2Password = "";
        while (step2Password.length() < 8) {
            String thisMD5 = getMD5ForIndex(index++);
            if (thisMD5.startsWith("00000")) {
                try {
                    int pos = Integer.parseInt(thisMD5.substring(5, 6));
                    char value = thisMD5.charAt(6);

                    if (pos >=0 && pos <= 7 && step2PasswordArr[pos] == '_') {
                        step2PasswordArr[pos] = value;
                        step2Password = Arrays.stream(step2PasswordArr).filter(character -> character != '_').map(Object::toString).collect(Collectors.joining());
                    }
                } catch (NumberFormatException ignored) {};

            }

            if (step2Password.length() > lastPrintedLength) {
                lastPrintedLength = step2Password.length();
                System.out.println("Got " + lastPrintedLength + " chars of step 2!");
            }
        }

        System.out.println("Step 2 pw: " + step2Password);
    }
}
