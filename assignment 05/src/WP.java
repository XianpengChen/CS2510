// Assignment 5
// Chen Xianpeng
// chenxianp
// Khomiakov Kevin 
// khomiakovkevin

import tester.*;

class WebPage {
    String url;
    String title;
    ILoItem items;
    WebPage(String url, String title, ILoItem items) {
        this.url = url;
        this.title = title;
        this.items = items;
    }
    boolean sameWebPage(WebPage given) {
        return this.url.equals(given.url) &&
                this.title.equals(given.title) &&
                this.items.sameLoItem(given.items);           
    }
}



interface ILoItem {
    boolean sameLoItem(ILoItem given);
    boolean sameMtLoItem(MtLoItem given);
    boolean sameConsLoItem(ConsLoItem given);
    
  
}

class MtLoItem implements ILoItem {
    public boolean sameLoItem(ILoItem given) {
        return given.sameMtLoItem(this);
    }
    public boolean sameMtLoItem(MtLoItem given) {
        return true;
    }
    public boolean sameConsLoItem(ConsLoItem given) {
        return false;
    }
 
   
    
}

class ConsLoItem implements ILoItem {
    IItem first;
    ILoItem rest;
    ConsLoItem(IItem first, ILoItem rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean sameLoItem(ILoItem given) {
        return given.sameConsLoItem(this);
    }
    public boolean sameMtLoItem(MtLoItem given) {
        return false;
    }
    public boolean sameConsLoItem(ConsLoItem given) {
        return this.first.sameItem(given.first) &&
                this.rest.sameLoItem(given.rest);
    }
   
}

interface IItem {
    boolean sameItem(IItem given);
    boolean isText();
    boolean isImage();
    boolean isLink();
  

}


class Text implements IItem {
    String contents;
    Text(String contents) {
        this.contents = contents;
    }
    public boolean sameItem(IItem given) {
        return given.isText() && this.sameText((Text)given);
    }
    public boolean sameText(Text given) {
        return (given.isHeader() && this.sameHeader((Header)given)) || 
                (given.isParagraph() && this.sameParagraph((Paragraph)given));
    }
    public  boolean isText() {
        return true;
    }
    public boolean isImage() {
        return false;
    }
    public boolean isLink() {
        return false;
    }
    boolean sameParagraph(Paragraph given) {
        return false;
    }
    boolean sameHeader(Header given) {
        return false;
    }
    boolean isParagraph() {
        return false;
    }
    boolean isHeader() {
        return false;
    }
    
}

class Paragraph extends Text {
    Paragraph(String contents) {
        super(contents);
    }
    public boolean sameParagraph(Paragraph given) {
        return this.contents.equals(given.contents);
    }
    public boolean sameHeader(Header given) {
        return false;
    }
    public boolean isParagraph() {
        return true;
    }
    public boolean isHeader() {
        return false;
    }
    
}

class Header extends Text {
    Header(String contents) {
        super(contents);
    }
    public boolean sameParagraph(Paragraph given) {
        return false;
    }
    public boolean sameHeader(Header given) {
        return this.contents.equals(given.contents);
    }
    public boolean isParagraph() {
        return false;
    }
    public boolean isHeader() {
        return true;
    }
}

class Image implements IItem {
    String fileName;
    int size;
    String fileType;
    Image(String fileName, int size, String fileType) {
        this.fileName = fileName;
        this.size = size;
        this.fileType = fileType;
    }
    public  boolean isText() {
        return false;
    }
    public boolean isImage() {
        return true;
    }
    public boolean isLink() {
        return false;
    }
    public boolean sameItem(IItem given) {
        return given.isImage() && this.sameImage((Image)given);
    }
    public boolean sameImage(Image given) {
        return this.fileName.equals(given.fileName) &&
                this.size == given.size &&
                this.fileType.equals(given.fileType);
    }
    
}

   
class Link implements IItem {
    String name;
    WebPage page;
    Link(String name, WebPage page) {
        this.name = name;
        this.page = page;
    }
    public  boolean isText() {
        return false;
    }
    public boolean isImage() {
        return false;
    }
    public boolean isLink() {
        return true;
    }
    public boolean sameItem(IItem given) {
        return given.isLink() && this.sameLink((Link)given);
    }
    public boolean sameLink(Link given) {
        return this.name.equals(given.name) &&
                this.page.sameWebPage(given.page);
    }
   
}


class ExamplesWebPage {
    ILoItem g = new MtLoItem();
    Text o = new Paragraph("How to Design Programs");
    Image p = new Image("htdp", 4300, "tiff");
    ILoItem q = new ConsLoItem(this.p, this.g);
    ILoItem r = new ConsLoItem(this.o, this.q);
    WebPage HtDP = new WebPage("htdp.org", "HtDP", this.r);
    Text s = new Paragraph("Stay classy, Java");
    Link t = new Link("Back to the Future", this.HtDP);
    ILoItem u = new ConsLoItem(this.t, this.g);
    ILoItem v = new ConsLoItem(this.s, this.u);
    WebPage OOD = new WebPage("ccs.neu.edu/OOD", "OOD", this.v);
    Text a = new Header("Home sweet home");
    Image b = new Image("wvh-lab", 400, "png");
    Text c = new Header("The staff");
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
   
    boolean testsameWebPage(Tester t) {
        return t.checkExpect(this.x.sameWebPage(this.x), true) &&
                t.checkExpect(this.fundiesWP.sameWebPage(HtDP), false) &&
                t.checkExpect(this.OOD.sameWebPage(x), false);
    }
    boolean testsameLoItem(Tester t) {
        return t.checkExpect(this.m.sameLoItem(n), false) &&
                t.checkExpect(this.g.sameLoItem(g), true) &&
                t.checkExpect(this.k.sameLoItem(g), false) &&
                t.checkExpect(this.n.sameLoItem(n), true);
    }
    boolean testsameItem(Tester t) {
        return t.checkExpect(this.a.sameItem(a), true) &&
                t.checkExpect(this.d.sameItem(b), false) &&
                t.checkExpect(this.e.sameItem(f), false) &&
                t.checkExpect(this.b.sameItem(b), true) &&
                t.checkExpect(this.e.sameItem(e), true);
    }
    boolean testsameMtLoItem(Tester t) {
        return t.checkExpect(this.g.sameMtLoItem(new MtLoItem()), true) &&
                t.checkExpect(this.j.sameMtLoItem(new MtLoItem()), false);         
    }
    boolean testsameConsLoItem(Tester t) {
        return t.checkExpect(this.g.sameConsLoItem(new ConsLoItem(this.c, this.j)), false) &&
                t.checkExpect(this.j.sameConsLoItem(new ConsLoItem(this.d, this.I)), true);
    }
    boolean testisText(Tester t) {
        return t.checkExpect(this.a.isText(), true) &&
                t.checkExpect(this.b.isText(), false) &&
                t.checkExpect(this.e.isText(), false);
    }
    boolean testisImage(Tester t) {
        return t.checkExpect(this.a.isImage(), false) &&
                t.checkExpect(this.e.isImage(), false) &&
                t.checkExpect(this.b.isImage(), true);
    }
    boolean testisLink(Tester t) {
        return t.checkExpect(this.a.isLink(), false) &&
                t.checkExpect(this.d.isLink(), false) &&
                t.checkExpect(this.fu.isLink(), true);
    }
}   
