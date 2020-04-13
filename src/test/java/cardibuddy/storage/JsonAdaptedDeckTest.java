package cardibuddy.storage;

import static cardibuddy.storage.JsonAdaptedDeck.MISSING_FIELD_MESSAGE_FORMAT;
import static cardibuddy.testutil.Assert.assertThrows;
import static cardibuddy.testutil.TypicalDecks.POSTGRESQL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cardibuddy.commons.exceptions.IllegalValueException;
import cardibuddy.model.deck.Statistics;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

public class JsonAdaptedDeckTest {
    private static final String INVALID_TITLE = "CS!!";
    private static final String INVALID_TAG = "#HASHTAG";

    private static final String VALID_TITLE = POSTGRESQL.getTitle().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = POSTGRESQL.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final JsonAdaptedStatistic VALID_STATISTICS = new JsonAdaptedStatistic(new Statistics());

    @Test
    public void toModelType_validDeckDetails_returnsDeck() throws Exception {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(POSTGRESQL);
        assertEquals(POSTGRESQL, deck.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(INVALID_TITLE, null, VALID_TAGS, VALID_STATISTICS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedDeck deck = new JsonAdaptedDeck(null, null, VALID_TAGS, VALID_STATISTICS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedDeck deck = new JsonAdaptedDeck(VALID_TITLE, null, invalidTags, VALID_STATISTICS);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deck::toModelType);
    }
}
