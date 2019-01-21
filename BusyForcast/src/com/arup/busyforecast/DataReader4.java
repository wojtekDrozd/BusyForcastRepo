package com.arup.busyforecast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader4 {
	// creating tables with teams data
	double[][] StruTable = new double[9][12];
	double[][] PMTable = new double[9][12];
	double[][] MechTable = new double[9][12];
	double[][] PHTable = new double[9][12];
	double[][] ElecTable = new double[9][12];
	double[][] ConsTable = new double[9][12];
	double[][] GeotTable = new double[9][12];
	double[][] WaterTable = new double[9][12];
	double[][] EnvTable = new double[9][12];
	double[][] BridgeTable = new double[9][12];
	double[][] RoadTable = new double[9][12];
	double[][] AdminTable = new double[9][12];
	double[][] OtherTable = new double[9][12];
	//final table with data
	double[][] dataTable = new double[117][13];
	// get current month and year
	static Calendar c = Calendar.getInstance();
	static int currYear = c.get(Calendar.YEAR);
	static int currMonth = c.get(Calendar.MONTH) + 1;

	public DataReader4() {

		//adds ID to final table
		for (int i = 0; i < dataTable.length; i++) {
			dataTable[i][0] = i + 1;

		}
		// creates final file list for analysis
		String fileName;
		String[] nameParts;
		List<String> projectList = new LinkedList<String>();
		File dir = new File(System.getProperty("user.dir"));
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				nameParts = child.getName().split("\\.");
				fileName = nameParts[0];
				if (StringUtils.isNumeric(fileName)) {
					projectList.add(child.getName());
					System.out.println(child.getName());
				}
			}
		} else {
			//TO DO
		}

		try {
			for (String project : projectList) {
				Sheet dataSheet = createDataSheet(project);
				StruTable = createTeamTable("Structural", dataSheet);
				PMTable = createTeamTable("Project Management", dataSheet);
				MechTable = createTeamTable("Mechanical", dataSheet);
				PHTable = createTeamTable("Public Health/Plumbing", dataSheet);
				ElecTable = createTeamTable("Electrical", dataSheet);
				ConsTable = createTeamTable("Management Consultancy", dataSheet);
				GeotTable = createTeamTable("Geotechnical", dataSheet);
				WaterTable = createTeamTable("Water", dataSheet);
				EnvTable = createTeamTable("Environmental", dataSheet);
				BridgeTable = createTeamTable("Bridges", dataSheet);
				RoadTable = createTeamTable("Highways", dataSheet);
				AdminTable = createTeamTable("Administration", dataSheet);
				OtherTable = createTeamTable("Other", dataSheet);
				updateDataTable(dataTable, StruTable, 0);
				updateDataTable(dataTable, PMTable, 9);
				updateDataTable(dataTable, MechTable, 18);
				updateDataTable(dataTable, PHTable, 27);
				updateDataTable(dataTable, ElecTable, 36);
				updateDataTable(dataTable, ConsTable, 45);
				updateDataTable(dataTable, GeotTable, 54);
				updateDataTable(dataTable, WaterTable, 63);
				updateDataTable(dataTable, EnvTable, 72);
				updateDataTable(dataTable, BridgeTable, 81);
				updateDataTable(dataTable, RoadTable, 90);
				updateDataTable(dataTable, AdminTable, 99);
				updateDataTable(dataTable, OtherTable, 108);
			}

			// test print
			for (int i = 0; i < 117; i++) {
				for (int j = 0; j < 13; j++) {
					System.out.print(dataTable[i][j] + " ");
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// returns second sheet from workbook
	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(1);
		return dataSheet;
	}

	// creates table for specific team based on work sheet data
	public static double[][] createTeamTable(String teamName, Sheet dataSheet) {
		Cell currentCell;
		Cell daysCell;
		double[][] teamTable = new double[9][12];

		// get project start date
		currentCell = dataSheet.getRow(9).getCell(17);
		String projDate = new SimpleDateFormat("MM/dd/yyyy").format(currentCell.getDateCellValue());
		int projYear = Integer.parseInt(projDate.substring(6, 10));
		int projMonth = Integer.parseInt(projDate.substring(0, 2));
		// calculate time difference
		int dateDiff = (currYear * 12 + currMonth) - (projYear * 12 + projMonth);

		// i = 11 - from 11 to 61 rows to check
		for (int i = 11; i < 61; i++) {

			currentCell = dataSheet.getRow(i).getCell(3);
			// System.out.println(currentCell.getCellTypeEnum());
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.FORMULA) { // Formula instead of string
				// System.out.println("check");
				if (currentCell.getStringCellValue().equals(teamName)) {
					int grade = (int) dataSheet.getRow(i).getCell(4).getNumericCellValue();

					// System.out.println(currentCell.getStringCellValue());
					// System.out.println(grade);

					// j = 17 from 17 to 18 column (counting from 0) checking for days of work
					for (int j = 17; j < 29; j++) {
						// if project is already started
						if (dateDiff >= 0) {
							daysCell = dataSheet.getRow(i).getCell(j + dateDiff);
							if (daysCell != null && daysCell.getCellTypeEnum() == CellType.NUMERIC) {
								teamTable[grade - 1][j - 17] = teamTable[grade - 1][j - 17]
										+ daysCell.getNumericCellValue();
							}
						}
						// if project is not yet started
						if (dateDiff < 0 && j < 29 + dateDiff) {
							daysCell = dataSheet.getRow(i).getCell(j);
							if (daysCell != null && daysCell.getCellTypeEnum() == CellType.NUMERIC) {
								teamTable[grade - 1][j - 17 - dateDiff] = teamTable[grade - 1][j - 17 - dateDiff]
										+ daysCell.getNumericCellValue();
							}
						}
					}
				}
			}

		}

		// System.out.println(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 12;
		// j++) { System.out.print(teamTable[i][j] + " "); } System.out.println(); }
		// System.out.println("check");

		return teamTable;
	}

	// updates final data table based on team data
	public static void updateDataTable(double[][] dataTable, double[][] teamTable, int startRow) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 12; j++) {
				dataTable[startRow + i][j + 1] = dataTable[startRow + i][j + 1] + teamTable[i][j];
			}
		}
	}
}
