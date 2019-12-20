import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


class Cell {
    int x;
    int y;
    ArrayList<Cell> representatives;
    Cell(int x, int y, ArrayList<Cell> representatives) {
        this.x = x;
        this.y = y;
        this.representatives = representatives;
    }
}

class Edge {
    Cell a;
    Cell b;
    int weight;
    Edge(Cell a, Cell b, int weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }
}

class Util {
    ArrayList<ArrayList<Cell>> buildCell(int i, int j) {
        ArrayList<ArrayList<Cell>> setup = new ArrayList<ArrayList<Cell>>();
        for (int a = 0; a < i; a++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int b = 0; b < j; b++) {
                Cell now = new Cell(b, a, new ArrayList<Cell>());
                row.add(now);
            }
            setup.add(row);
        }
        return setup;
    }
    
    //to union two ArrayList<Cell>
    //effect:
    void union(ArrayList<Cell> arr1, ArrayList<Cell> arr2) {
        int n = arr1.size();
        for (Cell c : arr2) {
            arr1.add(c);
        }
        for (int i = 0; i < n; i++) {
            arr2.add(arr1.get(i));
        } 
    }
    boolean find(ArrayList<Cell> arr, Cell given) {
        boolean temp = false;
        for (Cell c : arr) {
            if (c.x == given.x && c.y == given.y) {
                temp = true;
            }
        }
        return temp;
    }
    ArrayList<Edge> buildHorizontalEdge(ArrayList<ArrayList<Cell>> arr) {
        ArrayList<Edge> setup = new ArrayList<Edge>();
        for (int i = 0; i < arr.size() - 1; i++) {
            
            for (int j = 0; j < arr.get(0).size(); j++) {
                Cell top = arr.get(i).get(j);
                Cell bottom = arr.get(i - 1).get(j);
                Random r = new Random();
                int weight = r.nextInt() * 120;
                Edge e = new Edge(top, bottom, weight);
                setup.add(e);
            }
        }
        return setup;   
    }
    ArrayList<Edge> buildVerticalEdge(ArrayList<ArrayList<Cell>> arr) {
        ArrayList<Edge> setup = new ArrayList<Edge>();
        for (int i = 0; i < arr.size(); i++) {
            
            for (int j = 0; j < arr.get(0).size() - 1; j++) {
                Cell left = arr.get(i).get(j);
                Cell right = arr.get(i).get(j + 1);
                Random r = new Random();
                int weight = r.nextInt() * 120;
                Edge e = new Edge(left, right, weight);
                setup.add(e);
            }
        }
        return setup;   
    }
    ArrayList<Edge> addAll(ArrayList<Edge> arr1, ArrayList<Edge> arr2) {
        for (Edge e : arr2) {
            arr1.add(e);
        }
        return arr1;
    }
    void updateRepresentatives(ArrayList<Edge> arr) {
        for (int i = 0; i < arr.size(); i++) {
            Edge e = arr.get(i);
            Cell a = e.a;
            Cell b = e.b;
            b.representatives = a;
            
        }
    }
    
}


