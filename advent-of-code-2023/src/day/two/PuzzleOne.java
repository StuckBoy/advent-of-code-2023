package day.two;

import java.util.List;

import helpers.Util;

public class PuzzleOne {

    private static final String inputFile = "DayTwoInput.txt";

    public static void main(String[] args) {
        List<String> input = Util.obtainPuzzleInput(inputFile);
        List<Game> games = Util.dissectGames(input);
        System.out.println("I think it's " + Util.sumValidGames(games));
        System.out.println("The power is " + Util.calcCubePower(games));
    }
}
