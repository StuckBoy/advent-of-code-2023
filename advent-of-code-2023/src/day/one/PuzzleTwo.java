package day.one;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import helpers.Util;

public class PuzzleTwo {

    public enum Digit {
        ONE(1), TWO(2), THREE(3), FOUR(4),
        FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9);

        public final Integer value;

        Digit(Integer value) {
            this.value = value;
        }
    }

    private static final String inputFile = "DayOneInput.txt";

    public static void main(String[] args) {
        List<String> input = Util.obtainPuzzleInput(inputFile);
        List<Integer> numbers = extractIntegers(input);
        int finalNumber = Util.doMath(numbers);
        System.out.println("I think it's " + finalNumber);
    }

    private static List<Integer> extractIntegers(List<String> input) {
        List<Integer> integers = new ArrayList<>();
        input.forEach(row -> {
            //Pass One: Literal Digits
            Map<Integer, Integer> digitPositionMap = getLiteralDigits(row);
            //Pass Two: Written Digits
            getWrittenDigits(digitPositionMap, row);
            //Javascript Math Time
            Object[] positions = digitPositionMap.keySet().toArray();
            List<Integer> positionIntegers = new ArrayList<>();
            for (Object o : positions) {
                if (o instanceof Integer) {
                    positionIntegers.add((Integer) o);
                }
            }
            Collections.sort(positionIntegers);
            Integer firstKey = positionIntegers.getFirst();
            Integer lastKey = positionIntegers.getLast();
            String start = String.valueOf(digitPositionMap.get(firstKey));
            String end = String.valueOf(digitPositionMap.get(lastKey));
            Integer finalNumber = Integer.valueOf(start.concat(end));
            integers.add(finalNumber);
        });
        return integers;
    }

    private static Map<Integer, Integer> getLiteralDigits(String row) {
        Map<Integer, Integer> digitPositionMap = new LinkedHashMap<>();
        for (int i = 0; i < row.length(); i++) {
            char character = row.charAt(i);
            try {
                int number = character - '0';
                if (number >= 0 && number <= 9) {
                    digitPositionMap.put(i, number);
                }
            } catch (NumberFormatException ignored) {}
        }
        return digitPositionMap;
    }

    private static void getWrittenDigits(Map<Integer, Integer> digitPositionMap, String row) {
        List<Digit> foundDigits = locateWrittenDigits(row);
        for (Digit digit : foundDigits) {
            String lowerDigit = digit.name().toLowerCase();
            int firstPos = row.indexOf(lowerDigit);
            digitPositionMap.put(firstPos, digit.value);
            int lastPos = row.lastIndexOf(lowerDigit);
            digitPositionMap.put(lastPos, digit.value);
        }
    }

    private static List<Digit> locateWrittenDigits(String row) {
        List<Digit> digits = new ArrayList<>();
        for (Digit digit : Digit.values()) {
            if (row.contains(digit.name().toLowerCase())) {
                digits.add(digit);
            }
        }
        return digits;
    }
}
