import tester.*;
class Time {
    int day;
    int month;
    int year;
    Time(int year, int month, int day) {
        if (this.validYear(year)) {
            this.year = year;
        }
        else {
            throw new IllegalArgumentException("Invalid year: " + Integer.toString(year));
        }
       
        if (this.validMonth(month)) {
            this.month = month;
        }
        else {
            throw new IllegalArgumentException("Invalid month: " + Integer.toString(month));
        }
       
        if (this.validDay(day)) {
            this.day = day;
        }
        else {
            throw new IllegalArgumentException("Invalid day: " + Integer.toString(day));
        }
    }
    boolean  validDay(int given) { 
        return (0 < given) && (given < 32);
    }
    boolean  validMonth(int given) { 
        return (0 < given) && (given < 13);
    }
    boolean  validYear(int given) { 
        return (0 < given) && (given < 2400);
    }
    boolean validNumber(int given, int low, int high) {
        return (low < given) && (given < high);
    }
    boolean sameTime(Time given) {
        return (this.day == given.getDay()) &&
                (this.month == given.getMonth()) &&
                (this.year == given.getYear());
               
    }
    int getDay() {
        return this.day;
    }
    int getMonth() {
        return this.month;
    }
    int getYear() {
        return this.year;
    }
   
}


class ExamplesTime {
    Time a = new Time(1988, 2, 1);
    Time b = new Time(1999, 3, 2);
    Time c = new Time(2000, 4, 3);
    boolean testvalidDay(Tester t) {
        return t.checkConstructorException(
                // the expected exception
                new IllegalArgumentException("Invalid year: 53000"),
            
                // the *name* of the class (as a String) whose constructor we invoke
                "Time",
            
                // the arguments for the constructor
                53000, 12, 30);
    }
    boolean testsameTime(Tester t) {
        return t.checkExpect(this.a.sameTime(a), true) &&
                t.checkExpect(this.a.sameTime(b), false) &&
                t.checkExpect(new Time(2300, 4, 3).sameTime(c), false);
    }
}
