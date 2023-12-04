package day.one;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import helpers.Util;

public class PuzzleOne {
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
            LinkedHashMap<Integer, Integer> digitPositionMap = getLiteralDigits(row);
            //Javascript Math Time
            Object[] positions = digitPositionMap.keySet().toArray();
            List<Integer> positionIntegers = new ArrayList<>();
            for (Object o : positions) {
                if (o instanceof Integer) {
                    positionIntegers.add((Integer) o);
                }
            }
            Integer firstKey = positionIntegers.getFirst();
            Integer lastKey = positionIntegers.getLast();
            String start = String.valueOf(digitPositionMap.get(firstKey));
            String end = String.valueOf(digitPositionMap.get(lastKey));
            Integer finalNumber = Integer.valueOf(start.concat(end));
            integers.add(finalNumber);
        });
        return integers;
    }

    private static LinkedHashMap<Integer, Integer> getLiteralDigits(String row) {
        LinkedHashMap<Integer, Integer> digitPositionMap = new LinkedHashMap<>();
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
}
