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

    private String original;
    private String correctAnswer; // should be "a" or "b" or "c" or ....
    private List<String> A;

    public McqAnswer(String answer) {
        requireNonNull(answer);
        checkArgument(isValid(answer), MESSAGE_CONSTRAINTS);
        original = answer;

        separateIndexes(answer);
    }

    /**
     * Separates indexes to be inserted into the list.
     * @param answer
     */
    private void separateIndexes(String answer) {
        A = new ArrayList();

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

        assignToList(indexA, indexB, indexC, first, second, third, answer);

    }

    /**
     * Assigns positions of choices a, b and c.
     * @param indexA
     * @param indexB
     * @param indexC
     * @param first
     * @param second
     * @param third
     * @param answer
     */
    private void assignToList(int indexA, int indexB, int indexC,
                              int first, int second, int third, String answer) {
        if (indexA == first) {
            A.add(answer.substring(indexA, second));
        } else if (indexA == second) {
            A.add(answer.substring(indexA, third));
        } else {
            A.add(answer.substring(indexA));
        }

        if (indexB == first) {
            A.add(answer.substring(indexB, second));
        } else if (indexB == second) {
            A.add(answer.substring(indexB, third));
        } else {
            A.add(answer.substring(indexB));
        }

        if (indexC == first) {
            A.add(answer.substring(indexC, second));
        } else if (indexC == second) {
            A.add(answer.substring(indexC, third));
        } else {
            A.add(answer.substring(indexC));
        }
    }

    public List getAnswerList() {
        return this.A;
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
        return original;
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

