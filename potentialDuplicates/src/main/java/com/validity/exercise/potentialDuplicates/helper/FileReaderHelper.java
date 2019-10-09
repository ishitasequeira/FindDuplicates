package com.validity.exercise.potentialDuplicates.helper;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileReaderHelper {

    // Stores the headers of the file
    private static List<String> fileHeaders = null;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
    * This method converts multipart file to a normal file
    * */
    public File multipartFileToFile(MultipartFile originalFile) {
        File file = new File(originalFile.getOriginalFilename());

        try (OutputStream fos = Files.newOutputStream(Paths.get(file.getPath()))) {
            fos.write(originalFile.getBytes());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

    /*
    * This method reads the file and returns the entire list of records. Each record is a key value pair stored in a Map.
    * It uses CSVReader to read a CSV file and stores in the Map
    * */
    public static List<Map<String, String>> readAll(File file) {
        List<Map<String, String>> recordsData = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(isr)) {
            String[] fieldNamesString;
            if ((fieldNamesString = reader.readNext()) != null) {
                fileHeaders = Arrays.asList(fieldNamesString);
            }
            String[] nextLine;
            int counter = 0;
            while ((nextLine = reader.readNext()) != null) {
                List<String> list = Arrays.asList(nextLine);
                Map<String, String> record = new LinkedHashMap<>();
                record.put("index", String.valueOf(++counter));
                for (int i = 0; i < list.size(); i++) {
                    record.put(fileHeaders.get(i), list.get(i).isEmpty() ? "" : list.get(i));
                }
                recordsData.add(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recordsData;
    }

    /*
    * Getter of fileHeader
    * */
    public static List<String> getFileHeaders() {
        return fileHeaders;
    }

}
