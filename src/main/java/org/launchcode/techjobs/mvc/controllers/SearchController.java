package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Objects;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

//  Task 3: Complete SearchController - I used a lot of the same code/ideas from listJobsByColumnAndValue() method in the ListController.java file.
    @PostMapping(value = "results")  // the path from which the info is coming, found in <form> tag in search.html
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs;    // create an ArrayList of jobs of type Job.

        if (searchTerm.equals("all") || searchTerm.equals("")) {      // if 'all' or 'empty string' is entered in search box, then do this...
//        if (Objects.equals(searchTerm, "all") || Objects.equals(searchTerm, "")) {    // Is this better than line above? Both work.
            jobs = JobData.findAll();   // find/return all jobData info.
            model.addAttribute("title", "All Jobs: " + searchTerm);  // create a 'title' Model attribute
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);    // if not 'all' or 'empty string' then do this alt method search...
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);  // create alt 'title' Model attribute
        }

        model.addAttribute("columns", columnChoices);   // Create 'columns' attribute for use in search.html <span> tage column : columns.
//        model.addAttribute("searchType", searchType);     // Don't think I need this. Works fine without it.
//        model.addAttribute("searchTerm", searchTerm);     // Don't think I need this. Works fine without it.
        model.addAttribute("jobs", jobs);   // Create 'jobs' attribute to use in search.html to print job info.

        return "search";
    }

}
