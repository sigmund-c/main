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
        if (checkAnd(keywords)) {
            anyMatch = testAnd(deck, keywords);
        } else {
            anyMatch = testOr(deck, keywords);
        }
        return anyMatch;
    }

    /**
     * Checks if deck contains either of the keywords.
     * @param keywords list of keywords from SearchCommand.
     * @return true if deck's title or tag contains any of the keywords.
     */
    private boolean testOr(Deck deck, List<String> keywords) {
        boolean anyMatch = false;
        anyMatch = new ArrayList<>(keywords).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deck.getTitle().toString(), keyword));
        if (anyMatch) {
            return anyMatch;
        } else {
            for (Tag t : deck.getTags()) {
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

    /**
     * Checks if deck contains all of the keywords with &.
     * @param keywords list of keywords from SearchCommand.
     * @return true if deck's title or tag contains all of the keywords with &.
     */
    private boolean testAnd(Deck deck, List<String> keywords) {
        boolean anyMatch = false;
        List<List<String>> filteredKeywords = filterKeywords(keywords);

        for (List<String> keywordList : filteredKeywords) {
            boolean prevMatch = true;
            for (String keyword : keywordList) {
                if (!anyMatch && prevMatch) {
                    prevMatch = new ArrayList<>(deck.getTags()).stream()
                            .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.toString(), "[" + keyword + "]"));
                } else {
                    break;
                }
            }

            if (prevMatch) {
                anyMatch = true;
            }
        }
        return anyMatch;
    }

    private List<List<String>> filterKeywords(List<String> keywords) {
        List<List<String>> newList = new ArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            if (keywords.get(i).equals("&")) {
                List<String> prev = newList.get(i - 1);
                prev.add(keywords.get(i + 1));
                i++;
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(keywords.get(i));
                newList.add(temp);
            }
        }
        return newList;
    }

    /**
     * Checks if the symbol & is present in the SearchCommand keywords.
     * @param keywords list of keywords to be searched
     * @return true if & is present
     */
    private boolean checkAnd(List<String> keywords) {
        for (String keyword : keywords) {
            if (keyword.equals("&")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeckContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DeckContainsKeywordsPredicate) other).keywords)); // state check
    }

}
