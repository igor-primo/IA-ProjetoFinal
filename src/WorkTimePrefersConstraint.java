import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class WorkTimePrefersConstraint<VAR extends Variable, VAL> implements Constraint<Variable, String>{

	private List<Variable> varList;
	private List<Person> persons;
	private List<Variable> scope;

	public WorkTimePrefersConstraint(List<Variable> varList, List<Person> persons){

		this.varList = varList;
		this.persons = persons;
		scope = new ArrayList<>(1);

	}

	@Override
	public List<Variable> getScope() { return scope; }

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, String> assignment){

		boolean condition = true;
		for(Variable v : varList){
			String value = assignment.getValue(v);
			int hour = Integer.parseInt(v.toString());
			List<Integer> hours = new ArrayList<>();
			for(Person p : persons)
				if(p.getName().equals(value))
					hours = p.getHours();

			condition = condition && hours.contains(hour);
		}
		
		return condition;
			
	}

}
