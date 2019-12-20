import tester.*;
//Assignment 2

// Chen Xianpeng

// chenxianp
class WebPage {
    String url;
    String title;
    ILoItem items;
    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items;
    }

    /* TEMPLATE:
     * Fields:
     * ... this.url ...                 -- String
     * ... this.title ...               -- String
     * ... this.items ...               -- ILoItem
     *
     * Methods:
     * ... this.totalImageSize() ...    -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...      -- String
     
     * Methods of Fields:
     * ... this.items.textLength() ...        -- int
     * ... this.items.totalImageSize() ...    -- int
     * ... this.items.images() ...            -- String
     * ... this.title.length() ...            -- int
     */
    //get all the Image size on this page
    int totalImageSize() {
        return this.items.totalImageSize();
    } 
    
    
    int textLength() {
        return this.title.length() + this.items.textLength();
    }
    
    String images() {
        return this.imagesHelper().substring(0, this.imagesHelper().length() - 2);
    }
    String imagesHelper() {
        return this.items.imagesHelper();
    }
 
}



interface ILoItem {
    
    int totalImageSize();
    int textLength();
    
    String imagesHelper();
    
}

class MtLoItem implements ILoItem {
    
    /* TEMPLATE:
     * Fields:
     * (none)
     *
     * Methods:
     * ... this.getAllSize() ...        -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...            -- String
     * ... this.getSize() ...           -- int
     * ... this.getLinks() ...          -- ILoItem
     */
 
    public int totalImageSize() {
        return 0;
    }
    public int textLength() {
        return 0;
    }
   
    
    public String imagesHelper() {
        return "";
    }
    
}

class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
    /* TEMPLATE:
     * Fields:
     * ... this.first ...                -- IItem
     * ... this.rest ...                -- ILoI
     *
     * Methods:
     * ... this.getAllSize() ...        -- int
     * ... this.getLinks() ...          -- ILoItem
     * ... this.getSize() ...           -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...            -- String
     *
     * Methods of Fields:
     * ... this.first.getAllSize() ...    -- int
     * ... this.rest.getAllSize() ...     -- int
     * ... this.rest.getLinks() ...       -- ILoItem
     * ... this.first.getPage() ...       -- WebPage
     * ... this.first.textLength() ...        -- int
     * ... this.rest.textLength() ...         -- int
     * ... this.first.images() ...            -- String
     * ... this.rest.images() ...             -- String
     *
     */

    public int totalImageSize() {
        return this.first.totalImageSize() + 
                this.rest.totalImageSize();
    }
    
    
    
    
    public int textLength() {
        return this.first.textLength() + this.rest.textLength();
    }
 
    public String imagesHelper() {
        return this.first.images() +  this.rest.imagesHelper();
    }
}

interface IItem {
    int totalImageSize();
    int textLength();
    String images();
    WebPage getPage();

}

class Text implements IItem {
    String contents;
    Text(String contents) {
        this.contents = contents;
    }
    /* TEMPLATE:
     * Fields:
     * ... this.contents ...            -- String
     *
     * Methods:
     * ... this.totalImageSize() ...    -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...            -- String
     * ... this.getPage() ...           -- WebPage
     * Methods for Fields:
     * ... this.contents.length() ...   -- int
     */
    public int totalImageSize() {
        return 0;
    }
    public int textLength() {
        return this.contents.length();
    }
    public String images() {
        return "";
    }
    public WebPage getPage() {
        return new WebPage("a", "b", new MtLoItem());
    }
}

