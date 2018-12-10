/*
 * The form was put together using the GUI Builder feature of the Netbeans IDE.
 */
import javax.swing.*;


public class AddNoteForm extends JDialog {


    /**
     * Creates new form AddNote
     */
    public AddNoteForm(JFrame parent, boolean modal) {
        super(parent, modal);
        //Sets dialog location
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {

        taskNoteLabel = new JLabel();
        taskNote = new JTextField();
        okButton = new JButton();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add a note");

        taskNoteLabel.setText("Note:");

        taskNote.setText("(Ex: Someone else has worked on this task.)");

        okButton.setText("OK");
        okButton.addActionListener(evt -> {
            this.note = taskNote.getText();
            dispose();
        });
        //This Stackoverflow question was looked at: https://stackoverflow.com/questions/344969/making-a-jdialog-button-respond-to-the-enter-key
        //Makes "OK" button respond to the 'Enter' key.
        getRootPane().setDefaultButton(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(evt -> dispose());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(taskNoteLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(taskNote, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(taskNoteLabel)
                                        .addComponent(taskNote, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(okButton)
                                        .addComponent(cancelButton)))
        );

        pack();
        setVisible(true);
    }

    public String getNote() {
        return note;
    }

    private JButton cancelButton;
    private JButton okButton;
    private JTextField taskNote;
    private JLabel taskNoteLabel;
    private String note;

}
