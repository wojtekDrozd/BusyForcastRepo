import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
	int[][] resurceTable = new int[12][9];
	
	// nazwa pliku z zasobami
	String fileName = "Lista.xlsx";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
