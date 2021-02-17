package covidvirus.tracker.services;

import covidvirus.tracker.model.LocalStat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyCovidDataServices {
    private static final String VIRUS_DATA_URL=
            "https://opendata.arcgis.com/datasets/d8eb52d56273413b84b0187a4e9117be_0.geojson";

    private List<LocalStat> allStats;

    public List<LocalStat> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "*   *   1  *   *   *")
    public void fetchTrackerData() throws IOException, InterruptedException {
        JSONObject responseBody = getResponseBodyFromRequest();
        JSONArray records = (JSONArray) responseBody.get("features");

        List<LocalStat> newStats = new ArrayList<>();
        for(int i = records.length()-1; i>=0; i--){
            JSONObject record = (JSONObject) records.get(i);
            JSONObject properties = (JSONObject) record.get("properties");

            LocalStat localStat = new LocalStat();
            localStat.setConfirmedCovidCases((Integer) properties.get("ConfirmedCovidCases"));
            localStat.setConfirmedCovidDeaths((Integer) properties.get("ConfirmedCovidDeaths"));
            java.lang.Integer hospitalisedCases = 0;
            if(!properties.get("HospitalisedCovidCases").equals(JSONObject.NULL)){
                hospitalisedCases = (Integer) properties.get("HospitalisedCovidCases");
            }
            localStat.setHospitalisedCovidCases(hospitalisedCases.intValue());

            localStat.setDate( properties.get("Date").toString().replace("T00:00:00Z",""));

            newStats.add(localStat);
        }
        this.allStats = newStats;
    }

    private JSONObject getResponseBodyFromRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(httpResponse.body());
    }
}
