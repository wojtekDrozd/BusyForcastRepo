import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader {
	
	// musi czytać wszystkie pliki w katalogu których nazwa to tylko cyferki i po kolei je sprawdzac
	// musi stworzyc tablice z zespolami i gradami która bedzie aktualizowana po przejsciu kazdego zespolu w kazdym arkuszu
	public static void main(String[] args) {
		try {
			Sheet dataSheet = createDataSheet("240849.xlsx");
			printTeamTable(43, 52, 5, 17, dataSheet);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(1);
		return dataSheet;
	}

	public static void printTeamTable(int rowMin, int rowMax, int colMin, int colMax, Sheet dataSheet) {
		Cell currentCell;
		for (int i = rowMin; i < rowMax; i++) {
			for (int j = colMin; j < colMax; j++) {
				currentCell = dataSheet.getRow(i).getCell(j);
				if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					System.out.print(currentCell.getNumericCellValue() + " ");
				} else {
					System.out.print("0.0 ");
				}
			}
			System.out.println();
		}
		System.out.println("check");
	}
}
