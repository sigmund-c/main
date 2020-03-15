package cardibuddy.model.deck;

import cardibuddy.commons.util.StringUtil;
import cardibuddy.model.flashcard.Flashcard;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class DeckContainsKeywordsPredicate implements Predicate<Deck> {
    private final List<String> keywords;

    public DeckContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Deck deck) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deck.getTitle().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeckContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DeckContainsKeywordsPredicate) other).keywords)); // state check
    }

}
