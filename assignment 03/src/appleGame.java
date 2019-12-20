import java.awt.Color;
import java.util.Random;

import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;

interface ILoApple {
    ILoApple insertApple();
    int centerX();
    int centerY();
    ILoApple moveDownApples();
}

class MtLoApple implements ILoApple {
    public ILoApple insertApple() {
        return this;
    }

    public int centerX() {
        return 0;
    }

    public int centerY() {
        return 0;
    }
    public ILoApple moveDownApples() {
        return this;
    }
}

class ConsLoApple implements ILoApple {
    Apple first;
    ILoApple rest;
    
    ConsLoApple(Apple first, ILoApple rest) {
    this.first = first;
    this.rest = rest;
    }
    
    public ILoApple insertApple() {
        if (this.first.center.y == 200) {
            return new ConsLoApple(new Apple (new Posn (new Random().nextInt(390),
                    380)), new ConsLoApple(this.first, this.rest));
        } else return new MtLoApple();
    }
    public int centerX() {
        return this.first.center.x;
    }
    public int centerY() {
        return this.first.center.y;
    }
    public ILoApple moveDownApples() {
        return new ConsLoApple(this.first.moveDown(), this.rest.moveDownApples());
    }
}

class Apple {
    Posn center;
    
    Apple(Posn center) {
        this.center = center;
    }
    WorldImage RedApple() {
        return new FromFileImage("red-apple.png");
    }
    
    public Apple moveDown() {
        return new Apple(new Posn(this.center.x, this.center.y + 5));
    }
    
    public boolean onTheGround() {
     return this.center.y == 365;
    }
    
    Apple fall() {
        if (this.onTheGround()) {
            return new Apple(new Posn(this.center.x + new Random().nextInt(399 - this.center.x), 
                    this.center.y));
        }
        else {
            return this.moveDown();
        }
    }
}


class Basket {
    
    int width;
    int length;
    Posn center;
    Color col;
    
    /** The constructor */
    Basket(int width, int length, Posn center, Color col) {
        this.width = width;
        this.length = length;
        this.center = center;
        this.col = col;
    }
    
    /** produce the image of this Basket */
    WorldImage BasketImage() {
        return new RectangleImage(this.width, this.length, OutlineMode.OUTLINE, this.col);
    }
    public Basket moveOnKey(String ke) {
        if (ke.equals("right")) {
            return new Basket(this.width, this.length, new Posn(this.center.x + 5, this.center.y),
                     this.col);
        } else if (ke.equals("left")) {
            return new Basket(this.width,this.length, new Posn(this.center.x - 5, this.center.y),
                     this.col);
        } else return this;
        }
        
}

class AppleGame extends World {
    int width = 400;
    int height = 400;
    Basket basket;
    ILoApple apple;
    int caught;
    WorldImage background = new FromFileImage ("apple-tree.png");
    
    AppleGame(int width, int height, Basket basket, ILoApple apple, int caught) {
        super();
        this.basket = basket;
        this.apple = apple;
        this.caught = caught;
    }
    
    public World onTick() {
        return new AppleGame(this.width, this.height,
                this.basket, this.apple.moveDownApples(), this.caught);
    }
    
    public boolean caughtApple() {
        return (this.apple.centerX() < (this.basket.center.x + (this.basket.width / 2))) &&
                (this.apple.centerX() > (this.basket.center.x - (this.basket.width / 2))) &&
                (this.apple.centerY() > (this.basket.center.y - (this.basket.length / 2))) &&
                (this.apple.centerY() < (this.basket.center.y + (this.basket.length / 2)));
                
    }

    public World onKeyEvent(String ke) {
        if (ke.equals("x"))
            return this.endOfWorld("Goodbye");
        else
            return new AppleGame(this.basket.moveOnKey(ke), this.apple.moveDown());
    }
    
    public WorldEnd worldEnds() {
        if (this.apple.onTheGround()) {
            return new WorldEnd(true, this.makeScene().placeImageXY(
                    new TextImage("Game Over", 13, FontStyle.BOLD_ITALIC, Color.red),
                    100, 40));
        } else {
            return new WorldEnd(false, this.makeScene());
        }
    }

    public WorldScene makeScene() {
        return new WorldScene(width, height).placeImageXY(this.background, 200, 200);
    }
    
}






