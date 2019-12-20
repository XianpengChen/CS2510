import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
public class PermutationCode {
    /** The original list of characters to be encoded */
    ArrayList<Character> alphabet = 
            new ArrayList<Character>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                    't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> code = new ArrayList<Character>(26);

    /** A random number generator */

    Random rand = new Random();

    /**
     * Create a new instance of the encoder/decoder with a new permutation code 
     */
    PermutationCode() {
        this.code = this.initEncoder();
    }

    /**
     * Create a new instance of the encoder/decoder with the given code 
     */
    PermutationCode(ArrayList<Character> code) {
        this.code = code;
    }

    /** Initialize the encoding permutation of the characters */
    ArrayList<Character> initEncoder() {
        ArrayList<Character> alph = new ArrayList<Character>();
        for (Character c : alphabet) {
            alph.get((findchar(alphabet, c) *
                    rand.nextInt(alphabet.size())) % 26);
        }
        return alph;
    }

    /**
     * produce an encoded <code>String</code> from the given <code>String</code>
     * @param source the <code>String</code> to encode
     * @return the secretly encoded <String>
     */
    String encode(String source) {
        String str = "";
        ArrayList<Character> newcode = addString(source);
        for (Character c : newcode) {
            str += (this.code.get(findchar(alphabet, c)).toString());
        }
        return str;
    }

    /**
     * produce an decoded <code>String</code> from the given <code>String</code>
     * @param source the <code>String</code> to decode
     * @return the revealed <String>
     */
    String decode(String code) {
        String str = "";
        ArrayList<Character> newcode = addString(code);
        for (Character c : newcode) {
            str += (alphabet.get(findchar(this.code, c)).toString());
        }
        return str;
    }

    ArrayList<Character> addString(String str) {
        ArrayList<Character> arr = new ArrayList<Character>();
        for (int i = 0; i < str.length(); i++) {
            arr.add(str.charAt(i));
        }
        return arr;
    }

    Integer findchar(ArrayList<Character> arr, Character ch) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(ch)) {
                return i;
            }
        }
        throw new RuntimeException("Error");
    }
}

class ExamplesSecretCode {
    ArrayList<Character> array =
            new ArrayList<Character>(Arrays.asList(
                    'z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r', 'q',
                    'p', 'o', 'n', 'm', 'l', 'k', 'j', 'i', 'h',
                    'g', 'f', 'e', 'd', 'c', 'b', 'a'));


    PermutationCode code = new PermutationCode(array);

    void testFindIndex(Tester t) {
        t.checkExpect(code.findchar(array, 'a'), 25);
        t.checkExpect(code.findchar(array, 'z'), 0);
    }


    void testDecode(Tester t) {
        t.checkExpect(code.decode("zyx"), "abc");
    }

    void testEncode(Tester t) {
       t.checkExpect(code.encode("abc"), "zyx");
    }

    //void testInitEncoder(Tester t) {
    //    t.checkInexact(code.findchar(code.initEncoder(), 'a'), 13, 13);
    //}

}