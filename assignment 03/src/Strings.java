
// Assignment 3
// Chen Xianpeng
// chenxianp
// Khomiakov Kevin 
// khomiakovkevin

import tester.*;



// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();
    ILoString sort();
    // insert the given string into the list
    ILoString insert(String given);
    boolean isSorted();
    String getFirst();
    ILoString interleave(ILoString given);
    ILoString getRest();
    ILoString  merge(ILoString given);
    ILoString reverse();
    ILoString reverseHelper(ILoString given);
    boolean isDoubledList();
    boolean isPalindromeList();
    //to determine if two lists are the same
    boolean isEqual(ILoString given);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    
    // combine all Strings in this list into one
    public String combine() {
        return "";
    } 
    public ILoString sort() {
        return this;
    }
    // insert the given string into the list
    public ILoString insert(String given) {
        return new ConsLoString(given, this);
    }
    public boolean isSorted() {
        return true;
    }
    public String getFirst() {
        return null;
    }
    public ILoString interleave(ILoString given) {
        return given;
    }
    public ILoString getRest() {
        return this;
    }
    public ILoString  merge(ILoString given) {
        return given;
    }
    public ILoString reverse() {
        return this;
    }
    public ILoString reverseHelper(ILoString given) {
        return given;
    }
    public boolean isDoubledList() {
        return true;
    }
    public boolean isPalindromeList() {
        return true;
    }
    public boolean isEqual(ILoString given) {
        return (given instanceof MtLoString);
    }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;  
    }
    
    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString
     
     METHODS
     ... this.combine() ...     -- String
     ... this.sort() ...        -- ILoString
     ... this.insert(String given) ... -- ILoString
     ... this.isSorted() ...        -- boolean
     ... this.getFirst() ...        -- String
     ... this.getRest() ...        -- ILoString
     ... this.interleave(ILoString given) ...-- ILoString
     ... this.merge(ILoString given) ...-- ILoString
     ... this.reverse() ...             -- ILoString
     ... this.reversehelper(ILoString given) ...-- ILoString
     ... this.isDoubleList() ...                -- boolean
     ... this.isPalindromeList() ...            -- boolean
     ... this.isEqual(ILoString given) ...      -- boolean
     
     
     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareTo(String) ...     -- int
     ... this.rest.combine() ...              -- String
     
     */
    
    // combine all Strings in this list into one
    public String combine() {
        return this.first.concat(this.rest.combine());
    } 
    public ILoString sort() {
        return this.rest.sort().insert(this.first);
            
    }
    // insert the given string into the list
    public ILoString insert(String given) {
        if  (given.compareTo(this.first) <= 0) {
            return new ConsLoString(given, this);
        }
        else {
            return new ConsLoString(this.first, this.rest.insert(given));
        }
    }
    public boolean isSorted() {
        if (this.first == this.sort().getFirst()) {
            return this.rest.isSorted();
        }
        else {
            return false;
        }
    }
    public String getFirst() {
        return this.first;
    }
    public ILoString getRest() {
        return this.rest;
    }
    public ILoString interleave(ILoString given) {
        if (given instanceof MtLoString) {
            return this;
        }
        else {
            return new ConsLoString(this.first,
                   new ConsLoString(given.getFirst(),
                    this.rest.interleave(given.getRest())));         
        }
    }
    public ILoString  merge(ILoString given) {
        if (given instanceof MtLoString) {
            return this;
        }
        else {
            return this.rest.merge(given.insert(this.first));
        }
    }
    public ILoString reverse() {
        return this.rest.reverseHelper(new ConsLoString(this.first, 
                new MtLoString()));
    }
    public ILoString reverseHelper(ILoString given) {
        return this.rest.reverseHelper(new ConsLoString(this.first, given));
    }
    public boolean isDoubledList() {
        if (this.first == this.rest.getFirst()) {
            return this.rest.getRest().isDoubledList();
        }
        else {
            return false;
        }
    }
    public boolean isPalindromeList() {
        return (this.reverse().isEqual(this));
    }
    public boolean isEqual(ILoString given) {
        if (given instanceof MtLoString) {
            return false;
        }
        else {
            if (this.first == given.getFirst()) {
                return this.rest.isEqual(given.getRest());
            }
            else {
                return false;
            }
        }
    }
}
    



// to represent examples for lists of strings
class ExamplesStrings {
    
    ILoString mary = new ConsLoString("mary ",
                    new ConsLoString("had ",
                        new ConsLoString("a ",
                            new ConsLoString("little ",
                                new ConsLoString("lamb.", new MtLoString())))));
    
