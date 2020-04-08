package cardibuddy.model.deck;

import static cardibuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }


    @Test
    public void isValidTitle() {
        // null name
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidTitle("CS2103*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidTitle("hello world")); // alphabets only
        assertTrue(Title.isValidTitle("1231")); // numbers only
        assertTrue(Title.isValidTitle("algorithms 2nd edition")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Steven Halim")); // with capital letters
        assertTrue(Title.isValidTitle("Competitive Programming in C Programming Language")); // long names
    }
}
