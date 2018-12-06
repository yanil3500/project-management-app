import javax.swing.*;

public class TaskModal extends JDialog {
    private int SIZE = 300;
    private JTextField textField;
    private JLabel label;
    private String title;
    private JButton okButton;
    private JButton cancelButton;
    private String EXAMPLE_TEXT = "Ex: Implement 'Add task' feature.";


    public TaskModal(JFrame owner, boolean modal) {
        super(owner, "Add a task.", modal);
        setSize(SIZE, SIZE);
        setModal(modal);
        setLocationRelativeTo(owner);
        setLayout(null);

        //Create label and set is location
        label = new JLabel("Add a task:");

        //Get location
        int labelXLocation = (int) (SIZE * 0.1);
        int labelYLocation = (int) (SIZE * 0.2);

        label.setLocation(labelXLocation, labelYLocation);
        label.setBounds(labelXLocation, labelYLocation, 180, 10);

        //Creates buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        int cancelButtonXLocation = (int) (SIZE * 0.1);
        int okButtonXLocation = (int) (SIZE * 0.7);
        int cancelButtonYLocation = (int) (SIZE * 0.7);
        int okButtonYLocation = cancelButtonYLocation;

        okButton.setLocation(okButtonXLocation - cancelButtonXLocation, okButtonYLocation);
        cancelButton.setLocation(cancelButtonXLocation, cancelButtonYLocation);


        okButton.setSize(60, 30);
        cancelButton.setSize(80, 30);

        okButton.setBounds(okButtonXLocation, okButtonYLocation, 60, 30);
        cancelButton.setBounds(cancelButtonXLocation, cancelButtonYLocation, 80, 30);


        //Create text area
        textField = new JTextField(EXAMPLE_TEXT);

        textField.setEditable(true);

        int textFieldWidth = SIZE - (int) (SIZE * 0.2);
        textField.setSize(textFieldWidth, 30);

        int textAreaXLocation = cancelButtonXLocation;
        int textAreaYLocation = (labelYLocation + cancelButtonYLocation) / 2;

        textField.setLocation(textAreaXLocation, textAreaYLocation);
        textField.setBounds(textAreaXLocation, textAreaYLocation, textFieldWidth, 30);

        //Set visible
        label.setVisible(true);
        okButton.setVisible(true);
        cancelButton.setVisible(true);
        textField.setVisible(true);


        okButton.addActionListener(l -> {
            if (textField.getText() != null || !textField.getText().equals(EXAMPLE_TEXT) || !textField.getText().equals("")) {
                //Gets the title of the task from text field.
                title = textField.getText();
                // Dismisses the modal
                dispose();
            }
        });

        //Close the dialog window
        cancelButton.addActionListener(l -> {
            // Dismisses the modal
            dispose();
        });
        //Add to dialog box.
        add(label);
        add(okButton);
        add(cancelButton);
        add(textField);
        setVisible(true);
    }


    @Override
    public String getTitle() {
        return title;
    }
}
