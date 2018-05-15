import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ResourceReader {

	/*
	 * zasoby będą pakowane do tablicy o nastepujacej strukturze team grd1 grd2 grd3
	 * ... grd9 struct 0 3 3 1 bridge (bez pierwszej kolumny)
	 */
	double[][] resourceTable = new double[12][9];

	// nazwa pliku z zasobami
	String fileName = "Lista.xlsx";
	public ResourceReader() {
		
		try {
			Sheet dataSheet = createDataSheet(fileName);
			int i = 0;
			Cell teamCell;

			String teamName;
			for (Row row : dataSheet) {

				teamCell = dataSheet.getRow(i).getCell(6);
				//System.out.print(teamCell.getStringCellValue() + "\n");
				teamName = teamCell.getStringCellValue();

				// wyrzucenie wiersza nagłówka z analizy
				if (i > 0) {
					updateFromRow(teamName, dataSheet, i, resourceTable);
				}
				i += 1;
			}
			printResourceTable(resourceTable);
			calculateDays(21,resourceTable);
			printResourceTable(resourceTable);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void calculateDays(int daysForMonth, double[][]resourceTable) {
		for (int i = 0; i<12;i++) {
			for (int j = 0; j<9;j++) {
				resourceTable[i][j] = resourceTable[i][j]*daysForMonth;
			}
		}
	}
	
	public static void printResourceTable(double[][] resourceTable) {
		for (int i = 0; i<12;i++) {
			for (int j = 0; j<9;j++) {
				System.out.print(resourceTable[i][j]+" ");
			}
			System.out.println();
		}
	}
	

	// metoda przyjmuje team z wiersza, arkusz, numer wiersza i tabelę zasobów która aktualizuje
	public static void updateFromRow(String teamName, Sheet dataSheet, int i, double[][] resourceTable) {
		Cell gradeCell;
		int grade;
		int teamNumber;
		gradeCell = dataSheet.getRow(i).getCell(7);
		//System.out.print((int) gradeCell.getNumericCellValue() + "\n");
		grade = (int) gradeCell.getNumericCellValue();
		switch (teamName) {
		case "Administration":
			teamNumber = 11;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Bridges":
			teamNumber = 9;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Electrical":
			teamNumber = 4;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Environmental(inc Ecological Sustainable Design)":
			teamNumber = 8;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Geotechnical":
			teamNumber = 6;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Highways":
			teamNumber = 10;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Management Consultancy":
			teamNumber = 5;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Mechanical":
			teamNumber = 2;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Project Management(inc Programme Management and Construction Admin)":
			teamNumber = 1;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Public Health/Plumbing":
			teamNumber = 3;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Structural":
			teamNumber = 0;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
		case "Water":
			teamNumber = 7;
			switchOnGrade(grade,resourceTable,teamNumber);
			break;
			
		}
	}
	
	// metoda przyjmuje grade, tabele zasobow, i numer teamu i na tej podstawie aktualizuje table zasobow
	public static void switchOnGrade(int grade, double[][] resourceTable, int teamNumber) {
		switch (grade) {
		case 1:
			resourceTable[teamNumber][0] += 1;
			break;
		case 2:
			resourceTable[teamNumber][1] += 1;
			break;
		case 3:
			resourceTable[teamNumber][2] += 1;
			break;
		case 4:
			resourceTable[teamNumber][3] += 1;
			break;
		case 5:
			resourceTable[teamNumber][4] += 1;
			break;
		case 6:
			resourceTable[teamNumber][5] += 1;
			break;
		case 7:
			resourceTable[teamNumber][6] += 1;
			break;
		case 8:
			resourceTable[teamNumber][7] += 1;
			break;
		case 9:
			resourceTable[teamNumber][8] += 1;
			break;
		}
	}

	// zwraca pierwszy arkusz z podanego excela
	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(0);
		return dataSheet;
	}

}
