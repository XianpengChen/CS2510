 // Assignment 1
 // Chen Xianpeng
 // chenxianp

//to represent a taco
interface ITaco {
    
}

//to represent an empty shell
class EmptyShell implements ITaco {
    boolean softShell;

    EmptyShell(boolean softShell) {
        this.softShell = softShell;
	}
}

//to represent filled
class Filled implements ITaco {
    ITaco taco;
    String filling;

    Filled(ITaco taco, String filling) {
        this.taco = taco;
        this.filling = filling;
    }
}

class ExamplesTaco {
    ExamplesTaco() {}
    /*
     – a soft-shelled taco with "carnitas", "salsa", "lettuce", "cheddar cheese"

     – a hard-shelled taco with "veggies", "guacamole", "sour cream"
     */
    
    ITaco order1 = new Filled(new Filled(new Filled(new Filled(new EmptyShell(true),
            "cheddar cheese"), "lettuce"), "salsa"), "carnitas");
    ITaco order2 = new Filled(new Filled(new Filled(new EmptyShell(false),
            "sour cream"), "guacamole"), "veggies");
}
