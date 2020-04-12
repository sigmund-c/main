package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.Answer;
import cardibuddy.model.flashcard.Flashcard;
import cardibuddy.model.flashcard.Question;

/**
 * A stub class for Card, overrides method that are used by TestSession.
 */
public class CardStub extends Flashcard {
    private Question questionStub;
    private Answer answerStub;

    private String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CardStub(Deck deck, Question question, Answer answer, String path) {
        super(deck, question, answer, path);
        questionStub = question;
        answerStub = answer;
    }

    @Override
    public Question getQuestion() {
        return questionStub;
    }

    @Override
    public Answer getAnswer() {
        return answerStub;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CardStub
                && this.questionStub.equals(((CardStub) o).questionStub)
                && this.answerStub.equals(((CardStub) o).answerStub);
    }
}
