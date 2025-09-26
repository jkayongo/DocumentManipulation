package docmanipulationworkspace.DocumentManipulation.controller;

import docmanipulationworkspace.DocumentManipulation.docs.CourseDataExportToPdf;
import docmanipulationworkspace.DocumentManipulation.model.Course;
import docmanipulationworkspace.DocumentManipulation.service.ExcelAndPdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
public class ExcelAndPdfController {

    private final ExcelAndPdfService excelAndPdfService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelAndPdfController.class);

    public ExcelAndPdfController(ExcelAndPdfService excelAndPdfService) {
        this.excelAndPdfService = excelAndPdfService;
    }


    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; courses.xlsx";
        response.setHeader(headerKey, headerValue );
        excelAndPdfService.generateExcel(response);
    }

    @GetMapping("/displaySavePage")
    public String displaySavePageForCourse(){
        return "savecourse";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute Course course, Model model){
        Long courseId = excelAndPdfService.saveCourse(course).getCourseId();
        String message = "Record with id: " + courseId + " " + "has successfully been saved";
        model.addAttribute("message", message);
        model.addAttribute("courseId", course.getCourseId());
        return "success";
    }

    @GetMapping("/exportToPdf")
    public ResponseEntity<InputStreamResource> exportCourseToPdf(@RequestParam Long courseId) throws IOException {
        Course course = excelAndPdfService.getCourseById(courseId);
        CourseDataExportToPdf courseDataExporterToPdf = new CourseDataExportToPdf();
        ByteArrayInputStream pdfStream = courseDataExporterToPdf.exportCourseToPdf(course);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; course_" + courseId + ".pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfStream));
    }

}
