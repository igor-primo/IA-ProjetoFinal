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

public class Program{

	public static String usage = "Uso: java -jar <caminho_jar> <caminho_arquivo_csv> [-v <numero_variante>] [-i <intervalo_de_horas>]";
	public static String example = "Exemplo: java -jar <caminho_jar> <caminho_arquivo_cs> -v 1 2 3 -i 8 15";

	public static void main(String[] args){

		List<String> variants = new ArrayList<>();
		List<String> hours_interval = new ArrayList<>();
		int c = 0;
		boolean debug = false;

		if(args.length < 1 || 8 < args.length){
			System.out.println(usage);
			System.exit(1);
		} else {
			List<String> variant_possible = Arrays.asList("1", "2", "3");
			List<String> hours_interval_possible = new ArrayList<>();
			for(int i=1;i<=24;i++)
				hours_interval_possible.add(Integer.toString(i));
			List<String> cmd_args = Arrays.asList("-v", "-i", "-d");
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
						if(3 < c || (i < args.length && !cmd_args.contains(args[i]))){
							System.out.println(example);
							System.exit(1);
						}
						break;
					case "-i":
						i++;
						c = 0;
						while(i < args.length && hours_interval_possible.contains(args[i])){
							hours_interval.add(args[i]);
							i++;
							c++;
						}
						if(2 != c || (i < args.length && !cmd_args.contains(args[i]))){
							System.out.println(example);
							System.exit(1);
						}
						break;
					case "-d":
						debug = true;
						i++;
						break;
					default:
						i++;
						break;
				}
		}

		if((!hours_interval.isEmpty() 
				&& !variants.contains("3"))
					|| (hours_interval.isEmpty() 
						&& variants.contains("3"))){
			System.out.println("Os argumentos \"-v 3\" e \"-i\" precisam ser informadas conjuntamente. Abortando.");
			System.exit(1);
		}

		System.out.println("Variantes:");
		System.out.println(variants);
		System.out.println("Intervalo de horários:");
		System.out.println(hours_interval);

		if(!variants.isEmpty())
			System.out.println("\nSobre as variantes consideradas:\n");
		if(variants.contains("1")){
			System.out.println("1 - Prioridade no trabalho. Considerar-se-á que algum(s) ");
			System.out.println("trabalhador(es) terá que esperar que algum outro termine.\n");
		}
		if(variants.contains("2")){
			System.out.println("2 - Nova normalidade. Considerar-se-á que ");
			System.out.println("trabalhadores vacinados podem trabalhar no mesmo ");
			System.out.println("local ao mesmo tempo e que trabalhadores não vacinados trabalham sozinhos.\n");
		}
		if(variants.contains("3")){
			System.out.println("3 - Restrição de horas no ofício. Considerar-se-á como ");
			System.out.println("restrição um intervalo de horas especificado pelo usuário.");
			System.out.println("O intervalo padrão é entre 1 (inclusive) e 24 (inclusive) horas.\n");
		}

		System.out.println("Começou.\n");

		if(debug){
			Office off = new Office(args[0], variants, hours_interval);
			for(Variable v : off.getVariables()){
				String s = v.toString();
				Domain<Integer> domain = off.getDomain(v);
				System.out.print(s+" ");
				System.out.println(domain);
			}
		}

		CSP<Variable, Integer> csp = new Office(args[0], variants, hours_interval);
		CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, Integer> solver;
		Optional<Assignment<Variable, Integer>> solution;
		
		/*
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
		*/

		csp = new Office(args[0], variants, hours_interval);
		if(debug) System.out.println("-Scheduling (Backtracking)");
		solver = new FlexibleBacktrackingSolver<>();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if(debug){
			if (solution.isPresent())
				System.out.println(solution.get());
			System.out.println(stepCounter.getResults() + "\n");
		}
	
		if(solution.isPresent()){
			for(int hour=1;hour<=24;hour++)
				for(Variable v: solution.get().getVariables())
					if(solution.get().getValue(v) == hour)
						System.out.println(Integer.toString(hour) + " " + v.toString());
		} else
			System.out.println("Não há solução dadas as restrições atuais.");

	}

}
