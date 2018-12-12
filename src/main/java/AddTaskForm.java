
import javax.swing.*;


/**
 * The <code>AddTaskForm</code>class is used for adding tasks to the board.
 */
public class AddTaskForm extends JDialog {

    private JButton cancelButton;
    private JButton okButton;
    private JTextField titleTextField;
    private JLabel titleTextFieldLabel;
    private String taskName;

    /**
     * Creates new form AddTaskForm
     */
    public AddTaskForm(JFrame parent, boolean modal) {
        super(parent, modal);
        //Sets dialog location
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {

        //Initialization code
        titleTextFieldLabel = new JLabel();
        titleTextField = new JTextField();
        okButton = new JButton();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add a task.");

        titleTextFieldLabel.setText("Name:");

        titleTextField.setText("(Ex: Implement the 'Add Task' feature.)");

        okButton.setText("OK");
        okButton.addActionListener(evt -> {
            taskName = titleTextField.getText();
            if (taskName.equals("")) {
                presentErrorMessage(
                        titleTextField,
                        "The task must have a name.",
                        "Required Field");
                return;
            }
            if (taskName.length() > Task.TITLE_LENGTH_LIMIT) {
                presentErrorMessage(
                        titleTextField,
                        "The task must be 140 characters or less in length.",
                        "Task name too long.");
                return;
            }

            dispose();
        });

        //This Stackoverflow question was looked at: https://stackoverflow.com/questions/344969/making-a-jdialog-button-respond-to-the-enter-key
        //Makes "OK" button respond to the 'Enter' key.
        getRootPane().setDefaultButton(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(evt -> dispose());

        //NOTE: This layout code was generated by the Netbeans GUI Builder.
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(titleTextFieldLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(cancelButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(okButton)
                                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleTextFieldLabel)
                                        .addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okButton)
                                        .addComponent(cancelButton)))
        );

        pack();
        setVisible(true);
    }


    /**
     * Gets the title entered by the user.
     * @return String.
     */
    public String getTitle(){
        return taskName;
    }


    /**
     * Presents an error message to the user.
     * @param field
     * @param message
     * @param dialogTitle
     */
    private void presentErrorMessage(JComponent field, String message, String dialogTitle){
        JOptionPane.showMessageDialog(this, message, dialogTitle, JOptionPane.ERROR_MESSAGE);
        field.requestFocus();
    }


}

