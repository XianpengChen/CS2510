import tester.*;
//Assignment 6
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin
interface IComparator<T> {
 // Returns a negative number if t1 comes before t2 in this ordering
    // Returns zero              if t1 is the same as t2 in this ordering
    // Returns a positive number if t1 comes after t2 in this ordering
    int compare(T t1, T t2);
}

class BooksByTitle implements IComparator<Book> {
    public int compare(Book t1, Book t2) {
        return t1.title.compareTo(t2.title);
    }
    
}

class  BooksByAuthor implements IComparator<Book> {
    public int compare(Book t1, Book t2) {
        return t1.author.compareTo(t2.author);
    }
}

class BooksByPrice implements IComparator<Book> {
    public int compare(Book t1, Book t2) {
        return t1.price - t2.price;
    }
    
}
abstract class ABST<T> {
    IComparator<T> order;
    ABST(IComparator<T> order) {
        this.order = order;
    }
    abstract ABST<T> insert(T given);
    abstract T getLeftmost();
    abstract ABST<T> getRight();
    abstract boolean isLeaf();
    abstract boolean isNode();
    abstract boolean sameTree(ABST<T> given);
    abstract boolean sameData(ABST<T> given);
    abstract T getData();
    abstract ABST<T> getLeftBranch();
    abstract ABST<T> getRightBranch();
    abstract IList<T> buildList(IList<T> given);
    abstract boolean sameAsList(IList<T> given);
}

class Leaf<T> extends ABST<T> {
    Leaf(IComparator<T> order) {
        super(order);
    }

    public ABST<T> insert(T given) {
        return new Node<T>(this.order, given, 
                new Leaf<T>(this.order), new Leaf<T>(this.order));

    }
    public T getLeftmost() {
        throw new RuntimeException("No leftmost item of an empty tree");
    }
    public ABST<T> getRight() {
        throw new RuntimeException("No right of an empty tree");  
    }
    public boolean isLeaf() {
        return true;
    }
    public boolean isNode() {
        return false;
    }
    public T getData() {
        return null;
    }
    public boolean sameTree(ABST<T> given) {
        return given.isLeaf() && 
                given.order.equals(this.order);
    }
    public ABST<T> getLeftBranch() {
        return null;
    }
    public ABST<T> getRightBranch() {
        return null;
    }
    public boolean sameData(ABST<T> given) {
        return given.isLeaf();
    }
    public IList<T> buildList(IList<T> given) {
        return given;
    }
    public boolean sameAsList(IList<T> given) {
        return !given.isConsList();
    }
    
}

class Node<T> extends ABST<T> {
    T data;
    ABST<T> left;
    ABST<T> right;
    Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }
    public ABST<T> insert(T given) {
        if (this.order.compare(given, this.data) < 0) {
            return new Node<T>(this.order, 
                    this.data, this.left.insert(given), this.right);
        }
        else {
            return new Node<T>(this.order, 
                    this.data, this.left, this.right.insert(given));
        }
    }
    public T getLeftmost() {
        if (this.left instanceof Leaf<?>) {
            return this.data;    
        }
        else {
            return this.left.getLeftmost();
        }
    }
    public ABST<T> getRight() {
        if (this.left instanceof Leaf<?>) {
            return this.right;
        }
        else {
            return new Node<T>(this.order, this.data, 
                    this.left.getRight(), this.right);
        }
    }
    public boolean isLeaf() {
        return false;
    }
    public boolean isNode() {
        return true;
    }
    public T getData() {
        return this.data;
    }
    public boolean sameTree(ABST<T> given) {
        return given.isNode() &&
                this.order.equals(given.order) &&
                this.data.equals(given.getData()) &&
                this.left.sameTree(given.getLeftBranch()) &&
                this.right.sameTree(given.getRightBranch());
    }
    public ABST<T> getLeftBranch() {
        return this.left;
    }
    public ABST<T> getRightBranch() {
        return this.right;
    }
    public boolean sameData(ABST<T> given) {
        return this.getLeftmost().equals(given.getLeftmost()) &&
                this.getRight().sameData(given.getRight());
    }
    public IList<T> buildList(IList<T> given) {
        return this.getRight().buildList(new Cons<T>(this.getLeftmost(), given));
    }
    public boolean sameAsList(IList<T> given) {
        return this.getLeftmost().equals(given.getFirst()) &&
                this.getRight().sameAsList(given.getRest());
    }
    
}

interface IList<T> {
    boolean isSorted(IComparator<T> c);
    T getFirst();
    IList<T> getRest();
    boolean isConsList();
    boolean sameList(IList<T> given);
    ABST<T>  buildTree(ABST<T> given); 
}

