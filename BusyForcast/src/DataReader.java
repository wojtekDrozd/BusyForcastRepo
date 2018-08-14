import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//bez sensu skomplikowane sa poki co dwie tabele - może wymyslic cos innego

//trzeba rozwiaząc problem z datami

//musi zgłaszać problemy

public class DataReader {
	// utworzenie tabel z danymi teamów
	static double[][] StruTable = new double[9][12];
	static double[][] PMTable = new double[9][12];
	static double[][] MechTable = new double[9][12];
	static double[][] PHTable = new double[9][12];
	static double[][] ElecTable = new double[9][12];
	static double[][] ConsTable = new double[9][12];
	static double[][] GeotTable = new double[9][12];
	static double[][] WaterTable = new double[9][12];
	static double[][] EnvTable = new double[9][12];
	static double[][] BridgeTable = new double[9][12];
	static double[][] RoadTable = new double[9][12];
	static double[][] AdminTable = new double[9][12];
	static double[][] OtherTable = new double[9][12];
	// utworzenie docelowej tabeli z danymi
	static double[][] dataTable = new double[117][13];
	
	public static void main(String[] args) { //dla testów klasy musi być tak i zmienne static, docelowo dajemy tu konstruktor i ususwamy static

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
				StruTable = createTeamTable(13, 22, 5, 17, dataSheet);
				PMTable = createTeamTable(28, 37, 5, 17, dataSheet);
				MechTable = createTeamTable(43, 52, 5, 17, dataSheet);
				PHTable = createTeamTable(58, 67, 5, 17, dataSheet);
				ElecTable = createTeamTable(78, 87, 5, 17, dataSheet);
				ConsTable = createTeamTable(98, 107, 5, 17, dataSheet);
				GeotTable = createTeamTable(113, 122, 5, 17, dataSheet);
				WaterTable = createTeamTable(128, 137, 5, 17, dataSheet);
				EnvTable = createTeamTable(143, 152, 5, 17, dataSheet);
				BridgeTable = createTeamTable(158, 167, 5, 17, dataSheet);
				RoadTable = createTeamTable(173, 182, 5, 17, dataSheet);
				AdminTable = createTeamTable(192, 201, 5, 17, dataSheet);
				OtherTable = createTeamTable(208, 217, 5, 17, dataSheet);
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
	public static double[][] createTeamTable(int rowMin, int rowMax, int colMin, int colMax, Sheet dataSheet) {
		Cell currentCell;
		double[][] teamTable = new double[9][12];
		int countRow = 0;
		int countCol = 0;
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
		 */
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
