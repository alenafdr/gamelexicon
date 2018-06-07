package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.study.gamelexicon.service.CsvParser;

@RestController
public class CsvUploadController {
    private static final Logger logger = LoggerFactory.getLogger(CsvUploadController.class);

    @Autowired
    CsvParser csvParser;

    @PostMapping("/upload")
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            csvParser.read(file);
        } else {
            logger.error("file is empty");
            return "file is empty";
        }
        return "ok";
    }
}
