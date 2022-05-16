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

	public static String usage = "Uso: java -jar <caminho_jar> <caminho_arquivo_csv> [-v <numero_variante>] [-i <intervalo_de_horas>].";
	public static String example = "Exemplo: java -jar <caminho_jar> <caminho_arquivo_cs> -v 1 2 3 -i 8 15";

	public static void main(String[] args){

		List<String> variants = new ArrayList<>();
		List<String> hours_interval = new ArrayList<>();
		int c = 0;

		if(args.length < 1 || 8 < args.length){
			System.out.println(usage);
			System.exit(1);
		} else {
			List<String> variant_possible = Arrays.asList("0", "1", "2", "3");
			List<String> hours_interval__possible = new ArrayList<>();
			for(int i=1;i<=24;i++)
				hours_interval__possible.add(Integer.toString(i));
			List<String> cmd_args = Arrays.asList("-v", "-i");
			for(int i=0;i<args.length;)
				switch(args[i]){
					case "-v":
						i++;
						c = 0;
						while(i < args.length && variant_possible.contains(args[i])){
							variants.add(args[i]);
							i++;
							c++;
						}
						if(i < args.length && (!cmd_args.contains(args[i]) || 3 < c)){
							System.out.println(example);
							System.exit(1);
						}
						break;
					case "-i":
						i++;
						c = 0;
						while(i < args.length && hours_interval__possible.contains(args[i])){
							hours_interval.add(args[i]);
							i++;
							c++;
						}
						if(i < args.length && (!cmd_args.contains(args[i]) || c != 2)){
							System.out.println(example);
							System.exit(1);
						}
						break;
					default:
						i++;
						break;
				}
		}

		System.out.println("Variantes:");
		System.out.println(variants);
		System.out.println("Intervalo de horários:");
		System.out.println(hours_interval);

		Office off = new Office(args[0], variants, hours_interval);

		System.out.println("Started");

		for(Variable v : off.getVariables()){
			String s = v.toString();
			Domain<Integer> domain = off.getDomain(v);
			System.out.print(s+" ");
			System.out.println(domain);
		}
		/*

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
		if (solution.isPresent())
			System.out.println(solution.get());
		System.out.println(stepCounter.getResults() + "\n");
	
		*/

	}

}
