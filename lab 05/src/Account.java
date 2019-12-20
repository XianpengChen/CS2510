
import tester.*;

public abstract class Account {

    int accountNum;  // Must be unique
    int balance;     // Must remain above zero (others Accts have more restrictions)
    String name;     // Name on the account

    public Account(int accountNum, int balance, String name) {
        this.accountNum = accountNum;
        this.balance = balance;
        this.name = name;
    }
    
    // produce the amount available for withdrawal from this account
    public abstract int amtAvailable();
    public abstract boolean isChecking();
    public abstract boolean isSaving();
    public abstract boolean same(Account given);
    public abstract Checking asChecking(); 
    public abstract Savings asSaving(); 
}


//Represents a checking account
class Checking extends Account {

    int minimum; // The minimum account balance allowed

    public Checking(int accountNum, int balance, String name, int minimum) {
        super(accountNum, balance, name);
        this.minimum = minimum;
    }

 /* TEMPLATE:
  Fields:
  ... this.accountNum ...     -- int
  ... this.balance ...        -- int
  ... this.name ...           -- String
  ... this.minimum ...        -- int
  
  Methods:
  ... this.amtAvailable() ...  -- int
  
  */
 
 // produce the amount available for withdrawal from this account
    public int amtAvailable() {
        return this.balance - this.minimum;
    }
    public boolean isChecking() {
        return true;
    }
    public boolean isSaving() {
        return false;
    }
    public boolean same(Account given) {
        return given.isChecking() &&
            this.sameChecking(given.asChecking());
    }
    public Checking asChecking() {
        return this;
    }
    public Savings asSaving() {
        throw new ClassCastException("the input can not be consider as saving account");
    }
    public boolean sameChecking(Checking that) {
        return this.accountNum == that.accountNum;
    }
}


//Represents a savings account
class Savings extends Account {

    double interest; // The interest rate

    public Savings(int accountNum, int balance, String name, 
                    double interest) {
        super(accountNum, balance, name);
        this.interest = interest;
    }

 /* TEMPLATE:
  Fields:
  ... this.accountNum ...     -- int
  ... this.balance ...        -- int
  ... this.name ...           -- String
  ... this.interest ...       -- double
  
  Methods:
  ... this.amtAvailable() ...  -- int
  
  */
 
 // produce the amount available for withdrawal from this account
    public int amtAvailable() {
        return this.balance;
    }
    public boolean isChecking() {
        return false;
    }
    public boolean isSaving() {
        return true;
    }
    public boolean same(Account given) {
        return given.isSaving() &&
         this.sameSavings(given.asSaving());
    }
    public Checking asChecking() {
        throw new ClassCastException("the input can not be consider as checking account");
    }
    public Savings asSaving() {
        return this;
    }
    public boolean sameSavings(Savings that) {
        return this.accountNum == that.accountNum;
    }

}


//Bank Account Examples and Tests
class ExamplesBanking {


    Account check1 = new Checking(1, 100, "First Checking Account", 20);
    Account savings1 = new Savings(4, 200, "First Savings Account", 2.5);    

    // Tests the exceptions we expect to be thrown when
    //   performing an "illegal" action.
    boolean testAmtAvailable(Tester t) {
        return t.checkExpect(this.check1.amtAvailable(), 80) &&
                t.checkExpect(this.savings1.amtAvailable(), 200);
    }
    boolean testsame(Tester t) {
        return t.checkExpect(this.check1.same(savings1), false) &&
                t.checkExpect(this.savings1.same(check1), false) &&
                t.checkExpect(this.check1.same(check1), true) &&
                t.checkExpect(this.savings1.same(savings1), true);
    }
}

