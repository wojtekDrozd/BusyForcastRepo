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
	
	// bez sensu skomplikowane sa poki co dwie tabele - wymyslic cos innego

	// musi czytać wszystkie pliki w katalogu których nazwa to tylko cyferki i po
	// kolei je sprawdzac
	// musi stworzyc tablice z zespolami i gradami która bedzie aktualizowana po
	// przejsciu kazdego zespolu w kazdym arkuszu
	public static void main(String[] args) {
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
		double[][] dataTable = new double[108][13];
		// dodanie ID do docelowej tabeli z danymi
		for (int i = 0; i < dataTable.length; i++) {
			dataTable[i][0] = i + 1;
		}
		try {
			Sheet dataSheet = createDataSheet("240849.xlsx");
			StruTable = printTeamTable(13, 22, 5, 17, dataSheet);
			PMTable = printTeamTable(28, 37, 5, 17, dataSheet);
			MechTable = printTeamTable(43, 52, 5, 17, dataSheet);
			PHTable = printTeamTable(58, 67, 5, 17, dataSheet);
			ElecTable = printTeamTable(78, 87, 5, 17, dataSheet);
			ConsTable = printTeamTable(98, 107, 5, 17, dataSheet);
			GeotTable = printTeamTable(113, 122, 5, 17, dataSheet);
			WaterTable = printTeamTable(128, 137, 5, 17, dataSheet);
			EnvTable = printTeamTable(143, 152, 5, 17, dataSheet);
			BridgeTable = printTeamTable(158, 167, 5, 17, dataSheet);
			RoadTable = printTeamTable(173, 181, 5, 17, dataSheet);
			AdminTable = printTeamTable(193, 202, 5, 17, dataSheet);
			OtherTable = printTeamTable(209, 218, 5, 17, dataSheet);
			updateDataTable(dataTable,StruTable,0); 
			updateDataTable(dataTable,PMTable,9);
			updateDataTable(dataTable,MechTable,18);
			
			
		//testowy wydruk tabeli z danymi
			for (int i = 0; i < 108; i++) {
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

	// zwraca arkusz z podanego excela
	public static Sheet createDataSheet(String fileName) throws IOException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook wb = new XSSFWorkbook(excelFile);
		Sheet dataSheet = wb.getSheetAt(1);
		return dataSheet;
	}

	// tworzy tabele z danymi dla konkretnego team u na podstawie zakresu komorek w
	// excelu
	public static double[][] printTeamTable(int rowMin, int rowMax, int colMin, int colMax, Sheet dataSheet) {
		Cell currentCell;
		double[][] teamTable = new double[9][12];
		int countRow = 0;
		int countCol = 0;
		for (int i = rowMin; i < rowMax; i++) {
			countCol = 0;
			for (int j = colMin; j < colMax; j++) {
				currentCell = dataSheet.getRow(i).getCell(j);
				if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					System.out.print(currentCell.getNumericCellValue() + " ");
					teamTable[countRow][countCol] = currentCell.getNumericCellValue();
				} else {
					System.out.print("0.0 ");
					teamTable[countRow][countCol] = 0;
				}
				countCol += 1;
			}
			System.out.println();
			countRow += 1;
		}
		System.out.println();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 12; j++) {
				System.out.print(teamTable[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("check");
		return teamTable;
	}

	// aktualizuje docelowa tabele z danymi w oparciu o dane team
	public static void updateDataTable(double[][] dataTable, double[][] teamTable, int startRow) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 12; j++) {
				dataTable[startRow + i][j + 1] = teamTable[i][j];
			}
		}
	}
}
