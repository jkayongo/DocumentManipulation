package docmanipulationworkspace.DocumentManipulation.controller;

import docmanipulationworkspace.DocumentManipulation.model.Course;
import docmanipulationworkspace.DocumentManipulation.service.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    private final ExcelService excelService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/saveCourse")
    public ResponseEntity<String> saveCourse(@RequestBody Course course){
        try{
            excelService.saveCourse(course);
            return new ResponseEntity<>("You have successfully saved a course", HttpStatus.OK);
        }catch(Exception exception){
            logger.error("Failed to save a course: {}", exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                    body("Failed to save a course: {}" + exception.getMessage());
        }
    }

    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //the http header key like Content-Disposition, Authorization, Cache-control
        String headerKey = "Content-Disposition";
        //the information you want to send for that header
        String headerValue = "attachment; filename=courses.xlsx";
        response.setHeader(headerKey, headerValue );
        excelService.generateExcel(response);
    }
}
