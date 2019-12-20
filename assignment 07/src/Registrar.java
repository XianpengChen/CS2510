//assignment 07
//Chen Xianpeng
//chenxianp
//Michael Delpapa   
//mdelpapa

import tester.*;

interface IFunc<T, U> {
    U apply(T given);
}

class CourseToInstructor implements IFunc<Course, Instructor> {
    public Instructor apply(Course given) {
        return given.in;
    }
}

interface IList<T> {
    IList<T> append(IList<T> given);
    //to determine if two list have common object;
    boolean common(IList<T> given);
    // to determine if a given T is in this list;
    boolean contain(T given);
    <U> IList<U> map(IFunc<T, U> f);
    //delete the given object's first instance in the list
    IList<T> delete(T given);  
}

class Empty<T> implements IList<T> {
   
    
    public IList<T> append(IList<T> given) {
        return given;
    }
    public  boolean common(IList<T> given) {
        return false;
    }
    public  boolean contain(T given) {
        return false;
    }
    public <U> IList<U> map(IFunc<T, U> f) {
        return new Empty<U>();
    }
    public IList<T> delete(T given) {
        return this;
    }
    
}


class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    public  IList<T> append(IList<T> given) {
        return new Cons<T>(this.first, this.rest.append(given));
    }
    public  boolean common(IList<T> given) {
        return given.contain(this.first) || this.rest.common(given);
    }
    public  boolean contain(T given) {
        return this.first.equals(given) || this.rest.contain(given);
    }
    public <U> IList<U> map(IFunc<T, U> f) {
        return new Cons<U>(f.apply(first), this.rest.map(f));
    } 
    public IList<T> delete(T given) {
        if (this.first.equals(given)) {
            return this.rest;
        }
        else {
            return new Cons<T>(this.first, this.rest.delete(given));
        }
    }
}
    
class Instructor {
    String name;
    IList<Course> clist;
    Instructor(String name, IList<Course> clist) {
        this.name = name;
        this.clist = clist;
    }
    boolean dejavu(Student c) {
        return c.clist.map(new CourseToInstructor()).delete(this).contain(this);
    }
}

class Student {
    String name;
    int id;
    IList<Course> clist;
    Student(String name, int id, IList<Course> clist) {
        this.name = name;
        this.id = id;
        this.clist = clist;
    }
    boolean classmates(Student c) {
        return this.clist.common(c.clist); 
    }
    void enroll(Course c) {
        this.clist = new Cons<Course>(c, this.clist);
        c.slist = new Cons<Student>(this, c.slist);  
    }
    
}

    
class Course {
    String name;
    Instructor in;
    IList<Student> slist;
    Course(String name, Instructor in, IList<Student> slist) {
        this.name = name;
        this.in = in;
        this.slist = slist;
    }
}

class ExamplesRegistrar {
   
    Student a = new Student("a", 111, null);
    Student b = new Student("b", 222, null);
    Student c = new Student("c", 333, null);
    Student d = new Student("d", 444, null);
    Student e = new Student("e", 555, null);
    
    Instructor in1 = new Instructor("in1", null);
    Instructor in2 = new Instructor("in2", null);
    
    IList<Integer> empty = new Empty<Integer>();
    IList<Integer> loin1 = new Cons<Integer>(1, empty);
    
    Course c1 = new Course("c1", in1, new Cons<Student>(a, 
                new Cons<Student>(b, new Empty<Student>())));
    Course c2 = new Course("c2", in1, new Cons<Student>(b, 
                new Cons<Student>(c, new Empty<Student>())));
    Course c3 = new Course("c3", in2, new Cons<Student>(c, 
                new Cons<Student>(d, new Empty<Student>())));
    Course c4 = new Course("c4", in2, new Cons<Student>(d, 
                new Cons<Student>(e, new Empty<Student>())));
    
    boolean testclassmates(Tester t) {
    
        in1.clist = new Cons<Course>(c1, new Cons<Course>(c2, new Empty<Course>()));
        in2.clist = new Cons<Course>(c3, new Cons<Course>(c4, new Empty<Course>()));
    
        a.clist = new Cons<Course>(c1, new Empty<Course>());
        b.clist = new Cons<Course>(c1, new Cons<Course>(c2, new Empty<Course>()));
        c.clist = new Cons<Course>(c2, new Cons<Course>(c3, new Empty<Course>()));
        d.clist = new Cons<Course>(c3, new Cons<Course>(c4, new Empty<Course>()));
        e.clist = new Cons<Course>(c4, new Empty<Course>());
        
        return t.checkExpect(a.classmates(b), true) &&
                t.checkExpect(a.classmates(e), false) &&
                t.checkExpect(b.classmates(d), false) &&
                t.checkExpect(b.classmates(c), true) &&
                t.checkExpect(in1.dejavu(a), false) &&
                t.checkExpect(in1.dejavu(b), true) &&
                t.checkExpect(in2.dejavu(d), true) &&
                t.checkExpect(in2.dejavu(c), false);
    }
    boolean testCoursec2(Tester t) {
        a.enroll(c2);
        return t.checkExpect(c2.slist.contain(a), true) &&
                t.checkExpect(a.clist.contain(c2), true);
    }
    boolean testCoursec3(Tester t) {
        e.enroll(c3);
        return t.checkExpect(c3.slist.contain(e), true) &&
                t.checkExpect(e.clist.contain(c3), true) &&
                t.checkExpect(c3.slist.contain(a), false);
        
    }
    boolean testappend(Tester t) {
        return t.checkExpect(empty.append(empty), empty) &&
                t.checkExpect(empty.delete(4), empty) &&
                t.checkExpect(loin1.append(empty), loin1);
        
    }
}