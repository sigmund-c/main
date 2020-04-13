package cardibuddy.model.flashcard;

import static cardibuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import cardibuddy.logic.parser.exceptions.ParseException;
import cardibuddy.model.flashcard.exceptions.WrongMcqAnswerTypeException;


/**
 * McqAnswer class.
 */
public class McqAnswer extends Answer {
    public static final String MESSAGE_CONSTRAINTS = "MCQ answers should be a single letter corresponding to answer.";
    private static final String MCQ_REGEX = "[A][)].*|[B][)].*|[C][)].*";
    private static final String ANS_REGEX = "(?=.{1}$)^[ABC]";

    protected ArrayList<String> answerList;

    private String original;
    private String correctAnswer; // should be "A" or "B" or "C"

    public McqAnswer(String answer) throws ParseException {
        requireNonNull(answer);

        if (answer.length() > 1) {
            checkArgument(isValid(answer), MESSAGE_CONSTRAINTS);
            separateIndexes(answer);
            original = answer;
        }

    }

    /**
     * Separates indexes to be inserted into the list.
     * @param answer
     */
    private void separateIndexes(String answer) throws ParseException {
        answerList = new ArrayList();

        try {
            int indexA = answer.indexOf("A)");
            int indexB = answer.indexOf("B)");
            int indexC = answer.indexOf("C)");

            int first = Math.min(indexA, Math.min(indexB, indexC));
            int second;
            int third;

            if (indexA == first) {
                correctAnswer = "A";
                second = Math.min(indexB, indexC);
                third = Math.max(indexB, indexC);
            } else if (indexB == first) {
                correctAnswer = "B";
                second = Math.min(indexA, indexC);
                third = Math.max(indexA, indexC);
            } else {
                correctAnswer = "C";
                second = Math.min(indexA, indexB);
                third = Math.max(indexA, indexB);
            }

            assignToList(indexA, indexB, indexC, first, second, third, answer);
        } catch (Exception e) {
            throw new WrongMcqAnswerTypeException(e.getMessage());
        }

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
            answerList.add(answer.substring(indexA, second));
        } else if (indexA == second) {
            answerList.add(answer.substring(indexA, third));
        } else {
            answerList.add(answer.substring(indexA));
        }

        if (indexB == first) {
            answerList.add(answer.substring(indexB, second));
        } else if (indexB == second) {
            answerList.add(answer.substring(indexB, third));
        } else {
            answerList.add(answer.substring(indexB));
        }

        if (indexC == first) {
            answerList.add(answer.substring(indexC, second));
        } else if (indexC == second) {
            answerList.add(answer.substring(indexC, third));
        } else {
            answerList.add(answer.substring(indexC));
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
    @Override
    public boolean checkAnswer(String toCheck) throws IllegalArgumentException {
        requireNonNull(toCheck);
        if (!(toCheck.equals("A") || toCheck.equals("B") || toCheck.equals("C"))) {
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

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
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

