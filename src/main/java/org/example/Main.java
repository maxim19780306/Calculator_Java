package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


    public static String calc(String input) throws Exception {
        int a, b;
        char operand = 0;
        String result = "";
        char[] chars = new char[10];
        for (int i = 0; i < input.length(); i++) {
            chars[i] = input.charAt(i);
            if (chars[i] == '+') {
                operand = '+';
            }
            if (chars[i] == '-') {
                operand = '-';
            }
            if (chars[i] == '*') {
                operand = '*';
            }
            if (chars[i] == '/') {
                operand = '/';
            }
        }
        String str = String.valueOf(chars);
        String[] strings = str.split("[+-/*]");
        if (strings.length < 2) throw new Exception("строка не является математической операцией");
        else if (strings.length > 2)
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        String str1 = strings[0];
        String str2 = strings[1];
        String str3 = str2.trim();


        if (isNumeric(str1) && isNumeric(str3)) {
            a = Integer.parseInt(str1);
            b = Integer.parseInt(str3);
            if (a > 0 && a <= 10 && b > 0 && b <= 10) {
                result = String.valueOf(calculated(a, b, operand));
            } else {
                throw new Exception("величина вводимых чисел не соответствует заданию");
            }

        } else if (!isNumeric(str1) && !isNumeric(str3)) {
            a = romanToNumber(str1);
            b = romanToNumber(str3);
            result = arabicToRoman(calculated(a, b, operand));
        } else if ((!isNumeric(str1) && isNumeric(str3)) || (isNumeric(str1) && !isNumeric(str3))) {
            throw new Exception("используются одновременно разные системы счисления");
        } else {
            throw new Exception(" строка не является математической операцией");
        }


        return result;
    }

    public static void main(String[] args) throws Exception {
        String input = bufferedReader.readLine();
        System.out.println(calc(input));

    }

    public static String arabicToRoman(int number) throws Exception {
        if ((number <= 0)) {
            throw new Exception("в римской системе нет отрицательных чисел ");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }


    private static int romanToNumber(String roman) {
        String romanNumeral = roman.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }


        return result;


    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int calculated(int num1, int num2, char op) {
        int result = 0;
        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                try {
                    result = num1 / num2;
                } catch (ArithmeticException | InputMismatchException e) {
                    System.out.println("Exception : " + e);
                    System.out.println("Only integer non-zero parameters allowed");

                    break;
                }
                break;
            default:
                throw new IllegalArgumentException("Не верный знак операции");
        }
        return result;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}