package cardibuddy.storage;

import static cardibuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import cardibuddy.model.deck.Statistics;

public class JsonAdaptedStatisticTest {
    public static final String INVALID_NUM = "BABA YAGA";

    public static final String VALID_INT = "0";
    public static final String VALID_DOUBLE = "0.0";
    public static final HashMap<String, List<Double>> VALID_HISTORY = new HashMap<>();

    @Test
    public void toModelType_validStatisticsDetails_returnsStatistics() throws Exception {
        JsonAdaptedStatistic statistic = new JsonAdaptedStatistic(VALID_INT, VALID_INT, VALID_INT, VALID_INT, VALID_INT,
                VALID_INT, VALID_DOUBLE, VALID_DOUBLE, VALID_HISTORY);
        Statistics validStatistic = new Statistics();
        assertEquals(validStatistic, statistic.toModeltype());
    }

    @Test
    public void toModelType_invalidInt_throwsNumberFormatException() {
        JsonAdaptedStatistic statistic = new JsonAdaptedStatistic(INVALID_NUM, VALID_INT, VALID_INT, VALID_INT,
                VALID_INT, VALID_INT, VALID_DOUBLE, VALID_DOUBLE, VALID_HISTORY);
        assertThrows(NumberFormatException.class, statistic::toModeltype);
    }

    @Test
    public void toModelType_invalidDouble_throwsNumberFormatException() {
        JsonAdaptedStatistic statistic = new JsonAdaptedStatistic(VALID_INT, VALID_INT, VALID_INT, VALID_INT, VALID_INT,
                VALID_INT, INVALID_NUM, VALID_DOUBLE, VALID_HISTORY);
        assertThrows(NumberFormatException.class, statistic::toModeltype);
    }
}
