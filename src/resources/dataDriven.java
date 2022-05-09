package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {

	public ArrayList<String> getData(String testcaseName, String sheetName) throws IOException {
		// FileInputStream object to excel document .xlsx format
		FileInputStream fis = new FileInputStream("C://Users//SherwQUE//Desktop//seleni//datademo.xlsx");

		// Array list to store data
		ArrayList<String> arr = new ArrayList();

		// workbook object for excel
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		// get total number of excel sheet
		int sheets = workbook.getNumberOfSheets();

		for (int i = 0; i < sheets; i++) {
			// check for the specific excel sheet/tab name
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {

				// place specific excel sheet/tab index on the object
				XSSFSheet sheet = workbook.getSheetAt(i);

				// identify "testcases" cell column by scanning the entire 1st row
				// sheet is a collection of rows
				Iterator<Row> rows = sheet.iterator();
				// moves to the next row
				Row firstrow = rows.next();
				// the 1st row is a collection of cells
				Iterator<Cell> cells = firstrow.cellIterator();
				int k = 0;
				int column = 0;

				while (cells.hasNext()) {
					Cell value = cells.next();
					// Look for the TestCases title in the sheet
					if (value.getStringCellValue().equalsIgnoreCase("testcases")) {
						column = k;
					}
					k++;
				}
				// System.out.println("column is: " + column);

				// once column is identified, scan whole col to identify "testcaseName" cell row
				while (rows.hasNext()) {
					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testcaseName)) {
						// after identify "purchase" cell row, pull all data row and feed it to test
						Iterator<Cell> cv = r.cellIterator();
						while (cv.hasNext()) {
							Cell cvalue = cv.next();
							// check if cell value is string
							if (cvalue.getCellType() == CellType.STRING) {
								arr.add(cvalue.getStringCellValue());
							} else {
								// convert numeric value to string
								arr.add(NumberToTextConverter.toText(cvalue.getNumericCellValue()));
							}
						}
					}
				}
			}
		}
		return arr;
	}
}
