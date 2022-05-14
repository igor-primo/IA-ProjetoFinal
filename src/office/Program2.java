package office;
import aima.core.search.csp.*;
import aima.core.search.csp.solver.CspListener;
import aima.core.search.csp.solver.CspSolver;
import aima.core.search.csp.solver.FlexibleBacktrackingSolver;
import aima.core.search.csp.solver.MinConflictsSolver;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;


public class Program2{

	public static void main(String[] args) {

		// Files
		File f0 = getFile("file0.csv");
		File f0n =getFile("file0noSol.csv");
		File f2 = getFile("file2.csv");

		// Offices
		Office off0 = new Office(f0, 0);
		Office off0n = new Office(f0n, 0);// no solution
		Office off2 = new Office(f2, 2);// Alice, Bob e Eve são vacinados

		// CSP parameters
		CSP<Variable, Integer> csp0 = new Office(f0, 0);
		CSP<Variable, Integer> csp0n = new Office(f0n, 0);
		CSP<Variable, Integer> csp2 = new Office(f2, 2);
		CspListener.StepCounter<Variable, Integer> stepCounter = new CspListener.StepCounter<>();
		CspSolver<Variable, Integer> solver = null;
		Optional<Assignment<Variable, Integer>> solution = null;

		// Execution

		// Office file0
		System.out.println("Main Office Started\n");

		for (Variable v : off0.getVariables()) {
			String s = v.toString();
			Domain<Integer> domain = off0.getDomain(v);
			System.out.print(s + " ");
			System.out.println(domain);
		}
		startBacktracking(csp0, stepCounter, solver, solution);

		// Office file0noSol
		System.out.println("Main Office Started\n");

		for (Variable v : off0n.getVariables()) {
			String s = v.toString();
			Domain<Integer> domain = off0n.getDomain(v);
			System.out.print(s + " ");
			System.out.println(domain);
		}
		startBacktracking(csp0n, stepCounter, solver, solution);

		// Office file2
		System.out.println("Variant 2 Office Started\n");

		for (Variable v : off2.getVariables()) {
			String s = v.toString();
			Domain<Integer> domain = off2.getDomain(v);
			System.out.print(s + " ");
			System.out.println(domain);
		}
		System.out.println("Vacinados: "+off2.getAllNamesVac());
		startBacktracking(csp2, stepCounter, solver, solution);

	}
	
	public static File getFile(String filename) {
		String path = Program2.class.getResource(filename).getFile();

		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		File f2 = new File(path);
		
		if(f2.toString().length()< 1){
			System.out.println("Arquivo"+filename+" não existente.");
			System.exit(1);
		}
		
		return f2;
	}
	
	//tentiva de ler executando o .jar
	public File getFile2(String filename) {
		File f2 = null;
		java.net.URL url = getClass().getResource(filename);
		try {
			f2 = new File(url.toURI());

		}
		catch(Exception e) {
		}

		return f2;
	}
	
	public static void startMinConflicts(CSP<Variable, Integer> csp, CspListener.StepCounter<Variable, Integer> stepCounter ,CspSolver<Variable, Integer> solver, Optional<Assignment<Variable, Integer>> solution) {
		System.out.println("-Scheduling (Min-Conflicts)");
		solver = new MinConflictsSolver<>(1000);
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println((solution.get().isSolution(csp) ? ":-) " : ":-( ") + solution.get());
		System.out.println(stepCounter.getResults() + "\n");
	}
	
	public static void startBacktrackingWithInference(CSP<Variable, Integer> csp, CspListener.StepCounter<Variable, Integer> stepCounter ,CspSolver<Variable, Integer> solver, Optional<Assignment<Variable, Integer>> solution) {
		System.out.println("-Scheduling (Backtracking + MRV & DEG + LCV + AC3)");
		solver = new FlexibleBacktrackingSolver<Variable, Integer>().setAll();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println(solution.get());
		System.out.println(stepCounter.getResults() + "\n");
	}
	
	public static void startBacktracking(CSP<Variable, Integer> csp, CspListener.StepCounter<Variable, Integer> stepCounter ,CspSolver<Variable, Integer> solver, Optional<Assignment<Variable, Integer>> solution) {
		System.out.println("\n-Scheduling (Backtracking)");
		solver = new FlexibleBacktrackingSolver<>();
		solver.addCspListener(stepCounter);
		stepCounter.reset();
		solution = solver.solve(csp);
		if (solution.isPresent())
			System.out.println(solution.get());
		System.out.println(stepCounter.getResults() + "\n");
	}


}
