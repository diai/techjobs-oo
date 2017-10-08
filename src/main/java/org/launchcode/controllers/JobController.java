package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job",job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        String name = jobForm.getName();
        Employer employer = null;
        Location location = null;
        PositionType positionType = null;
        CoreCompetency coreCompetency = null;
        for (Employer item : jobForm.getEmployers()) {
            if (item.getId() == jobForm.getEmployerId())
                employer = item;
        }
        for (Location item : jobForm.getLocations()) {
            if (item.getId() == jobForm.getLocationId())
                location = item;
        }
        for (PositionType item : jobForm.getPositionTypes()) {
            if (item.getId() == jobForm.getPositionTypeId())
                positionType = item;
        }
        for (CoreCompetency item : jobForm.getCoreCompetencies()) {
            if (item.getId() == jobForm.getCoreCompetencyId())
                coreCompetency = item;
        }
        if (name == "" || employer == null || location == null || positionType == null || coreCompetency == null)
        {
            jobForm.setName("Name may not be empty");
            return "new-job";
        }
        else
        {
            Job job = new Job(name,employer,location,positionType,coreCompetency);
            jobData.add(job);
            return "redirect:/job?id=" + job.getId();

        }

    }
}
