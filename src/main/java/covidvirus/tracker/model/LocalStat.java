package covidvirus.tracker.model;

import java.time.LocalDateTime;
import java.util.Date;

public class LocalStat {
    private int confirmedCovidDeaths;
    private int confirmedCovidCases;
    private String date;
    private int hospitalisedCovidCases;

    public int getConfirmedCovidDeaths() {
        return confirmedCovidDeaths;
    }

    public void setConfirmedCovidDeaths(int confirmedCovidDeaths) {
        this.confirmedCovidDeaths = confirmedCovidDeaths;
    }

    public int getConfirmedCovidCases() {
        return confirmedCovidCases;
    }

    public void setConfirmedCovidCases(int confirmedCovidCases) {
        this.confirmedCovidCases = confirmedCovidCases;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setHospitalisedCovidCases(int hospitalisedCovidCases) {
        this.hospitalisedCovidCases = hospitalisedCovidCases;
    }

    public int getHospitalisedCovidCases() {
        return hospitalisedCovidCases;
    }
}
