package cardibuddy.model.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import cardibuddy.commons.util.StringUtil;


/**
 * Tests that a {@code Deck or Card}'s {@code Title} matches any of the keywords given.
 */
public class SearchDeckKeywordsPredicate implements Predicate<Deck> {
    private final List<String> keywords;

    public SearchDeckKeywordsPredicate(List<String> keywords) {
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
     * @param keywords list of keywords from SearchCommand.
     * @return true if deck's title or tag contains any of the keywords.
     */
    private boolean searchOr(Deck deck, List<String> keywords) {
        boolean anyMatch = false;
        anyMatch = new ArrayList<>(keywords).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deck.getTitle().toString(), keyword));
        return anyMatch;
    }

    /**
     * Checks if deck contains all of the keywords with &.
     * @param keywords list of keywords from SearchCommand.
     * @return true if deck's title or tag contains all of the keywords with &.
     */
    private boolean searchAnd(Deck deck, List<String> keywords) {
        boolean anyMatch = false;
        List<List<String>> filteredKeywords = filterKeywords(keywords);

        for (List<String> keywordList : filteredKeywords) {
            boolean prevMatch = true;
            for (String keyword : keywordList) {
                if (!anyMatch && prevMatch) {
                    prevMatch = Arrays.stream(deck.getTitle().toString().split(" "))
                            .anyMatch(titleWord -> StringUtil.containsWordIgnoreCase(titleWord, keyword));
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
     * @param keywords list of keywords from SearchCommand.
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
                || (other instanceof SearchDeckKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SearchDeckKeywordsPredicate) other).keywords)); // state check
    }

}
