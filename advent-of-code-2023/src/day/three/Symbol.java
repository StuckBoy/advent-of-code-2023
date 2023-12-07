package day.three;

import java.util.ArrayList;
import java.util.List;

public class Symbol {
    private final String value;
    private final Position position;

    public Symbol(String value, Position position) {
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public Integer getRow() {
        return position.row();
    }

    public Integer getPosition() {
        return position.position();
    }

    public Position getSymbolPosition() {
        return position;
    }

    public List<Position> locateNeighbors() {
        List<Position> neighbors = new ArrayList<>();
        //Behind in row
        Position behind;
        neighbors.add(new Position(getRow(), getPosition()-1));
        //Ahead in row
        Position ahead;
        neighbors.add(new Position(getRow(), getPosition()+1));
        //Diagonal Behind (Down)
        Position diagDownBehind;
        neighbors.add(new Position(getRow()+1, getPosition()-1));
        //Diagonal Behind (Up)
        Position diagUpBehind;
        neighbors.add(new Position(getRow()-1, getPosition()-1));
        //Diagonal Ahead (Down)
        Position diagDownAhead;
        neighbors.add(new Position(getRow()+1, getPosition()+1));
        //Diagonal Ahead (Up)
        Position diagUpAhead;
        neighbors.add(new Position(getRow()-1, getPosition()+1));
        //Above
        Position above;
        neighbors.add(new Position(getRow()-1, getPosition()));
        //Below
        Position below;
        neighbors.add(new Position(getRow()+1, getPosition()));
        return neighbors;
    }
}
