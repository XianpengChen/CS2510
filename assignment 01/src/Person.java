// Assignment 1
// Chen Xianpeng
// chenxianp


class Person {
    String name;
    int yob;
    String state;
    boolean citizen;
    
    Person(String name, int yob, String state, boolean citizen) {
        this.name = name;
        this.yob = yob;
        this.state = state;
        this.citizen = citizen;
    }
}

class ExamplesPerson {
    Person simone = new Person("Simone Manuel", 1996, "TX", true);
    Person marty = new Person("Marty Walsh", 1967, "MA", true);
    Person bob = new Person("Bob Jack", 1988, "MA", false);
}
