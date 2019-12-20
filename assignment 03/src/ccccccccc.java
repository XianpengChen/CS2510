import tester.*;                // The tester library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Red, Green, Yellow, Blue, Black, White)

//Assignment 3
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin


interface IMobile {
    int totalWeight();
    int totalHeight();
    boolean isBalanced();
    IMobile buildMobile(int length, int strut, IMobile given);
    int getStrutlength(IMobile given, int strut);
    int curWidth();
    double curWidthHelper();
    double getLeft();
    double getRight();   
}

class Simple implements IMobile {
    int length;
    int weight;
    Color color;
    Simple(int length, int weight, Color color) {
        this.length = length;
        this.weight = weight;
        this.color = color;
    }
    /*
    TEMPLATE
    field:
    ... this.length ... --int
    ... this.weight ... --int
    ... this.color ... --color
    
    
    methods:
    ... this.totalWeight() ... --int
    ... this.totalHeight() ... --int
    ... this.isBalanced() ... --boolean
    ... this.getStrutlength(IMobile given, int strut) ... --int
    ... this.buildMobile(int length, int strut, IMobile given) ... --IMobile
    ... this.curWidthHelper() ... --double
    ... this.getRight() ... --double
    ... this.getLeft() ... --double
    ... this.curWidth() ... --int
    
    */
    public int totalWeight() {
        return this.weight;
    }
    public int totalHeight() {
        return this.length + (this.weight / 10);
    }
    public boolean isBalanced() {
        return true;
    }
    public int getStrutlength(IMobile given, int strut) {
        return strut / (1 + (this.totalWeight() / given.totalWeight()));
    }
    public  IMobile buildMobile(int length, int strut, IMobile given) {
        return new Complex(length, strut - given.getStrutlength(this, strut), 
                given.getStrutlength(this, strut), this, given);
    }
    public double curWidthHelper() {
        return  Math.ceil(this.weight / 10.0);
    }
    public double getLeft() {
        return Math.ceil(this.curWidth() / 2);
    }
    public double getRight() {
        return Math.ceil(this.curWidth() / 2);
    }
    public int curWidth() {
        return (int) this.curWidthHelper();
    }
}

class Complex implements IMobile {
    int length;
    int leftside;
    int rightside;
    IMobile left;
    IMobile right;
    Complex(int length, int leftside, int rightside, 
            IMobile left, IMobile right) {
        this.length = length;
        this.leftside = leftside;
        this.rightside = rightside;
        this.left = left;
        this.right = right;
    }
    /*
    TEMPLATE
    field:
    ... this.length ... --int
    ... this.left ... --IMobile
    ... this.right ... --IMobile
    ... this.leftside ... --color
    ... this.rightside ... --color
    
    methods:
    ... this.totalWeight() ... --int
    ... this.totalHeight() ... --int
    ... this.isBalanced() ... --boolean
    ... this.getStrutlength(IMobile given, int strut) ... --int
    ... this.buildMobile(int length, int strut, IMobile given) ... --IMobile
    ... this.curWidthHelper() ... --double
    ... this.getRight() ... --double
    ... this.getLeft() ... --double
    ... this.curWidth() ... --int
     METHODS FOR FIELDS:
     ... this.left.totalWeight() ... --int
     ... this.right.totalWeight() ... --int
     ... this.left.totalHeight() ... --int
     ... this.right.totalHeight() ... --int
     ... this.left.isBalanced() ... --boolean
     ... this.right.isBalanced() ... --boolean
     ... this.left.getLeft() ... --int
     ... this.right.getLeft() ... --int
     ... this.left.getRight() ... --int
     ... this.right.getRight() ... --int
    */
    public int totalWeight() {
        return this.left.totalWeight() + this.right.totalWeight();
    }
    public int totalHeight() {
        if (this.left.totalHeight() < this.right.totalHeight()) {
            return this.right.totalHeight() + this.length;
        }
        else {
            return this.left.totalHeight() + this.length;
        }
    }
    public boolean isBalanced() {
        if (this.left.totalWeight() * this.leftside
            == this.right.totalWeight() * this.rightside) {
            return this.left.isBalanced() && this.right.isBalanced(); 
        }
        else {
            return false;
        }
    }
    public int getStrutlength(IMobile given, int strut) {
        return strut / (1 + (this.totalWeight() / given.totalWeight()));
    }
    public  IMobile buildMobile(int length, int strut, IMobile given) {
        return new Complex(length, strut - given.getStrutlength(this, strut), 
                given.getStrutlength(this, strut), this, given);
    }
    public double curWidthHelper() {
        return this.getLeft() + this.getRight(); 
            
    }
    public double getLeft() {
        if ((this.leftside + this.left.getLeft()) >= 
                (this.right.getLeft() - this.rightside)) {
            return this.leftside + this.left.getLeft();
        }
        else {
            return this.right.getLeft() - this.rightside;
        }
    }
    public double getRight() {
        if ((this.rightside + this.right.getRight()) >= 
                (this.left.getRight() - this.leftside)) {
            return this.rightside + this.right.getRight();
        }
        else {
            return this.left.getRight() - this.leftside;
        }
        
    }
    public int curWidth() {
        return (int) this.curWidthHelper();
    }
}

class ExamplesMobiles {
    Simple exampleSimple = new Simple(2, 20, Color.blue);
    Simple b = new Simple(2, 36, Color.red);
    Simple c = new Simple(1, 60, Color.green);
    Simple d = new Simple(1, 12, Color.red);
    Simple f = new Simple(1, 36, Color.blue);
   
    Complex a = new Complex(2, 5, 3, this.b, this.c);
    Complex e = new Complex(2, 8, 1, this.d, this.a);
    
    Complex exampleComplex = new Complex(1, 9, 3, this.f, this.e);
    Complex example3 = new Complex(1, 5, 1, this.exampleSimple, this.exampleComplex);
    boolean testtotalWeight(Tester t) {
        return  t.checkExpect(this.exampleSimple.totalWeight(), 20) &&
                t.checkExpect(this.exampleComplex.totalWeight(), 144);
    }
    boolean testtotalHeight(Tester t) {
        return t.checkExpect(this.exampleSimple.totalHeight(), 4) &&
               t.checkExpect(this.exampleComplex.totalHeight(), 12);
    }
    boolean testisBalanced(Tester t) {
        return t.checkExpect(this.exampleSimple.isBalanced(), true) &&
               t.checkExpect(this.exampleComplex.isBalanced(), true);
    }
    boolean testbuildMobile(Tester t) {
        return t.checkExpect(this.exampleSimple.buildMobile(5, 41, this.exampleComplex), 
                new Complex(5, 36, 5, this.exampleSimple, this.exampleComplex)) &&
               t.checkExpect(this.exampleSimple.buildMobile(4, 8, this.c), 
                        new Complex(4, 6, 2, this.exampleSimple, this.c));
    }
    boolean testcurWidth(Tester t) {
        return t.checkExpect(this.exampleSimple.curWidth(), 2) &&
               t.checkExpect(this.f.curWidth(), 4) &&
               t.checkExpect(this.exampleComplex.curWidth(), 21) &&
               t.checkExpect(this.example3.curWidth(), 21);
    }
    
}