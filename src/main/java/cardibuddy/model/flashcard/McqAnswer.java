package cardibuddy.model.flashcard;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * McqAnswer class.
 */
public class McqAnswer implements Answer {
    public static final String MESSAGE_CONSTRAINTS = "MCQ answers should be a single letter corresponding to answer.";
    private static final String MCQ_REGEX = "[a][)].*|[b][)].*|[c][)].*";

    private String correctAnswer; // should be "a" or "b" or "c" or ....
    private List<String> answerList;

    public McqAnswer(String answer) {
        requireNonNull(answer);
        checkArgument(isValid(answer), MESSAGE_CONSTRAINTS);
        answerList = new ArrayList();

        int indexA = answer.indexOf("a)");
        int indexB = answer.indexOf("b)");
        int indexC = answer.indexOf("c)");

        int first = Math.min(indexA, Math.min(indexB, indexC));
        int second;
        int third;

        if (indexA == first) {
            correctAnswer = "a";
            second = Math.min(indexB, indexC);
            third = Math.max(indexB, indexC);
        } else if (indexB == first) {
            correctAnswer = "b";
            second = Math.min(indexA, indexC);
            third = Math.max(indexA, indexC);
        } else {
            correctAnswer = "c";
            second = Math.min(indexA, indexB);
            third = Math.max(indexA, indexB);
        }

        if (indexA == first) {
            answerList.add(answer.substring(indexA, second));
        } else if (indexA == second) {
            answerList.add(answer.substring(indexA, third));
        } else {
            answerList.add(answer.substring(indexA));
        }
    }

    public List getAnswerList() {
        return this.answerList;
    }

    /**
     * Checks if test length is valid.
     * @param test
     * @return true if ...
     */
    public boolean isValid(String test) {
        return test.matches(MCQ_REGEX);
    }

    /**
     * Javadocs to pass checkstyle.
     * @param toCheck
     * @return true if answer is valid.
     */
    public boolean checkAnswer(String toCheck) {
        requireNonNull(toCheck);
        if (!isValid(toCheck)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return toCheck.equals(correctAnswer);
    }

    /**
     * Shows the list of possible answers
     * @return a string of all possible answers
     */
    @Override
    public String toString() {
        return correctAnswer;
    }

    // returns the corresponding number for alphabetical letters, eg. a -> 1; b -> 2; c -> 3; ...
    private int getNumberForChar(char c) {
        int num = ((int) c) - 64;
        return num > 0 && num < 27 ? num : -1;
    }

    // returns the corresponding alphabetical letter for numbers
    private char getCharForNumber(int i) { // 1 -> a; 2 -> b; 3 -> c, ...
        return i > 0 && i < 27 ? (char) (i + 64) : null;
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

        return correctAnswer.equals(o.correctAnswer);
    }
}

