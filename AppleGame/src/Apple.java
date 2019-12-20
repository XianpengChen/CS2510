import java.awt.Color;
import java.util.Random;

import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;


//Assignment 5
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin

class Apple {
    Posn center;
    Apple(Posn center) {
        this.center = center;
    }
    Apple  moveDown(int n) {
        return new Apple(new Posn(this.center.x, this.center.y + n));
    } 
    boolean onTheGround() {
        return this.center.y >= 400;
    }
    Apple fall(Basket given) {
        if (this.onTheGround()) {
            return new Apple(new Posn(30 + new Random().nextInt(401 - 30), 30));
        }
        else if (this.getCaught(given)) {
            return new Apple(new Posn(30 + new Random().nextInt(401 - 30), 30));
        }
        else {
            return this.moveDown(2);
        }
    }
    boolean getCaught(Basket given) {
        return (this.center.x < (given.center.x + 38)) &&
                (this.center.x > (given.center.x - 38)) &&
                (this.center.y > (given.center.y - 20)) &&
                (this.center.y < (given.center.y + 20));
    }
    WorldImage drawApple() {
        return new FromFileImage("small-red-apple.png");
    }
}


class Basket {
    Posn center;
    Basket(Posn center) {
        this.center = center;
    }
    Basket  moveOnKey(String ke) {
        if ( ke.equals("left") && (this.center.x > 39)) {
            
            return new Basket(new Posn(this.center.x - 5, this.center.y));
        }
        else if (ke.equals("right") && (this.center.x < 361)) {
            return new Basket(new Posn(this.center.x + 5, this.center.y));
        }
        else {
            return this;
        }
    }
}

interface ILoApple {
    boolean caughtApple(Basket given);
    Apple getFirst();
    ILoApple getRest();
    ILoApple fallList(Basket given);
    int caughtMany(Basket given);
    
    WorldScene draw(WorldImage given);
    
    
}

class MtLoApple implements ILoApple {
    public boolean caughtApple(Basket given) {
        return false;
    }
    public Apple getFirst() {
        return null;
    }
    public ILoApple getRest() {
        return this;
    }
    public ILoApple fallList(Basket given) {
        return this;
    }
    public int caughtMany(Basket given) {
        return 0;
    }
    public  WorldScene draw(WorldImage given) {
        return new WorldScene(400, 400).placeImageXY(given, 200, 200);
    }
    
    
}


class ConsLoApple implements ILoApple {
    Apple first;
    ILoApple rest;
    
    ConsLoApple(Apple first, ILoApple rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean caughtApple(Basket given) {
        if (this.first.getCaught(given)) {
            return true;
        }
        else {
            return this.rest.caughtApple(given);
        }
        
    }
    public Apple getFirst() {
        return this.first;
    }
    public ILoApple getRest() {
        return this.rest;
    }
    public ILoApple fallList(Basket given) {
        return new ConsLoApple(this.first.fall(given), this.rest.fallList(given));
    }
    public int caughtMany(Basket given) {
        if (this.first.getCaught(given)) {
            return 1 + this.rest.caughtMany(given);
        }
        else {
            return this.rest.caughtMany(given);
        }
    }
    public  WorldScene draw(WorldImage given) {
        return this.rest.draw(given).placeImageXY(this.first.drawApple(), 
                this.first.center.x, this.first.center.y);
    }
    
}

class AppleGame extends World {
    int width = 400;
    int height = 400;
    ILoApple listapple;
    Basket basket;
    int caught;
    WorldImage background = new FromFileImage("apple-tree.png");
    AppleGame(ILoApple listapple, Basket basket, int caught) {
        super();
        this.listapple = listapple;
        this.basket = basket;
        this.caught = caught;
    }
    public int getNmuber() {
        return this.caught;
    }
    
    public World onKeyEvent(String ke) {
        return new AppleGame(this.listapple, this.basket.moveOnKey(ke), this.caught);
        
    }
    
    public World onTick() {
        
        return new AppleGame(this.listapple.fallList(this.basket), 
                this.basket, this.caught + this.listapple.caughtMany(this.basket));
        
        
    }
    
