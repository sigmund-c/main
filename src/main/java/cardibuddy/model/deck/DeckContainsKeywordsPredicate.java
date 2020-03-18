package cardibuddy.model.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import cardibuddy.commons.util.StringUtil;
import cardibuddy.model.tag.Tag;


/**
 * Tests that a {@code Deck}'s {@code Keyword} matches any of the keywords given.
 */
public class DeckContainsKeywordsPredicate implements Predicate<Deck> {
    private final List<String> keywords;

    public DeckContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Deck deck) {
        boolean anyMatch = false;
        anyMatch = new ArrayList<>(keywords).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deck.getTitle().toString(), keyword));
        if (anyMatch) {
            return anyMatch;
        } else {
            for (Tag t:deck.getTags()) {
                System.out.println(t.toString());
                if (!anyMatch) {
                    anyMatch = new ArrayList<>(keywords).stream()
                            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(t.toString(), "[" + keyword + "]"));
                } else {
                    break;
                }
            }
            return anyMatch;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeckContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DeckContainsKeywordsPredicate) other).keywords)); // state check
    }

}
