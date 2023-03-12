package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddLectureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Lecture;
import seedu.address.model.module.ModuleName;
import seedu.address.model.module.Time;
import seedu.address.model.module.Teacher;
import seedu.address.model.module.Venue;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddLectureCommand object
 */
public class AddLectureCommandParser implements Parser<AddLectureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLectureCommand
     * and returns an AddLectureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLectureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLectureCommand.MESSAGE_USAGE));
        }

        ModuleName moduleName = ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_NAME).get());
        Teacher teacher = ParserUtil.parseTeacher(argMultimap.getValue(PREFIX_PHONE).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_EMAIL).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Lecture lecture = new Lecture(moduleName, teacher, time, venue, tagList);

        return new AddLectureCommand(lecture);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
