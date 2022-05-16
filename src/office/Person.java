package office;
import java.util.List;

public class Person{
	
	int id;
	String name;
	boolean is_vacinado;
	int required_hours;
	int depende_de;
	List<Integer> hours;

	public Person(
			int id,
			String name, 
			boolean is_vacinado,
			int required_hours,
			int depende_de,
			List<Integer> hours
	){

		this.id = id;
		this.name = name;
		this.is_vacinado = is_vacinado;
		this.required_hours = required_hours;
		this.depende_de = depende_de;
		this.hours = hours;

	}

	public int getId(){ return this.id; }

	public String getName(){ return this.name; }

	public boolean getIsVacinado(){ return this.is_vacinado; }

	public int getRequiredHours(){ return this.required_hours; }

	public int getDependeDe(){ return this.depende_de; }

	public List<Integer> getHours(){ return this.hours; }
	
}
