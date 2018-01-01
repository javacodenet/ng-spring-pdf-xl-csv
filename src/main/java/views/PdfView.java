package views;

import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.test.dto.EmployeeDTO;

public class PdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// this will download the file instead of displaying it on the browser
		// response.setHeader("Content-Disposition", "attachment;
		// filename=\"my-pdf-file.pdf\"");

		@SuppressWarnings("unchecked")
		List<EmployeeDTO> employees = (List<EmployeeDTO>) model.get("employee");
		document.add(new Paragraph("Generated on " + LocalDate.now()));

		if (employees != null && employees.size() > 0) {
			PdfPTable table = new PdfPTable(employees.stream().findAny().get().getColumnCount());
			table.setWidthPercentage(100.0f);
			table.setSpacingBefore(10);

			// define font for table header row
			Font font = FontFactory.getFont(FontFactory.TIMES);

			// define table header cell
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(Color.DARK_GRAY);
			cell.setPadding(5);

			// write table header
			cell.setPhrase(new Phrase("ID", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Name", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Company", font));
			table.addCell(cell);

			employees.stream().forEach(user -> {
				table.addCell(user.getId());
				table.addCell(user.getName());
				table.addCell(user.getCompany());
			});

			document.add(table);
		}

	}
}
