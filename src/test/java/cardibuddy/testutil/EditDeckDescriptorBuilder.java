package cardibuddy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cardibuddy.logic.commands.EditDeckCommand;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

/**a
 * A utility class to help with building EditDeckDescriptor objects.
 */
public class EditDeckDescriptorBuilder {

    private EditDeckCommand.EditDeckDescriptor descriptor;

    public EditDeckDescriptorBuilder() {
        descriptor = new EditDeckCommand.EditDeckDescriptor();
    }

    public EditDeckDescriptorBuilder(EditDeckCommand.EditDeckDescriptor descriptor) {
        this.descriptor = new EditDeckCommand.EditDeckDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDeckDescriptor} with fields containing {@code deck}'s details
     */
    public EditDeckDescriptorBuilder(Deck deck) {
        descriptor = new EditDeckCommand.EditDeckDescriptor();
        descriptor.setTitle(deck.getTitle());
        descriptor.setTags(deck.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditDeckDescriptor} that we are building.
     */
    public EditDeckDescriptorBuilder withTitle(String name) {
        descriptor.setTitle(new Title(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditDeckDescriptor}
     * that we are building.
     */
    public EditDeckDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditDeckCommand.EditDeckDescriptor build() {
        return descriptor;
    }
}
