package com.validity.exercise.potentialDuplicates.controller;

import com.validity.exercise.potentialDuplicates.helper.FileReaderHelper;
import com.validity.exercise.potentialDuplicates.service.FindDuplicateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SearchController {

    @Autowired
    FileReaderHelper fileReaderHelper;

    @Autowired
    FindDuplicateService findDuplicateService;

    /*
     * Below endpoint is a GET endpoint
     * Takes user to the index page
     * */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /*
    * Below endpoint is a POST endpoint
    * It accepts MultiPart file as an input
    * It returns the list of Duplicate and Non-duplicate records and takes us to the result page
    * */
    @PostMapping("/potentialDuplicates")
    public String potentialDuplicates(@RequestParam("file")MultipartFile file, ModelMap modelMap) {
        if (file != null) {
            // Convert multipart file to normal file
            File file1 = fileReaderHelper.multipartFileToFile(file);

            // Reading CSV Data
            List<Map<String, String>> recordsData = fileReaderHelper.readAll(file1);

            // Find the result list with Duplicates and Non-Duplicates
            List<Set<Map<String, String>>> list = findDuplicateService.findPotentialDuplicates(recordsData);

            // Seperating non-duplicates from duplicates
            Set<Map<String,String>> nonDuplicateEntries = list.get(list.size()-1);
            list.remove(list.size()-1);

            // Setting results into ModelMap to view at response
            modelMap.addAttribute("duplicates", list);
            modelMap.addAttribute("nonDuplicates", nonDuplicateEntries);
            modelMap.addAttribute("headers", fileReaderHelper.getFileHeaders());

            // Result View Page
            return "result";
        }
        modelMap.addAttribute("error", "File is null");
        return "result";
    }
}
