package problem3;

interface Menuitem {

}
class Soup implements Menuitem{
	boolean vegetarians;
	
	Soup(boolean vegetarians){
		this.vegetarians = vegetarians;
	}
}
class ExamplesSoup{
	Soup a = new Soup(true);
	Soup b = new Soup(false);
}

class Salad implements Menuitem{
	String dressing;
	
	Salad(String dressing){
		this.dressing = dressing;
	}
}
class ExamplesSalad{
	Salad A = new Salad("a");
	Salad b = new Salad("b");
}

class Sandwich implements Menuitem{
	String bread;
	String fillings;
	
	Sandwich(String bread, String fillings){
		this.bread = bread;
		this.fillings = fillings;
	}
}
class ExamplesSandwich{
	Sandwich a = new Sandwich("a", "peanut butter and jelly");
	Sandwich b = new Sandwich("b", "ham and cheese");
}

class ExamplesMenuitem{
	Soup a = new Soup(true);
	Salad A = new Salad("a");
	Sandwich b = new Sandwich("b", "ham and cheese");
	
	
}