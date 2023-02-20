package com.example.covtrack;


public class StateCasesModel {
    private String State, Death, Active, Recovered, Confirmed, LastUpdated, TodayDeath, TodayRecovered, TodayActive;

    StateCasesModel(String state, String death, String active, String recovered, String confirmed, String lastUpdated, String todayDeath, String todayRecovered, String todayActive) {
        State = state;
        Death = death;
        Active = active;
        Recovered = recovered;
        Confirmed = confirmed;
        LastUpdated = lastUpdated;
        TodayDeath = todayDeath;
        TodayRecovered = todayRecovered;
        TodayActive = todayActive;
    }

    public String getState() {
        return State;
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

    public String getLastUpdated() {
        return LastUpdated;
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
