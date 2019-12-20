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
    Cell left;
    Cell top;
    Cell right;
    Cell bottom;

    // to check if it is flooded
    boolean isFlooded;
    static final int CELL_SIZE = ForbiddenIslandWorld.WORLD_SIZE / ForbiddenIslandWorld.ISLAND_SIZE;

    Cell(double height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.isFlooded = false;

        this.left = this;
        this.right = this;
        this.bottom = this;
        this.top = this;
    }

    // get the color for the cell
    Color getColor(int waterheight) {
        double index = this.height - waterheight;
        if (index > 0) {
            this.isFlooded = false;
           
            int c1 = (int) (index * 7);
            if (c1 >= 255) {
                
                return new Color(255, 200, 255);
            }
            else {
                return new Color(c1, 200, c1); 
            }
        }
        else {
            if (this.top.isFlooded || this.bottom.isFlooded || 
                    this.left.isFlooded || this.right.isFlooded) {
                this.isFlooded = true;
                if ((255 + 7 * index) < 0) {
                    return new Color(0, 0, 0);   
                }
                else {
                    return new Color(0, 0, (int) (255 + 7 * index));
                }
            }
            else {
                this.isFlooded = false;
                int red = (int) Math.abs(index * 12);
                int green = (int) (80 + index);
                if ((int) Math.abs(index * 12) >= 255) {
                    if ((int) (80 + index) <= 0) {
                        return new Color(255, 0, 0);
                    }
                    else {
                        return new Color(255, green, 0);
                    }
                }
                else {
                    if ((int) (80 + index) <= 0) {
                        return new Color(red, 0, 0);
                    }
                    else {
                        return new Color(red, green, 0);
                    }
                }
            }
        }
    }

    // to draw a cell
    WorldImage drawCell(int waterHeight) {
        return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID,
                this.getColor(waterHeight));
    }
}

//to represent an ocean cell
class OceanCell extends Cell {
    OceanCell(int x, int y) {
        super(0, x, y);
        this.isFlooded = true;
    }

    // to draw a cell
    WorldImage drawCell(int waterHeight) {
        return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, Color.BLUE);
    }
}




// define a target class

class Target {
    int x;
    int y;
    Target(int x, int y) {
        this.x = x;
        this.y = y;
    }
    WorldImage drawTarget(IList<Cell> ilist) {
        FindCell f = new FindCell();
        Cell c = ilist.find(f, this.x, this.y);
        if (c.isFlooded) {
            return new RectangleImage(Cell.CELL_SIZE, Cell.CELL_SIZE, OutlineMode.SOLID, Color.BLUE);
        }
        else {
            return new CircleImage(5, OutlineMode.SOLID, Color.YELLOW);
        }
    }
}

class Helicopter extends Target {

    Helicopter(int x, int y) {
        super(x, y);
        this.x = ForbiddenIslandWorld.ISLAND_SIZE / 2 + 1;
        this.y = ForbiddenIslandWorld.ISLAND_SIZE / 2 + 1;

    }

    WorldImage drawTarget(IList<Cell> ilist) {
        return new FromFileImage("helicopter.png");
    }
}

class Player {
    int x;
    int y;
    int piecesgot;
    Player(int x, int y, int piecesgot) {
        this.x = x;
        this.y = y;
        this.piecesgot = piecesgot;
    }
    WorldImage drawPlayer() {
        return new FromFileImage("pilot-icon.png");
    }
}

interface IPred<T> {
    boolean compare(T t, int x, int y);
}

class FindCell implements IPred<Cell> {

    public boolean compare(Cell t, int x, int y) {
        return t.x == x && t.y == y;
    }   
}

class FindTarget implements IPred<Target> {

