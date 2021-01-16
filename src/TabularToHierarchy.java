import java.util.*;

public class TabularToHierarchy {
	
	final static String path = "F:\\Engineering\\Assignment\\hierarchy_case\\hierarchy_case_20May2020.xlsx";

	public static void main(String[] args) {
		
		// Reading the Excel File
		
		ReadExcelFile ref = new ReadExcelFile();
		HashMap<String,Employee> employees = ref.readExcelFile(path);
		String str = null;
		employees.remove(str);
		
		// Manager -> All those who report to him/her (HashMap<String,List<String>>)
		
		HashMap<String,LinkedList<String>> reportees = new HashMap<>();
		
		Set<String> noManager = new HashSet<>(employees.keySet()); // Employees who do not have a manager
		
		for(String emp : employees.keySet()) {
			String manager = employees.get(emp).getManagerid();
			
			if(manager != null) {
				noManager.remove(emp);
			}
			
			if(reportees.containsKey(manager)) {
				reportees.get(manager).add(emp);
			} else {
				LinkedList<String> tempList = new LinkedList<String>();
				tempList.add(emp);
				reportees.put(manager, tempList);
			}
		}
		
		StringBuilder JSON = new StringBuilder("{");
		boolean first = true;
		for(String emp : noManager) {
			if(first) {
				first = false;
			} else {
				JSON.append(",");
			}
			JSON.append("employeeID: " + emp + ", name: " + employees.get(emp).getName() + ", reportees: " + findReportees(employees, reportees, emp) + "}");
		}
		
		System.out.println(JSON);
		
	}
	
	private static String findReportees(HashMap<String,Employee> employees, HashMap<String,LinkedList<String>> reportees, String emp) {
		
		if(!reportees.containsKey(emp)) { // If emp is not a manager, return null
			return new String();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for(String reportee : reportees.get(emp)) {
			StringBuilder temp = new StringBuilder();
			if(first) {
				first = false;
			} else {
				temp.append(",");
			}
			temp.append("{employeeID: " + reportee + ", name: " + employees.get(reportee).getName() + ", reportees: " + findReportees(employees, reportees, reportee) + "}");
			sb.append(temp);
		}
		
		sb.append("],");
		return sb.toString();
		
	}

}
