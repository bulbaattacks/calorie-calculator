package ru.systems.calorie_calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.report.DailyCalorieCheckDto;
import ru.systems.calorie_calculator.dto.report.DailyReportDto;
import ru.systems.calorie_calculator.service.ReportService;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/daily/{id}")
    public DailyReportDto getDailyReport(@PathVariable Long id) {
        return service.calculateDailyReport(id);
    }

    @GetMapping("/calorie/{id}")
    public DailyCalorieCheckDto getCalorieReport(@PathVariable Long id) {
        return service.calculateCalorieReport(id);
    }
}
