import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewReadTest {
	
	static double[][] StruTable = new double[9][12];
	
	public static void main(String[] args) {
		try {
			Sheet dataSheet = createDataSheet("312235.xlsx");
			System.out.println("Read");
			StruTable = createTeamTable("Structural", dataSheet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("OK");
	}

	
	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(1);
		return dataSheet;
	}
	
	public static double[][] createTeamTable(String teamName, Sheet dataSheet) {
		Cell currentCell;
		Cell daysCell;
		double[][] teamTable = new double[9][12];
		for (int i= 0; i<9; i++) {
			for(int j=0;j<12; j++) {
				teamTable[i][j]=0;
			}
		}
		// i = 10 - od dziesiątego do 60 wiersza sprawdzamy w excelu z templatem
		for (int i = 10; i < 60; i++) {
			currentCell = dataSheet.getRow(i).getCell(1);
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
				if (currentCell.getStringCellValue().equals(teamName)) {
					int grade = (int) dataSheet.getRow(i).getCell(2).getNumericCellValue();
					System.out.println(currentCell.getStringCellValue());
					System.out.println(grade);

					// j = 6 od 6 do 18 kolumny (licząc od 0) sprawdzamy w excelu w poszukiwaniu dniówek
					for (int j =6; j<18;j++) {
						daysCell = dataSheet.getRow(i).getCell(j);
						if (daysCell != null && daysCell.getCellTypeEnum() == CellType.NUMERIC) {	
							teamTable[grade-1][j-6] = teamTable[grade-1][j-6]+ daysCell.getNumericCellValue();
						}
					}
				}
			} 
	
		}
		
//		 System.out.println(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 12;
//		 j++) { System.out.print(teamTable[i][j] + " "); } System.out.println(); }
//		 
//		System.out.println("check");
		
		return teamTable;
	}
}
