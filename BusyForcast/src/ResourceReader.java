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
	
	/* zasoby będą pakowane do tablicy o nastepujacej strukturze
	 * team grd1 grd2 grd3 ... grd9
	 * struct 0 	3    3		 1
	 * bridge 
	 * (bez pierwszej kolumny)
	 */
	
	
	public static void main(String[] args) {
		int[][] resurceTable = new int[12][9];
		
		// nazwa pliku z zasobami
		String fileName = "Lista.xlsx";
		try {
			Sheet dataSheet = createDataSheet(fileName);
			int i = 0;
			Cell teamCell;
			Cell gradeCell;
			int grade;
			for (Row row : dataSheet) {
				
				
				teamCell = dataSheet.getRow(i).getCell(6);
				System.out.print(teamCell.getStringCellValue() + "\n");
				
				// z tego trzeba zrobić metodę która będzie w argumencie przyjmowała obecny team
				// i aktualizowała reasourceTable
				if (teamCell.getStringCellValue().equals("Administration")) {
					gradeCell = dataSheet.getRow(i).getCell(7);
					System.out.print((int)gradeCell.getNumericCellValue() + "\n");
					grade = (int) gradeCell.getNumericCellValue();
					switch(grade) {
					case 1: resurceTable[0][0] +=1;
						break;
					case 2: resurceTable[0][1] +=1;
						break;
					case 3: resurceTable[0][2] +=1;
						break;
					case 4: resurceTable[0][3] +=1;
						break;
					case 5: resurceTable[0][4] +=1;
						break;
					case 6: resurceTable[0][5] +=1;
						break;
					case 7: resurceTable[0][6] +=1;
						break;
					case 8: resurceTable[0][7] +=1;
						break;
					case 9: resurceTable[0][8] +=1;
						break;
					}
				}
				i+=1;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// zwraca pierwszy arkusz z podanego excela
		public static Sheet createDataSheet(String fileName) throws IOException {
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook wb = new XSSFWorkbook(excelFile);
			Sheet dataSheet = wb.getSheetAt(0);
			return dataSheet;
		}

		/*
		// tworzy liste ilości gradów od 1 do 9 z danymi dla konkretnego team u na podstawie excela z danymi
		public static double[][] createTeamTable(int rowMin, int rowMax, int colMin, int colMax, Sheet dataSheet) {
			Cell currentCell = dataSheet.getRow(1).getCell(6);
			int[] teamList = new int[9];
			int countRow = 0;
			int countCol = 0;
			while (currentCell != null) {
				
			}
			for (int i = rowMin; i < rowMax; i++) {
				countCol = 0;
				for (int j = colMin; j < colMax; j++) {
					currentCell = dataSheet.getRow(i).getCell(j);
					if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						// System.out.print(currentCell.getNumericCellValue() + " ");
						teamTable[countRow][countCol] = currentCell.getNumericCellValue();
					} else {
						// System.out.print("0.0 ");
						teamTable[countRow][countCol] = 0;
					}
					countCol += 1;
				}
				// System.out.println();
				countRow += 1;
			}
			/*
			 * System.out.println(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 12;
			 * j++) { System.out.print(teamTable[i][j] + " "); } System.out.println(); }
			 
			// System.out.println("check");

			return teamTable;
		}*/
}
