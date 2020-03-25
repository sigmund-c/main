package cardibuddy.model.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import cardibuddy.commons.util.StringUtil;
import cardibuddy.model.tag.Tag;


/**
 * Tests that a {@code Deck or Card}'s {@code Tags} matches any of the keywords given.
 */
public class FilterDeckKeywordsPredicate implements Predicate<Deck> {
    private final List<String> keywords;

    public FilterDeckKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param deck the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Deck deck) {
        boolean anyMatch = false;
        if (checkAnd(keywords)) {
            anyMatch = searchAnd(deck, keywords);
        } else {
            anyMatch = searchOr(deck, keywords);
        }
        return anyMatch;
    }

    /**
     * Checks if deck contains either of the keywords.
     * @param keywords list of keywords from FilterCommand.
     * @return true if deck's title or tag contains any of the keywords.
     */
    private boolean searchOr(Deck deck, List<String> keywords) {
        boolean anyMatch = false;
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

    /**
     * Checks if deck contains all of the keywords with &.
     * @param keywords list of keywords from FilterCommand.
     * @return true if deck's title or tag contains all of the keywords with &.
     */
    private boolean searchAnd(Deck deck, List<String> keywords) {
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

    /**
     * Filters keywords to remove & symbol and to group keywords together as List objects.
     * @param keywords list of keywords from FilterCommand.
     * @return a List of Lists of keywords that are grouped accordingly.
     */
    private List<List<String>> filterKeywords(List<String> keywords) {
        List<List<String>> newList = new ArrayList<>();
        for (int i = 0; i < keywords.size(); i++) {
            if (keywords.get(i).equals("&")) {
                List<String> prev = newList.get(newList.size() - 1);
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
     * Checks if the symbol & is present in the FilterCommand keywords.
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
                || (other instanceof FilterDeckKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((FilterDeckKeywordsPredicate) other).keywords)); // state check
    }

}
