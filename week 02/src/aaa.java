// Khomiakov Kevin
// Xianpeng Chen
// HW 9

import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Iterator;
import java.util.Random;


//to represent a cell
class Cell {
 double height;
 int x;
 int y;
 Cell left, top, right, bottom;
 
 // to check if it is flooded
 boolean isFlooded;
 static final int CELL_SIZE = ForbiddenIslandWorld.WORLD_SIZE / ForbiddenIslandWorld.ISLAND_SIZE;
 
 Cell(double height, int x, int y) {
     this.height = height;
     this.x = x;
     this.y = y;
 }
 
 void helpLeft(Cell left) {
     this.left = left;
 }
 
 void helpRight(Cell right) {
     this.right = right;
 }
 
 void helpTop(Cell top) {
     this.top = top;
 }

 void helpBot(Cell bot) {
     this.bottom = bot;
 }
 
 // to draw a cell
 WorldImage drawCell() {
     int c1 = Math.min(255, (int) (this.height * (255 / (ForbiddenIslandWorld.ISLAND_SIZE / 2))));
     Color color = new Color(c1, 200, c1);
     
     return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, color);
 }
}

//to represent an ocean cell
class OceanCell extends Cell {
 OceanCell(int x, int y) {
     super(0, x, y);
     this.isFlooded = true;
 }
 
 // to draw a cell
 WorldImage drawCell() {
     return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, new Color(0, 0, 255));
 }
}


//to represent IList
interface IList<T> extends Iterable<T> {
 // to transform it to a cons
 Cons<T> asCons();
 // to transform it to an empty
 Empty<T> asEmpty();
 // to check if it is empty or not
 boolean isEmpty();
 // to check if it is cons or not
 boolean isCons();
}

// to represent an List of Iterator
class IListIterator<T> implements Iterator<T> {
    int next;
    IList<T> items;
    
    IListIterator(IList<T> items) {
        this.items = items;
    }
    
    // to output a next value
    public T next() {
        if (!this.hasNext()) {
            throw new RuntimeException("There is no next");
        }
        else {
            Cons<T> item1 = this.items.asCons();
            T newitem = item1.first;
            this.items = item1.rest;
            
            return newitem;
        }
    }
    
    // to check if it has a next item
    public boolean hasNext() {
        return this.items.isCons();
    }
    
    // to remove a value
    public void remove() {
        throw new UnsupportedOperationException("Something");
    }
    
}

// to represent an empty list
class Empty<T> implements IList<T> {

    
    public Empty<T> asEmpty() {
        return this;
    }
    
    public Cons<T> asCons() {
        throw new RuntimeException("it is a cons");
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean isCons() {
        return false;
    }

    public Iterator<T> iterator() {
        return new IListIterator<T>(this);
    }
}

// to represent a cons List
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;
    
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    
    public Empty<T> asEmpty() {
        throw new RuntimeException("it is an empty");
    }
    
    public Cons<T> asCons() {
        return this;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public boolean isCons() {
        return true;
    }

    public Iterator<T> iterator() {
        return new IListIterator<T>(this);
    }
}

// to represent A ForbiddenIslandWorld
class ForbiddenIslandWorld extends World {
    IList<Cell> board;
    int waterHeight;
    static final int ISLAND_SIZE = 64;
    static final int WORLD_SIZE = ISLAND_SIZE * 10;
    
    ForbiddenIslandWorld() {
        this.board = this.makeIList(this.transformArraytoCell(this.makeMountain()));
    }
    
