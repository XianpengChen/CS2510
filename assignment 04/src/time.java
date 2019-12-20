import tester.*;

//CS 2510 Fall 2016
//Assignment 4
//Chen Xianpeng
//chenxianp
//Khomiakov Kevin 
//khomiakovkevin
class Time {
    int hour;
    int minute;
    int second;
    boolean isAm;
    Time(int hour, int minute, int second) {
        this.hour = new Utils().checkRange(hour, 0, 23,
                "Invalid hour: " + Integer.toString(hour));
       
        this.minute = new Utils().checkRange(minute, 0, 59,
                "Invalid minute: " + Integer.toString(minute));
       
        this.second = new Utils().checkRange(second, 0, 59,
                "Invalid second: " + Integer.toString(second));
    }
    
    Time(int hour, int minute) {
        this.hour = new Utils().checkRange(hour, 0, 23,
                "Invalid hour: " + Integer.toString(hour));
        this.minute = new Utils().checkRange(minute, 0, 59,
                "Invalid minute: " + Integer.toString(minute));
        this.second = 0;
        
    }
    Time(int hour, int minute, boolean isAm) {
        this.hour = new Utils().checkRange(hour, 1, 12,
                "Invalid hour: " + Integer.toString(hour));
        this.minute = new Utils().checkRange(minute, 0, 59,
                "Invalid minute: " + Integer.toString(minute));
       
        this.isAm = isAm;
    }
    boolean sameTime(Time given) {
        return (this.hour == given.hour &&
                this.minute == given.minute &&
                this.second == 0 && 
                given.second == 0) || 
                (this.hour == given.hour &&
                this.minute == given.minute &&
                this.second == given.second) || 
                (this.hour == given.hour &&
                this.minute == given.minute &&
                this.isAm == given.isAm);
                
    }
   
}

class Utils {
    int checkRange(int val, int min, int max, String msg) {
        if (val >= min && val <= max) {
            return val;
        }
        else {
            throw new IllegalArgumentException(msg);
        }
    }
}


class ExamplesTime {
    Time a = new Time(19, 2, 1);
    Time b = new Time(19, 3, 2);
    Time c = new Time(20, 4, 3);
    Time t2 = new Time(12, 15, true);
    Time t4 = new Time(4, 30, false);
    Time t6 = new Time(12, 0);
    Time t7 = new Time(11, 12);
    
    boolean testsameTime(Tester t) {
        return t.checkExpect(this.a.sameTime(a), true) &&
                t.checkExpect(this.a.sameTime(b), false) &&
                t.checkExpect(new Time(23, 4, 3).sameTime(c), false) &&
                t.checkExpect(this.t2.sameTime(t2), true) &&
                t.checkExpect(this.t2.sameTime(t4), false) &&
                t.checkExpect(this.t6.sameTime(t6), true) &&
                t.checkExpect(this.t6.sameTime(t7), false);
        
    }
}
