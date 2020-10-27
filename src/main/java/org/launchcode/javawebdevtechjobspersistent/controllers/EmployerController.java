package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    //makes sure that the controller can utilize the database.
    @Autowired
    private EmployerRepository employerRepository;


    //loads new add employer form and gives it an employer object to work with
    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    //reloads add employer page if errors are present
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        //if no errors, add the emplopyer to the list and go back to add job page.
        employerRepository.save(newEmployer);
        return "redirect:/add";
    }

    //views information about the employer.
    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        //picks out the right employer object.
        Optional optEmployer = employerRepository.findById(employerId);
        //if employer object exists, look at info.
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            //return to the home page if employer doesn't exist.
            return "redirect:/";
        }
    }
}
