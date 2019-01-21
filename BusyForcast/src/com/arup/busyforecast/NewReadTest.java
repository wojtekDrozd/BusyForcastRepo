package com.arup.busyforecast;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewReadTest {

	static double[][] StruTable = new double[9][12];
	static Calendar c = Calendar.getInstance();
	static int currYear = c.get(Calendar.YEAR);
	static int currMonth = c.get(Calendar.MONTH) + 1;

	public static void main(String[] args) {

		try {
			Sheet dataSheet = createDataSheet("312237.xlsx");
			System.out.println("Read");
			StruTable = createTeamTable("Bridges", dataSheet);
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

		// get project start date
		currentCell = dataSheet.getRow(8).getCell(6);
		String projDate = new SimpleDateFormat("MM/dd/yyyy").format(currentCell.getDateCellValue());
		int projYear = Integer.parseInt(projDate.substring(6, 10));
		int projMonth = Integer.parseInt(projDate.substring(0, 2));
		// System.out.println(projYear);
		// System.out.println(projMonth);
		// System.out.println(currMonth);
		// różnica dat w miesiącach
		int dateDiff = (currYear * 12 + currMonth) - (projYear * 12 + projMonth);
		System.out.println(dateDiff);

		// i = 10 - od dziesiątego do 60 wiersza sprawdzamy w excelu z templatem
		for (int i = 10; i < 60; i++) {
			currentCell = dataSheet.getRow(i).getCell(1);
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
				if (currentCell.getStringCellValue().equals(teamName)) {
					int grade = (int) dataSheet.getRow(i).getCell(2).getNumericCellValue();
					//System.out.println(currentCell.getStringCellValue());
					//System.out.println(grade);

					// j = 6 od 6 do 18 kolumny (licząc od 0) sprawdzamy w excelu w poszukiwaniu
					// dniówek
					for (int j = 6; j < 18; j++) {
						// jeśli projekt już się rozpoczął tzn. pierwszy miesiąc w forcaście jest wcześniejszy bądz rowny obecnemu
						if (dateDiff >= 0) {
							daysCell = dataSheet.getRow(i).getCell(j + dateDiff);
							if (daysCell != null && daysCell.getCellTypeEnum() == CellType.NUMERIC) {
								teamTable[grade - 1][j - 6] = teamTable[grade - 1][j - 6]
										+ daysCell.getNumericCellValue();
							}
						}
						// jeśli projekt jeszcze sie nie rozpoczal
						if (dateDiff < 0 && j < 18 + dateDiff) {
							daysCell = dataSheet.getRow(i).getCell(j);
							if (daysCell != null && daysCell.getCellTypeEnum() == CellType.NUMERIC) {
								teamTable[grade - 1][j - 6 - dateDiff] = teamTable[grade - 1][j - 6-dateDiff]
										+ daysCell.getNumericCellValue();
							}
						}
					}
				}
			}

		}

		 System.out.println(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 12;
		 j++) { System.out.print(teamTable[i][j] + " "); } System.out.println(); }
		
		 System.out.println("check");

		return teamTable;
	}
}