class Empty<T> implements IList<T> {
    public  boolean isSorted(IComparator<T> c) {
        return true;
    }
    public  T getFirst() {
        return null;
    }
    public IList<T> getRest() {
        return this;
    }
    public boolean isConsList() {
        return false;
    }
    public boolean sameList(IList<T> given) {
        return !(given.isConsList());
    }
    public ABST<T> buildTree(ABST<T> given) {
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
    public  boolean isSorted(IComparator<T> c) {
        if (this.rest.isConsList()) {
            return c.compare(this.first, this.rest.getFirst()) < 0
                    && this.rest.isSorted(c);
        }
        else {
            return true;
        }
    }
    public T getFirst() {
        return this.first;
    }
    public IList<T> getRest() {
        return this.rest;
    }
    public boolean isConsList() {
        return true;
    }
    public boolean sameList(IList<T> given) {
        return this.first.equals(given.getFirst()) && 
                this.rest.sameList(given.getRest());
    }
    public ABST<T> buildTree(ABST<T> given) {
        return this.rest.buildTree(given.insert(first));
            
        
    }
    
}


class Book {
    String title;
    String author;
    int price;
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}

class Examples {
    Book a1 = new Book("a1", "alan", 35);
    Book b1 = new Book("b1", "bob", 40);
    Book c1 = new Book("c1", "clerk", 30);
    Book d1 = new Book("d1", "david", 20);
    Leaf<Book> a = new Leaf<Book>(new BooksByPrice());
    Node<Book> b = new Node<Book>(new BooksByPrice(), this.a1, this.a, this.a);
    Node<Book> c = new Node<Book>(new BooksByPrice(), this.b1, this.b, this.a);
    Node<Book> d = new Node<Book>(new BooksByPrice(), this.d1, this.a, this.a);
    Node<Book> e = new Node<Book>(new BooksByPrice(), this.c1, this.d, this.c);
    IList<Book> genmt = new Empty<Book>();
    IList<Book> gen1 = new Cons<Book>(b1, genmt);
    IList<Book> gen2 = new Cons<Book>(a1, gen1);
    IList<Book> gen3 = new Cons<Book>(c1, gen2);
    IList<Book> gen4 = new Cons<Book>(d1, gen3);
    boolean testinsert(Tester t) {
        return t.checkExpect(this.a.insert(a1), this.b) &&
                t.checkExpect(new Node<Book>(new BooksByPrice(), 
                        this.c1, this.a, this.c).insert(d1), this.e);
    }
    boolean testgetLeftmost(Tester t) {
        return t.checkExpect(this.e.getLeftmost(), this.d1) &&
                t.checkExpect(this.c.getLeftmost(), this.a1);
    }
    boolean testgetRight(Tester t) {
        return t.checkExpect(this.e.getRight(), new Node<Book>(new BooksByPrice(), 
                this.c1, this.a, this.c)) &&
                t.checkExpect(this.c.getRight(), new Node<Book>(new BooksByPrice(), 
                        this.b1, this.a, this.a));
    }
    boolean testsameTree(Tester t) {
        return t.checkExpect(this.a.sameTree(a), true) &&
                t.checkExpect(this.c.sameTree(c), true);
    }
    boolean testsameData(Tester t) {
        return t.checkExpect(this.e.sameData(e), true) &&
                t.checkExpect(this.b.sameData(c), false);
    }
    boolean testsameAsList(Tester t) {
        return t.checkExpect(this.e.sameAsList(gen4), true) &&
                t.checkExpect(this.c.sameAsList(gen3), false);
    }
    boolean testbuildTree(Tester t) {
        return t.checkExpect(new Cons<Book>(this.c1, new Cons<Book>(this.d1, 
                new Cons<Book>(this.b1, 
                    new Cons<Book>(this.a1, genmt)))).buildTree(a), this.e) &&
                t.checkExpect(this.genmt.buildTree(a), this.a);
    }
    boolean testbuildList(Tester t) {
        return t.checkExpect(this.c.buildList(genmt), new Cons<Book>(this.b1, 
                new Cons<Book>(this.a1, genmt))) &&
                t.checkExpect(this.c.buildList(gen1), new Cons<Book>(this.b1, 
                    new Cons<Book>(this.a1, gen1)));
    }
    boolean testisSorted(Tester t) {
        return t.checkExpect(this.gen1.isSorted(new BooksByTitle()), true) &&
                t.checkExpect(this.gen4.isSorted(new BooksByPrice()), true);
    }
    
    
}