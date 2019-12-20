import tester.*;
/*
           Jen(captain)
         /             \
        May            Bea
     /      \        /     \
   Kim     Pat     Ann     Joy
  /   \   /   \   /   \   /   \
 Tay Zoe Meg Lou Cam Eve Tam  EMPTY
 */

// to represent a phone chain
interface IPhoneChain {
   int CountPlayers();
   int CountPlayersCalled();
   boolean WillCall(String name);
   IPhoneChain LongestChain();
}

// to represent a player on a team
class Player {
    String name;
    String phoneNumber;
    
    Player(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

// the end of the phone chain
class Empty implements IPhoneChain{
    Empty() { }
    public int CountPlayers() {
        return 0;
    }
    public int CountPlayersCalled() {
        return 0;
    }
    public boolean WillCall(String name) {
        return false;   
    }
    public IPhoneChain LongestChain() {
        return this;
    }
}

// to represent a link in a phone chain
// a player and those she has to call
class Link implements IPhoneChain {
    Player self;
    IPhoneChain call1;
    IPhoneChain call2;
    
    Link(Player self, IPhoneChain call1, IPhoneChain call2) {
        this.self = self;
        this.call1 = call1;
        this.call2 = call2;
    }
    /* 
    TEMPLATE
    Fields: 
    ... this.self    -- Player
    ... this.call1  -- IPhoneChain
    ... this.call2   --IPhoneChain
    
    Methods:
    ... this.countPlayers() ...  --int
    ... this.countPlayersCalled() ...  -- int
    ... this.willCall(String) ...  --boolean
    ... this.longestChain() ...  --IPhoneChain
    
    Methods for Fields:
    ... this.call1.countPlayers() ... -- int
    ... this.call2.countPlayers() ... --int
    ... this.countPlayers() ... --int
    ... this.self.equals(String) ... --boolean
    ... this.call1.willCall(String) ... --boolean
    ... this.call2.willCall(String) ... --boolean
    
    */
    public int CountPlayers() {
        return 1 + this.call1.CountPlayers() + 
                this.call2.CountPlayers();
        }
    public int CountPlayersCalled() {
        return this.CountPlayers();     
    }
    public boolean WillCall(String name) {
         if (this.self.equals(name))
             return true;
         else {return (this.call2.WillCall(name) ||
        (this.call1.WillCall(name)));
        }         
    }
    public IPhoneChain LongestChain() {
        return this;
    }
}



// to represent examples of phone chains
class ExamplesPhoneChain {
    ExamplesPhoneChain() { }
    
    /*       Jen(captain)
            /             \
           May            Bea
        /      \        /     \
       Kim     Pat     Ann     Joy
      /   \   /   \   /   \   /   \
     Tay Zoe Meg Lou Cam Eve Tam  EMPTY
     */  
    
    IPhoneChain empty = new Empty();
    Player tay = new Player("Tay", "2345");
    Player zoe = new Player("Tay", "3456");
    Player meg = new Player("Tay", "4567");
    Player lou = new Player("Tay", "5678");
    Player cam = new Player("Tay", "6789");
    Player eve = new Player("Tay", "7890");
    Player tam = new Player("Tay", "2233");
    
    IPhoneChain tayLink = new Link(this.tay, this.empty, this.empty);
    IPhoneChain zoeLink = new Link(this.zoe, this.empty, this.empty);
    IPhoneChain megLink = new Link(this.meg, this.empty, this.empty);
    IPhoneChain louLink = new Link(this.lou, this.empty, this.empty);
    IPhoneChain camLink = new Link(this.cam, this.empty, this.empty);
    IPhoneChain eveLink = new Link(this.eve, this.empty, this.empty);
    IPhoneChain tamLink = new Link(this.tam, this.empty, this.empty);
    
    Player kim = new Player("Kim", "3344");
    IPhoneChain kimLink = new Link(this.kim, this.tayLink, this.zoeLink);
    Player pat = new Player("Pat", "4455");
    IPhoneChain patLink = new Link(this.pat, this.megLink, this.louLink);
    Player ann = new Player("Ann", "5566");
    IPhoneChain annLink = new Link(this.ann, this.camLink, this.eveLink);
    Player joy = new Player("Joy", "6677");
    IPhoneChain joyLink = new Link(this.joy, this.tamLink, this.empty);
    
    Player may = new Player("May", "7788");
    IPhoneChain mayLink = new Link(this.may, this.kimLink, this.patLink);
    Player bea = new Player("Bea", "8899");
    IPhoneChain beaLink = new Link(this.bea, this.annLink, this.joyLink);
    
    Player jen = new Player("Jen", "9900");
    IPhoneChain jenLink = new Link(this.jen, this.mayLink, this.beaLink);
    
    boolean testCountPlayers(Tester t) {
        return 
        t.checkExpect(this.beaLink.CountPlayers(), 6);
        }
    boolean testCountPlayersCalled(Tester t) {
        return 
        t.checkExpect(this.beaLink.CountPlayersCalled(), 6);
        }
    boolean testWillCall(Tester t) {
        return
        t.checkExpect(this.kimLink.WillCall("Bea"), false);
        }
}