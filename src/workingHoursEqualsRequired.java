import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class workingHoursEqualsRequired<VAR extends Variable, VAL> implements Constraint<Variable, String>{
	private List<Person> pL;
	private List<Variable> vL;
	private List<Variable> scope;

	workingHoursEqualsRequired(List<Person> pL, List<Variable> vL){
		this.pL = pL;
		this.vL = vL;
		scope = new ArrayList<>(vL.size());
		for(Variable v : vL)
			scope.add(v);
	}

	@Override
	public List<Variable> getScope(){ return scope; }

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, String> assignment){
		List<String> assigned = new ArrayList<>();
		for(Variable v : vL)
			assigned.add(assignment.getValue(v));
		//for(String s : assigned)
			//System.out.print(s + " ");
		//System.out.println("");
		for(Person p : pL){
			int cont = 0;
			for(String s : assigned)
				if(s.equals(p.getName()))
					cont++;
			if(cont != p.required_hours)
				return false;
		}
		return true;
	}
}
