package day.three;

import java.util.ArrayList;
import java.util.List;

import helpers.Util;

public class PuzzleOne {

//    private static final String inputFile = "TestInput.txt";
    private static final String inputFile = "DayThreeInput.txt";

    private static List<String> input;

    public static void main(String[] args) {
        input = Util.obtainPuzzleInput(inputFile);
        List<String> symbols = Util.scanForSymbols(input);
        List<Symbol> foundSymbols = locateSymbols(input, symbols);
        List<Digit> neighboringDigits = findNeighboringDigits(foundSymbols);
        Integer sumOfDigits = Util.sumPartNumbers(neighboringDigits);
        System.out.println("Try: " + sumOfDigits);
    }

    private static List<Symbol> locateSymbols(List<String> input, List<String> symbols) {
        List<Symbol> foundSymbols = new ArrayList<>();
        int rowCount = 0;
        //Iterate over each row
        for (String row : input) {
            for (String symbol : symbols) {
                //If we find a symbol
                if (row.contains(symbol)) {
                    //Map its position (rowCount, char position)
                    for (int i = 0; i < row.length(); i++) {
                        char character = row.charAt(i);
                        if (symbol.contains(String.valueOf(character))) {
                            Position pos = new Position(rowCount, i);
                            foundSymbols.add(new Symbol(symbol, pos));
                        }
                    }
                }
            }
            rowCount++;
        }
        return foundSymbols;
    }

    private static List<Digit> findNeighboringDigits(List<Symbol> foundSymbols) {
        List<Digit> neighboringDigits = new ArrayList<>();
        for (Symbol aSymbol : foundSymbols) {
            //Generate adjacent positions
            List<Position> positions = aSymbol.locateNeighbors();
            for (Position p : positions) {
                char l = input.get(p.row()).charAt(p.position());
                if (Character.isDigit(l)) {
                    List<String> chars = new ArrayList<>();
                    List<Position> digitPositions = new ArrayList<>();
                    chars.add(String.valueOf(l));
                    digitPositions.add(new Position(p.row(), p.position()));
                    //Moonwalk
                    checkBehind(p, chars, digitPositions);
                    //Run
                    checkAhead(p, chars, digitPositions);
                    Digit nextDigit = new Digit(chars, digitPositions);
                    boolean seen = false;
                    //Store the digit if we haven't seen it before
                    for (Digit digit : neighboringDigits) {
                        if (digit.equal(nextDigit)) {
                            seen = true;
                        }
                        if (seen) {
                            break;
                        }
                    }
                    if (!seen) {
                        neighboringDigits.add(nextDigit);
                    }
                }
            }
        }
        return neighboringDigits;
    }

    private static void checkBehind(Position p, List<String> chars, List<Position> digitPositions) {
        int moonwalkPos = 1;
        while (isCharBehind(p, moonwalkPos)) {
            chars.addFirst(getCharBehind(p, moonwalkPos));
            Position digitPos = new Position(p.row(), p.position() - moonwalkPos);
            digitPositions.add(digitPos);
            moonwalkPos++;
        }
    }

    private static void checkAhead(Position p, List<String> chars, List<Position> digitPositions) {
        int runPos = 1;
        while (isCharAhead(p, runPos)) {
            chars.addLast(getCharAhead(p, runPos));
            Position digitPos = new Position(p.row(), p.position() + runPos);
            digitPositions.add(digitPos);
            runPos++;
        }
    }

    private static boolean isCharBehind(Position p, int rowPos) {
        int row = p.row();
        int pos = p.position();
        try {
            char value = input.get(row).charAt(pos - rowPos);
            return Character.isDigit(value);
        } catch (StringIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private static String getCharBehind(Position p, int rowPos) {
        int row = p.row();
        int pos = p.position();
        char value = input.get(row).charAt(pos - rowPos);
        return String.valueOf(value);
    }

    private static boolean isCharAhead(Position p, int rowPos) {
        int row = p.row();
        int pos = p.position();
        try {
            char value = input.get(row).charAt(pos + rowPos);
            return Character.isDigit(value);
        } catch (StringIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private static String getCharAhead(Position p, int rowPos) {
        int row = p.row();
        int pos = p.position();
        char value = input.get(row).charAt(pos + rowPos);
        return String.valueOf(value);
    }
}
