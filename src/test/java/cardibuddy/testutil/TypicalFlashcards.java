package cardibuddy.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cardibuddy.model.flashcard.Flashcard;

/**
 * A utility class containing a list of {@code Flashcard} objects to be used in tests.
 */
public class TypicalFlashcards {

    public static final Flashcard HELLOWORLD = new FlashcardBuilder().withQuestion("What follows 'Hello'?")
            .withAnswer("World").build();

    public static List<Flashcard> getTypicalFlashcards() {
        return new ArrayList<>(Arrays.asList(HELLOWORLD));
    }
}
