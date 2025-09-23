package docmanipulationworkspace.DocumentManipulation.service;

import docmanipulationworkspace.DocumentManipulation.model.Course;
import docmanipulationworkspace.DocumentManipulation.repository.CourseRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    private final CourseRepository courseRepository;

    public ExcelService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void saveCourse(Course course){
        courseRepository.save(course);
    }


    public void generateExcel(HttpServletResponse response) throws IOException {
        //first,get data from the database
        List<Course> courseList = courseRepository.findAll();

        try(XSSFWorkbook workbook = new XSSFWorkbook();
            ServletOutputStream outputStream = response.getOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("courses info");
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("course_id");
            row.createCell(1).setCellValue("course_name");
            row.createCell(2).setCellValue("price");
            int dataRowIndex = 1;
            for(Course course: courseList){
                XSSFRow dataRow = sheet.createRow(dataRowIndex);
                dataRow.createCell(0).setCellValue(course.getCourseId());
                dataRow.createCell(1).setCellValue(course.getCourseName());
                dataRow.createCell(2).setCellValue(course.getPrice());
                dataRowIndex++;
            }
            workbook.write(outputStream);
        }catch (IOException exception){
            throw new RuntimeException("Failed to export excel file: " + exception.getMessage(), exception);
        }
    }
}

