package day.one;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PuzzleOne {

    public static void main(String[] args) {
        //Fetch puzzle input
        List<String> input = obtainPuzzleInput();
        List<Integer> numbers = extractIntegers(input);
        int finalNumber = doMath(numbers);
        //Output
        System.out.println("I think it's: " + finalNumber);
    }

    private static List<String> obtainPuzzleInput() {
        return null; //TODO
    }

    private static List<Integer> extractIntegers(List<String> input) {
        List<Integer> integers = new ArrayList<>();
        input.parallelStream().forEach(row -> {
            String start = "";
            String end = "";
            //Toss into StringBuilder to simplify trims
            StringBuilder sbRow = new StringBuilder(row);
            while (!sbRow.isEmpty()) {
                start = String.valueOf(sbRow.charAt(0));
                end = String.valueOf(sbRow.charAt(sbRow.length()));
                try {
                    //Convert the stored values
                    int numOne = Integer.parseInt(start);
                    int numTwo = Integer.parseInt(end);
                    //If neither threw the exception, we're done.
                    break;
                } catch (NumberFormatException ignored) {
                }
                sbRow.deleteCharAt(0);
                sbRow.deleteCharAt(sbRow.length());
            }
            //Do Javascript Math
            integers.add(Integer.valueOf(start.concat(end)));
        });
        return integers;
    }

    private static int doMath(List<Integer> input) {
        AtomicInteger sum = new AtomicInteger();
        input.forEach(sum::addAndGet);
        return sum.get();
    }
}
