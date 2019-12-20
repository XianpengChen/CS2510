import tester.*;

//Assignment 6
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin

interface IArithVisitor<R> {
    R visitConst(Const c);
    R visitFormula(Formula f);
}

class EvalVisitor implements IArithVisitor<Double> {
    public Double visitConst(Const c) {
        return c.num;
    }
    public Double visitFormula(Formula f) {
        return f.fun.apply(f.left.accept(this), f.right.accept(this));
    }
}

class PrintVisitor implements IArithVisitor<String> {
    public String visitConst(Const c) {
        return Double.toString(c.num);
    }
    public String visitFormula(Formula f) {
        return "(" + f.name + " " +  f.left.accept(this) + " " + f.right.accept(this) + ")";
    }
}

class DoublerVisitor implements IArithVisitor<IArith> {
    public IArith visitConst(Const c) {
        return new Const(c.num * 2);
    }
    public IArith visitFormula(Formula f) {
        return new Formula(f.fun, f.name, f.left.accept(this), f.right.accept(this));
    }
}

class AllSmallVisitor implements IArithVisitor<Boolean> {
    public Boolean visitConst(Const c) {
        return c.num < 10;
    }
    public Boolean visitFormula(Formula f) {
        return f.left.accept(this) && f.right.accept(this);
    }
}

class NoDivBy0 implements IArithVisitor<Boolean> {
    public Boolean visitConst(Const c) {
        return true;
    }
    public Boolean visitFormula(Formula f) {
        if (f.name.equals("div")) {
            return f.right.accept(this) && f.left.accept(this) &&
                        f.right.accept(new EvalVisitor()) > 0.0001;
        }
        else {
            return f.left.accept(this) && f.right.accept(this);
        }
    }
}


interface IFunc2<A1, A2, R> {
    R apply(A1 a1, A2 a2);
}

class Plus implements IFunc2<Double, Double, Double> {
    public Double apply(Double a1, Double a2) {
        return a1 + a2;
    }
}

class Subtraction implements IFunc2<Double, Double, Double> {
    public Double apply(Double a1, Double a2) {
        return a1 - a2;
    }
}

class Multiplication implements IFunc2<Double, Double, Double> {
    public Double apply(Double a1, Double a2) {
        return a1 * a2;
    }
}



class Division implements IFunc2<Double, Double, Double> {
    public Double apply(Double a1, Double a2) {
        return a1 / a2;
    }
}

interface IArith {
    <R> R accept(IArithVisitor<R> v);
    
}

class Const implements IArith {
    double num;
    Const(double num) {
        this.num = num;
    }
    public <R> R accept(IArithVisitor<R> v) {
        return v.visitConst(this);
        
    }
}

class Formula implements IArith {
    IFunc2<Double, Double, Double> fun;
    String name;
    IArith left;
    IArith right;
    Formula(IFunc2<Double, Double, Double> fun, String name,
            IArith left, IArith right) {
        this.fun = fun;
        this.name = name;
        this.left = left;
        this.right = right;
        
    }
    public <R> R accept(IArithVisitor<R> v) {
        return v.visitFormula(this);
        
    }
}

class ExamplesIArith {
    
    IArith a = new Const(1.0);
    IArith b = new Const(2.0);
    IArith c = new Const(3.0);
    IArith d = new Const(4.0);
    IArith e = new Const(5.0);
    IArith f = new Const(0.00000000000001);
    IArith g = new Formula(new Plus() , "plus", this.a, this.b);
    IArith h = new Formula(new Subtraction() , "subtract", this.a, this.b);
    IArith i = new Formula(new Multiplication() , "multiply", this.a, this.b);
    IArith j = new Formula(new Division() , "div", this.a, this.b);
    IArith k = new Formula(new Division() , "div", this.a, this.f);
    boolean testaccept(Tester t) {
        return t.checkExpect(this.a.accept(new EvalVisitor()), 1.0) &&
                t.checkExpect(this.g.accept(new EvalVisitor()), 3.0) &&
                t.checkExpect(this.a.accept(new PrintVisitor()), "1.0") &&
                t.checkExpect(this.g.accept(new PrintVisitor()), "(plus 1.0 2.0)") &&
                t.checkExpect(this.a.accept(new DoublerVisitor()), new Const(2.0)) &&
                t.checkExpect(this.g.accept(new DoublerVisitor()), new Formula(new Plus(),
                        "plus", new Const(2.0), new Const(4.0))) &&
                t.checkExpect(this.a.accept(new AllSmallVisitor()), true) &&
                t.checkExpect(this.i.accept(new AllSmallVisitor()), true) &&
                t.checkExpect(this.a.accept(new NoDivBy0()), true) &&
                t.checkExpect(this.k.accept(new NoDivBy0()), false);
    }
    
    
    
    
}