package office;
import java.util.ArrayList;
import java.util.List;

public class Result {
	String name;
	List <Integer>hours_work = new ArrayList<>(); 
	
	public Result(String name, Integer hours_work) {
		this.name = name;
		this.hours_work.add(hours_work);
	}
	
	public void addHour(int hour) {
		this.hours_work.add(hour);
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Integer> getHoursWork(){
		return this.hours_work;
	}

}
