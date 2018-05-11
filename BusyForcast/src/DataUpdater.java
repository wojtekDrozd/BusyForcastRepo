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
	
	public DataUpdater(){}
	
	public static void update()
	{
		FileInputStream excelFile;
		FileOutputStream excelOutFile;
		DataReader reader = new DataReader();
		try {
			excelFile = new FileInputStream(new File("test_wynik.xlsx"));
			Workbook wb = new XSSFWorkbook(excelFile);
			Sheet dataSheet = wb.getSheetAt(0);
			Row sheetrow;
			Cell currentCell;
			int row;
			int col;

			for (int i = 1; i < 118; i++) {
				row = i-1;
				col = 1;
				sheetrow = dataSheet.getRow(i);
				if (sheetrow == null) {
					sheetrow = dataSheet.createRow(i);
				}
				for (int j = 3; j < 26; j += 2) {
					
					currentCell = dataSheet.getRow(i).getCell(j);
					if (currentCell == null) {
						currentCell = sheetrow.createCell(j);
					}

					currentCell.setCellValue(reader.dataTable[row][col]);
					col += 1;
				}
			}
			excelFile.close();
			excelOutFile = new FileOutputStream(new File("test_wynik.xlsx"));
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
