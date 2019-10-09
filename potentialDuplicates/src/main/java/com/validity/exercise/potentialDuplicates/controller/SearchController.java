package com.validity.exercise.potentialDuplicates.controller;

import com.validity.exercise.potentialDuplicates.helper.FileReaderHelper;
import com.validity.exercise.potentialDuplicates.service.FindDuplicateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/potentialDuplicates")
    public String potentialDuplicates(@RequestParam("file")MultipartFile file, ModelMap modelMap) {
        if (file != null) {
            log.info("Endpoint reached. Reading file " + file.getOriginalFilename());
            File file1 = fileReaderHelper.convertMultiPartToFile(file);
            List<Set<Map<String, String>>> finalList = findDuplicateService.findPotentialDuplicates(file1);
            return "result";
        }
        modelMap.addAttribute("error", "File is null");
        return "result";
    }
}
