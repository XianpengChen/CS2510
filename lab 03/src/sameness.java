import tester.*;
public interface sameness {

}
interface IShape {
    boolean sameShape(IShape that);
    boolean sameCircle(IShape that);
    boolean sameRect(IShape that);
    boolean sameSquare(IShape that);
}

class Circle implements IShape {
    int x;
    int y;
    int radius;
    public boolean sameCircle(Circle that) {
        return this.x == that.x && this.y == that.y &&
                this.radius == that.radius;
    }
    public boolean same(IShape that) {
        return that.sameCircle(this);
    }
    public boolean sameShape(IShape that) {
        return that.sameCircle(this);
    }
    public   boolean sameRect(IShape that) {
        return false;
    }
    public   boolean sameSquare(IShape that) {
        return false;
    }
    public boolean sameCircle(IShape that) {
        return false;
    }
}

interface IAT {
    boolean sameIAT(IAT that);
    boolean samePerson(Person that);
    boolean sameUnknown(Unknown that);
}

class Person implements IAT {
    String name;
    IAT dad, mom;
    Person(String name, IAT dad, IAT mom) {
        this.name = name;
        this.dad = dad;
        this.mom = mom;
        
    }
    public boolean samePerson(Person that) {
        return this.name.equals(that.name) &&
                this.dad.sameIAT(that.dad) &&
                this.mom.sameIAT(that.mom);
                
    }
    public boolean sameUnknown(Unknown that) {
        return false;
    }
    public boolean sameIAT(IAT that) {
        return that.samePerson(this);
    }
}

class Unknown implements IAT {
    
    Unknown() {
        
    }
    
    public boolean sameUnknown(Unknown that) {
        return true;
    }
    public boolean samePerson(Person that) {
        return false;
    }
    public boolean sameIAT(IAT that) {
        return that.sameUnknown(this);
    }
}

// double dispatch