    // test the method combine for the lists of Strings
    boolean testCombine(Tester t) {
        return 
            t.checkExpect(this.mary.combine(), "mary had a little lamb.");
    }
    boolean testsort(Tester t) {
        return t.checkExpect(new MtLoString().sort(), 
                             new MtLoString()) &&
               t.checkExpect(new ConsLoString("lamb.", 
                             new MtLoString()).sort(), 
                             new ConsLoString("lamb.", 
                             new MtLoString())) &&
               t.checkExpect(new ConsLoString("a ",
                             new ConsLoString("little ",
                             new ConsLoString("lamb.", 
                             new MtLoString()))).sort(), 
                             new ConsLoString("a ", 
                             new ConsLoString("lamb.", 
                             new ConsLoString("little ", 
                               new MtLoString())))) &&
               t.checkExpect(this.mary.sort(), 
                        new ConsLoString("a ",
                        new ConsLoString("had ",
                           new ConsLoString("lamb.",
                               new ConsLoString("little ",
                                   new ConsLoString("mary ", 
                                       new MtLoString()))))));
    }
    boolean testisSorted(Tester t) {
        return t.checkExpect(this.mary.isSorted(), false) &&
               t.checkExpect(new MtLoString().isSorted(), true) &&
               t.checkExpect(new ConsLoString("lamb.",
                             new ConsLoString("little ",
                             new ConsLoString("mary ", 
                             new MtLoString()))).isSorted(), true);
    }
    boolean testinterleave(Tester t) {
        return t.checkExpect(new MtLoString().interleave(new MtLoString()), 
                new MtLoString()) &&
               t.checkExpect(new ConsLoString("lamb.", 
                             new ConsLoString("little ", 
                             new MtLoString())).interleave(new ConsLoString("little ", 
                                 new ConsLoString("mary ", 
                                     new MtLoString()))), 
                             new ConsLoString("lamb.",
                             new ConsLoString("little ",
                             new ConsLoString("little ",
                                 new ConsLoString("mary ",
                                     new MtLoString()))))) &&
               t.checkExpect(this.mary.interleave(new MtLoString()), this.mary);
    }
    boolean testmerge(Tester t) {
        return t.checkExpect(new ConsLoString("lamb.",
                             new ConsLoString("little ",
                             new ConsLoString("mary ", 
                             new MtLoString()))).merge(new MtLoString()), 
                             new ConsLoString("lamb.",
                             new ConsLoString("little ",
                             new ConsLoString("mary ", 
                             new MtLoString())))) &&
               t.checkExpect(new ConsLoString("had ",
                             new ConsLoString("mary ", 
                             new MtLoString())).merge(new ConsLoString("a ",
                                 new ConsLoString("lamb.",
                                     new ConsLoString("little ", 
                                         new MtLoString())))), 
                             new ConsLoString("a ",
                             new ConsLoString("had ",
                             new ConsLoString("lamb.",
                                 new ConsLoString("little ",
                                     new ConsLoString("mary ", 
                                         new MtLoString())))))) &&
               t.checkExpect(new MtLoString().merge(new ConsLoString("mary ", 
                             new MtLoString())), 
                             new ConsLoString("mary ", 
                             new MtLoString()));
    }
    boolean testreverse(Tester t) {
        return t.checkExpect(new MtLoString().reverse(), new MtLoString()) &&
               t.checkExpect(this.mary.reverse(), 
                             new ConsLoString("lamb.",
                             new ConsLoString("little ",
                             new ConsLoString("a ",
                                 new ConsLoString("had ",
                                     new ConsLoString("mary ", 
                                         new MtLoString())))))) &&
               t.checkExpect(new ConsLoString("little ",
                             new ConsLoString("mary ", 
                             new MtLoString())).reverse(), 
                             new ConsLoString("mary ",
                             new ConsLoString("little ", 
                             new MtLoString())));
    }
    boolean testisDoubledList(Tester t) {
        return t.checkExpect(this.mary.isDoubledList(), false) &&
               t.checkExpect(new MtLoString().isDoubledList(), true) &&
               t.checkExpect(new ConsLoString("mary ",
                             new ConsLoString("mary ", 
                             new MtLoString())).isDoubledList(), true);
    }
    boolean testisPalindromeList(Tester t) {
        return t.checkExpect(this.mary.isPalindromeList(), false) &&
               t.checkExpect(new MtLoString().isPalindromeList(), true) &&
               t.checkExpect(new ConsLoString("a",
                             new ConsLoString("a", 
                             new MtLoString())).isPalindromeList(), true);
    }
}