package day.three;

import java.util.ArrayList;
import java.util.List;

import helpers.Util;

public class PuzzleOne {

    private static final String inputFile = "TestInput.txt";

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
                    int index = row.indexOf(symbol);
                    Position pos = new Position(rowCount, index);
                    foundSymbols.add(new Symbol(symbol, pos));
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
                    int moonwalkPos = 1;
                    while (isCharBehind(p, moonwalkPos)) {
                        //Always put it at the front
                        chars.add(0, getCharBehind(p, moonwalkPos));
                        Position digitPos = new Position(p.row(), p.position() - moonwalkPos);
                        digitPositions.add(digitPos);
                        moonwalkPos++;
                    }
                    //Run
                    int runPos = 1;
                    while (isCharAhead(p, runPos)) {
                        //Always put it at the back
                        chars.add(getCharAhead(p, runPos));
                        Position digitPos = new Position(p.row(), p.position() + runPos);
                        digitPositions.add(digitPos);
                        runPos++;
                    }
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

    private static List<Position> extrapolate(Position p, int behind, int ahead) {
        List<Position> positions = new ArrayList<>();
        while (ahead - behind != 0) {
            if (isCharAhead(p, behind)) {
                int cursor = p.position()+(behind);
                Position newPosition = new Position(p.row(), cursor);
                positions.add(newPosition);
            }
            behind++;
        }
        return positions;
    }
}
