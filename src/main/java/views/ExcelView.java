package views;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.test.dto.EmployeeDTO;

public class ExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<EmployeeDTO> employees = (List<EmployeeDTO>) model.get("employee");
		createExcelSheet(workbook, employees);

	}

	public static void createExcelSheet(Workbook workbook, List<EmployeeDTO> employees) {
		// Create Worksheet
		Sheet employeeDetailsSheet = workbook.createSheet("Employee details");

		// Create Header ROw
		Row rowHeader = employeeDetailsSheet.createRow(0);
		CellStyle boldCellStyle = getBoldCellStyle(workbook);
		createHeaderCell(rowHeader, 0, boldCellStyle, "EmployeeId");
		createHeaderCell(rowHeader, 1, boldCellStyle, "EmployeeName");
		createHeaderCell(rowHeader, 2, boldCellStyle, "Company");

		// Create data cells
		int rowNum = 1;
		for (EmployeeDTO employee : employees) {
			Row row = employeeDetailsSheet.createRow(rowNum++);
			Cell empId = row.createCell(0);
			empId.setCellValue(employee.getId());
			Cell empName = row.createCell(1);
			empName.setCellValue(employee.getName());
			Cell dept = row.createCell(2);
			dept.setCellValue(employee.getCompany());
		}
	}

	private static void createHeaderCell(Row rowHeader, int i, CellStyle boldCellStyle, String employeeId) {
		Cell empIdHeader = rowHeader.createCell(i);
		empIdHeader.setCellStyle(boldCellStyle);
		empIdHeader.setCellValue(employeeId);
	}

	private static CellStyle getBoldCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = getBoldFont(workbook);
		cellStyle.setFont(font);
		return cellStyle;
	}

	private static Font getBoldFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setFontName("Arial");
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		font.setItalic(true);
		return font;
	}

}