/*
 * The form was put together using the GUI Builder feature of the Netbeans IDE.
 */
import javax.swing.*;


public class AddNoteForm extends JDialog {


    /**
     * Creates new form AddNote
     */
    public AddNoteForm(JFrame parent, boolean modal, Task task) {
        super(parent, modal);
        //Sets dialog location
        setLocationRelativeTo(parent);
        initComponents();
        this.task = task;
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
            String taskNoteText = taskNote.getText();
            task.addNote(taskNoteText);
            dispose();
        });

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
    }


    private JButton cancelButton;
    private JButton okButton;
    private JTextField taskNote;
    private JLabel taskNoteLabel;
    private Task task;

}
