package day.two;

import java.util.List;

public class Game {
    private final List<Pull> pullList;
    private boolean validGame;
    private final int gameNumber;

    public Game(int gameNumber, List<Pull> pullList) {
        this.gameNumber = gameNumber;
        this.pullList = pullList;
        validatePulls();
    }

    private void validatePulls() {
        validGame = true;
        for (Pull pull : pullList) {
            if (pull.blueCubes() > 14) {
                validGame = false;
                break;
            } else if (pull.redCubes() > 12) {
                validGame = false;
                break;
            } else if (pull.greenCubes() > 13) {
                validGame = false;
                break;
            }
        }
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public boolean isValidGame() {
        return validGame;
    }

    public int getHighestRedCount() {
        return countColor("red");
    }

    private int countColor(String color) {
        int count = 0;
        switch (color) {
            case "red":
                for (Pull pull : pullList) {
                    if (pull.redCubes() > count) {
                        count = pull.redCubes();
                    }
                }
                break;
            case "green":
                for (Pull pull : pullList) {
                    if (pull.greenCubes() > count) {
                        count = pull.greenCubes();
                    }
                }
                break;
            case "blue":
                for (Pull pull : pullList) {
                    if (pull.blueCubes() > count) {
                        count = pull.blueCubes();
                    }
                }
                break;
        }
        return count;
    }

    public int getHighestGreenCount() {
        return countColor("green");
    }

    public int getHighestBlueCount() {
        return countColor("blue");
    }
}