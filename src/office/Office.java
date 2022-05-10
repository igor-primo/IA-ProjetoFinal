package office;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Office extends CSP<Variable, Integer>{

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

		//cria uma variável para cada hora que a pessoa tem que trabalhar
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
				addConstraint(new NotEqualConstraint(v,getVariables()));
		}

	}

}
