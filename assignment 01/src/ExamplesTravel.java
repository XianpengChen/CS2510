// Assignment 1
// Chen Xianpeng
// chenxianp

interface Habitation {

}

class Planet implements Habitation {
    String name;
    int population; //in thousands of people
    int spaceports;

    Planet(String name, int population, int spaceports) {
        this.name = name;
        this.population = population;
        this.spaceports = spaceports;
    }
}

class SpaceStation implements Habitation {
    String name;
    int population; //in thousands of people
    int transporterPads;

    SpaceStation(String name, int population, int transporterPads) {
        this.name = name;
        this.population = population;
        this.transporterPads = transporterPads;
    }
}



interface Travel {

}

class Shuttle implements Travel {
    Habitation from;
    Habitation to;
    int fuel;
    int capacity;

    Shuttle(Habitation from, Habitation to, int fuel, int capacity) {
        this.from = from;
        this.to = to;
        this.fuel = fuel;
        this.capacity = capacity;
    }
}

class SpaceElevator implements Travel {
    Planet from;
    SpaceStation to;

    SpaceElevator(Planet from, SpaceStation to) {
        this.from = from;
        this.to = to;
    }
}

class Transporter implements Travel {
    Habitation from;
    Habitation to;

    Transporter(Habitation from, Habitation to) {
        this.from = from;
        this.to = to;
    }
}

class ExamplesTravel {
   
    Habitation nausicant = new Planet("Nausicant", 6000000, 8);
    Habitation earth = new Planet("Earth", 9000000, 14);
    Habitation remus = new Planet("Remus", 18000000, 23);
    Habitation watcherGrid = new SpaceStation("WatcherGrid", 1, 0);
    Habitation deepSpace42 = new SpaceStation("Deep Space 42", 7, 8);
    Habitation a = new SpaceStation("WatchA", 1, 0);
    Travel shuttle1 = new Shuttle(new Planet("Nausicant", 6000000, 8),
            new Planet("Earth", 9000000, 14), 44444, 555555);
    Travel shuttle2 = new Shuttle(new Planet("Nausicant", 6000000, 8), 
          new SpaceStation("Deep Space 42", 7, 8), 666666, 777777);
    Travel elevator1 = new SpaceElevator(new Planet("Earth", 9000000, 14), 
          new SpaceStation("WatcherGrid", 1, 0));
    Travel elevator2 = new SpaceElevator(new Planet("Remus", 18000000, 23), 
          new SpaceStation("WatcherGrid", 1, 0));
    Travel transporter1 = new Transporter(new Planet("Earth", 9000000, 14), 
          new SpaceStation("Deep Space 42", 7, 8));
    Travel transporter2 = new Transporter(new SpaceStation("Deep Space 42", 7, 8), 
          new Planet("Nausicant", 6000000, 8));
}

