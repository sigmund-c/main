import cardibuddy.model.testing.TestSetting;

import java.util.stream.Stream;

public class TestSession {
    public final Deck deck;
    public Flashcard current;
    public boolean correct;
    // space to set deck settings


    // space to set test statistics
    public int numberRight;

    // test queue
    public ArrayList<Flashcard> testQueue;

    // test response strings
    public String CORRECT_STRING = "Correct! The answer is %s.\nType /n to go to the next question.";
    public String WRONG_STRING = "Wrong! The answer is %s.\nType /n to go to the next question.";
    public String END_STRING = "Test complete!";

    public TestSession(Deck deck) {
        this.deck = deck;
    }


    public Flashcard start() {
        this.testQueue = deck.cards; // to change to whatever name there is
        this.current = testQueue.remove();
        return current;
    }

    public String checkAnswer(String userAnswer) {
        String answer = current.getAnswer();
        if (answer.equals(userAnswer)) {
            this.correct = true;
            return String.format(CORRECT_STRING, answer);
        }
        else {
            this.correct = false;
            return String.format(WRONG_STRING, answer);
        }
    }

    public Flashcard showNext() {

    }

}


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof TestSession)
                && this.deck.equals((TestSession) other.deck)
                && this.settings.equals((TestSession) other.settings);
    }
}
