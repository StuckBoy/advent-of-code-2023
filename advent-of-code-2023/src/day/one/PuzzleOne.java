package day.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleOne {

    public static void main(String[] args) {
        List<String> input = obtainPuzzleInput();
        List<Integer> numbers = extractIntegers(input);
        int finalNumber = doMath(numbers);
        System.out.println("I think it's " + finalNumber);
    }

    private static List<String> obtainPuzzleInput() {
        List<String> input = new ArrayList<>();
        String inputFile = "PuzzleOneInput.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException ignored) {} //YOLO
        return input;
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

    private static int doMath(List<Integer> input) {
        AtomicInteger sum = new AtomicInteger();
        input.forEach(sum::addAndGet);
        return sum.get();
    }
}
