package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import day.two.Game;
import day.two.Pull;

public class Util {

    public static List<String> obtainPuzzleInput(String inputFile) {
        List<String> input = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                input.add(line);
            }
        } catch (IOException ignored) {} //YOLO
        return input;
    }

    public static int doMath(List<Integer> input) {
        AtomicInteger sum = new AtomicInteger();
        input.forEach(sum::addAndGet);
        return sum.get();
    }

    public static List<Game> dissectGames(List<String> input) {
        List<Game> gameList = new ArrayList<>();
        for (String gameString : input) {
            //Separate into parts (ID, Pull 1, Pull 2, etc.)
            int gameId = locateId(gameString);
            List<Pull> pulls = buildPullList(gameString);
            gameList.add(buildGame(gameId, pulls));
        }
        return gameList;
    }

    private static int locateId(String gameString) {
        int colonIndex = gameString.indexOf(":");
        String gameNumber = gameString.substring(0, colonIndex);
        gameNumber = gameNumber.substring(gameNumber.indexOf(" ")+1, colonIndex);
        return Integer.parseInt(gameNumber);
    }

    private static List<Pull> buildPullList(String gameString) {
        List<Pull> pullList = new ArrayList<>();
        //Remove "Game #:"
        int length = gameString.length();
        String allPulls = gameString.substring(gameString.indexOf(":")+1, length);
        String[] pulls =  allPulls.split(";");
        Arrays.stream(pulls).forEach(pull -> pullList.add(buildPull(pull)));
        return pullList;
    }

    private static Pull buildPull(String input) {
        String[] cubeList = input.split(",");
        //Determine cube counts
        int redCubes = 0;
        int blueCubes = 0;
        int greenCubes = 0;
        //Extract Number
        for (String cubeCount : cubeList) {
            String cleanedCount = cubeCount.trim();
            String[] numbers = cleanedCount.split(" ");
            if (cleanedCount.contains("red")) {
                redCubes = Integer.parseInt(numbers[0]);
            } else if (cleanedCount.contains("blue")) {
                blueCubes = Integer.parseInt(numbers[0]);
            } else if (cleanedCount.contains("green")) {
                greenCubes = Integer.parseInt(numbers[0]);
            }
        }
        return new Pull(blueCubes, redCubes, greenCubes);
    }

    private static Game buildGame(int gameNumber, List<Pull> pullList) {
        return new Game(gameNumber, pullList);
    }

    public static int sumValidGames(List<Game> games) {
        AtomicInteger sum = new AtomicInteger();
        games.forEach(game -> {
            if (game.isValidGame()) {
                /* Debug Stuff
                int gameNumber = game.getGameNumber();
                int highestRed = game.getHighestRedCount();
                int highestGreen = game.getHighestGreenCount();
                int highestBlue = game.getHighestBlueCount();
                System.out.println("Game " + gameNumber + " was valid (" + highestRed + ", " + highestGreen + ", " + highestBlue + ")");
                 */
                sum.addAndGet(game.getGameNumber());
            }
        });
        return sum.get();
    }

    public static int calcCubePower(List<Game> games) {
        AtomicInteger sum = new AtomicInteger();
        games.forEach(game -> sum.addAndGet(game.calcPower()));
        return sum.get();
    }
}
