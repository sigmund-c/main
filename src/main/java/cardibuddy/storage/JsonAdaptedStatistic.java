package cardibuddy.storage;

import cardibuddy.model.deck.Statistics;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAdaptedStatistic {

    private final String cardsAdded;
    private final String cardsDeleted;
    private final String decksAdded;
    private final String decksDeleted;
    private final String timesPlayed;
    private final String cardsPlayed;
    private final String avgCorrectPercentage;
    private final String avgTriesToGetCorrect;
    private final HashMap<String, List<Double>> correctPercentageHistory = new HashMap<>();

    @JsonCreator
    public JsonAdaptedStatistic(
                        @JsonProperty("cardsAdded") String cardsAdded, @JsonProperty("cardsDeleted") String cardsDeleted,
                        @JsonProperty("decksAdded") String decksAdded, @JsonProperty("decksDeleted") String decksDeleted,
                        @JsonProperty("timesPlayed") String timesPlayed, @JsonProperty("cardsPlayed") String cardsPlayed,
                        @JsonProperty("avgCorrectPercentage") String avgCorrectPercentage,
                        @JsonProperty("avgTriesToGetCorrect") String avgTriesToGetCorrect,
                        @JsonProperty("correctPercentageHistory")
                                HashMap<String, List<Double>> correctPercentageHistory) {
        this.cardsAdded = cardsAdded;
        this.cardsDeleted = cardsDeleted;
        this.decksAdded = decksAdded;
        this.decksDeleted = decksDeleted;
        this.timesPlayed = timesPlayed;
        this.cardsPlayed = cardsPlayed;
        this.avgCorrectPercentage = avgCorrectPercentage;
        this.avgTriesToGetCorrect = avgTriesToGetCorrect;
        if (correctPercentageHistory != null) {
            this.correctPercentageHistory.putAll(correctPercentageHistory);
        }
    }

    public JsonAdaptedStatistic(Statistics source) {
        cardsAdded = String.valueOf(source.getCardsAdded());
        cardsDeleted = String.valueOf(source.getCardsDeleted());
        decksAdded = String.valueOf(source.getDecksAdded());
        decksDeleted = String.valueOf(source.getDecksAdded());
        timesPlayed = String.valueOf(source.getTimesPlayed());
        cardsPlayed = String.valueOf(source.getCardsPlayed());
        avgCorrectPercentage = String.valueOf(source.getAvgCorrectPercentage());
        avgTriesToGetCorrect = String.valueOf(source.getAvgTriesToGetCorrect());
        correctPercentageHistory.putAll(source.getCorrectPercentageHistory());
    }

    public Statistics toModeltype() {
        Statistics toReturn = new Statistics();
        toReturn.setCardsAdded(Integer.parseInt(cardsAdded));
        toReturn.setCardsDeleted(Integer.parseInt(cardsDeleted));
        toReturn.setDecksAdded(Integer.parseInt(decksAdded));
        toReturn.setDecksDeleted(Integer.parseInt(decksDeleted));
        toReturn.setTimesPlayed(Integer.parseInt(timesPlayed));
        toReturn.setCardsPlayed(Integer.parseInt(cardsPlayed));
        toReturn.setAvgCorrectPercentage(Double.parseDouble(avgCorrectPercentage));
        toReturn.setAvgTriesToGetCorrect(Double.parseDouble(avgTriesToGetCorrect));
        ObjectMapper mapper = new ObjectMapper();
        toReturn.setCorrectPercentageHistory(correctPercentageHistory);

        return toReturn;
    }
}