    IList<Cell> makeIList(ArrayList<ArrayList<Cell>> list) {
        IList<Cell> result = new Empty<Cell>();
        
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                Cell component = list.get(i).get(j);
                result = new Cons<Cell>(component, result);
            }
        }
        return result;
    }

    ArrayList<ArrayList<Cell>> transformArraytoCell(ArrayList<ArrayList<Double>> list) {
        ArrayList<ArrayList<Cell>> cList = new ArrayList<ArrayList<Cell>>();
        
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < list.size(); j++) {
                double height = list.get(i).get(j);
                Cell c;
                if (height > 0) {
                    c = new Cell(height, j, i);
                }
                else {
                    c = new OceanCell(j, i);
                }
                
                
                row.add(c);
            }
            cList.add(row);
        }

        for (int y = 0; y < cList.size(); y++) {
            for (int x = 0; x < cList.size(); x++) {
                ArrayList<Cell> row = cList.get(y);
                Cell c = row.get(x);
                
                if (y == 0) {
                    c.helpTop(c);
                    c.helpBot(cList.get(y + 1).get(x));
                }
                else if (y == cList.size() - 1) {
                    c.helpBot(c);
                    c.helpTop(cList.get(y - 1).get(x));
                }
                else {
                    Cell top = cList.get(y - 1).get(x);
                    Cell bot = cList.get(y + 1).get(x);
                    
                    c.helpTop(top);
                    c.helpBot(bot);
                }
                
                if (x == 0) {
                    c.helpLeft(c);
                    c.helpRight(cList.get(y).get(x + 1));
                }
                else if (x == cList.size() - 1) {
                    c.helpRight(c);
                    c.helpLeft(cList.get(y).get(x - 1));
                }
                else {
                    Cell left = cList.get(y).get(x - 1);
                    Cell right = cList.get(y).get(x + 1);
                    
                    c.helpLeft(left);
                    c.helpRight(right);
                }
            }
        }
        return cList;
    }

    ArrayList<ArrayList<Double>> makeMountain() {
        ArrayList<ArrayList<Double>> newlist = new ArrayList<ArrayList<Double>>();
        int h = ISLAND_SIZE / 2;
        
        for (int y = 0; y <= ISLAND_SIZE; y++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int x = 0; x <= ISLAND_SIZE; x++) {
                double newX = Math.abs(h - x);
                double newY = Math.abs(h - y);
                
                double height = h - (newX + newY);
                if (height < 0) {
                    height = 0;
                }
                row.add(height);
            }
            newlist.add(row);
        }
        return newlist;
    }

    ArrayList<ArrayList<Double>> makeRandomMountain() {
        ArrayList<ArrayList<Double>> newlist = new ArrayList<ArrayList<Double>>();
        int h = ISLAND_SIZE / 2;
        Random r = new Random();
        
        for (int i = 0; i <= ISLAND_SIZE; i++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int j = 0; j <= ISLAND_SIZE; j++) {
                double newX = Math.abs(h - j);
                double newY = Math.abs(h - i);
                double regHeight = h - (newX + newY);
                
                double randHeight;
                if (regHeight > 0) {
                    randHeight = 1 + r.nextInt(h);
                }
                else {
                    randHeight = 0;
                }
                row.add(randHeight);
            }
            newlist.add(row);
        }
        return newlist;
    }
    
    // to draw cells
    public WorldScene drawCells() {
        WorldScene w = new WorldScene(WORLD_SIZE, WORLD_SIZE);
        
        for (Cell c : this.board) {
            w.placeImageXY(c.drawCell(), Cell.CELL_SIZE * c.x, Cell.CELL_SIZE * c.y);
        }
        
        return w;
    }
    
    // to render
    public WorldScene makeScene() {
        return this.drawCells();
    }
    // to represent an onkeyevent
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.board = this.makeIList(this.transformArraytoCell(this.makeMountain()));
        }
        else if (ke.equals("r")) {
            this.board = this.makeIList(this.transformArraytoCell(this.makeRandomMountain()));
        }
        else {
        }
    }
}

// examples and tests
class ExamplesForbiddenIsland {
    ForbiddenIslandWorld w1;
    IList<String> emptylist;
    Iterator<String> emptylistIt;
    IList<String> list1;
    IList<String> list2;
    
    void initData() {
        w1 = new ForbiddenIslandWorld();
        emptylist = new Empty<String>();
        emptylistIt = emptylist.iterator();
        list1 = new Cons<String>("something", new Empty<String>());
        list2 = new Cons<String>("something2", list1);
    }
    
  
    
    void testAsEmpty(Tester t) {
        initData();
        t.checkExpect(emptylist.asEmpty(), emptylist);
        t.checkException(new RuntimeException("it is an empty"), list1, "asEmpty");
    }
    
    void testAsCons(Tester t) {
        initData();
        t.checkExpect(list1.asCons(), list1);
        t.checkException(new RuntimeException("it is a cons"), emptylist, "asCons");
    }
    
    void testIsEmpty(Tester t) {
        initData();
        t.checkExpect(emptylist.isEmpty(), true);
        t.checkExpect(list2.isEmpty(), false);
    }
    
    void testIsCons(Tester t) {
        initData();
        t.checkExpect(emptylist.isCons(), false);
        t.checkExpect(list2.isCons(), true);
    }
    
    
    void testHasNext(Tester t) {
        initData();
        
        t.checkExpect(list1.iterator().hasNext(), true);
        t.checkExpect(list2.iterator().hasNext(), true);
        t.checkExpect(emptylist.iterator().hasNext(), false);
    }
    
    void testNext(Tester t) {
        initData();
        
        t.checkExpect(list2.iterator().next(), "something2");
        t.checkExpect(list1.iterator().next(), "something");
        t.checkException(new RuntimeException("There is no next"), emptylistIt, "next");
    }
    
    void testRemove(Tester t) {
        initData();
        t.checkException(new UnsupportedOperationException("Something"), emptylistIt, "remove");
    }
    
    void testMakeMountain(Tester t) {
        initData();
        t.checkExpect(w1.makeMountain().get(0).get(0), 0.0);
        t.checkExpect(w1.makeMountain().get(32).get(32), 32.0);
        t.checkExpect(w1.makeMountain().get(32).get(31), 31.0);
        t.checkExpect(w1.makeMountain().get(31).get(32), 31.0);
    }
    
    void testMakeRandomMountain(Tester t) {
        initData();
        t.checkNumRange(w1.makeRandomMountain().get(32).get(32), 1, 32);
        t.checkExpect(w1.makeRandomMountain().get(0).get(0), 0.0);
    }
 
    
    void testOnKeyEvent(Tester t) {
        initData();
        w1.onKeyEvent("m");
        w1.onKeyEvent("r");
        t.checkExpect(w1.board.iterator().next().height, 0.0);
    }
    void testGame(Tester t) {
      initData();
      w1.bigBang(ForbiddenIslandWorld.WORLD_SIZE, ForbiddenIslandWorld.WORLD_SIZE, 0.1);
  }
}