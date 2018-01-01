package views;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.test.dto.EmployeeDTO;


public class CsvView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/csv");
		buildCsvDocument(model, request, response);

	}

	private void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		  	response.setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");

			@SuppressWarnings("unchecked")
			List<EmployeeDTO> employees = (List<EmployeeDTO>) model.get("employee");
			
	        String[] header = {"Id","Name","Company"};
	        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
	                CsvPreference.STANDARD_PREFERENCE);

	        csvWriter.writeHeader(header);

	        for(EmployeeDTO employee : employees){
	            csvWriter.write(employee, header);
	        }
	        csvWriter.close();
	}

}