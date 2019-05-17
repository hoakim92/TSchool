package com.tsystems.lims.controller;

import com.tsystems.lims.dto.CureDto;
import com.tsystems.lims.models.Cure;
import com.tsystems.lims.models.CureType;
import com.tsystems.lims.service.interfaces.ICureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@Secured("ROLE_ADMIN")
public class CureController {
    @Autowired
    ICureService cureService;

    @PostMapping("/cures")
    public String postCure(@RequestParam(value = "id", required = false) Integer id, @RequestParam("name") String name, @RequestParam("type") String type, Model model) {
        CureDto cureDto = cureService.saveOrUpdateCure(id, name, type);
        return "redirect:cures/"+cureDto.getId();
    }

    @GetMapping("cures/createcure")
    public String createCure(Model model) {
        model.addAttribute("types", CureType.values());
        return "cure";
    }

    @GetMapping("/cures")
    public String getAllCures() {
        return "curesList";
    }

    @RequestMapping(value = "/cures/{id}", method = GET)
    public String getCure(Model model, @PathVariable int id) {
        model.addAttribute("cure", cureService.getCure(id));
        model.addAttribute("types", CureType.values());
        return "cure";
    }
}
