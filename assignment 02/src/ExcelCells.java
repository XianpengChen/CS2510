import tester.*;
// Assignment 2

// Chen Xianpeng

// chenxianp

class Cell {
    String column;
    int row;
    IData stored;
    Cell(String column, int row, IData stored) {
        this.column = column;
        this.row = row;
        this.stored = stored;
    }
    /*
     *  TEMPLATES:
     *  Fields:
     *  ... this.column ... -- String
     *  ... this.row ...    -- int
     *  ... this.stored ... -- IData
     *  
     *  Methods:
     *  ... this.value() ...  -- int
     *  ... this.countArgs() ... -- int
     *  ... this.countFuns() ... -- int
     *  ... this.formulaDepth()... -- int
     *  ... this.formula() ...     -- String
     *  
     *  Methods of Fields:
     *  ... this.stored.getValue() ... --int
     *  ... this.stored.countArgsData() ... -- int 
     *  ... this.stored.countFunsData() ... -- int
     *  ... this.stored.formulaDepthData() ... -- int
     */
    int value() {
        return this.stored.getValue();
    }
    int countArgs() {
        return this.stored.countArgs();
    }
    int countFuns() {
        return this.stored.countFunsData();
    }
    int formulaDepth() {
        return this.stored.formulaDepthData();
    }
    String formula(int depth) {
        if (0 == depth) {
            return (this.column + Integer.toString(this.row));
        }
        else {
            return this.stored.formulaData(depth);
        }
    }
    
}

interface IData {
    int getValue();
    int countArgs();
    int countFunsData();
    int formulaDepthData();
    String formulaData(int depth);
    
}

class Number implements IData {
    int number;
    Number(int number) {
        this.number = number;
    }
    /*
     * Fields:
     * ... this.number ... -- int
     * 
     * Methods:
     * ... this.getValue() ... -- int
     * ... this.countArgsData() ... -- int
     * ... this.countFunsData() ... -- int
     * ... this.formulaDepthData() ... -- int
     * ... this.formulaData(int depth) ... -- String
     */
    public int getValue() {
        return this.number;
    }
    public int countArgs() {
        return 1;
    }
    public int countFunsData() {
        return 0;
    }
    public int formulaDepthData() {
        return 0;
    }
    public String formulaData(int depth) {
        return Integer.toString(this.number); 
    }
}

