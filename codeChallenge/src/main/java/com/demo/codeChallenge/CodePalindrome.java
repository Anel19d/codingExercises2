package main.java.com.demo.codeChallenge;

import java.util.ArrayList;
import java.util.List;
/**
 * 1.Entender casos de uso
 *
 * TEST CASE 1: Ana = TRUE, Sol= false
 * TC2: Sol=false,
 * null=-1
 * space empty = null
 * constraint =
 * Ignorar mayusculas y minusculas
 *
 * 2. Solution
 * 2.1 crear un nuevo string
 * 2.2
 *     Comparar el principal y el final progresivamente
 *     right=0
 *     left=
 *     string.length - 1
 *          r ==    right      left
 *    0 r    true    0          6
 *    1 a    true    1          5
 *    2 c    true    2          4
 *    3 e    true    3          3
 *    4 c
 *    5 a
 *    6 r
 * 2.3
 *     Comparar el principal y el final progresivamente
 *  *     right=0
 *  *     left=
 *  *     string.length - 1
 *  *          r ==    right      left
 *  *    0 a    true    1          2
 *  *    1 n    true    2          2
 *  *    2 a    true    0          0
 *
 *  2.4
 *  *     Comparar el principal y el final progresivamente
 *  *  *     right=0
 *  *  *     left=
 *  *  *     string.length - 1
 *  *  *          r ==    right      left
 *  *  *    0 r    true    0          4
 *  *  *    1 a    true    1          3
 *  *  *    2 d    true    2          2
 *          3 a
 *          4 r
 *
 * 3 impl
 * */

public class CodePalindrome {

    public static void findPalindromes(){

        List<String> palinds = new ArrayList<>();
        String[] words = {"ana", "sol", "Radar", "hola", "RoTor", "Accenture"};
        StringBuilder palindrome= new StringBuilder();
        int wordIsNotPalindrome = 0;
        int wordIsPalindrome = 0;
        for (String word: words) {
            for (int i = 0; i < word.length(); i++)//subtract 1 from the length
            {
                if (word.toLowerCase().charAt(i) == word.toLowerCase().charAt(word.length() -(i + 1))) {
                    palindrome.append(word.charAt(word.length() -(i+1)));
                } else {
                    wordIsNotPalindrome++;
                    break;
                }

                if(palindrome.length() == word.length()) {
                    palinds.add(palindrome.toString());
                    wordIsPalindrome++;
                    palindrome = new StringBuilder();
                }
            }

        }

        System.out.println("Palindromes found: " + palinds);
        System.out.println("Palindromes found: " + palinds);

        System.out.println("Number of palindromes: " + wordIsPalindrome);
        System.out.println("Number of not palindromes: " + wordIsNotPalindrome);
    }

}
