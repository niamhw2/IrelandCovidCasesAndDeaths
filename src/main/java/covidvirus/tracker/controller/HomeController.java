package covidvirus.tracker.controller;

import covidvirus.tracker.model.LocalStat;
import covidvirus.tracker.services.DailyCovidDataServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

@Controller
public class HomeController {

    private DailyCovidDataServices dailyCovidDataServices;

    public HomeController(DailyCovidDataServices dailyCovidDataServices) {
        this.dailyCovidDataServices = dailyCovidDataServices;
    }

    @GetMapping("/")
    public String home(Model model) throws ParseException {
        List<LocalStat> allStats = dailyCovidDataServices.getAllStats();
        DecimalFormat df = new DecimalFormat("###,###,###");
        int totalCases1 = allStats.stream().mapToInt(x -> x.getConfirmedCovidCases()).sum();
        String totalCases = df.format(totalCases1);
        int totalDeaths1= allStats.stream().mapToInt(x -> x.getConfirmedCovidDeaths()).sum();
        String totalDeaths = df.format(totalDeaths1);
        model.addAttribute("locationStats",allStats);
        model.addAttribute("totalReportedCases",totalCases);
        model.addAttribute("totalReportedDeaths",totalDeaths);
        return "home";
    }
}