    public WorldScene makeScene() {
        return this.listapple.draw(background).placeImageXY(new FromFileImage("basket.png"), 
                this.basket.center.x, this.basket.center.y);
        
    }
    public WorldScene lastScene(String s) {
        return new WorldScene(this.width, this.height).placeImageXY(new TextImage(s, 
                30, FontStyle.BOLD, Color.BLACK), this.width / 2, this.height / 2);
        
    }
    public WorldEnd worldEnds() {
        if (this.caught >= 10) {
            return new WorldEnd(true, this.lastScene("congratulation!"));
        }
        else {
            return new WorldEnd(false, this.makeScene());
        }
    }
    
}

class ExamplesAppleGame {
    Apple a = new Apple(new Posn(20, 40));
    Apple b = new Apple(new Posn(40, 60));
    Apple c = new Apple(new Posn(80, 40));
    Basket d = new Basket(new Posn(200, 390));
    Basket e = new Basket(new Posn(200, 390));
    Basket f = new Basket(new Posn(200, 395));
    ILoApple h = new ConsLoApple(this.b, new MtLoApple());
    ILoApple i = new ConsLoApple(this.c, this.h);
    AppleGame g = new AppleGame(new ConsLoApple(this.a, this.i), new Basket(new Posn(200, 370)), 0);
    boolean testmoveDown(Tester t) {
        return t.checkExpect(this.a.moveDown(5),  new Apple(new Posn(20, 45))) &&
                t.checkExpect(this.b.moveDown(10), new Apple(new Posn(40, 70))) &&
                t.checkExpect(this.c.moveDown(10), new Apple(new Posn(80, 50)));
    }
    boolean testonTheGround(Tester t) {
        return t.checkExpect(this.a.onTheGround(), false) &&
                t.checkExpect(new Apple(new Posn(40, 402)).onTheGround(), true);
    }
    boolean testfall(Tester t) {
        return t.checkExpect(this.a.fall(this.f), new Apple(new Posn(20, 42))) &&
                t.checkRange(new Apple(new Posn(40, 395)).fall(this.e).center.x, 0, 401);
    }
    boolean testgetCaught(Tester t) {
        return t.checkExpect(this.a.getCaught(e), false) &&
                t.checkExpect(new Apple(new Posn(200, 395)).getCaught(f), true);
    }
    boolean testmoveOnKey(Tester t) {
        return t.checkExpect(this.d.moveOnKey("right"), 
                new Basket(new Posn(205, 390))) &&
                t.checkExpect(this.e.moveOnKey("left"), 
                        new Basket(new Posn(195, 390))) &&
                t.checkExpect(this.f.moveOnKey("up"), this.f);
    }
    boolean testcaughtApple(Tester t) {
        return t.checkExpect(new ConsLoApple(this.a, this.i).caughtApple(e), false) &&
                t.checkExpect(this.h.caughtApple(new Basket(new Posn(40, 60))), true);
    }
    boolean testfallList(Tester t) {
        return t.checkExpect(new MtLoApple().fallList(e), new MtLoApple()) &&
                t.checkExpect(this.h.fallList(e), new ConsLoApple(new Apple(new Posn(40, 62)), 
                        new MtLoApple())) &&
                t.checkExpect(this.h.fallList(new Basket(new Posn(40, 60)))
                        .getFirst().center.y, 30);
    }
    boolean testcaughtMany(Tester t) {
        return t.checkExpect(this.h.caughtMany(new Basket(new Posn(40, 60))), 1) &&
                t.checkExpect(this.i.caughtMany(f), 0);
    }
    boolean testonKeyEvent(Tester t) {
        return t.checkExpect(this.g.onKeyEvent("left"), new AppleGame(this.g.listapple,
                new Basket(new Posn(195, 370)), this.g.caught)) &&
                t.checkExpect(this.g.onKeyEvent("right"), new AppleGame(this.g.listapple,
                        new Basket(new Posn(205, 370)), this.g.caught)) &&
                t.checkExpect(this.g.onKeyEvent("up"), new AppleGame(this.g.listapple,
                        this.g.basket, this.g.caught));
    }
    boolean testonTick(Tester t) {
        return t.checkExpect(this.g.onTick(), 
                new AppleGame(new ConsLoApple(new Apple(new Posn(20, 42)), 
                    new ConsLoApple(new Apple(new Posn(80, 42)),
                        new ConsLoApple(new Apple(new Posn(40, 62)), 
                            new MtLoApple()))), this.g.basket, this.g.caught)) &&
                t.checkExpect(new AppleGame(new MtLoApple(), this.e, 0).onTick(), 
                        new AppleGame(new MtLoApple(), this.e, 0));
    }
    boolean testmakeScene(Tester t) {
        return t.checkExpect(new AppleGame(new MtLoApple(), this.d, 0).makeScene(), 
                new MtLoApple().draw(new FromFileImage("apple-tree.png"))
                    .placeImageXY(new FromFileImage("basket.png"), 200, 390)) &&
                t.checkExpect(new AppleGame(this.h, this.d, 0).makeScene(), 
                        this.h.draw(new FromFileImage("apple-tree.png"))
                        .placeImageXY(new FromFileImage("basket.png"), 200, 390));
    }
    boolean testlastScene(Tester t) {
        return t.checkExpect(this.g.lastScene("a"), new WorldScene(400, 400)
                .placeImageXY(new TextImage("a", 30, FontStyle.BOLD, Color.BLACK), 200, 200)) &&
               t.checkExpect(this.g.lastScene("b"), new WorldScene(400, 400)
                .placeImageXY(new TextImage("b", 30, FontStyle.BOLD, Color.BLACK), 200, 200));
    }
    boolean testworldEnds(Tester t) {
        return t.checkExpect(this.g.worldEnds(), new WorldEnd(false, this.g.makeScene())) &&
                t.checkExpect(new AppleGame(new MtLoApple(), this.d, 10).worldEnds(), 
                        new WorldEnd(true, this.g.lastScene("congratulation!")));
    }   
    //boolean runAnimation = this.g.bigBang(400, 400, 0.04); 
}
