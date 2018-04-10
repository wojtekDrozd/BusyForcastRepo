import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataUpdater {

	public static void main(String[] args) {
		FileInputStream excelFile;
		FileOutputStream excelOutFile;
		try {
			excelFile = new FileInputStream(new File("test_excel.xlsx"));
			Workbook wb = new XSSFWorkbook(excelFile);
			Sheet dataSheet = wb.getSheetAt(0);
			Row sheetrow;
			Cell currentCell;

			for (int i = 1; i < 109; i++) {
				sheetrow = dataSheet.getRow(i);
				if (sheetrow == null) {
					sheetrow = dataSheet.createRow(i);
				}
				for (int j = 3; j < 27; j += 2) {
					currentCell = dataSheet.getRow(i).getCell(j);
					if (currentCell == null) {
						currentCell = sheetrow.createCell(j);
					}

					currentCell.setCellValue(3.0);
				}
			}
			excelFile.close();
			excelOutFile = new FileOutputStream(new File("test_excel.xlsx"));
			wb.write(excelOutFile);
			excelOutFile.close();
			System.out.println("check");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
