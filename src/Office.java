import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;

public class Office extends CSP<Variable, Integer>{

	public static final Variable Alice = new Variable("Alice");
	public static final Variable Bob = new Variable("Bob");
	public static final Variable Charlie = new Variable("Charlie");
	public static final Variable David = new Variable("David");
	public static final Variable Eve = new Variable("Eve");

	public Office(){

		List<Integer> valuesAlice = new ArrayList<Integer>();
		valuesAlice.add(4);
		valuesAlice.add(13);
		valuesAlice.add(19);
		valuesAlice.add(21);
		valuesAlice.add(22);
		Domain<Integer> domainAlice = 
			new Domain<Integer>(valuesAlice);

		List<Integer> valuesBob = new ArrayList<Integer>();
		valuesBob.add(6);
		valuesBob.add(9);
		valuesBob.add(10);
		valuesBob.add(14);
		valuesBob.add(15);
		valuesBob.add(21);
		Domain<Integer> domainBob = 
			new Domain<Integer>(valuesBob);

		List<Integer> valuesCharlie = new ArrayList<Integer>();
		valuesCharlie.add(5);
		valuesCharlie.add(8);
		valuesCharlie.add(10);
		valuesCharlie.add(13);
		valuesCharlie.add(14);
		valuesCharlie.add(21);
		valuesCharlie.add(22);
		valuesCharlie.add(23);
		Domain<Integer> domainCharlie = 
			new Domain<Integer>(valuesCharlie);

		List<Integer> valuesDavid = new ArrayList<Integer>();
		valuesDavid.add(1);
		valuesDavid.add(3);
		valuesDavid.add(4);
		valuesDavid.add(5);
		valuesDavid.add(6);
		valuesDavid.add(7);
		valuesDavid.add(19);
		valuesDavid.add(23);
		Domain<Integer> domainDavid = 
			new Domain<Integer>(valuesDavid);

		List<Integer> valuesEve = new ArrayList<Integer>();
		valuesEve.add(2);
		valuesEve.add(4);
		valuesEve.add(7);
		valuesEve.add(10);
		valuesEve.add(11);
		valuesEve.add(13);
		valuesEve.add(14);
		valuesEve.add(15);
		valuesEve.add(18);
		valuesEve.add(21);
		Domain<Integer> domainEve = 
			new Domain<Integer>(valuesEve);

		addVariable(Alice);
		addVariable(Bob);
		addVariable(Charlie);
		addVariable(David);
		addVariable(Eve);

		setDomain(Alice, domainAlice);
		setDomain(Bob, domainBob);
		setDomain(Charlie, domainCharlie);
		setDomain(David, domainDavid);
		setDomain(Eve, domainEve);

	}

}
