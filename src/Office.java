import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Office extends CSP<Variable, String>{

	int hours = 24;

	public Office(){

		List<Person> persons = new ArrayList<>();
		persons.add(new Person(
					"Alice", 
					Arrays.asList(new Integer[]{4, 13, 19, 21, 22}),
					2
					));
		persons.add(new Person(
					"Bob", 
					Arrays.asList(new Integer[]{6, 9, 10, 14, 15, 21}),
					3
					));
		persons.add(new Person(
					"Charlie", 
					Arrays.asList(new Integer[]{5, 8, 10, 13, 14, 21, 22, 23}),
					1
					));
		persons.add(new Person(
					"David", 
					Arrays.asList(new Integer[]{1, 3, 4, 5, 6, 7, 19, 23}),
					2
					));
		persons.add(new Person(
					"Eve", 
					Arrays.asList(new Integer[]{2, 4, 7, 10, 11, 13, 14, 15, 18, 21}),
					4
					));

		for(int i=0;i<hours;i++){
			Variable v = new Variable(Integer.toString(i+1));
			addVariable(v);
		}

		for(Variable v : getVariables()){
			int vint = Integer.parseInt(v.toString()); 		//Inteiro representando a hora
			List<String> domainValues = new ArrayList<>(); 	//Lista de valores de domínio para a hora
			for(Person p : persons){						//Para cada pessoa			
				if(p.getHours().contains(vint))				//Verifique se a hora é uma em que ele pode trabalhar
					domainValues.add(p.getName());			//Se sim, adicione na lista de valores de domínio

			}
			Domain<String> domain = new Domain<>(domainValues);
			setDomain(v, domain);
		}

		//Restricoes

		List<Variable> variablesList = getVariables();
		addConstraint(new WorkTimePrefersConstraint<>(variablesList, persons));

	}

}
