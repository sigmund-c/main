package cardibuddy.ui;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import cardibuddy.commons.core.LogsCenter;
import cardibuddy.model.deck.Statistics;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private HBox statisticsPane;

    @FXML
    private Label cardsAdded;

    @FXML
    private Label cardsDeleted;

    @FXML
    private Label timesPlayed;

    @FXML
    private Label correctPercentage;

    @FXML
    private Label avgTriesToGetCorrect;

    @FXML
    private LineChart correctPercentageHistory;


    public StatisticsPanel(Statistics statistics) {
        super(FXML);
        this.statistics = statistics;
        cardsAdded.setText("No. of Cards added: " + statistics.getCardsAdded());
        cardsDeleted.setText("No. of Cards deleted: " + statistics.getCardsDeleted());
        timesPlayed.setText("No. of sessions played: " + statistics.getTimesPlayed());
        correctPercentage.setText("Percent of correct answers: "
                                     + percentFormat.format(statistics.getCorrectPercentage()));
        avgTriesToGetCorrect.setText("Average tries to get correct answer: " + statistics.getAvgTriesToGetCorrect());

        XYChart.Series series = new XYChart.Series();
        int recordIndex = 1;
        for (Double record : statistics.getCorrectPercentageHistory()) {
            series.getData().add(new XYChart.Data(recordIndex, record));
            recordIndex++;
        }

        correctPercentageHistory.getData().setAll(series);
        correctPercentageHistory.setLegendVisible(false);
    }


}
