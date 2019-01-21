package com.arup.busyforecast;
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

public class ResourceUpdater {
	
	public ResourceUpdater() {}

	public static void update() {
		FileInputStream excelFile;
		FileOutputStream excelOutFile;
		ResourceReader reader = new ResourceReader();
		try {
			excelFile = new FileInputStream(new File("test_wynik.xlsx"));
			Workbook wb = new XSSFWorkbook(excelFile);
			Sheet dataSheet = wb.getSheetAt(0);
			Row sheetrow;
			Cell currentCell;
			int row;
			int col;
			int j;
			double[][] resourceColumn = new double[118][1];

			for (int i = 0; i < 108; i++) {
				row = i / 9;
				col = i % 9;
				resourceColumn[i][0] = reader.resourceTable[row][col];
				System.out.println(resourceColumn[i][0]);
			}

			for (int i = 1; i < 109; i++) {

				row = i - 1;

				sheetrow = dataSheet.getRow(i);
				if (sheetrow == null) {
					sheetrow = dataSheet.createRow(i);
				}

				for (j = 4; j < 27; j += 2) {
					currentCell = dataSheet.getRow(i).getCell(j);
					if (currentCell == null) {
						currentCell = sheetrow.createCell(j);
					}
					currentCell = dataSheet.getRow(i).getCell(j);
					currentCell.setCellValue(resourceColumn[row][0]);

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
