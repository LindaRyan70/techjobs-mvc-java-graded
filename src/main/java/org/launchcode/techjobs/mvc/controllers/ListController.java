package org.launchcode.techjobs.mvc.controllers;


import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    static HashMap<String, Object> tableChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");

//   Task 2, Part 2: Add 'View All' Link to the 'All' column on the /list 'View Jobs By Category' page from list.html file.
//  Added a key/value pair to tableChoices in ListController() below by using 'all' for key name, (from listJobsByColumnAndValue() method further below.)
        tableChoices.put("all","View All Jobs");

        tableChoices.put("employer", JobData.getAllEmployers());
        tableChoices.put("location", JobData.getAllLocations());
        tableChoices.put("positionType", JobData.getAllPositionTypes());
        tableChoices.put("coreCompetency", JobData.getAllCoreCompetency());

    }

    @GetMapping(value = "")
    public String list(Model model) {
        model.addAttribute("columns", columnChoices);
        model.addAttribute("tableChoices", tableChoices);   //modified tableChoices by adding k/v pair in constructor above.
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "list";      // routes to list.html
    }

    @GetMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model, @RequestParam String column, @RequestParam(required = false) String value) {
        ArrayList<Job> jobs;

//   Task 2, Part 2: Add 'View All' Link to the 'All' column on the /list 'View Jobs By Category' page from list.html file.
//  Used 'all' from column.equals('all') below for the key when I added a key/value pair to tableChoices in ListController() above.
        if (column.equals("all")){

            jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
//  Task 2, Part 1: Display List of Jobs - This is where I got the attribute name 'jobs' to loop through in list-jobs.html
        model.addAttribute("jobs", jobs);

        return "list-jobs";     // routes to list-jobs.html
    }
}
