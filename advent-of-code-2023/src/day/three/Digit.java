package day.three;

import java.util.Comparator;
import java.util.List;

public class Digit {
    /**
     * Holds the number(s) representing this Digit in ascending order
     */
    private List<String> numbers;
    /**
     * Holds the {@link Position}(s) this Digit occupies in the grid
     */
    private List<Position> positions;

    public Digit(List<String> numbers, List<Position> positions) {
        this.numbers = numbers;
        this.positions = positions;
        sortPositions(positions);
    }

    private void sortPositions(List<Position> positions) {
        positions.sort(Comparator.comparing(Position::position));
    }

    public Integer getValue() {
        StringBuilder combinedString = new StringBuilder();
        for (String number : numbers) {
            combinedString.append(number);
        }
        return Integer.valueOf(combinedString.toString());
    }

    private List<Position> getPositions() {
        return positions;
    }

    public boolean equal(Digit aDigit) {
        return (getValue().equals(aDigit.getValue()))
                && getPositions().equals(aDigit.getPositions());
    }
}
