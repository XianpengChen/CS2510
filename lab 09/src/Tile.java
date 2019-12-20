import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;


interface IPred<T> {
    boolean apply(T t);
}
class ArrayUtils {
    <T> int find0Tile(ArrayList<T> arr, IPred<T> whichOne) {
        return this.findHelp(arr, whichOne, 0);

    }
    <T> int findHelp(ArrayList<T> arr, IPred<T> whichOne, int index) {
        if (index >= arr.size()) {
            return -1;
        }
        else if (whichOne.apply(arr.get(index))) {
            return index;
        }
        else {
            return findHelp(arr, whichOne, index + 1);
        }
    }

}
// Represents an individual tile
class Tile {
    // The number on the tile.  Use 0 to represent the hole
    int value;

    // Draws this tile onto the background at the specified logical coordinates
    WorldImage drawAt(int col, int row, WorldImage background) {

        int column = (int) (background.getWidth() / 4);

        int rowes = (int) (background.getHeight() / 4);
        return new OverlayOffsetImage(new RectangleImage(column, rowes, 
                OutlineMode.SOLID, Color.BLUE),
                background.pinhole.x - (col * column - column / 2),
                background.pinhole.y - (row * rowes - rowes / 2), background);
    }
}

class FifteenGame extends World {
    // represents the rows of tiles
    ArrayList<ArrayList<Tile>> tiles;




    // handles keystrokes
    //needs to handle up, down, left, right to move the hole
    // extra: handle "u" to undo moves
    public void onKeyEvent(String k) {
        
    }




    @Override
    public WorldScene makeScene() {
        // TODO Auto-generated method stub
        return null;
    }

}

class ExampleFifteenGame {
    void testGame(Tester t) {
        FifteenGame g = new FifteenGame();
        g.bigBang(120, 120);
    }
}