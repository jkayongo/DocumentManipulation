package docmanipulationworkspace.DocumentManipulation.controller;

import docmanipulationworkspace.DocumentManipulation.dto.ResponseMessage;
import docmanipulationworkspace.DocumentManipulation.service.Fileservice;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    private final Fileservice fileservice;

    public FileController(Fileservice fileservice) {
        this.fileservice = fileservice;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> upload(@RequestParam("file") MultipartFile file){
        if(fileservice.hasCsvFormat(file)){
            fileservice.processAndSaveData(file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage((file.getOriginalFilename() + " " + "uploaded successfully")));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Please upload a csv file"));
    }
}
