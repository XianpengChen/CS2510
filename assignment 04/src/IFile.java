import tester.Tester;

// CS 2510 Fall 2016
// Assignment 4
// Chen Xianpeng
// chenxianp
// Khomiakov Kevin 
// khomiakovkevin
public interface IFile {
    // compute the size of this file
    int size();
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    int downloadTime(int rate);
    
    // is the owner of this file the same 
    // as the owner of the given file?
    boolean sameOwner(IFile that);
    //helper function of same owner, to 
    // get the owner of the file
    String getOwner();

}

abstract class AFile implements IFile {
    String name;
    String owner;
    AFile(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
    public abstract int size();
    public int downloadTime(int rate) {
        return this.size() / rate;
    }
    public String getOwner() {
        return this.owner;
    }
    public boolean sameOwner(IFile that) {
        return (this.owner.equals(that.getOwner()));
    }
}

class TextFile extends AFile {
    int length;
    TextFile(String name, String owner, int length) {
        super(name, owner);
        this.length = length;
    }
    public int size() {
        return this.length;
    }
}

class ImageFile extends AFile {
    int width;
    int height;
    ImageFile(String name, String owner, int width, int height) {
        super(name, owner);
        this.width = width;
        this.height = height;
    }
    public int size() {
        return this.height * this.width; 
    }
}

class AudioFile extends AFile {
    int speed;   
    int length;
    AudioFile(String name, String owner, int speed, int length) {
        super(name, owner);
        this.speed = speed;
        this.length = length;
    }
    public int size() {
        return this.speed * this.length;
    }
}


class ExamplesFiles {
    
    IFile text1 = new TextFile("English paper", "Maria", 1234);
    IFile picture = new ImageFile("Beach", "Maria", 400, 200);
    IFile song = new AudioFile("Help", "Pat", 200, 120);
    IFile text2 = new TextFile("English essay", "Job", 1500);
    IFile picture2 = new ImageFile("sunshine", "Peter", 200, 200);
    IFile song1 = new AudioFile("wish", "Back", 200, 240);
    
    
    // test the method size for the classes that represent files
    boolean testSize(Tester t) {
        return
            t.checkExpect(this.text1.size(), 1234) &&
            t.checkExpect(this.picture.size(), 80000) &&
            t.checkExpect(this.song.size(), 24000) &&
            t.checkExpect(this.text2.size(), 1500) &&
            t.checkExpect(this.picture2.size(), 40000) &&
            t.checkExpect(this.song1.size(), 48000);
    }
    boolean testdownloadTime(Tester t) {
        return t.checkExpect(this.text2.downloadTime(100), 15) &&
               t.checkExpect(this.picture2.downloadTime(2000), 20) &&
               t.checkExpect(this.song1.downloadTime(1000), 48);
    }
    boolean testtsameOwner(Tester t) {
        return t.checkExpect(this.text1.sameOwner(this.picture), true) &&
               t.checkExpect(this.song.sameOwner(this.picture2), false) &&
               t.checkExpect(this.text1.sameOwner(this.text2), false);
    }
   
}
