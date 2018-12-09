/*
 * The form was put together using the GUI Builder feature of the Netbeans IDE.
 */

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;


public class ViewTaskForm extends JDialog {

    /**
     * Creates new form EditTaskForm
     */
    public ViewTaskForm(JFrame parent, boolean modal, Task task) {
        super(parent, modal);
        //Sets dialog location
        setLocationRelativeTo(parent);
        //Sets task
        this.task = task;
        initComponents();


    }

    private void initComponents() {

        //Labels
        titleTextFieldLabel = new JLabel();
        authorTextFieldLabel = new JLabel();
        assignedToTextFieldLabel = new JLabel();
        deadlineDatePickerLabel = new JLabel();
        descriptionTextFieldLabel = new JLabel();
        lastModifiedDateLabel = new JLabel();

        createDateLabel = new JLabel();

        //Text fields
        titleTextField = new JTextField(task.getTitle());
        authorTextField = new JTextField(task.getAuthor() != null ? task.getAuthor() : "");
        assignedToTextField = new JTextField(task.getAssignedTo() != null ? task.getAssignedTo() : "");
        createDateTextField = new JTextField(task.getMetadata().getDateCreated().toString());
        lastModifiedDateTextField = new JTextField(task.getMetadata().getLastModified().toString());
        descriptionScrollPane = new JScrollPane();
        descriptionTextArea = new JTextArea(task.getDescription() != null ? task.getDescription() : "");
        okButton = new JButton();
        cancelButton = new JButton();

        //Checkboxes for editing fields
        editTitleCheckBox = new JCheckBox();
        editDeadlineCheckBox = new JCheckBox();
        editAuthorCheckBox = new JCheckBox();
        editAssignedToCheckBox = new JCheckBox();
        editDescriptionCheckBox = new JCheckBox();

        //Set the date picker's veto policy so that setting date in the past isn't allowed.
        DatePickerSettings settings = new DatePickerSettings();
        deadlineDatePicker = new DatePicker(settings);
        settings.setVetoPolicy(new ExcludeDatesInPastPolicy());

        //Setting default date for DatePicker
        LocalDate defaultDate = task.getDeadline() != null ? DateTimeFormattingUtils.toLocalDate(task.getDeadline()): LocalDate.now();
        deadlineDatePicker.setDate(defaultDate);

        //Rename DatePicker button
        deadlineDatePicker.getComponentToggleCalendarButton().setText("Select...");

        //Make DatePicker textField not editable.
        deadlineDatePicker.getComponentDateTextField().setEditable(false);

        //Set tool tip for date picker
        deadlineDatePicker.getComponentDateTextField().setToolTipText("Use the 'Select...' button beside this field to select a date.");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View/Edit Task");

        titleTextFieldLabel.setText("Title:");

        authorTextFieldLabel.setText("Author:");

        assignedToTextFieldLabel.setText("Assigned To:");

        deadlineDatePickerLabel.setText("Deadline: ");

        descriptionTextFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionTextFieldLabel.setText("Description: ");

        titleTextField.setEnabled(false);

        authorTextField.setEnabled(false);

        assignedToTextField.setEnabled(false);

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setToolTipText("Descriptive text about task.");
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEnabled(false);
        descriptionScrollPane.setViewportView(descriptionTextArea);

        //This Stackoverflow question was looked at: https://stackoverflow.com/questions/344969/making-a-jdialog-button-respond-to-the-enter-key
        //Makes "OK" button respond to the 'Enter' key.
        getRootPane().setDefaultButton(okButton);

        okButton.setText("OK");
        okButton.addActionListener(evt -> {
            //The 'title' field is a required field. Covers case in which user might delete the title of an existing task.
            if (titleTextField.getText().equals("")) {
                presentErrorMessage(titleTextField, "The 'title' field cannot be empty.", "Required Field");
                return;
            }
            this.task.editTitle(titleTextField.getText());
            this.task.setAuthor(authorTextField.getText().equals("") ? null : authorTextField.getText());
            this.task.editAssignedTo(assignedToTextField.getText().equals("") ? null : assignedToTextField.getText());
            this.task.editDescription(descriptionTextArea.getText().equals("") ? null : descriptionTextArea.getText());
            this.task.editDeadline(deadlineDatePicker.getText());

            //Dismisses modal
            dispose();
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(evt -> dispose());

        editTitleCheckBox.setText("Edit Title");
        editTitleCheckBox.setToolTipText("Edit the task's title.");
        editTitleCheckBox.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                this.titleTextField.setEnabled(true);
                this.titleTextField.requestFocus();
            } else {
                this.titleTextField.setEnabled(false);
            }
        });


