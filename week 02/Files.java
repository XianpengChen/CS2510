// CS 2510 Fall 2016
// Assignment 4
// Chen Xianpeng
// chenxianp
// Khomiakov Kevin 
// khomiakovkevin
import tester.*;

// to represent different files in a computer
interface IFile {
    
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

// to represent a text file
class TextFile implements IFile {
    String name;
    String owner;
    int length;   // in bytes
    
    TextFile(String name, String owner, int length) {
        this.name = name;
        this.owner = owner;
        this.length = length;
    }
    
    // compute the size of this file
    public int size() {
        return this.length;
    }  
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    public int downloadTime(int rate) {
        return this.length / rate;
    }
    public String getOwner() {
        return this.owner;
    }
    
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that) {
        return (this.owner == that.getOwner());
    }
}

//to represent an image file
class ImageFile implements IFile { 
    String name;
    String owner;
    int width;   // in pixels
    int height;  // in pixels
    
    ImageFile(String name, String owner, int width, int height) {
        this.name = name;
        this.owner = owner;
        this.width = width;
        this.height = height;
    }
    
    // compute the size of this file
    public int size() {
        return this.width * this.height;
    }  
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    public int downloadTime(int rate) {
        return this.size() / rate;
    }
    public String getOwner() {
        return this.owner;
    }
    
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that) {
        return (this.owner == that.getOwner());
    }
}


//to represent an audio file
class AudioFile implements IFile {
    String name;
    String owner;
    int speed;   // in bytes per second
    int length;  // in seconds of recording time
    
    AudioFile(String name, String owner, int speed, int length) {
        this.name = name;
        this.owner = owner;
        this.speed = speed;
        this.length = length;
    }
    
    // compute the size of this file
    public int size() {
        return this.speed * this.length;
    }  
    
    // compute the time (in seconds) to download this file
    // at the given download rate
    public int downloadTime(int rate) {
        return this.size() / rate;
    }
    public String getOwner() {
        return this.owner;
    }
    
    // is the owner of this file the same 
    // as the owner of the given file?
    public boolean sameOwner(IFile that) {
        return (this.owner == that.getOwner());
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
