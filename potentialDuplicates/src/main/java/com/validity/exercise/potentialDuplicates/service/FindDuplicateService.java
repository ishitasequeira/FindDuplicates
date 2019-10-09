package com.validity.exercise.potentialDuplicates.service;

import com.validity.exercise.potentialDuplicates.helper.FileReaderHelper;
import com.validity.exercise.potentialDuplicates.helper.LevenshteinDistance;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Service
public class FindDuplicateService {

    @Autowired
    FileReaderHelper fileReaderHelper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${field.treshold}")
    private String recordValueFactor;

    @Value("${column.treshold}")
    private String recordHeaderFactor;

    /**
     * @param recordsData
     * @return Json output with List of multiple set of duplicates and one set of
     *         non duplicates
     *
     *
     */
    public List<Set<Map<String, String>>> findPotentialDuplicates(List<Map<String, String>> recordsData) {
        log.info("inside checkduplicates");

        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();

        // Get all record Headers
        List<String> recordHeaders = fileReaderHelper.getFileHeaders();

        // Set record header match threshold
        int recordHeaderThreshold = (int) (recordHeaders.size() * Float.parseFloat(recordHeaderFactor));

        // List of Set of potential duplicates
        List<Set<Map<String, String>>> result = new ArrayList<>();
        Set<Map<String, String>> duplicates = new HashSet<Map<String, String>>();

        // Set of non duplicates
        Set<Map<String, String>> nonDuplicates = new HashSet<Map<String, String>>();

        // adding initial entry for duplicate comparision
        duplicates.add(recordsData.get(0));

        for (int i = 0; i < recordsData.size(); i++) {

            Map<String, String> currentRecord = recordsData.get(i);
            boolean flag = false;
            for(Set<Map<String, String>> set1 : result) {
                if(set1.contains(currentRecord)){
                    flag = true; break;
                }
            }
            if(!flag) {
                if (i != 0) {
                    duplicates = new HashSet<Map<String, String>>();
                }
                List<Map<String, String>> entries = new ArrayList<>();
                for(Map<String, String> map : recordsData) {
                    if(map != currentRecord) {
                        boolean present = false;
                        for(Set set : result) {
                            if(set.contains(map)) {
                                present = true;
                            }
                        }
                        if(!present && !nonDuplicates.contains(map)){
                            entries.add(map);
                        }
                    }
                }

                for (Map<String, String> copy : entries) {
                    int fieldThreshold;
                    int matchingColumnCount = 0;
                    for (int j = 0; j < recordHeaders.size(); j++) {
                        // Calculate field threshold
                        int columnLength = currentRecord.get(recordHeaders.get(j)).length();
                        fieldThreshold = (int) (columnLength * Float.parseFloat(recordValueFactor));

                        String currentValue = currentRecord.get(recordHeaders.get(j));
                        String nextValue = copy.get(recordHeaders.get(j));

                        //Calculate LevenshteinDistance for each column

                        int fieldDistance = Integer.MAX_VALUE;
                        if(currentValue != null && nextValue != null) {
                            fieldDistance = LevenshteinDistance.calculate(currentValue.toLowerCase(), nextValue.toLowerCase());
                        }

                        //Verify Duplicates Using Double Metaphone
                        if (fieldDistance <= fieldThreshold) {
                            currentValue.replaceAll("[^a-zA-Z]", "");
                            nextValue.replaceAll("[^a-zA-Z]", "");
                            if ((!nextValue.isEmpty() && !currentValue.isEmpty()) && doubleMetaphone.isDoubleMetaphoneEqual(currentValue, nextValue))
                                matchingColumnCount++;
                        }
                    }

                    //Add duplicates to list
                    if (matchingColumnCount >= recordHeaderThreshold) {
                        duplicates.add(currentRecord);
                        duplicates.add(copy);
                    } else {
                        if (i == 0) {
                            duplicates.remove(currentRecord);
                        }
                    }

                }
                //Add set of non duplicates
                if (!duplicates.isEmpty())
                    result.add(duplicates);
                else
                    nonDuplicates.add(currentRecord);
            }
        }

        for(Set<Map<String, String>> set : result) {
            for(Map<String, String> map : set) {
                map.remove("index");
            }
        }

        for(Map<String, String> map : nonDuplicates) {
            map.remove("index");
        }

        result.add(nonDuplicates);

        return result;

    }

}
