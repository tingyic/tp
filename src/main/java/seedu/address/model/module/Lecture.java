package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lecture {

    // Identity fields
    private final ModuleName moduleName;
    private final Teacher teacher;
    private final Time time;

    // Data fields
    private final Venue venue;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Lecture(ModuleName moduleName, Teacher teacher, Time time, Venue venue, Set<Tag> tags) {
        requireAllNonNull(moduleName, teacher, time, venue, tags);
        this.moduleName = moduleName;
        this.teacher = teacher;
        this.time = time;
        this.venue = venue;
        this.tags.addAll(tags);
    }

    public ModuleName getModuleName() {
        return moduleName;
    }

    public Teacher getProfessor() {
        return teacher;
    }

    public Time getTime() {
        return time;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameLecture(Lecture otherLecture) {
        if (otherLecture == this) {
            return true;
        }

        return otherLecture != null
                && otherLecture.getModuleName().equals(getModuleName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lecture)) {
            return false;
        }

        Lecture otherLecture = (Lecture) other;
        return otherLecture.getModuleName().equals(getModuleName())
                && otherLecture.getProfessor().equals(getProfessor())
                && otherLecture.getTime().equals(getTime())
                && otherLecture.getVenue().equals(getVenue())
                && otherLecture.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleName, teacher, time, venue, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleName())
                .append("; Phone: ")
                .append(getProfessor())
                .append("; Email: ")
                .append(getTime())
                .append("; Address: ")
                .append(getVenue());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
