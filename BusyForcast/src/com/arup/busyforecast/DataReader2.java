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

// odczyt danych dostosowany do template project forcast w którym w zakładce days wybieramy z listy rozwijalnej

//bez sensu skomplikowane sa poki co dwie tabele - może wymyslic cos innego

//trzeba rozwiaząc problem z datami

//musi zgłaszać problemy

public class DataReader2 {
	// utworzenie tabel z danymi teamów
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
	// utworzenie docelowej tabeli z danymi
	static double[][] dataTable = new double[117][13];
	// ustalenie obecnego miesiaca i roku
	static Calendar c = Calendar.getInstance();
	static int currYear = c.get(Calendar.YEAR);
	static int currMonth = c.get(Calendar.MONTH) + 1;

	public DataReader2() {

		// dodanie ID do docelowej tabeli z danymi
		for (int i = 0; i < dataTable.length; i++) {
			dataTable[i][0] = i + 1;

		}
		// utworzenie listy plikow do analizy
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
			// co tu ma robić? wywalić jakiś błąd chyba..
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

			// testowy wydruk tabeli z danymi
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

	// zwraca drugi arkusz z podanego excela
	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(1);
		return dataSheet;
	}

	// tworzy tabele z danymi dla konkretnego team u na podstawie zakresu komorek w
	// excelu
	public static double[][] createTeamTable(String teamName, Sheet dataSheet) {
		Cell currentCell;
		Cell daysCell;
		double[][] teamTable = new double[9][12];

		// get project start date
		currentCell = dataSheet.getRow(8).getCell(6);
		String projDate = new SimpleDateFormat("MM/dd/yyyy").format(currentCell.getDateCellValue());
		int projYear = Integer.parseInt(projDate.substring(6, 10));
		int projMonth = Integer.parseInt(projDate.substring(0, 2));
		// obliczenie roznicy daty w miesiacach
		int dateDiff = (currYear * 12 + currMonth) - (projYear * 12 + projMonth);

		// i = 10 - od dziesiątego do 60 wiersza sprawdzamy w excelu z templatem
		for (int i = 10; i < 60; i++) {
			currentCell = dataSheet.getRow(i).getCell(1);
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
				if (currentCell.getStringCellValue().equals(teamName)) {
					int grade = (int) dataSheet.getRow(i).getCell(2).getNumericCellValue();
					// System.out.println(currentCell.getStringCellValue());
					// System.out.println(grade);

					// j = 6 od 6 do 18 kolumny (licząc od 0) sprawdzamy w excelu w poszukiwaniu
					// dniówek
					for (int j = 6; j < 18; j++) {
						// jeśli projekt już się rozpoczął tzn. pierwszy miesiąc w forcaście jest
						// wcześniejszy bądz rowny obecnemu
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
								teamTable[grade - 1][j - 6 - dateDiff] = teamTable[grade - 1][j - 6 - dateDiff]
										+ daysCell.getNumericCellValue();
							}
						}
					}
				}
			}

		}

		// System.out.println(); for (int i = 0; i < 9; i++) { for (int j = 0; j < 12;
		// j++) { System.out.print(teamTable[i][j] + " "); } System.out.println(); }
		//
		// System.out.println("check");

		return teamTable;
	}

	// aktualizuje docelowa tabele z danymi w oparciu o dane teamow
	public static void updateDataTable(double[][] dataTable, double[][] teamTable, int startRow) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 12; j++) {
				dataTable[startRow + i][j + 1] = dataTable[startRow + i][j + 1] + teamTable[i][j];
			}
		}
	}
}
