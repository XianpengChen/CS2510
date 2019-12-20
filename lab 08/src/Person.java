import tester.*;
// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    // returns true if this Person has that as a direct buddy
    boolean hasDirectBuddy(Person that) {
        return this.buddies.contain(that);
    }

    // returns the number of people who will show up at the party 
    // given by this person
    int partyCount() {
        return new ConsLoBuddy(this, this.buddies.buddiesofbuddies()).delete().count();
    }

    // returns the number of people that are direct buddies 
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.inCommon(that.buddies);
    }
        

    // will the given person be invited to a party 
    // organized by this person?
    boolean hasExtendedBuddy(Person that) {
        return this.buddies.buddiesofbuddies().contain(that);
    }
    // EFFECT:
    // Change this person's buddy list so that it includes the given person
    void addBuddy(Person given) {
        this.buddies = new ConsLoBuddy(given, this.buddies);
    }
}

//represents a list of Person's buddies
interface ILoBuddy {
    // to determine if the person is in this list
    boolean contain(Person given);
    Person getFirst();
    // to determine how many person are in both list
    int inCommon(ILoBuddy given);
    //to delete all repeated person in the list, make sure there is no repeatation
    // in the list
    ILoBuddy delete();
    //to produce a list of persons that can be reached out the this list of person
    ILoBuddy buddiesofbuddies();
    // append two lists of persons
    ILoBuddy append( ILoBuddy given);
    //to determine how many person in this list;
    int count();
    //to determine if two lists contain completely same elements, order does not matter
    boolean same(ILoBuddy given);
    ILoBuddy getRest();
    ILoBuddy expand();
    
    

}

//represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
   
    public boolean contain(Person given) {
        return false;
    }
    public  Person getFirst() {
        return null;
    }
    public int inCommon(ILoBuddy given) {
        return 0;
    }
    public ILoBuddy delete() {
        return this;
    }
    public ILoBuddy buddiesofbuddies() {
        return this;
    }
    public  ILoBuddy append( ILoBuddy given) {
        return given;
    }
    public int count() {
        return 0;
    }
    public boolean same(ILoBuddy given) {
        return given instanceof MTLoBuddy;
    }
    public ILoBuddy getRest() {
        return this;
    }
    public ILoBuddy expand() {
        return this;
    }

 

}

//represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean contain(Person given) {
        if (this.first.equals(given)) {
            return true;
        }
        else {
            return this.rest.contain(given);
        }
    }
    public  Person getFirst() {
        return this.first;
    }
    public int inCommon(ILoBuddy given) {
        if (given.contain(first)) {
            return 1 + this.rest.inCommon(given);
        }
        else {
            return this.rest.inCommon(given);
        }
    }
    public ILoBuddy delete() {
        if (this.rest.contain(first)) {
            return this.rest.delete();
        }
        else {
            return new ConsLoBuddy(this.first, this.rest.delete());
        }
    }

    public ILoBuddy buddiesofbuddies() {
        ILoBuddy local = this.expand().append(this).delete();
        if (local.same(this)) {
            return local;
        }
        return local.buddiesofbuddies();
        
    }
      
   
    public  ILoBuddy append( ILoBuddy given) {
        return new ConsLoBuddy(this.first, this.rest.append(given));
    }
    public int count() {
        return 1 + this.rest.count();
    }
    public boolean same(ILoBuddy given) {
        return this.inCommon(given) == given.count() &&
                given.count() == this.count();
    }
    
    public ILoBuddy getRest() {
        
        return this.rest;
    }
    public ILoBuddy expand() {
        return this.first.buddies.append(rest.expand());
    }
}

//runs tests for the buddies problem
class ExamplesBuddies {
    Person ann = new Person("Ann");
    Person bob = new Person("Bob");
    Person cole = new Person("Cole");
    Person dan = new Person("Dan");
    Person ed = new Person("Ed");
    Person fay = new Person("Fay");
    Person gabi = new Person("Gabi");
    Person hank = new Person("Hank");
    Person jan = new Person("Jan");
    Person kim = new Person("Kim");
    Person len = new Person("Len");
    
    boolean testhasDirectBuddy(Tester t) {
        ann.addBuddy(bob);
        ann.addBuddy(cole);
        ann.addBuddy(dan);
        bob.addBuddy(cole);
        bob.addBuddy(ed);
        bob.addBuddy(dan);
        cole.addBuddy(ed);
        cole.addBuddy(bob);
        dan.addBuddy(bob);
        dan.addBuddy(ed);
        ed.addBuddy(cole);
        ed.addBuddy(dan);
        
        return t.checkExpect(bob.hasDirectBuddy(ann), false) &&
                t.checkExpect(ann.hasDirectBuddy(bob), true) &&
                t.checkExpect(ann.countCommonBuddies(bob), 2) &&
                t.checkExpect(dan.countCommonBuddies(ann), 1) &&
                t.checkExpect(ann.hasExtendedBuddy(ed), true) &&
                t.checkExpect(ed.hasExtendedBuddy(ann), false) &&
                t.checkExpect(ann.partyCount(), 5) &&
                t.checkExpect(bob.partyCount(), 4) &&
                t.checkExpect(ed.partyCount(), 4);
    }
}