class Formula implements IData {
    Cell a;
    Cell b;
    String c;
    //if c is"mul"then it compromise the method
    //mul,if c is "sum" then it compromise the method sum
    //if c is "mod" then it compromise the method mod
    Formula(Cell a, Cell b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    /*
     * Fields:
     * ... this.a ... -- Cell
     * ... this.b ... -- Cell
     * ... this.c ... -- String
     * Methods:
     * ... this.getValue() ... -- int
     * ... this.mul(Cell cell1, Cell cell2) ... -- int
     * ... this.mod(Cell cell1, Cell cell2) ... -- int
     * ... this.sum(Cell cell1, Cell cell2) ... -- int
     * ... this.value(Cell cell3) ... -- int
     * ... this.countArgsData() ... -- int
     * ... this.countFunsData() ... -- int
     * ... this.formulaDepthData() ... -- int
     * ... this.formulaData(int depth) ... -- String
     */
    public int getValue() {
        if (this.c.equals("mul")) {
            return mul(this.a, this.b); 
        }
        else {
            if (this.c.equals("sum")) {
                return sum(this.a, this.b);
            }
            else {
                return mod(this.a, this.b);
            }
        }
    }
    public int mul(Cell cell1, Cell cell2) {
        return value(cell1) * value(cell2);  
    }

    public int value(Cell cell3) {
        return cell3.stored.getValue();
    }

    public int mod(Cell cell4, Cell cell5) {
        return value(cell4) % value(cell5);
    }

    public int sum(Cell cell6, Cell cell7) {
        return value(cell6) + value(cell7);
    }
    public int countArgs() {
        return this.a.countArgs() + this.b.countArgs();
    }
    public int countFunsData() {
        return this.a.countFuns() + this.b.countFuns() + 1;
    }
    public int formulaDepthData() {
        if (this.a.formulaDepth() < this.b.formulaDepth()) {
            return this.b.formulaDepth() + 1;
        }
        else {
            return this.a.formulaDepth() + 1;
        }
    }
    
    public String formulaData(int depth) {
        return (this.c + "(" + this.a.formula(depth - 1) + ", " 
                + this.b.formula(depth - 1) + ")");
    }
}

class ExamplesExcelCells {
    
    
    Cell cellA1 = new Cell("A", 1, new Number(25));
    Cell cellB1 = new Cell("B", 1, new Number(10));
    Cell cellC1 = new Cell("C", 1, new Number(1)); 
    Cell cellD1 = new Cell("D", 1, new Number(27));
    Cell cellE1 = new Cell("E", 1, new Number(16));
    Cell cellB2 = new Cell("B", 2, new Formula(this.cellB1, this.cellB1, "sum"));
    Cell cellA3 = new Cell("A", 3, new Formula(this.cellA1, this.cellB1, "mul"));
    Cell cellB3 = new Cell("B", 3, new Formula(this.cellE1, this.cellA3, "mod"));
    Cell cellC2 = new Cell("C", 2, new Formula(this.cellA3, this.cellC1, "sum"));
    Cell cellE2 = new Cell("E", 2, new Formula(this.cellE1, this.cellD1, "sum"));
    Cell cellD2 = new Cell("D", 2, new Formula(this.cellC2, this.cellE2, "mod"));
    
    Cell cellA5 = new Cell("A", 5, new Formula(this.cellD3, this.cellC5, "mod"));
    Cell cellC4 = new Cell("C", 4, new Formula(this.cellE1, this.cellD1, "mul"));
    Cell cellD3 = new Cell("D", 3, new Formula(this.cellD2, this.cellA1, "mul"));
    Cell cellD4 = new Cell("D", 4, new Formula(this.cellC4, this.cellA1, "sum"));
    Cell cellC5 = new Cell("C", 5, new Formula(this.cellD4, this.cellB3, "sum"));
    
    
    boolean testvalue(Tester t) {
        return t.checkExpect(this.cellA1.value(), 25) &&
               t.checkExpect(this.cellB3.value(), 16) &&
               t.checkExpect(this.cellA3.value(), 250) &&
               t.checkExpect(this.cellC4.value(), 432) &&
               t.checkExpect(this.cellD4.value(), 457);
    }
    boolean testcountArgs(Tester t) {
        return t.checkExpect(this.cellA1.countArgs(), 1) &&
               t.checkExpect(this.cellB2.countArgs(), 2) &&
               t.checkExpect(this.cellA3.countArgs(), 2) &&
               t.checkExpect(this.cellC2.countArgs(), 3) &&
               t.checkExpect(this.cellE2.countArgs(), 2) &&
               t.checkExpect(this.cellD2.countArgs(), 5);
    }
    boolean testcountFuns(Tester t) {
        return t.checkExpect(this.cellA1.countFuns(), 0) &&
               t.checkExpect(this.cellB2.countFuns(), 1) &&
               t.checkExpect(this.cellD2.countFuns(), 4);
    }
    boolean testformulaDepth(Tester t) {
        return t.checkExpect(this.cellA1.formulaDepth(), 0) &&
               t.checkExpect(this.cellC2.formulaDepth(), 2) &&
               t.checkExpect(this.cellB3.formulaDepth(), 2);
    }
    boolean testfomula(Tester t) {
        return t.checkExpect(this.cellA1.formula(0), "A1") &&
               t.checkExpect(this.cellD2.formula(2), 
                        "mod(sum(A3, C1), sum(E1, D1))") &&
               t.checkExpect(this.cellB1.formula(1), "10");
    }
} 




