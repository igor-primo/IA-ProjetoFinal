package office;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/**
 * Represents a binary constraint which forbids equal values.
 * 
 * @author Ruediger Lunde
 */
public class AfterThanConstraint<VAR extends Variable, VAL> implements Constraint<Variable, Integer> {

	private Variable var1;
	private List<Variable> varList;
	private List<Variable> scope;

	public AfterThanConstraint(Variable var1, List<Variable> vL) {
		this.var1 = var1;
		this.varList = vL;
		scope = new ArrayList<>(vL.size());
		for(Variable v : vL) {
			scope.add(v);
		}
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
		Integer value1 = assignment.getValue(var1);
		if(value1 == null) {
			return true;
		}
		
		for(Variable v : varList) {
			if(assignment.getValue(v) == null) {
				return true;
			}
			if(value1.compareTo(assignment.getValue(v))<0){
				
				return false;
			}
		}
		return true;
	}
}
