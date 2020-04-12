package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.*;

import java.util.Objects;
import java.util.Random;

public class CardStub extends Flashcard {
    Question questionStub;
    Answer answerStub;

    String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
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
