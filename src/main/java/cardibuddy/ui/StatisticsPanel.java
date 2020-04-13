package cardibuddy.ui;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.deck.Statistics;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing Statistics report.
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private static final DecimalFormat percentFormat = new DecimalFormat("##.##%");

    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    private final Statistics statistics;

    @FXML
    private ListView statsList;

    @FXML
    private LineChart correctPercentageHistory;


    public StatisticsPanel(Statistics statistics) {
        super(FXML);

        statsList.setStyle("-fx-control-inner-background: blue;");

        this.statistics = statistics;
        if (statistics.getCardsAdded() != 0) {

            statsList.getItems().add("No of Cards added: " + statistics.getCardsAdded());
        }
        if (statistics.getCardsDeleted() != 0) {
            statsList.getItems().add("No of Cards deleted: " + statistics.getCardsDeleted());
        }
        if (statistics.getDecksAdded() > 1) {
            statsList.getItems().add("No of Decks added: " + statistics.getDecksAdded());
        }
        if (statistics.getDecksDeleted() != 0) {
            statsList.getItems().add("No of Decks deleted: " + statistics.getDecksDeleted());
        }
        if (statistics.getTimesPlayed() != 0) {
            statsList.getItems().add("Test Sessions played: " + statistics.getTimesPlayed());
        }
        if (statistics.getCardsPlayed() != 0) {
            statsList.getItems().add("Total Cards played: " + statistics.getCardsPlayed());
        }
        if (statistics.getAvgTriesToGetCorrect() != 0.0) {
            statsList.getItems().add("Average tries to get correct: " + statistics.getAvgTriesToGetCorrect());
        }
        if (statistics.getAvgCorrectPercentage() != 0) {
            statsList.getItems().add("Average correct percentage: "
                                    + percentFormat.format(statistics.getAvgCorrectPercentage()));
        }

        for (Map.Entry<String, List<Double>> deckHistory: statistics.getCorrectPercentageHistory().entrySet()) {
            XYChart.Series newSeries = new XYChart.Series();
            newSeries.setName(deckHistory.getKey());

            int recordIndex = 1;
            for (Double record : deckHistory.getValue()) {
                newSeries.getData().add(new XYChart.Data("" + recordIndex, record * 100));
                recordIndex++;
            }

            correctPercentageHistory.getData().add(newSeries);
        }

    }

}
