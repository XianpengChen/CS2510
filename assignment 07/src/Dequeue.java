import tester.*;
class Deque<T> {
    Sentinel<T> header;
    Deque(Sentinel<T> header) {
        this.header = header;
    }
    Deque() {
        new Sentinel<T>();
    }
    
    int size() {
        return this.header.size();
    }
    void addAtHead(T given) {
        if (this.header.equals(given)) {
            return;
        }
        else {
            this.header.addAtHead(given);
        }
        
    }
    void addAtTail(T given) {
        this.header.addAtTail(given);
        
    }
    T removeFromHead() {
        return this.header.removeFromHead();
        
    }
    T removeFromTail() {
        return this.header.removeFromTail();
        
    }
    ANode<T> find(IPred<T> p) {
        
        return this.header.findHelper(p, this.header);
        
    }
    void removeNode(ANode<T> given) {
        if (this.header.equals(given)) {
            return;
        }
        this.header.removeNode(given, this.header);
    }
}

//Represents a boolean-valued question over values of type T
interface IPred<T> {
    boolean apply(T t);
}

//to determine if two strings are same
class EqualityString implements IPred<String> {
    String str;
    EqualityString(String str) {
        this.str = str;
    }
    public boolean apply(String t) {
        return this.str.equals(t);
    }
}

class EqualityNumber implements IPred<Integer> {
    int number;
    EqualityNumber(int number) {
        this.number = number;
    }
    public boolean apply(Integer t) {
        return this.number == t;
    }
}

abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;
    ANode(ANode<T> next, ANode<T> prev) {
        this.next = next;
        this.prev = prev;
    }
    ANode() {
        
    }
    
    abstract T getT();
    
    
}

class Sentinel<T> extends ANode<T> {
    
    
    Sentinel() {
       
        this.next = this;
        this.prev = this; 
    }  
    
    public T getT() {
        return null;
    }
    int size() {
        if (this.next.equals(this) &&
                this.prev.equals(this))
        {
            return 0;
        }
        else if (this.next.equals(this.prev)) {
            return 1;   
        }
        else {
            this.next = this.next.next;
            return this.size() + 1;
        }
        
    }
    void addAtHead(T given) {
        new Node<T>(this.next, this, given);
    }
    void addAtTail(T given) {
        new Node<T>(this, this.prev, given);
    }
    T removeFromHead() {
        if (this.next.equals(this)) {
            throw new RuntimeException("it is empty!");
        }
        else {
            return this.next.getT();
        }
        
    }
    T removeFromTail() {
        if (this.prev.equals(this)) {
            throw new RuntimeException("it is empty!"); 
        }
        else {
            return this.prev.getT();
        }
        
    }
    ANode<T> findHelper(IPred<T> p, ANode<T> acc) {
        if (acc.next.equals(this)) {
            return this;
        }
        else if (p.apply((T)acc.next.getT())) {
            return acc.next;
        }
        else {
            
            return this.findHelper(p, acc.next);
        }
        
    }
    void removeNode(ANode<T> given, ANode<T> acc) {
        if (this.next.equals(this)) {
            return;
        }
        else if (acc.next.equals(given)) { 
            acc.next = acc.next.next;  
        }
        else {
           acc = acc.next;
            this.removeNode(given, acc);
        }
    }
   
}

class Node<T> extends ANode<T> {
    T data;
    Node(T data) {
        
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    
    Node(ANode<T> next, ANode<T> prev, T data) {
        super(next, prev);
        this.data = data;
        
        next.prev = this;
        prev.next = this;
      
        if (this.next == null || this.prev == null) {
            throw new IllegalArgumentException("either of the given nodes is null.");
        }
        
    }
    public T getT() {
        return this.data;
    }
    
}


class ExamplesDeque {
    Sentinel<String> first = new Sentinel<String>();
    Deque<String> deque1 = new Deque<String>(first);
    
    Sentinel<String> letterString = new Sentinel<String>();
    
    Node<String> bcd = new Node<String>("bcd");
    Node<String> abc = new Node<String>(bcd, letterString, "abc");
    Node<String> cde = new Node<String>(bcd, bcd, "cde");
    Node<String> def = new Node<String>(letterString, cde, "def");
    
    Deque<String> deque2 = new Deque<String>(letterString);
    
    Sentinel<Integer> number = new Sentinel<Integer>();
    Node<Integer> abc2 = new Node<Integer>(2);
    Node<Integer> abc1 = new Node<Integer>(abc2, number, 1);
    Node<Integer> abc3 = new Node<Integer>(abc2, abc2, 3);
    Node<Integer> abc4 = new Node<Integer>(number, abc3, 4);
   
    
    
    Deque<Integer> deque3 = new Deque<Integer>(number);
    
    EqualityString a1 = new EqualityString("abc");
    EqualityNumber a2 = new EqualityNumber(2);
    EqualityString a3 = new EqualityString("abc3");
    
    
    
    
    void initData() {
        this.deque1 = new Deque<String>(new Sentinel<String>());
        this.deque2 = new Deque<String>(letterString);
        this.deque3 = new Deque<Integer>(number);
    }
    
    void testaddAtHead(Tester t) {
        this.initData();
        
        
        deque3.addAtHead(5);
        t.checkExpect(deque3.size(), 5);
        deque2.addAtHead("b");
        t.checkExpect(deque2.size(), 5);
        deque1.addAtHead("c");
        t.checkExpect(deque1.size(), 1);
        deque1.addAtTail("c");
        t.checkExpect(deque1.size(), 2);
        deque2.addAtTail("b");
        t.checkExpect(deque2.size(), 6);
        deque3.addAtTail(5);
        t.checkExpect(deque3.size(), 6);
        deque3.removeNode(abc1);
        t.checkExpect(deque3.size(), 5);
        deque2.removeNode(abc);
        t.checkExpect(deque2.size(), 5);
    }
    
    boolean testfind(Tester t) {
        this.initData();
        
        return t.checkExpect(deque1.find(a1), first) &&
                t.checkExpect(deque2.find(a1), abc) &&
                t.checkExpect(deque3.find(a2), abc2) &&
                t.checkExpect(deque2.find(a3), letterString);      
    }
    
    boolean testremoveFromHead(Tester t) {
        this.initData();
        return t.checkException(new RuntimeException("it is empty!"), deque1, "removeFromHead") &&
                t.checkExpect(deque2.removeFromHead(), "abc") &&
                t.checkExpect(deque3.removeFromHead(), 1) &&
                t.checkException(new RuntimeException("it is empty!"), deque1, "removeFromTail") &&
                t.checkExpect(deque2.removeFromTail(), "def") &&
                t.checkExpect(deque3.removeFromTail(), 4);
    }
  
}
