package office;
import java.util.List;

public class Person{
	
	String name;
	List<Integer> hours;
	int required_hours;
	int vaccinated=0; //1 é vacinada, 0 não é

	public Person(String name, List<Integer> hours, int required_hours){

		this.name = name;
		this.hours = hours;
		this.required_hours = required_hours;

	}
	
	//Pessoa na variante 2
	public Person(String name, List<Integer> hours, int required_hours, int vaccinated){

		this.name = name;
		this.hours = hours;
		this.required_hours = required_hours;
		this.vaccinated=vaccinated;

	}

	public String getName(){ return this.name; }
	
	public int isVaccinated(){ return this.vaccinated; }

	public List<Integer> getHours(){ return this.hours; }

}
