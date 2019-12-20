import tester.*;

/**
 * HtDC Labs
 * Lab 2: Methods for unions of classes
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */

//to represent a person
class Person {
    String name;
    Pet pet;
    int age;
    
    Person(String name, Pet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...    -- String
     ... this.pet ...     -- Pet
     ... this.age ...     -- int
     
     Methods:
     ... this.isOlder(Person) ...      -- boolean
     ... this.sameNamePet(String) ...  -- boolean
     ... this.perish() ...             -- Person
     
     Methods for Fields:
     ... this.pet.sameName(String) ... -- boolean
     */
    
    //is this person older than the given person?
    boolean isOlder(Person other) {
        return this.age > other.age;
    }
    
    //does the name of this person's pet match the given name?
    boolean sameNamePet(String petName) {
        return this.pet.sameName(petName);
    }
    
    // produce a person like this one, after her pet perished
    Person perish(){
        return new Person(this.name, new NoPet(), this.age);
    }
}

//to represent a pet
interface Pet { 
    
    //does the name of this person's pet match the given name?
    boolean sameName(String name);
}

//to represent a pet cat
class Cat implements Pet {
    String name;
    String kind; 
    boolean longhaired;
    
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...        -- String
     ... this.kind ...        -- String
     ... this.longhaired ...  -- boolean
     
     Methods:
     ... this.sameName(String) ...  -- boolean
     */
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

//to represent a pet dog
class Dog implements Pet {
    String name;
    String kind; 
    boolean male;
    
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    
    /* 
     TEMPLATE
     Fields: 
     ... this.name ...  -- String
     ... this.kind ...  -- String
     ... this.male ...  -- boolean
     
     Methods:
     ... this.sameName(String) ...  -- boolean
     */
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return this.name.equals(name);
    }
}

//to represent the absence of any pet
class NoPet implements Pet {
    NoPet() { }
    
    //does the name of this pet match the given name?
    public boolean sameName(String name) {
        return false;
    }
}

// to represent examples of pet owners and pets
class ExamplesPets {
    
    Pet kitty = new Cat("Boots", "tabby", false);
    Pet minny = new Cat("Minny", "angora", true);
    Pet spot = new Dog("Spot", "terrier", true);
    Pet tasha = new Dog("Tasha", "mutt", false);
    
    Pet nopet = new NoPet();
    
    Person tim = new Person ("Tim", this.kitty, 21);
    Person ann = new Person ("Ann", this.minny, 74);
    Person pat = new Person ("Pat", this.spot, 13);
    Person kim = new Person ("Kim", this.tasha, 18);
    
    Person dan = new Person("Dan", this.nopet, 23);
    
    // test the method isOlder in the class Person
    boolean testIsOlder(Tester t) {
        return
        t.checkExpect(this.tim.isOlder(this.ann), false) &&
        t.checkExpect(this.tim.isOlder(this.pat), true) &&
        t.checkExpect(this.ann.isOlder(this.kim), true) &&
        t.checkExpect(this.kim.isOlder(this.tim), false);
    } 
    
    // test the method sameName in the class Pet
    boolean testSameName(Tester t) {
        return
        t.checkExpect(this.kitty.sameName("Minny"), false) &&
        t.checkExpect(this.minny.sameName("Minny"), true) &&
        t.checkExpect(this.spot.sameName("Spot"), true) &&
        t.checkExpect(this.kitty.sameName("Spot"), false);
    }
    
    // test the method sameName in the class Pet
    boolean testSameNamePet(Tester t) {
        return
        t.checkExpect(this.kim.sameNamePet("Minny"), false) &&
        t.checkExpect(this.kim.sameNamePet("Tasha"), true) &&
        t.checkExpect(this.pat.sameNamePet("Spot"), true) &&
        t.checkExpect(this.tim.sameNamePet("Spot"), false) &&
        t.checkExpect(this.dan.sameNamePet("Spot"), false);
    }
    
    // test the method perish in the class Person
    boolean testPerish(Tester t) {
        return
        t.checkExpect(this.kim.perish(), 
                      new Person("Kim", this.nopet, 18));
    }
}

