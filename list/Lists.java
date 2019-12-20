//assignment 07
//Chen Xianpeng
//chenxianp
//Michael Delpapa   
//mdelpapa

import tester.*;


class Book {
    String title;
    int price;
    int quantity;
 
    Book(String title, int price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
}

class Phone {
    String name;
    int price;
    int yob; //year of built
    Phone(String name, int price, int yob) {
        this.name = name;
        this.price = price;
        this.yob = yob;
    }
}

interface IFunc<T, U> {
    U apply(T given);
}

class BookTitle implements IFunc<Book, String> {
    public String apply(Book given) {
        return given.title;
    }
}
// to determine if the price of this book is over 5
class BookOver5 implements IFunc<Book, Boolean> {
    public Boolean apply(Book given) {
        return given.price > 5;  
    }
}

class PhonePrice implements IFunc<Phone, Integer> {
    public Integer apply(Phone given) {
        return given.price;
    }
}


interface IListVisitor<T, U> {
    IList<U> visit(IList<T> given);
}

class MapVisitor<T, U> implements IListVisitor<T, U> {
    IFunc<T, U> f;
    MapVisitor(IFunc<T, U> f) {
        this.f = f;
    }
    public IList<U> visit(IList<T> given) {
        if (given instanceof Empty<?>) {
            return new Empty<U>();
        }
        else {
            return new Cons<U>(f.apply(given.getFirst()), this.visit(given.getRest()));
        }
    }
}

class FilterVisitor<T> implements IListVisitor<T, T> {
    IFunc<T, Boolean> f;
    FilterVisitor(IFunc<T, Boolean> f) {
        this.f = f;
    }
    public IList<T> visit(IList<T> given) {
        if (given instanceof Empty<?>) {
            return given;
        }
        else if (this.f.apply(given.getFirst())) {
            return new Cons<T>(given.getFirst(), this.visit(given.getRest()));
        }
        else {
            return this.visit(given.getRest());
        }
    }
}


interface IList<T> {
    T getFirst();
    IList<T> getRest();
    IList<T> append(IList<T> given);
    <U> IList<U> accept(IListVisitor<T, U> v);
    <U> IList<U> map(IFunc<T, U> f);
    IList<T> filter(IFunc<T, Boolean> f);
    
}

class Empty<T> implements IList<T> {
   
    public  T getFirst() {
        return null;
    }
    public IList<T> getRest() {
        return this;
    }
    public  IList<T> append(IList<T> given) {
        return given;
    }
    public <U> IList<U> accept(IListVisitor<T, U> v) {
        return  v.visit(this);
    }
    public <U> IList<U> map(IFunc<T, U> f) {
        return new Empty<U>();
    }
    public IList<T> filter(IFunc<T, Boolean> f) {
        return this;
    }
    
}


class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public  T getFirst() {
        return this.first;
    }
    public IList<T> getRest() {
        return this.rest;
    }
    public  IList<T> append(IList<T> given) {
        return new Cons<T>(this.first, this.rest.append(given));
    }

    public <U> IList<U> accept(IListVisitor<T, U> v) {
        return v.visit(this);
    }
    public <U> IList<U> map(IFunc<T, U> f) {
        return new Cons<U>(f.apply(first), this.rest.map(f));
    } 
    public IList<T> filter(IFunc<T, Boolean> f) {
        if (f.apply(first)) {
            return new Cons<T>(this.first, this.rest.filter(f));
        }
        else {
            return this.rest.filter(f);
        }
    }
}


class Examples {
    Book a = new Book("a", 1, 1);
    Book b = new Book("b", 1, 2);
    Book c = new Book("c", 6, 3);
    
    Phone a1 = new Phone("apple", 650, 2012);
    Phone s1 = new Phone("samsung", 750, 2013);
    Phone n1 = new Phone("sony", 600, 2014);
    
    IList<Phone> emp = new Empty<Phone>();
    IList<Phone> la = new Cons<Phone>(a1, emp);
    IList<Phone> ls = new Cons<Phone>(s1, la);
    IList<Phone> ln = new Cons<Phone>(n1, ls);
    
    
    IList<Book> empty = new Empty<Book>();
    IList<Book> d = new Cons<Book>(a, empty);
    IList<Book> e = new Cons<Book>(b, d);
    IList<Book> f = new Cons<Book>(c, e);
   
    IFunc<Book, String> bookTitle = new BookTitle();
    IFunc<Book, Boolean> bookover5 = new BookOver5();
    IFunc<Phone, Integer> phonePrice = new PhonePrice();
    
    MapVisitor<Book, String> mapBook2TitleVisitor = new MapVisitor<Book, String>(bookTitle);
    FilterVisitor<Book> filterbookover5 = new FilterVisitor<Book>(bookover5);
    MapVisitor<Phone, Integer> mapPhonePrice = new MapVisitor<Phone, Integer>(phonePrice);
    
    
    boolean testaccept(Tester t) {
        return t.checkExpect(f.accept(mapBook2TitleVisitor), 
                new Cons<String>("c", new Cons<String>("b",
                        new Cons<String>("a", new Empty<String>())))) &&
                t.checkExpect(f.accept(filterbookover5), new Cons<Book>(this.c, empty)) &&
                t.checkExpect(f.accept(mapBook2TitleVisitor), this.f.map(bookTitle)) &&
                t.checkExpect(f.accept(filterbookover5), this.f.filter(bookover5)) &&
                t.checkExpect(this.ln.accept(mapPhonePrice), this.ln.map(phonePrice));
    }
    boolean testmap(Tester t) {
        return  t.checkExpect(this.f.map(bookTitle), new Cons<String>("c", 
                new Cons<String>("b", new Cons<String>("a", 
                        new Empty<String>()))));
    }
    boolean testfilter(Tester t) {
        return t.checkExpect(this.f.filter(bookover5), new Cons<Book>(this.c, empty));
    }   
}