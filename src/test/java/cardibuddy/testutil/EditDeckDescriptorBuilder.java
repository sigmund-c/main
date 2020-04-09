package cardibuddy.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cardibuddy.logic.commands.EditCommand.EditDeckDescriptor;
import cardibuddy.model.deck.Deck;
import cardibuddy.model.deck.Title;
import cardibuddy.model.tag.Tag;

/**
 * A utility class to help with building EditDeckDescriptor objects.
 */
public class EditDeckDescriptorBuilder {

    private EditDeckDescriptor descriptor;

    public EditDeckDescriptorBuilder() {
        descriptor = new EditDeckDescriptor();
    }

    public EditDeckDescriptorBuilder(EditDeckDescriptor descriptor) {
        this.descriptor = new EditDeckDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDeckDescriptor} with fields containing {@code deck}'s details
     */
    public EditDeckDescriptorBuilder(Deck deck) {
        descriptor = new EditDeckDescriptor();
        descriptor.setTitle(deck.getTitle());
        descriptor.setTags(deck.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditDeckDescriptor} that we are building.
     */
    public EditDeckDescriptorBuilder withName(String name) {
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

    public EditDeckDescriptor build() {
        return descriptor;
    }
}