    public boolean compare(Target t, int x, int y) {
        return t.x == x && t.y == y;
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
    int length();
    T find(IPred<T> p, int x, int y);
    IList<T> delete(T given);

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

    public int length() {

        return 0;
    }

    public T find(IPred<T> p, int x, int y) {
        return null;
    }

    public IList<T> delete(T given) {
        return this;
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



    public int length() {
        return 1 + this.rest.length();
    }

    public T find(IPred<T> p, int x, int y) {
        if (p.compare(first, x, y)) {
            return this.first;
        }
        else {
            return this.rest.find(p, x, y);
        }
    }


    @Override
    public IList<T> delete(T given) {
        if (given.equals(first)) {
            return this.rest;
        }
        return new Cons<T>(this.first, this.rest.delete(given));
    }
}
class Utils {

    ArrayList<ArrayList<Double>> makeArrayArray() {
        ArrayList<ArrayList<Double>> newlist = new ArrayList<ArrayList<Double>>();
        for (int y = 0;  y <= ForbiddenIslandWorld.ISLAND_SIZE; y++) {
            ArrayList<Double> row = new ArrayList<Double>();
            for (int x = 0; x <= ForbiddenIslandWorld.ISLAND_SIZE; x++) {
                double height = 0;
                row.add(height);
            }
            newlist.add(row);   
        }
        return newlist;
    }
    // effect: get neighbors for every cell
    void getNeghbours(ArrayList<ArrayList<Cell>> cList) {
        for (int y = 0; y < cList.size(); y++) {
            ArrayList<Cell> row = cList.get(y);
            for (int x = 0; x < row.size(); x++) {
                Cell c = row.get(x);
                if (y == 0) {
                    c.top = new OceanCell(x, -1);
                    c.bottom = cList.get(y + 1).get(x);
                    if (x == 0) {
                        c.left = new OceanCell(-1, y);
                        c.right = cList.get(y).get(x + 1);
                    }
                    else if (x == row.size() - 1) {
                        c.left = cList.get(y).get(x - 1);
                        c.right = new OceanCell(x + 1, y);
                    }
                    else {
                        c.left = cList.get(y).get(x - 1);
                        c.right = cList.get(y).get(x + 1);
                    }
                }
                else if (y == cList.size() - 1) {
                    c.top = cList.get(y - 1).get(x);
                    c.bottom = new OceanCell(x, y + 1);
                    if (x == 0) {
                        c.left = new OceanCell(-1, y);
                        c.right = cList.get(y).get(x + 1);
                    }
                    else if (x == row.size() - 1) {
                        c.left = cList.get(y).get(x - 1);
                        c.right = new OceanCell(x + 1, y);
                    }
                    else {
                        c.left = cList.get(y).get(x - 1);
                        c.right = cList.get(y).get(x + 1);
                    }
                }
                else {
                    c.top = cList.get(y - 1).get(x);
                    c.bottom = cList.get(y + 1).get(x);
                    if (x == 0) {
                        c.left = new OceanCell(-1, y);
                        c.right = cList.get(y).get(x + 1);
                    }
                    else if (x == row.size() - 1) {
                        c.left = cList.get(y).get(x - 1);
                        c.right = new OceanCell(x + 1, y);
                    }
                    else {
                        c.left = cList.get(y).get(x - 1);
                        c.right = cList.get(y).get(x + 1);
                    }
                }

            }

        }

    }
    ArrayList<ArrayList<Double>> initial() {
        ArrayList<ArrayList<Double>> newlist = this.makeArrayArray();
        newlist.get(0).set(ForbiddenIslandWorld.ISLAND_SIZE / 2, (double) 1);
        newlist.get(ForbiddenIslandWorld.ISLAND_SIZE).set(ForbiddenIslandWorld.ISLAND_SIZE / 2, (double) 1);
        newlist.get(ForbiddenIslandWorld.ISLAND_SIZE / 2).set(0, (double) 1);
        newlist.get(ForbiddenIslandWorld.ISLAND_SIZE / 2).set(ForbiddenIslandWorld.ISLAND_SIZE, (double) 1);
        newlist.get(ForbiddenIslandWorld.ISLAND_SIZE / 2).set(ForbiddenIslandWorld.ISLAND_SIZE / 2, (double) 30);

        return newlist;
    }
    void generate(ArrayList<ArrayList<Double>> arr, int lr, int hr, int lc, int hc) {
        if (lr + 1 >= hr && lc + 1 >= hc) {
            return;
        }
        if (arr.get((lr + hr) / 2).get((lc + hc) / 2) == (double) 0) {

            Random rand = new Random();
            double sign = 1;


            double  n = rand.nextDouble() * 4 + 1;
            double tl = arr.get(lr).get(lc);
            double tr = arr.get(lr).get(hc);
            double bl = arr.get(hr).get(lc);
            double br = arr.get(hr).get(hc);

            if (n < 2) {
                sign = -1;
            }
            else {
                sign = 1;
            }

            arr.get((lr + hr) / 2).set((lc + hc) / 2, n * sign + ((tl + tr + bl + br) / 4));
            arr.get(lr).set((lc + hc) / 2, n * sign + ((tl + tr) / 2));
            arr.get(hr).set((lc + hc) / 2, n * sign + ((bl + br) / 2));
            arr.get((lr + hr) / 2).set(lc, n * sign  + ((tl + bl) / 2));
            arr.get((lr + hr) / 2).set(hc, n * sign  + ((tr + br) / 2));
            this.generate(arr, lr, (lr + hr) / 2, lc, (lc + hc) / 2);
            this.generate(arr, (lr + hr) / 2, hr, lc, (lc + hc) / 2);
            this.generate(arr, lr, (lr + hr) / 2, (lc + hc) / 2, hc);
            this.generate(arr, (lr + hr) / 2, hr, (lc + hc) / 2, hc);

        }
        this.generate(arr, lr, (lr + hr) / 2, lc, (lc + hc) / 2);
        this.generate(arr, (lr + hr) / 2, hr, lc, (lc + hc) / 2);
        this.generate(arr, lr, (lr + hr) / 2, (lc + hc) / 2, hc);
        this.generate(arr, (lr + hr) / 2, hr, (lc + hc) / 2, hc);
    }
    //to generate a list of targets
    IList<Target> renderTargets(ArrayList<ArrayList<Cell>> alist) {
        IList<Target> lot = new Empty<Target>();
        while (lot.length() < 6) {
            Random rand = new Random();
            int y = rand.nextInt(alist.size());
            int x = rand.nextInt(alist.size());
            double height = alist.get(y).get(x).height;
            if (height > 0) {
                lot = new Cons<Target>(new Target(x, y), lot);
            }
        }
        return lot;   
    }
    Player renderPlayer(ArrayList<ArrayList<Cell>> alist) {
        Player initial = new Player(0, 0, 0);
        while (alist.get(initial.y).get(initial.x).height <= 0) {


            Random rand = new Random();
            int y = rand.nextInt(alist.size());
            int x = rand.nextInt(alist.size());
            double height = alist.get(y).get(x).height;
            if (height > 0) {
                initial.x = x;
                initial.y = y;
            }
        }
        return initial;
    }
}

// to represent A ForbiddenIslandWorld
class ForbiddenIslandWorld extends World {
    IList<Cell> board;
    int waterHeight;
    static final int ISLAND_SIZE = 64;
    static final int WORLD_SIZE = (ISLAND_SIZE + 1) * 11;
    int tickcount;
    IList<Target> targets;
    Player player;

    ForbiddenIslandWorld() {
        Utils util = new Utils();

        ArrayList<ArrayList<Cell>> mountain = this.transformArraytoCell(this.makeMountain());
        util.getNeghbours(mountain);
        this.targets = new Cons<Target>(new Helicopter(ISLAND_SIZE / 2 + 1, 
                ISLAND_SIZE / 2 + 1), util.renderTargets(mountain));
        this.player = util.renderPlayer(mountain);
        this.board = this.makeIList(mountain);

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
                    c = new Cell(height, j + 1, i + 1);
                }
                else {
                    c = new OceanCell(j + 1, i + 1);
                }


                row.add(c);
            }
            cList.add(row);
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
            w.placeImageXY(c.drawCell(this.waterHeight), Cell.CELL_SIZE * c.x - 5,
                    Cell.CELL_SIZE * c.y - 5);
        }

