package cardibuddy.model.deck;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import cardibuddy.testutil.DeckBuilder;

public class FilterDeckKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FilterDeckKeywordsPredicate firstPredicate = new FilterDeckKeywordsPredicate(firstPredicateKeywordList);
        FilterDeckKeywordsPredicate secondPredicate = new FilterDeckKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FilterDeckKeywordsPredicate firstPredicateCopy = new FilterDeckKeywordsPredicate(firstPredicateKeywordList);
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
        FilterDeckKeywordsPredicate predicate = new FilterDeckKeywordsPredicate(Collections
                .singletonList("Asynchronous"));
        assertTrue(predicate.test(new DeckBuilder().withTags("Asynchronous", "Programming").build()));

        // Multiple keywords
        predicate = new FilterDeckKeywordsPredicate(Arrays.asList("Asynchronous", "&", "Programming"));
        assertTrue(predicate.test(new DeckBuilder().withTags("Asynchronous", "Programming").build()));

        // Only one matching keyword
        predicate = new FilterDeckKeywordsPredicate(Arrays.asList("Programming", "&", "Java"));
        assertFalse(predicate.test(new DeckBuilder().withTags("Asynchronous", "Java").build()));

        // Mixed-case keywords
        predicate = new FilterDeckKeywordsPredicate(Arrays.asList("aSYnChrOnoUs", "pRoGRAmminG"));
        assertTrue(predicate.test(new DeckBuilder().withTags("Asynchronous", "Programming").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FilterDeckKeywordsPredicate predicate = new FilterDeckKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DeckBuilder().withTags("Asynchronous").build()));

        // Non-matching keyword
        predicate = new FilterDeckKeywordsPredicate(Arrays.asList("Java"));
        assertFalse(predicate.test(new DeckBuilder().withTags("Asynchronous", "Programming").build()));
    }
}

