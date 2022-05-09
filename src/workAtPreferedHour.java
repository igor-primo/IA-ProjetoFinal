import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class workAtPreferedHour<VAR extends Variable, VAL> implements Constraint<Variable, String>{
	private Variable t;
	private List<Variable> scope;

	workAtPreferedHour(Variable t){
		this.t = t;
		scope = new ArrayList<>(1);
	}

	@Override
	public List<Variable> getScope(){ return scope; }

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, String> assignment){
		String assigned = assignment.getValue(t);
		//System.out.println(assigned);
		return true;
	}
}
