package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Deadline;
import seedu.address.model.module.Module;
import seedu.address.model.module.Name;
import seedu.address.model.module.Remark;
import seedu.address.model.module.Resource;
import seedu.address.model.module.Teacher;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.Venue;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String name;
    private final String resource;
    private final String timeSlot;
    private final String venue;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String remark;
    private final String deadline;
    private final String teacher;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("name") String name, @JsonProperty("resource") String resource,
            @JsonProperty("timeSlot") String timeSlot, @JsonProperty("venue") String venue,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("remark") String remark,
            @JsonProperty("deadline") String deadline, @JsonProperty("teacher") String teacher) {
        this.name = name;
        this.resource = resource;
        this.timeSlot = timeSlot;
        this.venue = venue;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.remark = remark;
        this.deadline = deadline;
        this.teacher = teacher;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        name = source.getName().fullName;
        resource = source.getResource().value;
        timeSlot = source.getTimeSlot().toString();
        venue = source.getVenue().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        remark = source.getRemark().value;
        deadline = source.getDeadline().toString();
        teacher = source.getTeacher().value;
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        final List<Tag> moduleTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            moduleTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (resource == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Resource.class.getSimpleName()));
        }
        if (!Resource.isValidResource(resource)) {
            throw new IllegalValueException(Resource.MESSAGE_CONSTRAINTS);
        }
        final Resource modelResource = new Resource(resource);

        if (timeSlot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeSlot.class.getSimpleName()));
        }
        if (!TimeSlot.isValidTimeSlot(timeSlot)) {
            throw new IllegalValueException(TimeSlot.MESSAGE_CONSTRAINTS);
        }
        final TimeSlot modelTimeSlot = new TimeSlot(timeSlot);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        if (!Venue.isValidVenue(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venue);

        final Set<Tag> modelTags = new HashSet<>(moduleTags);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(deadline);

        final Teacher modelTeacher = new Teacher(teacher);

        return new Module(modelName, modelResource, modelTimeSlot, modelVenue, modelTags, modelRemark,
                modelDeadline, modelTeacher);
    }

}
