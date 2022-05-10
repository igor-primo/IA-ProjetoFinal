package office;
import java.util.List;

public class Person{
	
	String name;
	List<Integer> hours;
	int required_hours;

	public Person(String name, List<Integer> hours, int required_hours){

		this.name = name;
		this.hours = hours;
		this.required_hours = required_hours;

	}

	public String getName(){ return this.name; }

	public List<Integer> getHours(){ return this.hours; }

}
