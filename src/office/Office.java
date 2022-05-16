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

	public Office(
			String path, 
			List<String> variants, 
			List<String> hours_interval
	){

		List<Person> persons = new ArrayList<>();
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null){
				String[] lineSplit = line.split(splitBy);
				if(lineSplit.length == 29){
					int id = Integer.parseInt(lineSplit[0]);
					String nome = lineSplit[1];
					boolean is_vacinado = lineSplit[2].equals("1") ? true : false;
					int carga_horaria = Integer.parseInt(lineSplit[3]);
					int depende_de = Integer.parseInt(lineSplit[4]);
					List<Integer> hours = new ArrayList<>();
					for(int i=5;i<29;i++)
						if(lineSplit[i].equals("1"))
							hours.add(i-4);
					persons.add(new Person(
								id,
								nome,
								is_vacinado,
								carga_horaria,
								depende_de,
								hours
							));
				} else{
					System.out.println("Número de argumentos na linha "+line+" está errado.");
					System.exit(1);
				}
			}
		} catch(IOException e){
			e.printStackTrace();
		}

		//cria uma variavel para cada pessoa 
		//o domínio é o conjunto de horas em que que ela prefere trabalhar
		for (int i = 0; i < persons.size(); i++) {
			for (int j = 0; j < persons.get(i).required_hours; j++) {
				Variable v = new Variable(persons.get(i).name+j);
				addVariable(v);
				Domain<Integer> domain = new Domain<>(persons.get(i).getHours());
				setDomain(v, domain);

			}
		}

		//Restricoes (caso sem variantes ou variante 0
		for(Variable v : getVariables())
				addConstraint(new NotEqualConstraint<>(v, getVariables()));

		if(variants.contains("1")){
			//Adicionar variante 1 como classe
		}

		if(variants.contains("2")){
			//Adicionar variante 2 como classe
		}

		if(variants.contains("3")){
			//Adicionar variante 3 como classe
		}

	}

}
