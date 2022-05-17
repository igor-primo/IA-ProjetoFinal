package office;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class DependentConstraint<VAR extends Variable, VAL> implements Constraint<Variable, Integer> {

	private Variable v;
	private Variable _v;
	private List<Person> perList;
	private List<Variable> scope;

	public DependentConstraint(Variable v, Variable _v, List<Person> pL) {
		this.v = v;
		this._v = _v;
		this.perList = pL;
		scope = new ArrayList<>(2);
		scope.add(this.v);
		scope.add(this._v);
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment<Variable, Integer> assignment) {
		String name_v = this.v.getName().substring(0, v.getName().length()-1);
		String name__v = _v.getName().substring(0, _v.getName().length()-1);
		if(!name_v.equals(name__v)){
			Integer vv = assignment.getValue(v);
			Integer v_v = assignment.getValue(_v);
			if(vv == null || v_v == null)
				return true;
			for(Person p : perList)
				if(p.getName().equals(name_v))
					for(Person _p : perList)
						if(_p.getName().equals(name__v))
							if(p.getDependeDe() == _p.getId())
								return v_v < vv;
		}
		return true;
	}
}
