package office;
import aima.core.search.csp.*;
import aima.core.search.csp.solver.CspListener;
import aima.core.search.csp.solver.CspSolver;
import aima.core.search.csp.solver.FlexibleBacktrackingSolver;
import aima.core.search.csp.solver.MinConflictsSolver;
import aima.core.search.csp.solver.inference.AC3Strategy;

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
		solver = new FlexibleBacktrackingSolver<Variable, Integer>().set(new AC3Strategy());
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if(debug){
			if (solution.isPresent())
				System.out.println(solution.get());
			System.out.println(stepCounter.getResults() + "\n");
		}
	
		if(solution.isPresent()){
			int hel = 0;
			List<Result> persons = new ArrayList<>();
			List<String> names = new ArrayList<>();
			int maior = 0;
			String saida = "";
			for(Variable v: solution.get().getVariables()) {
				ArrayList<Character> list = new ArrayList<Character>();
				for(int i = 0; i < v.toString().length(); i++) {
					char current = v.toString().charAt(i);
					if(current != '0' && current != '1' && current != '2' && current != '3' && current != '4' && current != '5' && current != '6' && current != '7' && current != '8'
							&& current != '9') list.add(current);
					saida = "";
					for(char h: list) saida = saida + h +"";
				}
				if(!names.contains(saida)) {
					if(saida.length()>maior) maior = saida.length();
					names.add(saida);
					persons.add(new Result(saida, solution.get().getValue(v)));
					hel++;
				}else 
					for(int i = 0; i < hel; i++) 
						if (persons.get(i).name.equals(saida)) persons.get(i).addHour(solution.get().getValue(v));
			}
			maior +=10;
			for(int i = 0;i<maior;i++) System.out.print(" ");
			for(int i = 1;i<=24;i++) System.out.print(i+" ");
			System.out.println();
			List <Integer> hours_work = new ArrayList<>();
			for(int i = 0; i < hel; i++) {
				System.out.print(persons.get(i).name);
				for(int j = 0;j<maior-persons.get(i).name.length();j++)
					System.out.print(" ");
				for(int j = 1; j<=9;j++) {
					if(persons.get(i).hours_work.contains(j))
						System.out.print(1+" ");
					else System.out.print(0 + " ");
				}
				System.out.print(" ");
				for(int j = 10; j<=24;j++) {
					if(persons.get(i).hours_work.contains(j))
						System.out.print(1+"  ");
					else System.out.print(0 + "  ");
				}
				System.out.println();
			}
		} else
			System.out.println("Sem solucao para essas restricoes");
	}
}