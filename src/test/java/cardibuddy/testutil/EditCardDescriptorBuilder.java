package cardibuddy.testutil;

import cardibuddy.logic.commands.EditCardCommand;
import cardibuddy.model.flashcard.Card;
import cardibuddy.model.flashcard.Question;
import cardibuddy.model.flashcard.ShortAnswer;

/**a
 * A utility class to help with building EditDeckDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCardCommand.EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardCommand.EditCardDescriptor descriptor) {
        this.descriptor = new EditCardCommand.EditCardDescriptor((descriptor));
    }

    /**
     * Returns an {@code EditCardDescriptorBuilder} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardCommand.EditCardDescriptor();
        descriptor.getDeck(card.getDeck());
        descriptor.setQuestion(card.getQuestion());
        descriptor.setAnswer(card.getAnswer());
        descriptor.getPath(card.getPath());
    }

    /**
     * Sets the {@code Question} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new ShortAnswer(answer));
        return this;
    }

    public EditCardCommand.EditCardDescriptor build() {
        return descriptor;
    }
}
