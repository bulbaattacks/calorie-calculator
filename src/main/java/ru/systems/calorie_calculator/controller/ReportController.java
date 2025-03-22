package ru.systems.calorie_calculator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.systems.calorie_calculator.dto.report.DailyCalorieCheckDto;
import ru.systems.calorie_calculator.dto.report.DailyReportDto;
import ru.systems.calorie_calculator.dto.report.HistoryReportDto;
import ru.systems.calorie_calculator.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/daily/{userId}")
    public DailyReportDto getDailyReport(@PathVariable Long userId) {
        return service.calculateDailyReport(userId);
    }

    @GetMapping("/calorie_exceeded/{userId}")
    public DailyCalorieCheckDto getCalorieReport(@PathVariable Long userId) {
        return service.calculateCalorieReminderReport(userId);
    }

    @GetMapping("/history/{userId}")
    public List<HistoryReportDto> getHistoryReport(@PathVariable Long userId) {
        return service.createHistoryReport(userId);
    }
}
