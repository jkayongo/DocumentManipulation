package docmanipulationworkspace.DocumentManipulation.service;

import docmanipulationworkspace.DocumentManipulation.model.User;
import docmanipulationworkspace.DocumentManipulation.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImple implements Fileservice {

    private final UserRepository userRepository;

    public FileServiceImple(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean hasCsvFormat(MultipartFile file) {
        String contentType = "text/csv";
        if(!contentType.equals(file.getContentType())){
            return false;
        }
        return true;
    }

    //converting csv data to User
    private List<User> csvToUsers(InputStream inputStream) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.
                builder().
                setHeader().
                setIgnoreHeaderCase(true).
                setTrim(true).
                build();
        try(
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                CSVParser csvParser = new CSVParser(fileReader, csvFormat);
        ){
            List<User> users = new ArrayList<>();
            List<CSVRecord> records = csvParser.getRecords();
            for(CSVRecord csvRecord : records){
                User user = new User(csvRecord.get("Height(Inches)"), csvRecord.get("Weight(Pounds)"));
                users.add(user);
            }
            return users;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse csv file: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void processAndSaveData(MultipartFile file) {
        try {
            List<User> users = csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
