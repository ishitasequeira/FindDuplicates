package com.validity.exercise.potentialDuplicates.helper;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class FileReaderHelper {

    static List<String> fileHeaders = null;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);

            fos.write(file.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return convFile;
    }

    public static List<Map<String, String>> readAll(File file) {
        List<Map<String, String>> jsonArray = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
            String[] fieldNamesString;
            if ((fieldNamesString = reader.readNext()) != null) {
//                if (fieldNamesString[0].startsWith(UTF8_BOM)) {
                    fieldNamesString[0] = fieldNamesString[0].substring(1);
//                }
                fileHeaders = Arrays.asList(fieldNamesString);
            }
            String[] nextLine;
            int counter = 0;
            while ((nextLine = reader.readNext()) != null) {

                List<String> list = Arrays.asList(nextLine);
                Map<String, String> obj = new LinkedHashMap<>();
                obj.put("index", String.valueOf(++counter));
                for (int i = 0; i < list.size(); i++) {
                    obj.put(fileHeaders.get(i), list.get(i).isEmpty() ? "" : list.get(i));
                }
                jsonArray.add(obj);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static List<String> getFileHeaders() {
        return fileHeaders;
    }

    public static void setFileHeaders(List<String> fileHeaders) {
        FileReaderHelper.fileHeaders = fileHeaders;
    }
}
