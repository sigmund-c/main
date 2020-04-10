package cardibuddy.model.deck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cardibuddy.testutil.DeckBuilder;

public class SearchDeckKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SearchDeckKeywordsPredicate firstPredicate = new SearchDeckKeywordsPredicate(firstPredicateKeywordList);
        SearchDeckKeywordsPredicate secondPredicate = new SearchDeckKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SearchDeckKeywordsPredicate firstPredicateCopy = new SearchDeckKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        SearchDeckKeywordsPredicate predicate = new SearchDeckKeywordsPredicate(Collections
                .singletonList("Asynchronous"));
        assertTrue(predicate.test(new DeckBuilder().withTitle("Asynchronous Programming").build()));

        // Multiple keywords
        predicate = new SearchDeckKeywordsPredicate(Arrays.asList("Asynchronous", "Programming"));
        assertTrue(predicate.test(new DeckBuilder().withTitle("Asynchronous Programming").build()));

        // Only one matching keyword
        predicate = new SearchDeckKeywordsPredicate(Arrays.asList("Programming", "Java"));
        assertTrue(predicate.test(new DeckBuilder().withTitle("Asynchronous Java").build()));

        // Mixed-case keywords
        predicate = new SearchDeckKeywordsPredicate(Arrays.asList("aSYnChrOnoUs", "pRoGRAmminG"));
        assertTrue(predicate.test(new DeckBuilder().withTitle("Asynchronous Programming").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SearchDeckKeywordsPredicate predicate = new SearchDeckKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DeckBuilder().withTitle("Asynchronous").build()));

        // Non-matching keyword
        predicate = new SearchDeckKeywordsPredicate(Arrays.asList("Java"));
        assertFalse(predicate.test(new DeckBuilder().withTitle("Asynchronous Programming").build()));
    }
}

