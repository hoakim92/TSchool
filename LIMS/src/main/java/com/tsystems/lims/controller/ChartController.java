package com.tsystems.lims.controller;

import com.tsystems.lims.service.interfaces.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ChartController {
    @Autowired
    IStatisticService statisticService;

    @GetMapping("/chart")
    public String getLoginPage(Model model) {
        model.addAttribute("doctorValues", statisticService.getDoctorsStatistic());
        model.addAttribute("eventValues", statisticService.getEventsStatistic());
        return "pieChart";
    }
}