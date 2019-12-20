import tester.*;

interface IBook {
    int daysOverdue(int today);
    boolean isOverdue(int givenDay);
    int computeFine(int givenDay);

}

abstract class ABook implements IBook {
    String title;
    int dayTaken;
    ABook(String title, int dayTaken) {
        this.title = title;
        this.dayTaken = dayTaken;
    }
    public abstract int daysOverdue(int today);
    public boolean isOverdue(int givenDay) {
        return (this.daysOverdue(givenDay) > 0);
    }
    public abstract int computeFine(int givenDay);
    
}

class Book extends ABook {
    String author;
    Book(String title, String author, int dayTaken) {
        super(title, dayTaken);
        this.author = author;
    }
    public int daysOverdue(int today) {
        return today - this.dayTaken - 14;
    }
    public int computeFine(int givenDay) {
        if (this.daysOverdue(givenDay) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(givenDay) * 10;
        }
    }
}

class RefBook extends ABook {
 
    RefBook(String title, int dayTaken) {
        super(title, dayTaken);
    }
    public int daysOverdue(int today) {
        return today - this.dayTaken - 2;
    }
   
    public int computeFine(int givenDay) {
        if (this.daysOverdue(givenDay) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(givenDay) * 10;
        }
    }
}

class AudioBook extends ABook {
    String author;
    AudioBook(String title, String author, int dayTaken) {
        super(title, dayTaken);
        this.author = author;
    }
    public int daysOverdue(int today) {
        return today - this.dayTaken - 14;
    }
    
    public int computeFine(int givenDay) {
        if (this.daysOverdue(givenDay) <= 0) {
            return 0;
        }
        else {
            return this.daysOverdue(givenDay) * 20;
        }
    }
}

class ExamplesIBook {
    IBook a = new Book("intro", "a", 3456);
    IBook b = new RefBook("integrate", 3456);
    IBook c = new AudioBook("advance", "c", 3456);
    boolean testdaysOverdue(Tester t) {
        return t.checkExpect(this.a.daysOverdue(3466), -4) &&
               t.checkExpect(this.b.daysOverdue(3458), 0) &&
               t.checkExpect(this.c.daysOverdue(3472), 2);
    }
    boolean testisOverdue(Tester t) {
        return t.checkExpect(this.a.isOverdue(3466), false) &&
               t.checkExpect(this.b.isOverdue(3458), false) &&
               t.checkExpect(this.c.isOverdue(3472), true);
    }
    boolean testcomputeFine(Tester t) {
        return t.checkExpect(this.a.computeFine(3466), 0) &&
               t.checkExpect(this.b.computeFine(3458), 0) &&
               t.checkExpect(this.c.computeFine(3472), 40);
    }
}






