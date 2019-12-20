import tester.*; 

/*
 *         +----------------------------------+
 *         |               ILoIF              |
 *         +----------------------------------+
 *         | boolean contains(ImageFile that) |
 *         +--------------+-------------------+
 *                       / \
 *                      +---+
 *                        |
 *          +-------------+-----------+
 *          |                         |
 *  +---------------+        +-------------------+
 *  |     MTLoIF    |        |     ConsLoIF      |
 *  +---------------+        +-------------------+
 *  +---------------+     +--| ImageFile  first  |
 *                        |  | ILoIF      rest   |
 *                        |  +-------------------+
 *                        v
 *               +---------------+
 *               | ImageFile     |
 *               +---------------+
 *               | String name   |
 *               | int    width  |
 *               | int    height |
 *               | String kind   |
 *               +---------------+
 */


//Represents an image file
class ImageFile {
 String name;
 int width;
 int height;
 String kind;

 ImageFile(String name, int width, int height, String kind) {
     this.name = name;
     this.width = width;
     this.height = height;
     this.kind = kind;
 }

/* Template:
  Fields:
  ... this.name ...     -- String
  ... this.width ...    -- int
  ... this.height ...   -- int
  ... this.kind ...     -- String
  
  Methods:
  ... this.size() ...                    -- int
  ... this.sameImageFile(ImageFile) ...  -- boolean
*/

 // Calculate the size of this image
 public int size(){
     return this.width * this.height;
 }

 // Is this image file the same as the given one?
 public boolean sameImageFile(ImageFile that) {
     return (this.name.equals(that.name) &&
             this.width == that.width &&
             this.height == that.height &&
             this.kind.equals(that.kind));
 }
}


//to represent a predicate for ImageFile-s
interface ISelectImageFile{

// Return true if the given ImageFile
// should be selected
boolean apply(ImageFile f);
}

/* Select image files smaller than 40000 */
class SmallImageFile implements ISelectImageFile {
 
  /* Return true if the given ImageFile is smaller than 40000 */
  public boolean apply(ImageFile f) {
    return f.height * f.width < 40000;
  }
}

class NameShorterThan4 implements ISelectImageFile {
    public boolean apply(ImageFile f) {
        return f.name.length() < 4;
    }
}

class GivenKind implements ISelectImageFile {
    String desired;
    GivenKind(String desired) {
        this.desired = desired;
    }
    public boolean apply(ImageFile f) {
        return this.desired.equals(f.kind);
        }
    }

interface IImageFileComparator {
    int compare(ImageFile a, ImageFile b);
}

class ImageFileSizeComparator implements IImageFileComparator {
    public int compare(ImageFile a, ImageFile b) {
        if (a.size() < b.size()) {
            return -1;
        }
        else if (a.size() == b.size()) {
            return 0;
        }
        else {
            return 1;
        }
    }
}

class ImageFileNameComparator implements IImageFileComparator {
    public int compare(ImageFile a, ImageFile b) {
       return a.name.compareTo(b.name);
    }
}



// Represents a list of ImageFiles
public interface ILoIF {

  // Does this list contain that ImageFile
  boolean contains(ImageFile that);
  ILoIF filter(ISelectImageFile p);
  boolean allNamesShorterThan4(ISelectImageFile p);
  boolean allSuchImageFile(ISelectImageFile p);
  boolean anySuchImageFile(ISelectImageFile p);
  ILoIF sort(IImageFileComparator c);
  ILoIF insert(IImageFileComparator c, ImageFile i);
  
}

//Represents an empty list of ImageFiles
class MtLoIF implements ILoIF {

 // Does this empty list contain that ImageFile   
 public boolean contains(ImageFile that) { 
     return false;
 } 
 public ILoIF filter(ISelectImageFile p) {
     return this;
 }
 public boolean allNamesShorterThan4(ISelectImageFile p) {
     return true;
 }
 public boolean allSuchImageFile(ISelectImageFile p) {
     return true;
 }
 public boolean anySuchImageFile(ISelectImageFile p) {
     return false;
 }
 public ILoIF sort(IImageFileComparator c) {
     return this;
 }
 public ILoIF insert(IImageFileComparator c, ImageFile i) {
     return new ConsLoIF(i, this);
 }
} 

