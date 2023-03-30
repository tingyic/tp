package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_DESC_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESOURCE_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESOURCE_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LECTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS3219;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_CS3230;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MODULE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.model.module.Name;
import seedu.address.model.module.Resource;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditModuleDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_CS3230, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_CS3230, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_CS3230, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Resource.MESSAGE_CONSTRAINTS); // invalid type
        assertParseFailure(parser, "1"
                + INVALID_TIMESLOT_DESC, TimeSlot.MESSAGE_CONSTRAINTS); // invalid timeSlot
        assertParseFailure(parser, "1"
                + INVALID_VENUE_DESC, Venue.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid type followed by valid timeSlot
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC
                + TIMESLOT_DESC_CS3230, Resource.MESSAGE_CONSTRAINTS);

        // valid type followed by invalid type. The test case for invalid type followed by valid type
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TYPE_DESC_CS3219 + INVALID_TYPE_DESC, Resource.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Module} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_TUTORIAL + TAG_DESC_LECTURE
                + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_TUTORIAL + TAG_EMPTY
                + TAG_DESC_LECTURE, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_TUTORIAL
                + TAG_DESC_LECTURE, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_TIMESLOT_DESC + VALID_VENUE_CS3230
                        + VALID_RESOURCE_CS3230, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MODULE;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_CS3219 + TAG_DESC_LECTURE
                + TIMESLOT_DESC_CS3230 + VENUE_DESC_CS3230 + NAME_DESC_CS3230 + TAG_DESC_TUTORIAL;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_CS3230)
                .withResource(VALID_RESOURCE_CS3219).withTimeSlot(VALID_TIMESLOT_CS3230)
                .withVenue(VALID_VENUE_CS3230)
                .withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_CS3219 + TIMESLOT_DESC_CS3230;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withResource(VALID_RESOURCE_CS3219)
                .withTimeSlot(VALID_TIMESLOT_CS3230).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_CS3230;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_CS3230).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_CS3230;
        descriptor = new EditModuleDescriptorBuilder().withResource(VALID_RESOURCE_CS3230).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // timeSlot
        userInput = targetIndex.getOneBased() + TIMESLOT_DESC_CS3230;
        descriptor = new EditModuleDescriptorBuilder().withTimeSlot(VALID_TIMESLOT_CS3230).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + VENUE_DESC_CS3230;
        descriptor = new EditModuleDescriptorBuilder().withVenue(VALID_VENUE_CS3230).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_TUTORIAL;
        descriptor = new EditModuleDescriptorBuilder().withTags(VALID_TAG_TUTORIAL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_CS3230 + VENUE_DESC_CS3230 + TIMESLOT_DESC_CS3230
                + TAG_DESC_TUTORIAL + TYPE_DESC_CS3230 + VENUE_DESC_CS3230 + TIMESLOT_DESC_CS3230 + TAG_DESC_TUTORIAL
                + TYPE_DESC_CS3219 + VENUE_DESC_CS3219 + TIMESLOT_DESC_CS3219 + TAG_DESC_LECTURE;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withResource(VALID_RESOURCE_CS3219)
                .withTimeSlot(VALID_TIMESLOT_CS3219).withVenue(VALID_VENUE_CS3219).withTags(VALID_TAG_TUTORIAL,
                        VALID_TAG_LECTURE).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + INVALID_TYPE_DESC + TYPE_DESC_CS3219;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withResource(VALID_RESOURCE_CS3219).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TIMESLOT_DESC_CS3219 + INVALID_TYPE_DESC + VENUE_DESC_CS3219
                + TYPE_DESC_CS3219;
        descriptor = new EditModuleDescriptorBuilder().withResource(VALID_RESOURCE_CS3219)
                .withTimeSlot(VALID_TIMESLOT_CS3219)
                .withVenue(VALID_VENUE_CS3219).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
