package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.launchcode.techjobsmvc.models.JobData;
import java.util.ArrayList;
import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;
import java.util.HashMap;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {


    @GetMapping(value = "")
    public String searchForm(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
//    handles the POST request when the form is submitted. Retrieves the search results using
//    'JobData.findByColumnAndValue' and adds them to the model w/ choices
    @PostMapping(value = "results")
    public String displaySearchResults(Model model,
                                       @RequestParam("searchType") String searchType,
                                       @RequestParam("searchTerm") String searchTerm) {
        ArrayList<Job> jobs;
        if (searchType.equals("all") || searchTerm.isEmpty()) {
            jobs = JobData.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("jobs", jobs);
        return "search";
    }

    private HashMap<String, Integer> calculateJobCounts(ArrayList<Job> jobs) {
        HashMap<String, Integer> jobCount = new HashMap<>();

        for (Job job : jobs) {
            String category = getCategory(job);
            jobCount.put(category, jobCount.getOrDefault(category, 0) + 1);
        }
        return jobCount;
    }
    private String getCategory(Job job) {
        return job.getCategory();
    }
}