        return w;
    }
    public WorldScene drawTargets(WorldScene given) {
        for (Target t : this.targets) {
            given.placeImageXY(t.drawTarget(this.board), Cell.CELL_SIZE * t.x - 5, Cell.CELL_SIZE * t.y - 5);
        }
        return given;

    }

    // to render the game image
    public WorldScene makeScene() {
        WorldScene cells =  this.drawCells();
        WorldScene addTargets = this.drawTargets(cells);
        addTargets.placeImageXY(this.player.drawPlayer(), 
                Cell.CELL_SIZE * this.player.x - 5, Cell.CELL_SIZE * this.player.y - 5);
        return addTargets;

    }
    public WorldScene lastScene(String s) {
        WorldScene in = new WorldScene(WORLD_SIZE, WORLD_SIZE);
        in.placeImageXY(new TextImage(s, 30, FontStyle.BOLD, Color.RED),
       
                (ISLAND_SIZE / 2 + 1) * 11,  (ISLAND_SIZE / 2 + 1) * 11);
        
        return in;
    }

    public WorldEnd worldEnds() {
       
        FindCell f = new FindCell();
        if (this.player.piecesgot >= 3 && this.player.x == ISLAND_SIZE / 2 + 1 &&
                this.player.y == ISLAND_SIZE / 2 + 1) {
            return  new WorldEnd(true, this.lastScene("Hold on! We gonna take off!"));
        }
        else if (this.board.find(f, this.player.x, this.player.y).isFlooded) {
            return new WorldEnd(true, this.lastScene("Whoops! You are flooded!"));
        }
        else {
            return new WorldEnd(false, this.makeScene());
        }

    }
    //effect: ticks on
    public void onTick() {
        if (this.tickcount == 9) {
            this.tickcount = 0;
            this.waterHeight = this.waterHeight + 1;
        }
        else {
            this.tickcount++;
        }  
    }
    // effect:to represent an onkeyevent
    /* (non-Javadoc)
     * @see javalib.impworld.World#onKeyEvent(java.lang.String)
     */
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.waterHeight = 0;
            this.tickcount = 0;
            Utils util = new Utils();

            ArrayList<ArrayList<Cell>> mountain = this.transformArraytoCell(this.makeMountain());
            util.getNeghbours(mountain);
            this.targets = new Cons<Target>(new Helicopter(ISLAND_SIZE / 2 + 1, 
                    ISLAND_SIZE / 2 + 1), util.renderTargets(mountain));
            this.player = util.renderPlayer(mountain);
            this.board = this.makeIList(mountain);

        }
        else if (ke.equals("r")) {
            Utils util = new Utils();
            this.waterHeight = 0;
            this.tickcount = 0;
            ArrayList<ArrayList<Cell>> randommountain = 
                    this.transformArraytoCell(this.makeRandomMountain());
            util.getNeghbours(randommountain);
            this.targets = new Cons<Target>(new Helicopter(ISLAND_SIZE / 2 + 1,
                    ISLAND_SIZE / 2 + 1), util.renderTargets(randommountain));
            this.player = util.renderPlayer(randommountain);
            this.board = this.makeIList(randommountain);
        }
        else if (ke.equals("t")) {
            this.waterHeight = 0;
            this.tickcount = 0;
            Utils util = new Utils();
            ArrayList<ArrayList<Double>> terrain = util.initial();
            util.generate(terrain, 0, ForbiddenIslandWorld.ISLAND_SIZE, 
                    0, ForbiddenIslandWorld.ISLAND_SIZE);
            ArrayList<ArrayList<Cell>> randomterrain = this.transformArraytoCell(terrain);
            util.getNeghbours(randomterrain);
            this.targets = new Cons<Target>(new Helicopter(ISLAND_SIZE / 2 + 1, 
                    ISLAND_SIZE / 2 + 1), util.renderTargets(randomterrain));
            this.player = util.renderPlayer(randomterrain);
            this.board = this.makeIList(randomterrain);
        }
        else if (ke.equals("left")) {
            FindCell f = new FindCell();
            FindTarget g = new FindTarget();

            int x = this.player.x;
            int y = this.player.y;
            if (x == 0) {
                this.player.x = 0;
            }
            else if (this.board.find(f, x - 1, y).isFlooded) {
                return;
            }
            else if (this.targets.find(g, x - 1, y) != null) {
                if (x - 1 == ISLAND_SIZE / 2 + 1 && y == ISLAND_SIZE / 2 + 1 && this.player.piecesgot < 3) {
                    this.player.x = x - 1;
                    
                }
                else {
                    this.targets = this.targets.delete(this.targets.find(g, x - 1, y));

                    this.player.x = x - 1;
                    this.player.piecesgot = this.player.piecesgot + 1;
                }
            }
            else {
                this.player.x = x - 1;
            }   
        }
        else if (ke.equals("right")) {
            FindCell f = new FindCell();
            FindTarget g = new FindTarget();

            int x = this.player.x;
            int y = this.player.y;
            if (x == ISLAND_SIZE + 1) {
                this.player.x = ISLAND_SIZE + 1;
            }
            else if (this.board.find(f, x + 1, y).isFlooded) {
                return;
            }
            else if (this.targets.find(g, x + 1, y) != null) {
                if (x + 1 == ISLAND_SIZE / 2 + 1 && y == ISLAND_SIZE / 2 + 1 && this.player.piecesgot < 3) {
                    this.player.x = x + 1;
                }
                else {
                    this.targets = this.targets.delete(this.targets.find(g, x + 1, y));
                    this.player.x = x + 1;
                    this.player.piecesgot = this.player.piecesgot + 1;
                }
            }
            else {
                this.player.x = x + 1;
            }   

        }
        else if (ke.equals("up")) {
            FindCell f = new FindCell();
            FindTarget g = new FindTarget();

            int x = this.player.x;
            int y = this.player.y;
            if (y == 0) {
                this.player.y = 0;
            }
            else if (this.board.find(f, x, y - 1).isFlooded) {
                return;
            }
            else if (this.targets.find(g, x, y - 1) != null) {
                if (x == ISLAND_SIZE / 2 + 1 && y - 1 == ISLAND_SIZE / 2 + 1 && this.player.piecesgot < 3) {
                    this.player.y = y - 1;
                }
                else {
                    this.targets = this.targets.delete(this.targets.find(g, x, y - 1));
                    this.player.y = y - 1;
                    this.player.piecesgot = this.player.piecesgot + 1;
                }
            }
            else {
                this.player.y = y - 1;
            }  
        }
        else if (ke.equals("down")) {
            FindCell f = new FindCell();
            FindTarget g = new FindTarget();

            int x = this.player.x;
            int y = this.player.y;
            if (y == ISLAND_SIZE + 1) {
                this.player.y = ISLAND_SIZE + 1;
            }
            else if (this.board.find(f, x, y + 1).isFlooded) {
                return;
            }
            else if (this.targets.find(g, x, y + 1) != null) {
                if (x == ISLAND_SIZE / 2 + 1 && y + 1 == ISLAND_SIZE / 2 + 1 && this.player.piecesgot < 3) {
                    this.player.y = y + 1;
                }
                else {
                    this.targets = this.targets.delete(this.targets.find(g, x, y + 1));
                    this.player.y = y + 1;
                    this.player.piecesgot = this.player.piecesgot + 1;
                }
            }
            else {
                this.player.y = y + 1;
            } 
        }
        else {
            return;
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
        w1.bigBang(ForbiddenIslandWorld.WORLD_SIZE, ForbiddenIslandWorld.WORLD_SIZE, 0.2);
    }
}