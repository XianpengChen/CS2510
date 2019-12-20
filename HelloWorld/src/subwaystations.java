interface IStation{}

class TStop{
	String name;
	String line;
	double price;
	
	TStop(String name, String line, double price){
		this.name = name;
		this.line = line;
		this.price = price;
		
}}
class COmstation{
	String name;
	String line;
	Boolean express;
	
	COmstation(String name, String line, Boolean express){
		this.name = name;
		this.line = line;
		this.express = express;
}}

class ExamplesIStation {
	IStation ruggles = (IStation) new TStop("Ruggles", "Orange", 2.0);
	COmstation backbay = (COmstation) new COmstation("backbay", "famingham", true);
}