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
	 public static void main(String[] args) {
		 try {
			 FileInputStream excelFile = new FileInputStream(new File("test_excel.xlsx"));
			 Workbook wb = new XSSFWorkbook(excelFile);
			 Sheet dataSheet = wb.getSheetAt(1);
			 Cell currentCell = dataSheet.getRow(13).getCell(5);
			 if (currentCell != null) {
				 System.out.print(currentCell.getNumericCellValue()+" ");}
			 else {
				 System.out.println("empty cell");
			 }
			 // minor change
			// System.out.println(value);
			 /*
			 FileInputStream excelFile = new FileInputStream(new File("test_excel.xlsx"));
			 Workbook workbook = new XSSFWorkbook(excelFile);
			 Sheet datatypeSheet = workbook.getSheetAt(1);
			 Iterator<Row> iterator = datatypeSheet.iterator();
			 
			 while (iterator.hasNext()) {
				 Row currentRow = iterator.next();
				 Iterator<Cell> cellIterator= currentRow.iterator();
				 while (cellIterator.hasNext()) {
					 Cell currentCell = cellIterator.next();
					 if (currentCell.getCellTypeEnum() == CellType.STRING) {
						 System.out.print(currentCell.getStringCellValue()+" ");
					 }
					 else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
						 System.out.print(currentCell.getNumericCellValue()+" ");
					 }
				 }
				 System.out.println();
			 }*/
		 } catch (FileNotFoundException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
}
