import tester.Tester;



class Deque<T> {
    Sentinel<T> header;
    
    Deque(Sentinel<T> header){
        this.header = header;
    }
    Deque() {
        this.header = new Sentinel<T>();
    }
    
    public int size() {
        return this.header.sizeHelp();
    }
    
    
    void add(T t, int index) {
        if (index == 0) {
            Node<T> n = new Node<T>(t, this.header.next, this.header);
        }
        else if (index == this.size() - 1) {
            Node<T> n = new Node<T>(t, this.header, this.header.prev);
        }
        else {
            this.header.next.addHelper(t, index - 1);
        }
    }
    void addAtHead(T t) {
        this.add(t, 0);
    }
    void addAtTail(T t) {
        this.add(t, this.size() - 1);
    }

    
    T remove(int index) {
        if (this.header == this.header.next){
            throw new RuntimeException ("no way");
        } 
        else  if (index == 0)
        { 
            T rem = ((Node<T>) this.header.next).data;
            this.header.next = this.header.next.next;
            this.header.next.prev = this.header;
            return rem;
        }
        else if (index == this.size() - 1) {
            T rem = ((Node<T>) this.header.prev).data;
            this.header.prev = this.header.prev.prev;
            this.header.prev.next = this.header;
            return rem;
        }
        else {
            return this.header.next.removeHelper(index - 1);
        }
    }
    
    T removeFromHead() {
        return this.remove(0);
    }
    T removeFromTail() {
        return this.remove(this.size() - 1);
    }
    
    ANode<T> find(IPred<T> p) {
        return this.header.findHelper(p, this.header);
    }
    
    void  removeNode(ANode<T> given) {
        this.header.removeNodeHelper(given, this.header);
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
    
    
    abstract void addHelper(T t, int index);
    abstract T removeHelper(int index);
    abstract T getT();

}

class Sentinel<T> extends ANode<T> {
    Sentinel() {
        
        this.next = this;
        this.prev = this;
    }
    Sentinel(ANode<T> next, ANode<T> prev) {
        super(next, prev);
    }
    
    int sizeHelp() {
        if (this.next instanceof Sentinel<?>) {
            return 0;
        }
        else {
            return 1 + new Sentinel<T>(this.next.next, this.prev).sizeHelp();
        }
    }
    
    void addHelper(T t, int index) {
        Node<T> n = new Node<T>(t, this, this);
    }

    T removeHelper(int index) {
        throw new RuntimeException ("no way");
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
    
    T getT() {
        
        return null;
    }
    void removeNodeHelper(ANode<T> given, ANode<T> acc) {
        if (this.next.equals(this)) {
            return;
        }
        else if (acc.next.equals(given)) { 
            acc.next = acc.next.next;  
        }
        else {
            acc = acc.next;
            this.removeNodeHelper(given, acc);
        }
    }
}

class Node<T> extends ANode<T> {
    T data;
    Node(T data) {
        super(null,null);
        this.data = data;
    }
    
    Node(T data, ANode<T> next, ANode<T> prev) {
        super(next,prev);
        if (this.next == null) {throw new IllegalArgumentException("no way");}
        if (this.prev == null) {throw new IllegalArgumentException("no way");}
        this.data = data;
        this.next.prev = this;
        this.prev.next = this;
    }
    
    
    T removeHelper(int index) {
        throw new RuntimeException ("no way");
    }

    void addHelper(T t, int index) {
        if (index == 0) {
            Node<T> n = new Node<T>(t, this.next, this);
        }
        else {
            this.next.addHelper(t, index - 1);
        }
    }

    @Override
    T getT() {
        
        return this.data;
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

class ExamplesDeque {
    Sentinel<String> first = new Sentinel<String>();
    Deque<String> deque1 = new Deque<String>(first);
    
    Node<String> abc = new Node<String>("abc");
    Node<String> bcd = new Node<String>("bcd", abc, abc);
    Node<String> def = new Node<String>("def");
    Node<String> cde = new Node<String>("cde", def, bcd);
    
    Sentinel<String> lettertring = new Sentinel<String>(abc, def);
    Deque<String> deque2 = new Deque<String>(lettertring);
    
    Node<Integer> abc1 = new Node<Integer>(1);
    Node<Integer> abc2 = new Node<Integer>(2, abc1, abc1);
    Node<Integer> abc4 = new Node<Integer>(4);
    Node<Integer> abc3 = new Node<Integer>(3, abc4, abc2);
    
    Sentinel<Integer> number = new Sentinel<Integer>(abc1, abc4);
    Deque<Integer> deque3 = new Deque<Integer>(number);
    
    EqualityString a1 = new EqualityString("abc");
    EqualityNumber a2 = new EqualityNumber(2);
    EqualityString a3 = new EqualityString("abc3");
    
    
    
    
    void initData() {
        this.deque1 = new Deque<String>(new Sentinel<String>());
        this.deque2 = new Deque<String>(lettertring);
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
                t.checkExpect(deque2.find(a3), lettertring);      
    }
    
    boolean testremoveFromHead(Tester t) {
        this.initData();
        return t.checkException(new RuntimeException("it is empty!"), deque1, "removeFromHead") &&
                t.checkExpect(deque2.removeFromHead(), abc) &&
                t.checkExpect(deque3.removeFromHead(), abc1) &&
                t.checkException(new RuntimeException("it is empty!"), deque1, "removeFromTail") &&
                t.checkExpect(deque2.removeFromTail(), def) &&
                t.checkExpect(deque3.removeFromTail(), abc4);
    }
  
}