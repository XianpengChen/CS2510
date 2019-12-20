import tester.*;
//assignment 07
//Chen Xianpeng
//chenxianp
//Michael Delpapa   
//mdelpapa

interface IATVisitor<R> {
 
    IList<R> visit(IAT given);
}

class StringVisitor implements IATVisitor<String> {
    public IList<String> visit(IAT given) {
        if (given instanceof Unknown) {
            return new Empty<String>();
        }
        else { 
            return new Cons<String>(((Person)given).name, 
                    this.visit(((Person)given).mom).append(this.visit(((Person)given).dad)));
        }
    }

 
    
}

interface IAT {
    <R> IList<R> accept(IATVisitor<R> v);
}

class Unknown implements IAT {
    public <R> IList<R> accept(IATVisitor<R> v) {
        return v.visit(this);
    }
    
}
class Person implements IAT {
    String name;
    IAT mom;
    IAT dad;
    
    Person(String name, IAT mom, IAT dad) {
        this.name = name;
        this.mom = mom;
        this.dad = dad;
    }
    public <R> IList<R> accept(IATVisitor<R> v) {
        return v.visit(this);
       
 
    }
}

class ExamplesIAT {
    IAT unk = new Unknown();
    IAT dan = new Person("dan", new Person("sue", unk, unk), new Person("jay", unk, unk));
}


interface IList<T> {
    IList<T> append(IList<T> given);
}

class Empty<T> implements IList<T> {
   
    
    public  IList<T> append(IList<T> given) {
        return given;
    }
    
}


class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public  IList<T> append(IList<T> given) {
        return new Cons<T>(this.first, this.rest.append(given));
    }

   
}

class ExamplesIATVisitor {
    IAT u = new Unknown();
    IAT a = new Person("a", u, u);
    IAT b = new Person("b", a, u);
    IAT c = new Person("c", a, u);
    IAT d = new Person("d", u, u);
    IAT e = new Person("e", u, d);
    IAT f = new Person("f", u, d);
    IAT g = new Person("g", a, e);
    IAT h = new Person("h", b, f);
    StringVisitor string = new StringVisitor();
    
    boolean testaccept(Tester t) {
        return t.checkExpect(u.accept(string), new Empty<String>()) &&
                t.checkExpect(a.accept(string), new Cons<String>("a", new Empty<String>())) &&
                t.checkExpect(g.accept(string), new Cons<String>("g", new Cons<String>("a", 
                        new Cons<String>("e", 
                                new Cons<String>("d", new Empty<String>())))));               
    }
}