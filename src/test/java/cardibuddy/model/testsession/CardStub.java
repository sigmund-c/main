package cardibuddy.model.testsession;

import cardibuddy.model.deck.Deck;
import cardibuddy.model.flashcard.*;

public class CardStub extends Card {
    int option;
    Question questionStub;
    Answer answerStub;
    
    public CardStub(int option) {
        super(null, new Question("test"), new ShortAnswer("test"), "");
        this.option = option;
        switch (option){
        case 0: // for MCQ flashcards
            questionStub = new Question("This is an MCQ questionStubStub");
            answerStub = new McqAnswer("A");
            break;
        case 1: // For TF and MCQ flashcards, tests single word testing.
            questionStub = new Question("This is a tf questionStubStub.");
            answerStub = new TfAnswer("True");
            break;
        case 2: // short answerStub questionStubs
            questionStub = new Question("This is a short answerStub questionStubStub.");
            answerStub = new ShortAnswer("Yes this is.");
            break;
        case 3: // test with numbers
            questionStub = new Question("questionStubStub");
            answerStub = new ShortAnswer("Answer");
            break;
        case 4:
            questionStub = new Question()
        }
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
    public CardType getCardType() {
        return null;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CardStub 
                && this.questionStub.equals(((CardStub) o).questionStub)
                && this.answerStub.equals(((CardStub) o).answerStub);
    }
}
