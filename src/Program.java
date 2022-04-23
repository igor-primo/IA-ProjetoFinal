import aima.core.search.csp.CSP;
import aima.core.search.csp.Variable;
import aima.core.search.csp.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Program{

	public static void main(String[] args){

		Office off = new Office();

		System.out.println("Started");

		for(Variable v : off.getVariables()){
			String s = v.toString();
			Domain<String> domain = off.getDomain(v);
			System.out.print(s+" ");
			System.out.println(domain);
		}

	}

}
