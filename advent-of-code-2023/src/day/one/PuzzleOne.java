package day.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleOne {

    public static void main(String[] args) {
        List<String> input = obtainPuzzleInput();
        List<Integer> numbers = extractIntegers(input);
        int finalNumber = doMath(numbers);
        //Output
        System.out.println("I think it's " + finalNumber);
    }

    private static List<String> obtainPuzzleInput() {
        List<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("PuzzleOneInput.txt"))) {
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
            String start = "";
            String end = "";
            //Toss into StringBuilder to simplify trims
            StringBuilder sbRow = new StringBuilder(row);
            while (!sbRow.isEmpty()) {
                if (notValidYet(start)) {
                    start = String.valueOf(sbRow.charAt(0));
                }
                if (notValidYet(end)) {
                    end = String.valueOf(sbRow.charAt(sbRow.length() - 1));
                }
                try {
                    Integer.parseInt(start);
                    Integer.parseInt(end);
                    //If neither threw the exception, we're done.
                    break;
                } catch (NumberFormatException ignored) {
                }
                if (notValidYet(start)) {
                    sbRow.deleteCharAt(0);
                }
                if (notValidYet(end)) {
                    sbRow.deleteCharAt(sbRow.length() - 1);
                }
            }
            //Do Javascript Math
            integers.add(Integer.valueOf(start.concat(end)));
        });
        return integers;
    }

    private static boolean notValidYet(String value) {
        try {
            Integer.valueOf(value);
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }

    private static int doMath(List<Integer> input) {
        AtomicInteger sum = new AtomicInteger();
        input.forEach(sum::addAndGet);
        return sum.get();
    }
}
