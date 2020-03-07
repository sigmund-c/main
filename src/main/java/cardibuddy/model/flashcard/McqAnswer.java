package cardibuddy.model.flashcard;

import java.util.List;

public class McqAnswer implements Answer{

    private String correctAnswer; // should be "a" or "b" or "c" or ....
    private List<String> answers;

    public McqAnswer(int correctIndex, List<String> answers) {
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public boolean isValid(String test) {
        if (test.length() != 1) {
            return false;
        }
        return getNumberForChar(test.charAt(0)) <= answers.size(); // makes sure test is within number of given answers
    }

    public boolean checkAnswer(String toCheck) {
        return toCheck.equals(correctAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Shows the list of possible answers
     * @return a string of all possible answers
     */
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("Answer one of the following:\n");
        for (int i = 0; i < answers.size(); i++) {
            ans.append(getCharForNumber(i) + ". " + answers.get(i) + "\n");
        }
        return ans.toString();
    }

    private int getNumberForChar(char c) { // a -> 1; b -> 2; c -> 3; ...
        return ((int) c) - 64;
    }

    private char getCharForNumber(int i) { // 1 -> a; 2 -> b; 3 -> c, ...
        return i > 0 && i < 27 ? (char)(i + 64) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof McqAnswer)) {
            return false;
        }
        McqAnswer o = (McqAnswer) other;

        return answers.equals(o.answers) && correctAnswer.equals(o.correctAnswer);
    }
}

