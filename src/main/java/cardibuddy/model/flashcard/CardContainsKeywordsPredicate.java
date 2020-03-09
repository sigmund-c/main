package cardibuddy.model.flashcard;

import java.util.List;
import java.util.function.Predicate;

import cardibuddy.commons.util.StringUtil;
import cardibuddy.model.flashcard.CardContainsKeywordsPredicate;
import cardibuddy.model.flashcard.Flashcard;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class CardContainsKeywordsPredicate implements Predicate<Flashcard> {
    private final List<String> keywords;

    public CardContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Flashcard card) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(card.getQuestion().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CardContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CardContainsKeywordsPredicate) other).keywords)); // state check
    }

}
