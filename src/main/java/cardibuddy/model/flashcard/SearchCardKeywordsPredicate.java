package cardibuddy.model.flashcard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import cardibuddy.commons.util.StringUtil;


/**
 * Tests that a {@code Card}'s {@code Question} matches any of the keywords given.
 */
public class SearchCardKeywordsPredicate implements Predicate<Card> {
    private final List<String> keywords;

    public SearchCardKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param card the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(Card card) {
        boolean anyMatch = false;
        if (checkAnd(keywords)) {
            anyMatch = searchAnd(card, keywords);
        } else {
            anyMatch = searchOr(card, keywords);
        }
        return anyMatch;
    }

    /**
     * Checks if card contains either of the keywords.
     * @param keywords list of keywords from SearchCardCommand.
     * @return true if card's question contains any of the keywords.
     */
    private boolean searchOr(Card card, List<String> keywords) {
        boolean anyMatch = false;
        String question = card.getQuestion().toString();
        if (question.charAt(question.length() - 1) == '?' || question.charAt(question.length() - 1) == '.') {
            question = question.substring(0, question.length() - 1);
        }
        final String filteredQues = question;

        anyMatch = new ArrayList<>(keywords).stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(filteredQues, keyword));
        return anyMatch;
    }

    /**
     * Checks if card contains all of the keywords with &.
     * @param keywords list of keywords from SearchCardCommand.
     * @return true if card's title contains all of the keywords with &.
     */
    private boolean searchAnd(Card card, List<String> keywords) {
        boolean anyMatch = false;

        String question = card.getQuestion().toString();
        if (question.charAt(question.length() - 1) == '?' || question.charAt(question.length() - 1) == '.') {
            question = question.substring(0, question.length() - 1);
        }
        final String filteredQues = question;

        List<List<String>> filteredKeywords = filterKeywords(keywords);

        for (List<String> keywordList : filteredKeywords) {
            boolean prevMatch = true;
            for (String keyword : keywordList) {
                if (!anyMatch && prevMatch) {
                    prevMatch = Arrays.stream(filteredQues.split(" "))
                            .anyMatch(words -> StringUtil.containsWordIgnoreCase(words, keyword));
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
     * @param keywords list of keywords from SearchDeckCommand.
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
     * Checks if the symbol & is present in the SearchDeckCommand keywords.
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
                || (other instanceof SearchCardKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SearchCardKeywordsPredicate) other).keywords)); // state check
    }

}

