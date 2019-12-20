package acestortree;

public interface IAT {

}

class Unknown implements IAT {
	
}
class Person implements IAT {
	String name;
	IAT mom;
	IAT dad;
	
	Person(String name, IAT mom, IAT dad){
		this.name = name;
		this.mom = mom;
		this.dad = dad;
}}

class ExamplesIAT {
	IAT unk = new Unknown();
	IAT dan = new Person("dan", new Person("sue", unk, unk), new Person("jay", unk, unk));
}