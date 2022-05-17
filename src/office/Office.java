package office;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Office extends CSP<Variable, Integer>{

	int hours = 24;
	int variant=0; //0 é o programa principal
	List<Person> persons = new ArrayList<>();

	public Office(File path, int variant){
		
		this.variant=variant;
        int length=26;
        if(variant==2) {
        	length=27;
        }
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null){
				String[] lineSplit = line.split(splitBy);
				if(lineSplit.length == length){
					//System.out.println("Nome: "+lineSplit[0]);
					//System.out.println("Carga horária: "+lineSplit[1]);
					List<Integer> hours = new ArrayList<>();
					for(int i=2;i<26;i++) {
						if(lineSplit[i].equals("1")) {
							hours.add(i-1);
						}
					}
					if(variant==0) {
					this.persons.add(new Person(lineSplit[0], hours, Integer.parseInt(lineSplit[1])));
					}
					if(variant==2) {
						this.persons.add(new Person(lineSplit[0], hours, Integer.parseInt(lineSplit[1]),Integer.parseInt(lineSplit[26])));
						}
				} else{
					System.out.println("Número de argumentos na linha "+line+" está errado.");
					System.exit(1);
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}

		//cria uma variavel para cada hora que a pessoa tem que trabalhar
		for (int i = 0; i < this.persons.size(); i++) {
			for (int j = 0; j < this.persons.get(i).required_hours; j++) {
				Variable v = new Variable(this.persons.get(i).name+j);
				addVariable(v);
				Domain<Integer> domain = new Domain<>(this.persons.get(i).getHours());
				setDomain(v, domain);

			}
		}
        
		//Restricoes
		for(Variable v : getVariables()) {
					String namePerson = v.toString().substring(0, v.toString().length()-1);
					for(int i = 0; i < this.persons.size(); i++) {
						if(this.persons.get(i).name.equals(namePerson)) { //encontra a pessoa que a variável se refere
							if(this.persons.get(i).isVaccinated()!=1) { //se a pessoa não for vacinada
								addConstraint(new NotEqualConstraint<>(v,getVariables()));	
							}
							else {// se a pessoa for vacinada
								List<Variable> allMe = new ArrayList<>();
								allMe.addAll(getAllMe(namePerson));
								addConstraint(new NotEqualConstraint<>(v, allMe) );	
							}
						}
					}
		}

	}
	
	public List<Variable> getAllMe(String me) {
		List<Variable> allMe = new ArrayList<>();
		for (Variable v : getVariables()) {
			String namePerson = v.toString().substring(0, v.toString().length()-1);
				if (namePerson.equals(me)) { // encontra variaveis referentes a mesma pessoa
					allMe.add(v);
				}
			
		}
		return allMe;
	}
	
	
	public List<String> getAllNamesVac() {
		List<String> namesVac = new ArrayList<>();
		for (int i = 0; i < this.persons.size(); i++) {
				if (this.persons.get(i).isVaccinated() == 1) { // se a pessoa for vacinada
					namesVac.add(this.persons.get(i).name);
				}
			
		}
		return namesVac;
	}
	
}
