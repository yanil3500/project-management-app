/*
 * The form was put together using the GUI Builder feature of the Netbeans IDE.
 */

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.regex.Pattern;


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
        //Code for showing tool tip immediately
        //Link:http://www.java2s.com/Tutorial/Java/0240__Swing/Makeatooltipsappearimmediately.htm
        // Get current delay
        int initialDelay = ToolTipManager.sharedInstance().getInitialDelay();

        // Show tool tips immediately
        ToolTipManager.sharedInstance().setInitialDelay(0);


        // Show tool tips after a second
        initialDelay = 1000;
        ToolTipManager.sharedInstance().setInitialDelay(initialDelay);

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

        //Creates default list for phone numbers
        DefaultListModel model = new DefaultListModel();

        //Add phone numbers to model
        for(String phoneNumber : task.getPhoneNumbers()){
            model.addElement(phoneNumber);
        }

        //Set the date picker's veto policy so that setting date in the past isn't allowed.
        DatePickerSettings settings = new DatePickerSettings();
        deadlineDatePicker = new DatePicker(settings);
        settings.setVetoPolicy(new ExcludeDatesInPastPolicy());

        //Gets references for date picker components; Will be used for rearrange their placement on the form.
        JButton calendarButton = deadlineDatePicker.getComponentToggleCalendarButton();
        JTextField dateTextField = deadlineDatePicker.getComponentDateTextField();
        //Will show text field after a date is selected.
        dateTextField.setVisible(true);

        //Set layout manager for datePicker
        deadlineDatePicker.setLayout(new FlowLayout());

        //Remove these components so that they could be re-arranged
        deadlineDatePicker.remove(dateTextField);
        deadlineDatePicker.remove(calendarButton);

        deadlineDatePicker.add(calendarButton);
        deadlineDatePicker.add(dateTextField);
        //Setting default date for DatePicker
        String taskDeadline = task.getDeadline();
        if(taskDeadline != null){
            LocalDate date = DateTimeFormattingUtils.toLocalDate(taskDeadline);
            deadlineDatePicker.setDate(date);
        }

        //Rename DatePicker button
        deadlineDatePicker.getComponentToggleCalendarButton().setText("Select Date");

        //Will show the text field after a date is selected.
        deadlineDatePicker.getComponentDateTextField().setVisible(false);

        deadlineDatePicker.addDateChangeListener(evt -> {
            System.out.println("Date getting picked.");
            deadlineDatePicker.getComponentDateTextField().setVisible(true);
            //Shows textField with selected date
            pack();
            deadlineDatePicker.repaint();
            //Enable the 'Receive SMS Notifications' text field
            phoneNumberFormattedField.setEnabled(true);
            phoneNumberFormattedField.setBackground(Color.WHITE);
        });

        //Make DatePicker textField not editable.
        deadlineDatePicker.getComponentDateTextField().setEditable(false);

        //Set tool tip for date picker
        deadlineDatePicker.getComponentDateTextField().setToolTipText("Use the 'Select...' button beside this field to select a date.");

        lastModifiedDateLabel = new JLabel();
        lastModifiedDateTextField = new JTextField("Sun Dec 09 03:51:45 EST 2018");
        ;
        createDateLabel = new JLabel();
        createDateTextField = new JTextField("Sun Dec 09 03:51:45 EST 2018");
        ;
        phoneNumberListScrollPane = new JScrollPane();
        phoneNumberList = new JList<>();
        phoneNumberList.setVisible(task.getPhoneNumbers().size() > 0 ? true : false);

        phoneNumberList.addListSelectionListener(listener -> {
            if(listener.getValueIsAdjusting()){
                //makes 'Delete Phone #' button visible and operable.
                deletePhoneNumberButton.setVisible(true);
                deletePhoneNumberButton.setEnabled(true);
                pack();
                repaint();
            }
        });
        addNotificationLabel = new JLabel();
        phoneNumberFormattedField = new JFormattedTextField();
        phoneNumberFormattedField.setBackground(phoneNumberFormattedField.isVisible() ? Color.WHITE : Color.DARK_GRAY);

        phoneNumberFormattedField.getDocument().addDocumentListener(new DocumentListener(){
            //Regex is from Twilio
            //Here is the link: https://www.twilio.com/docs/glossary/what-e164
            String phoneNumberRegex = "^\\+?[1-9]\\d{1,14}$";
            Pattern pattern = Pattern.compile(phoneNumberRegex);
            public void changedUpdate(DocumentEvent e) {

            }
            public void removeUpdate(DocumentEvent e) {
                addPhoneNumberButton.setEnabled(false);
            }
            public void insertUpdate(DocumentEvent e) {
                String phoneNumberFormattedFieldText = phoneNumberFormattedField.getText();
                if(phoneNumberFormattedField.isEditValid()){
                    //Enable the 'Add Phone Number' button only if input is valid
                    if(pattern.matcher(phoneNumberFormattedFieldText).matches()){
                        addPhoneNumberButton.setEnabled(true);
                    }
                } else {
                    addPhoneNumberButton.setEnabled(false);
                }
            }
        });

        //Display placeholder text in phoneNumber text field.
        TextPrompt textPrompt = new TextPrompt("Ex: ###########", phoneNumberFormattedField);
        textPrompt.setForeground(Color.DARK_GRAY);

        addPhoneNumberButton = new JButton();
        deletePhoneNumberButton = new JButton();
        //'Delete Phone #' button will only be visible if a number is selected fromm the list.
        deletePhoneNumberButton.setVisible(false);
        deletePhoneNumberButton.setEnabled(false);
        deletePhoneNumberButton.addActionListener(evt -> {
            String phoneNumber = phoneNumberList.getSelectedValue();
            int index = phoneNumberList.getSelectedIndex();
            if (phoneNumber ==  null || index < 0) {
                System.out.print("No phone number selected.");
                return;
            }
            int choice = presentConfirmMessage(null, "Are you sure you want to delete this phone #?", "Delete Phone #?");
            if (choice == JOptionPane.YES_OPTION) {
                task.deletePhoneNumber(index);
                model.removeElement(phoneNumber);
                if(this.task.getPhoneNumbers().isEmpty()){
                    phoneNumberList.setVisible(false);
                    phoneNumberListScrollPane.setVisible(false);
                    deletePhoneNumberButton.setVisible(false);
                    pack();
                    repaint();
                }
            }

        });

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

        phoneNumberListScrollPane.setVisible(task.getPhoneNumbers().size() > 0 ? true : false);

        phoneNumberList.setModel(model);
        phoneNumberList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        phoneNumberListScrollPane.setViewportView(phoneNumberList);

        addNotificationLabel.setText("Receive SMS notifications:");

        phoneNumberFormattedField.setBackground(new java.awt.Color(153, 153, 153));
        phoneNumberFormattedField.setToolTipText("Set a 'Deadline' date to enable SMS notifications.");
        phoneNumberFormattedField.setDragEnabled(false);
        phoneNumberFormattedField.setEnabled(false);

        //Makes it so only digits are acknowledged.
        //https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
        phoneNumberFormattedField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {

                char c = evt.getKeyChar();
                boolean isDigit = c >= '0' && c <= '9';
                if(c >= '0' && c <= '9'){
                }
                if(phoneNumberFormattedField.getText().length() > 15){
                    evt.consume();
                }

                if (!(isDigit || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE))) {
                    evt.consume();
                }
            }
        });

        addPhoneNumberButton.setText("Add Phone #");
        addPhoneNumberButton.setEnabled(false);
        addPhoneNumberButton.addActionListener(evt -> {
            if(phoneNumberFormattedField.isEditValid()){
                String phoneNumberFormattedFieldText = "+" + phoneNumberFormattedField.getText();
                task.addPhoneNumber(phoneNumberFormattedFieldText);
                if(!phoneNumberListScrollPane.isVisible()){
                    phoneNumberListScrollPane.setVisible(true);
                    phoneNumberList.setVisible(true);
                    pack();
                    repaint();
                }
                if(model.getSize() < 5){
                    model.addElement(phoneNumberFormattedFieldText);
                }
            }
        });
        deletePhoneNumberButton.setText("Delete Phone #");

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
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                        .addComponent(lastModifiedDateLabel)
                                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(lastModifiedDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(createDateLabel)
                                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(createDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(addNotificationLabel)
                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                        .addComponent(phoneNumberFormattedField)
                                                                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                                        .addComponent(addPhoneNumberButton, GroupLayout.Alignment.TRAILING)
                                                                                                        .addComponent(deletePhoneNumberButton, GroupLayout.Alignment.TRAILING))))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(phoneNumberListScrollPane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(8, 8, 8))))))
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
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(descriptionTextFieldLabel)
                                                .addContainerGap(234, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
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
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(addNotificationLabel)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(phoneNumberFormattedField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(addPhoneNumberButton)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(deletePhoneNumberButton))
                                                                        .addComponent(phoneNumberListScrollPane, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(2, 2, 2))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(descriptionScrollPane)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(okButton)
                                                                        .addComponent(cancelButton))))
                                                .addContainerGap(12, Short.MAX_VALUE))))
        );

        pack();
        setVisible(true);
    }

    private Task task;

    public void setTask(Task task) {
        this.task = task;
    }


    private void presentErrorMessage(JComponent field, String message, String dialogTitle){
        JOptionPane.showMessageDialog(this, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
        field.requestFocus();
    }

    private int presentConfirmMessage(JComponent field, String message, String dialogTitle) {
        JFrame frame = new JFrame();
        int choice = JOptionPane.showConfirmDialog(frame,
                message,
                dialogTitle,
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
        } else {
            frame.dispose();
        }

        return choice;
    }


    private JLabel addNotificationLabel;
    private JButton addPhoneNumberButton;
    private JTextField assignedToTextField;
    private JLabel assignedToTextFieldLabel;
    private JTextField authorTextField;
    private JLabel authorTextFieldLabel;
    private JButton cancelButton;
    private JLabel createDateLabel;
    private JTextField createDateTextField;
    private com.github.lgooddatepicker.components.DatePicker deadlineDatePicker;
    private JLabel deadlineDatePickerLabel;
    private JButton deletePhoneNumberButton;
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
    private JFormattedTextField phoneNumberFormattedField;
    private JList<String> phoneNumberList;
    private JScrollPane phoneNumberListScrollPane;
    private JTextField titleTextField;
    private JLabel titleTextFieldLabel;


}
