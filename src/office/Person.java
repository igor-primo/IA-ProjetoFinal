package office;
import java.util.List;

/*	Classe responsável criar Pessoa
 * 	Tem como váriaveis: 
 * 										name = Nome da pessoa, 
 * 										hours = Horas de trabalho disponíveis para aquela pessoa, 
 * 										required_hours = Quantidade de horas que deve trabalhar
 * 	Tem como métodos:
 * 										Construtor que cria a Pessoa, recebe => Name (string), Hours(lista de inteiros), Required_hours(int)
 * 										Construtor que cria a Pessoa para a variante dois, recebe => Name (string), Hours(lista de inteiros), Required_hours(int), Vaccinated(int 0 ou 1)
 * 										getName: Retorna o nome da pessoa
 * 										getHours: Retorna a lista com horas disponíveis
 * 										isVaccinated: Retorna 1 para vacinado e 0 para não vacinado
 */

public class Person{
	
	String name;
	List<Integer> hours;
	int required_hours;
	int vaccinated = 0; //1 é vacinada, 0 não é

	public Person(String name, List<Integer> hours, int required_hours){

		this.name = name;
		this.hours = hours;
		this.required_hours = required_hours;

	}
	
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