class Image implements IItem {
    String filename;
    int size;
    String filetype;
    Image(String filename, int size, String filetype) {
        this.filename = filename;
        this.size = size;
        this.filetype = filetype;
    }
    /* TEMPLATE:
     * Fields:
     * ... this.fileName ...            -- String
     * ... this.size ...                -- int
     * ... this.fileType ...            -- String
     *
     * Methods:
     * ... this.totalImageSize() ...    -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...            -- String
     * 
     * Methods for Fields:
     * ... this.filename.length() ...   -- int
     * ... this.filetype.length() ...   -- int 
     */
    public int totalImageSize() {
        return this.size;
    }
    public int textLength() {
        return this.filename.length() + 
                this.filetype.length();
    }
    public String images() {
        return this.filename + "." + this.filetype + ", ";
    }
    public WebPage getPage() {
        return new WebPage("a", "b", new MtLoItem());
    }
}

   
class Link implements IItem {
    String name;
    WebPage page;
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    /* TEMPLATE:
     * Fields:
     * ... this.name ...                -- String
     * ... this.page ...                -- WebPage
     *
     * Methods:
     * ... this.totalImageSize() ...    -- int
     * ... this.textLength() ...        -- int
     * ... this.images() ...            -- String
     *
     * Methods of Fields:
     * ... this.page.textLength() ...        -- int
     * ... this.page.totalImageSize() ...    -- int
     * ... this.page.imagesHelper() ...      -- String
     * ... this.page.images() ...            -- String
     *
     */
    public int totalImageSize() {
        return this.page.totalImageSize();
    }
    public int textLength() {
        return this.name.length() + this.page.textLength();
    }
    public String images() {
        return this.page.imagesHelper();
    }
    public WebPage getPage() {
        return this.page;
    }
}

class ExamplesWebPage {
    ILoItem g = new MtLoItem();
    Text o = new Text("How to Design Programs");
    Image p = new Image("htdp", 4300, "tiff");
    ILoItem q = new ConsLoItem(this.p, this.g);
    ILoItem r = new ConsLoItem(this.o, this.q);
    WebPage HtDP = new WebPage("htdp.org", "HtDP", this.r);
    Text s = new Text("Stay classy, Java");
    Link t = new Link("Back to the Future", this.HtDP);
    ILoItem u = new ConsLoItem(this.t, this.g);
    ILoItem v = new ConsLoItem(this.s, this.u);
    WebPage OOD = new WebPage("ccs.neu.edu/OOD", "OOD", this.v);
    Text a = new Text("Home sweet home");
    Image b = new Image("wvh-lab", 400, "png");
    Text c = new Text("The staff");
    Image d = new Image("profs", 240, "jpeg");
    Link e = new Link("A Look Back", this.HtDP);
    Link f = new Link("A Look Ahead", this.OOD);
   
    WebPage x = new WebPage("new", "x", this.g);
    Link y = new Link("y", this.x);
    
    ILoItem h = new ConsLoItem(this.f, this.g);
    ILoItem I = new ConsLoItem(this.e, this.h);
    ILoItem j = new ConsLoItem(this.d, this.I);
    ILoItem k = new ConsLoItem(this.c, this.j);
    ILoItem m = new ConsLoItem(this.b, this.k);
    ILoItem n = new ConsLoItem(this.a, this.m);
    WebPage fundiesWP = new WebPage("ccs.neu.edu/Fundies2", "Fundies II",
            this.n);
    Link fu = new Link("fu", this.fundiesWP);
    ILoItem w = new ConsLoItem(this.fu, this.n);
    ILoItem z = new ConsLoItem(this.y, this.w);
  
    WebPage WANTED = new WebPage("111111", "wanted", this.z);
   
    boolean testtotalImageSize(Tester t) {
        return t.checkExpect(this.HtDP.totalImageSize(), 4300) &&
                t.checkExpect(this.OOD.totalImageSize(), 4300) &&
                t.checkExpect(this.fundiesWP.totalImageSize(), 9240);
               
    }
    boolean testTextLength(Tester t) {
        return t.checkExpect(this.HtDP.textLength(), 34) &&
                t.checkExpect(this.OOD.textLength(), 72) &&
                t.checkExpect(this.fundiesWP.textLength(), 182);
    }
    boolean testImages(Tester t) {
        return t.checkExpect(this.HtDP.images(), "htdp.tiff") &&
                t.checkExpect(this.OOD.images(), "htdp.tiff") &&
                t.checkExpect(this.fundiesWP.images(), 
                        "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
    }
}   

