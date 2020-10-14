package seedu.address.ui;

import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.journal.Entry;

/**
 * An UI component that displays information of a {@code Entry} in detail.
 */
public class EntryContent extends UiPart<Region> {

    private static final String FXML = "EntryContent.fxml";

    private Entry entry;

    @FXML
    private VBox entryPane;
    @FXML
    private StackPane titlePane;
    @FXML
    private Text title;
    @FXML
    private JFXTextArea description;

    /**
     * Creates a {@code EntryContent} containing no {@code Entry} temporarily.
     */
    public EntryContent() {
        super(FXML);
        this.entry = null;
        title.setText("");
        description.setText("Please select a Journal Entry...");
    }


    /**
     * Updates the information of the new {@code Entry}.
     * @param entry the new entry
     */
    public void setEntryContentToUser(Entry entry) {
        if (entry == null) {
            return;
        }
        this.entry = entry;
        title.setText(entry.getTitle().title);
        description.setText(entry.getDescription().description);
    }

    /**
     * Resets the entry content back to the original state.
     */
    public void resetEntryContent() {
        entry = null;
        title.setText("");
        description.setText("Please select a Journal Entry...");
    }

    public Entry getEntry() {
        return entry;
    }
}
