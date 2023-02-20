package com.example.covtrack;

public class DistricCasesModel {
    private String District, Death, Active, Recovered, Confirmed, TodayDeath, TodayRecovered, TodayActive;

    public DistricCasesModel(String district, String death, String active, String recovered, String confirmed, String todayDeath, String todayRecovered, String todayActive) {
        District = district;
        Death = death;
        Active = active;
        Recovered = recovered;
        Confirmed = confirmed;
        TodayDeath = todayDeath;
        TodayRecovered = todayRecovered;
        TodayActive = todayActive;
    }

    public String getDistrict() {
        return District;
    }

    public String getDeath() {
        return Death;
    }

    public String getActive() {
        return Active;
    }

    public String getRecovered() {
        return Recovered;
    }

    public String getConfirmed() {
        return Confirmed;
    }

    public String getTodayDeath() {
        return TodayDeath;
    }

    public String getTodayRecovered() {
        return TodayRecovered;
    }

    public String getTodayActive() {
        return TodayActive;
    }
}