//Represents a nonempty list of ImageFiles
class ConsLoIF implements ILoIF {
    ImageFile first;
    ILoIF rest;

    ConsLoIF(ImageFile first, ILoIF rest) {
        this.first=first;
        this.rest=rest;
    }

 // Does this nonempty list contain that ImageFile   
 public boolean contains(ImageFile that) { 
     return (this.first.sameImageFile(that) ||
             this.rest.contains(that));
 }
 public ILoIF filter(ISelectImageFile p) {
     if (p.apply(this.first)) {
         return new ConsLoIF(this.first, this.rest.filter(p));
     }
     else {
         return this.rest.filter(p);
     }
 }
 public boolean allNamesShorterThan4(ISelectImageFile p) {
     if (new NameShorterThan4().apply(this.first)) {
         return this.rest.allNamesShorterThan4(p);
     }
     else {
         return false;
     }
 }
 public  boolean allSuchImageFile(ISelectImageFile p) {
     if (p.apply(this.first)) {
         return this.rest.allSuchImageFile(p);
     }
     else {
         return false;
     }
 }
 public boolean anySuchImageFile(ISelectImageFile p) {
     if (p.apply(this.first)) {
         return true;
     }
     else {
         return this.rest.allSuchImageFile(p);
     }
 }
 public ILoIF sort(IImageFileComparator c) {
     return this.rest.sort(c).insert(c, this.first);
 }
 
 public ILoIF insert(IImageFileComparator c, ImageFile i) {
     if (c.compare(i, this.first) < 0) {
         return new ConsLoIF(i, this);
     }
     else {
         return new ConsLoIF(this.first, this.rest.insert(c, i));
     }
 }
}



//Test the use of function objects with lists of ImageFile-s
class ExamplesImageFile {

 public ExamplesImageFile() { }

 // Sample data to be used for all tests
 public ImageFile img1 = new ImageFile("dog", 300, 200, "jpg");
 public ImageFile img2 = new ImageFile("cat", 200, 200, "png");
 public ImageFile img3 = new ImageFile("bird", 250, 200, "jpg");
 public ImageFile img4 = new ImageFile("horse", 300, 300, "giff");
 public ImageFile img5 = new ImageFile("goat", 100, 200, "giff");
 public ImageFile img6 = new ImageFile("cow", 150, 200, "jpg");
 public ImageFile img7 = new ImageFile("snake", 200, 300, "jpg");
 
 

 //empty list
 public ILoIF mt= new MtLoIF();

 // ImageFile list -- all Images
 public ILoIF imglistall = 
     new ConsLoIF(this.img1, 
         new ConsLoIF(this.img2,
             new ConsLoIF(this.img3, 
                 new ConsLoIF(this.img4, 
                     new ConsLoIF(this.img5, 
                         new ConsLoIF(this.img6, 
                             new ConsLoIF(this.img7, this.mt))))))); 

 // ImageFile list - short names (less than 4 characters)
 public ILoIF imglistshortnames = 
     new ConsLoIF(this.img1, 
         new ConsLoIF(this.img2, 
             new ConsLoIF(this.img6, this.mt))); 

 // ImageFile list - small size (< 40000)
 public ILoIF imglistsmall = 
     new ConsLoIF(this.img5, 
         new ConsLoIF(this.img6, this.mt));

 // ImageFile list - small size (< 40000)
 public ILoIF imglistsmall2 = 
     new ConsLoIF(this.img5, 
         new ConsLoIF(this.img6, this.mt));

 // ImageFile list -- large images
 public ILoIF imglistlarge = 
     new ConsLoIF(this.img1, 
         new ConsLoIF(this.img2,
             new ConsLoIF(this.img3, this.mt))); 

 // A sample test method
 public boolean testSize(Tester t){
     return (t.checkExpect(this.img1.size(), 60000) &&
             t.checkExpect(this.img2.size(), 40000));
 }

 // A sample test method
 public boolean testContains(Tester t){
     return (t.checkExpect(this.imglistsmall.contains(this.img3), false) &&
             t.checkExpect(this.imglistsmall.contains(this.img6), true));
 }    
}