        editAuthorCheckBox.setText("Edit Author");
        editAuthorCheckBox.setToolTipText("");
        editAuthorCheckBox.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                this.authorTextField.setEnabled(true);
                this.authorTextField.requestFocus();
            } else {
                this.authorTextField.setEnabled(false);
            }
        });

        editAssignedToCheckBox.setText("Edit Assigned To");
        editAssignedToCheckBox.setToolTipText("");
        editAssignedToCheckBox.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                this.assignedToTextField.setEnabled(true);
                this.assignedToTextField.requestFocus();
            } else {
                this.assignedToTextField.setEnabled(false);
            }
        });

        editDeadlineCheckBox.setText("Edit Deadline");
        editDeadlineCheckBox.setToolTipText("");
        editDeadlineCheckBox.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                this.deadlineDatePicker.setEnabled(true);
                this.deadlineDatePicker.requestFocus();
            } else {;
                this.deadlineDatePicker.setEnabled(false);
            }
        });


        editDescriptionCheckBox.setText("Edit Description");
        editDescriptionCheckBox.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                this.descriptionTextArea.setEnabled(true);
                this.descriptionTextArea.requestFocus();
            } else {
                this.descriptionTextArea.setEnabled(false);
            }
        });

        deadlineDatePicker.setEnabled(false);


        lastModifiedDateLabel.setText("Task last modified on:");
        lastModifiedDateTextField.setEditable(false);
        lastModifiedDateTextField.setText(task.getMetadata().getLastModified().toString());
        lastModifiedDateTextField.setEnabled(false);

        createDateLabel.setText("Task created on:");
        createDateTextField.setEditable(false);
        createDateTextField.setText(task.getMetadata().getDateCreated().toString());
        createDateTextField.setEnabled(false);

        //NOTE: This layout code was generated by the Netbeans GUI Builder.
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(deadlineDatePickerLabel)
                                                                .addGap(25, 25, 25)
                                                                .addComponent(deadlineDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(descriptionTextFieldLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(descriptionScrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(cancelButton)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)))))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editDeadlineCheckBox)
                                                                        .addComponent(editDescriptionCheckBox))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addComponent(lastModifiedDateLabel)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(lastModifiedDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(createDateLabel)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(createDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(assignedToTextFieldLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(assignedToTextField, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editAssignedToCheckBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(authorTextFieldLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(authorTextField, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editAuthorCheckBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(titleTextFieldLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editTitleCheckBox)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleTextFieldLabel)
                                        .addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editTitleCheckBox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(authorTextFieldLabel)
                                        .addComponent(authorTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editAuthorCheckBox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(assignedToTextFieldLabel)
                                        .addComponent(assignedToTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editAssignedToCheckBox))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(deadlineDatePickerLabel)
                                        .addComponent(editDeadlineCheckBox)
                                        .addComponent(deadlineDatePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(descriptionTextFieldLabel)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(editDescriptionCheckBox)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(createDateLabel)
                                                                .addComponent(createDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(9, 9, 9)
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                .addComponent(lastModifiedDateLabel)
                                                                .addComponent(lastModifiedDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(98, 98, 98))
                                                .addComponent(descriptionScrollPane)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(okButton)
                                        .addComponent(cancelButton))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setVisible(true);
    }

    private Task task;


    private void presentErrorMessage(JComponent field, String message, String dialogTitle){
        JOptionPane.showMessageDialog(this, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
        field.requestFocus();
    }


    private JTextField assignedToTextField;
    private JLabel assignedToTextFieldLabel;
    private JTextField authorTextField;
    private JLabel authorTextFieldLabel;
    private JButton cancelButton;
    private JLabel createDateLabel;
    private JTextField createDateTextField;
    private com.github.lgooddatepicker.components.DatePicker deadlineDatePicker;
    private JLabel deadlineDatePickerLabel;
    private JScrollPane descriptionScrollPane;
    private JTextArea descriptionTextArea;
    private JLabel descriptionTextFieldLabel;
    private JCheckBox editAssignedToCheckBox;
    private JCheckBox editAuthorCheckBox;
    private JCheckBox editDeadlineCheckBox;
    private JCheckBox editDescriptionCheckBox;
    private JCheckBox editTitleCheckBox;
    private JLabel lastModifiedDateLabel;
    private JTextField lastModifiedDateTextField;
    private JButton okButton;
    private JTextField titleTextField;
    private JLabel titleTextFieldLabel;


}
