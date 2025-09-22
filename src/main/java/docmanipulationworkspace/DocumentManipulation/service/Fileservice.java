package docmanipulationworkspace.DocumentManipulation.service;

import org.springframework.web.multipart.MultipartFile;

public interface Fileservice {

    boolean hasCsvFormat(MultipartFile file);

    void processAndSaveData(MultipartFile file);
}
