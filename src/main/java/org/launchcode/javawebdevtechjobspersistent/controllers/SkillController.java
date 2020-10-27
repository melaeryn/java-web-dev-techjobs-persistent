package org.launchcode.javawebdevtechjobspersistent.controllers;


import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;


//controller for skills-based tasks.
@Controller
@RequestMapping("skills")
public class SkillController {

    //links up the Database so that we can utilize it.
    @Autowired
    private SkillRepository skillRepository;

    //load add skills form, giving it a skill object to manipulate.
    @GetMapping("add")
    public String displayAddSkillsForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    //adds new skill to list, or reloads form if errors.
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }
        skillRepository.save(newSkill);
        return "redirect:/add";
    }

    //if skill id exists, loads info about it to display.
    @GetMapping("view/{skillId}")
    public String displayViewEmployer(Model model, @PathVariable int skillId) {

        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }
}
