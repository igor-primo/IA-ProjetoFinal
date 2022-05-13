package office;
import aima.core.search.csp.*;
import aima.core.search.csp.solver.CspListener;
import aima.core.search.csp.solver.CspSolver;
import aima.core.search.csp.solver.FlexibleBacktrackingSolver;
import aima.core.search.csp.solver.MinConflictsSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.util.Optional;

public class Program2{

	public static void main(String[] args){

		if(args.length < 1 || 1 < args.length){
			System.out.println("A aplicação precisa de exatamente 1 caminho de arquivo como argumento de linha de comando.");
			System.exit(1);
		}

		Office off = new Office(args[0]);

		System.out.println("Started");

		for(Variable v : off.getVariables()){
			String s = v.toString();
			Domain<Integer> domain = off.getDomain(v);
			System.out.print(s+" ");
			System.out.println(domain);
		}

		CSP<Variable, Integer> csp = new Office(args[0]);
		CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, Integer> solver;
		Optional<Assignment<Variable, Integer>> solution;
		
		System.out.println("-Scheduling (Min-Conflicts)");
		solver = new MinConflictsSolver<>(1000);
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println((solution.get().isSolution(csp) ? ":-) " : ":-( ") + solution.get());
		System.out.println(stepCounter.getResults() + "\n");

		
		System.out.println("-Scheduling (Backtracking + MRV & DEG + LCV + AC3)");
		solver = new FlexibleBacktrackingSolver<Variable, Integer>().setAll();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println(solution.get());
		System.out.println(stepCounter.getResults() + "\n");

		csp = new Office(args[0]);
		System.out.println("-Scheduling (Backtracking)");
		solver = new FlexibleBacktrackingSolver<>();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent()){
			//System.out.println(solution.get().toString());
			for(Variable v: solution.get().getVariables()){
				int hour = solution.get().getValue(v);
				System.out.println(v.toString() + " " + Integer.toString(hour));
			}
		}
		System.out.println(stepCounter.getResults() + "\n");
	

	}

}
