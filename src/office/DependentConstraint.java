package office;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class DependentConstraint<VAR extends Variable, VAL> implements Constraint<Variable, Integer> {

	private List<Variable> varList;
	private List<Person> perList;
	private List<Variable> scope;

	public DependentConstraint(List<Variable> vL, List<Person> pL) {
		this.varList = vL;
		this.perList = pL;
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
		for(Variable v : varList)
			for(Variable _v : varList){
				String name_v = v.getName().substring(0, v.getName().length()-1);
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
									if(p.getDependeDe() == _p.getId()
											&& vv < v_v)
										return true;
									else
										return false;
				}
				return false;
			}
		return false;
	}
}
