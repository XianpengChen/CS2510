class Runner {
    String name;    
    int age;        
    int bib;       
    boolean isMale; 
    int pos;        
    int time;
    Runner(String name, int age, int bib, boolean isMale, int pos, int time) {
        this.name = name;
        this.age = age;
        this.bib = bib;
        this.isMale = isMale;
        this.pos = pos;
        this.time = time;
    }
    public boolean isMale() {
        return this.isMale;
    }
}

interface ILoRunner {
    ILoRunner findAllmalerunnner();
    ILoRunner findAllfemalerunner();
    ILoRunner findAllover40();
     // In ILoRunner
    ILoRunner find(ILoIRunnerPredicate pred);

    
}

class MtLoRunner implements ILoRunner {
    MtLoRunner() {
        
    };
    public ILoRunner findAllmalerunnner() {
        return this;
    }
    public ILoRunner findAllfemalerunner() {
        return this;
    }
    public  ILoRunner findAllover40() {
        return this;
    }
     // In MtLoRunner
    public ILoRunner find(ILoIRunnerPredicate pred) { 
        return this; 
        }

}

class ConsLoRunner implements ILoRunner {
    Runner first;
    ILoRunner rest;
    ConsLoRunner(Runner first, ILoRunner rest) {
        this.first = first;
        this.rest = rest;
    }
    public ILoRunner findAllmalerunnner() {
        if (this.first.isMale()) {
            return new ConsLoRunner(this.first, this.rest.findAllmalerunnner());
        }
        else {
            return this.rest.findAllmalerunnner();
        }
    }
    public ILoRunner findAllfemalerunner() {
        if (this.first.isMale()) {
            return this.rest.findAllfemalerunner();
        }
        else {
            return new ConsLoRunner(this.first, this.rest.findAllfemalerunner());
        }
    }
    public  ILoRunner findAllover40() {
        if (this.first.age > 40) {
            return new ConsLoRunner(this.first, this.rest.findAllover40());
            
        }
        else {
            return this.rest.findAllover40();
        }
    }
 // In ConsLoRunner
    public ILoRunner find(ILoIRunnerPredicate pred) {
      if (pred.apply(this.first)) {
        return new ConsLoRunner(this.first, this.rest.find(pred));
      }
      else {
        return this.rest.find(pred);
      }
    }
}


interface IRunnerPredicate {
    boolean apply(Runner r);
  }
  class RunnerIsMale implements IRunnerPredicate {
    public boolean apply(Runner r) { return r.isMale; }
  }
  class RunnerIsFemale implements IRunnerPredicate {
    public boolean apply(Runner r) { return !r.isMale; }
  }
  class RunnerIsInFirst50 implements IRunnerPredicate {
    public boolean apply(Runner r) { return r.pos <= 50; }
  }
//Represents a predicate that is true whenever both of its component predicates are true
class AndPredicate implements IRunnerPredicate {
 IRunnerPredicate left, right;
 AndPredicate(IRunnerPredicate left, IRunnerPredicate right) {
   this.left = left;
   this.right = right;
 }
 public boolean apply(Runner r) {
   return this.left.apply(r) && this.right.apply(r);
 }
}

interface ILoIRunnerPredicate  {
    boolean apply(Runner r);
}

class MtLoIRunnerPredicate implements ILoIRunnerPredicate {
    public boolean apply(Runner r) {
        return true;
    }
}

class ConsLoIRunnerPredicate {
    IRunnerPredicate first;
    ILoIRunnerPredicate rest;
    ConsLoIRunnerPredicate(IRunnerPredicate first, ILoIRunnerPredicate rest) {
        this.first = first;
        this.rest = rest;
    }
    public boolean apply(Runner r) {
        return this.first.apply(r) && this.rest.apply(r);
    }
}
