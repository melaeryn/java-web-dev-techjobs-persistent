package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Job;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.JobRepository;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    //link up to the employer database
    @Autowired
    private EmployerRepository employerRepository;

    //link up to the skill database
    @Autowired
    private SkillRepository skillRepository;

    //link up to the job database
    @Autowired
    private JobRepository jobRepository;


    //homepage
    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");

        return "index";
    }

    //pull up the job form
    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        //give the page a title
        model.addAttribute("title", "Add Job");
        //give it a new job object to play with
        model.addAttribute(new Job());
        //make sure the skills and employers that are already in database show up
        model.addAttribute("employers",employerRepository.findAll());
        model.addAttribute("skills",skillRepository.findAll());
        return "add";
    }

    //process the job form
    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {
        //if there are errors, return to the add page.
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        //add the new job to the database of jobs, then take us to the landing page
        if (employerRepository.findById(employerId).isPresent()){
            newJob.setEmployer(employerRepository.findById(employerId).get());
        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);
        jobRepository.save(newJob);
    }
        return "redirect:";
    }

    //view the job by the id
    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        //actually adds proper job to the model.
        if (jobRepository.findById(jobId).isPresent()) {
            model.addAttribute("job", jobRepository.findById(jobId).get());
        }
        return "view";
    }


}
