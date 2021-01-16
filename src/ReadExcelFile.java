import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
	
	Boolean firstIteration = true;
	
	public HashMap<String,Employee> readExcelFile(String path) {
		
		HashMap<String,Employee> employees = new HashMap<>();
		
		File file = new File(path);
		try {
			
			FileInputStream fis = new FileInputStream(file);
			
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0); 
			
			// System.out.println(path + " " + 1);
			
			Iterator<Row> itrRow = sheet.iterator();
			while(itrRow.hasNext()) {
				Row row = itrRow.next();
				Iterator<Cell> itrCell = row.cellIterator();
				int cellIndex = 0;
				String id = null, designation = null, department = null, name = null, manager_id = null;
				// System.out.println(cellIndex);
				while(itrCell.hasNext()) {
					
					Cell cell = itrCell.next();
					
					if(firstIteration) {
						firstIteration = false;
						break;
					}
					
					if(cellIndex++ == 0)
						continue;
					
					String str = null;
					
					switch (cell.getCellType())               
					{  
					case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
						// System.out.print(cell.getStringCellValue() + "\t\t\t");
						str = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
						// System.out.print(cell.getNumericCellValue() + "\t\t\t");
						str = Double.toString(cell.getNumericCellValue());
						break;
					default:
					}
					
					// System.out.print(str + "  ");
					
					switch(cellIndex - 1) {
					case 1: id = str; break;
					case 2: designation = str; break;
					case 3: department = str; break;
					case 4: name = str; break;
					case 5: manager_id = str; break;
					default:
					}
					
					// cellIndex++;
				}
				
				Employee tempEmployee = new Employee(id,designation, department, name, manager_id);
				
				employees.put(id, tempEmployee);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return employees;	
		
	}

}

class Employee {
	String id, designation, department, name, manager_id;
	Employee(String id, String designation, String department, String name, String manager_id) {
		this.id = id;
		this.designation = designation;
		this.department = department;
		this.name = name;
		this.manager_id = manager_id;
	}
	
	public String getID() {
		return id;
	}
	
	public String getManagerid() {
		return manager_id;
	}
	
	public String getName() {
		return name;
	}
}
