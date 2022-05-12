package office;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Office extends CSP<Variable, Integer>{

	int hours = 24;

	public Office(String path){

		List<Person> persons = new ArrayList<>();
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null){
				String[] lineSplit = line.split(splitBy);
				if(lineSplit.length == 26){
					//System.out.println("Nome: "+lineSplit[0]);
					//System.out.println("Carga horária: "+lineSplit[1]);
					List<Integer> hours = new ArrayList<>();
					for(int i=2;i<26;i++)
						if(lineSplit[i].equals("1"))
							hours.add(i-1);
					persons.add(new Person(lineSplit[0], hours, Integer.parseInt(lineSplit[1])));
				} else{
					System.out.println("Número de argumentos na linha "+line+" está errado.");
					System.exit(1);
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}

		//cria uma variavel para cada hora que a pessoa tem que trabalhar
		for (int i = 0; i < persons.size(); i++) {
			for (int j = 0; j < persons.get(i).required_hours; j++) {
				Variable v = new Variable(persons.get(i).name+j);
				addVariable(v);
				Domain<Integer> domain = new Domain<>(persons.get(i).getHours());
				setDomain(v, domain);

			}
		}

		//Restricoes
		for(Variable v : getVariables()) {
				addConstraint(new NotEqualConstraint<>(v,getVariables()));
		}

	}

}
