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
	int variant=0;  //0 é o programa principal.
	int initialHour = 1;
	int finalHour = 24;
	List<Person> persons = new ArrayList<>();

	public Office(File path, int variant){
		
		this.variant = variant;
        int length = 26;
        if(variant == 2) {
        	length=27;
        }else if(variant == 3) {
        	length = 28;
        }
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null){ //Enquanto a linha não é vazia
				String[] lineSplit = line.split(splitBy); //Faz o split da linha usando vírgula
				if(variant == 3) {
					initialHour = Integer.parseInt(lineSplit[26]);
					finalHour = Integer.parseInt(lineSplit[27]);
				}
				if(lineSplit.length == length){ //Compara o tamanho real da  linha com o tamanho esperado
					List<Integer> hours = new ArrayList<>(); //Array de horas
					for(int i=2;i<26;i++) { //Percorre as linhas com horário do dia
						if(lineSplit[i].equals("1") && ((i-1)>=initialHour) && ((i-1)<=finalHour)) {
							hours.add(i-1); //Add as horas que aquela pessoa pode trabalhar
						}
					}
					if(variant!=2) { //Se a variante for a 0 ele cria uma pessoa daquela variante
						this.persons.add(new Person(lineSplit[0], hours, Integer.parseInt(lineSplit[1])));
					}else{ //Se a variante for a 2 ele cria uma pessoa daquela variante
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

		//Cria uma variável para cada hora que a pessoa deve trabalhar.
		for (int i = 0; i < this.persons.size(); i++) {
			for (int j = 0; j < this.persons.get(i).required_hours; j++) {
				Variable v = new Variable(this.persons.get(i).name+j);//Cria uma pessoa para cada hora
				addVariable(v);//Add a variável no código
				Domain<Integer> domain = new Domain<>(this.persons.get(i).getHours());
				setDomain(v, domain);
			}
		}
        
		// Restricoes
		for(Variable v : getVariables()) {
			if(variant!=2) {
				addConstraint(new NotEqualConstraint<>(v,getVariables()));
			}else {
				List<Variable> nonVac = new ArrayList<>();//Array de variáveis com os não vacinados
				String namePerson = v.toString().substring(0, v.toString().length()-1);
				for(int i = 0; i < this.persons.size(); i++) {
					if(this.persons.get(i).name.equals(namePerson)) { //Encontra a pessoa que a variável se refere
						if(this.persons.get(i).isVaccinated()!=1) { //Se a pessoa não for vacinada
							addConstraint(new NotEqualConstraint<>(v,getVariables()));	
						}
						else {//Se a pessoa for vacinada
							List<Variable> nonVacAndMe = new ArrayList<>();
							nonVacAndMe.addAll(getAllNonVac());
							nonVacAndMe.addAll(getAllMe(namePerson));
							addConstraint(new NotEqualConstraint<>(v, nonVacAndMe) );	
						}
					}
				}
			}
		}
	}
	
	//Pega todas as variáveis referentes a mesma pessoa
	public List<Variable> getAllMe(String me) {
		List<Variable> allMe = new ArrayList<>();
		for (Variable v : getVariables()) {
			String namePerson = v.toString().substring(0, v.toString().length()-1);
				if (namePerson.equals(me)) {
					allMe.add(v);
				}
			
		}
		return allMe;
	}
	
	public List<Variable> getAllNonVac() {
		List<Variable> nonVac = new ArrayList<>();
		for(Variable v : getVariables()) {
			String namePerson = v.toString().substring(0, v.toString().length()-1);
			for(int i = 0; i < this.persons.size(); i++) {
				if(this.persons.get(i).name.equals(namePerson)) { //Encontra a pessoa que a variável se refere
					if(this.persons.get(i).isVaccinated()!=1) { //Se a pessoa não for vacinada
						nonVac.add(v);
					}
				}
			}
		}
		return nonVac;
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
