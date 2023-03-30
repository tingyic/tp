package seedu.address.ui;

import java.nio.charset.Charset;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import seedu.address.model.module.Module;

/**
 * A UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {
    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label resource;
    @FXML
    private Label venue;
    @FXML
    private Label timeSlot;
    @FXML
    private Label remark;
    @FXML
    private Label deadline;
    @FXML
    private Label teacher;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ModuleCode} with the given {@code Module} and index to display.
     */
    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        name.setText(module.getName().fullName);
        resource.setText("Resource: " + module.getResource().value);
        venue.setText("Venue: " + module.getVenue().value);
        timeSlot.setText("Time: " + module.getTimeSlot().toString());
        remark.setText("Remark: " + module.getRemark().value);
        module.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        deadline.setText("Deadline: " + module.getDeadline().displayInUI());
        teacher.setText("Teacher: " + module.getTeacher().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return id.getText().equals(card.id.getText())
                && module.equals(card.module);
    }
}
