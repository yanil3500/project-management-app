/*
 * The form was put together using the GUI Builder feature of the Netbeans IDE.
 */

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * The <code>ViewNotesForm</code> class is responsible for presenting a GUI for the following three operations:
 * - Add/remove a note regarding a task.
 * - View existing notes.
 * - Edit existing notes.
 */
public class ViewNotesForm extends JDialog {

    private Task task;
    private ArrayList<Note> notes;
    private boolean addNote;
    private JPanel buttonPanel;
    private JPanel editPanel;
    private JButton cancelButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JLabel notesLabel;
    private JLabel editAuthorLabel;
    private JLabel editNoteLabel;
    private JTextField editAuthorTextField;
    private JTextArea editNoteTextArea;
    private JScrollPane editNoteTextAreaScrollPane;
    private JScrollPane listOfNotesScrollPane;
    private JList<Note> listOfNotes;
    private JCheckBox addNoteCheckbox;

    /**
     * Creates new form ViewNotesForm
     */
    public ViewNotesForm(JFrame parent, boolean modal, Task task) {
        super(parent, modal);
        //Sets dialog location
        setLocationRelativeTo(parent);
        //Sets task
        this.task = task;
        this.notes = task.getNotes();
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {

        listOfNotesScrollPane = new JScrollPane();
        listOfNotes = new JList<>();
        listOfNotes.setCellRenderer(new NoteCellRenderer());

        //Set default list
        DefaultListModel model = new DefaultListModel();

        //Add ListSelection listener
        listOfNotes.addListSelectionListener(listener -> {
            if (listener.getValueIsAdjusting()) {
                Note note = listOfNotes.getSelectedValue();
                editNoteTextArea.setText(note.getText());
                editAuthorTextField.setText(note.getAuthor());
                //makes 'Delete' button visible and operable.
                deleteButton.setVisible(true);
                deleteButton.setEnabled(true);
            }
        });

        //Adds notes to model
        for (int i = 0; i < notes.size(); i++) {
            model.addElement(notes.get(i));
        }
        notesLabel = new JLabel();
        editPanel = new JPanel();
        editNoteLabel = new JLabel();
        editAuthorTextField = new JTextField();
        buttonPanel = new JPanel();
        cancelButton = new JButton();
        saveButton = new JButton();
        deleteButton = new JButton();
        addNoteCheckbox = new JCheckBox();
        editNoteTextAreaScrollPane = new JScrollPane();
        editNoteTextArea = new JTextArea();
        editAuthorLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add/View/Edit Notes");

        listOfNotes.setModel(model);
        listOfNotes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listOfNotes.setAutoscrolls(false);
        listOfNotes.setVisible(model.getSize() > 0);
        listOfNotesScrollPane.setVisible(model.getSize() > 0);
        listOfNotes.setVisibleRowCount(model.getSize() >= 5 ? 5 : model.getSize());
        listOfNotesScrollPane.setViewportView(listOfNotes);

        notesLabel.setText("Notes");
        notesLabel.setVisible(model.getSize() > 0);

        editNoteLabel.setText("Edit Note:");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(evt -> dispose());

        saveButton.setText("Save");
        saveButton.addActionListener(evt -> {
            if (addNote) {
                task.addNote(editAuthorTextField.getText(), editNoteTextArea.getText());
                addNote = false;
                addNoteCheckbox.setSelected(false);
                int index = task.getNotes().size() - 1;
                model.addElement(task.getNotes().get(index));
                //Clear the input fields
                editNoteTextArea.setText("");
                editAuthorTextField.setText("");
                listOfNotes.setVisible(true);
                listOfNotesScrollPane.setVisible(true);
                listOfNotes.setVisibleRowCount(model.getSize() >= 5 ? 5 : model.getSize());
                notesLabel.setVisible(true);
                pack();
                repaint();
                return;
            }
            Note note = listOfNotes.getSelectedValue();
            int index = listOfNotes.getSelectedIndex();
            if (note == null || index < 0) {
                return;
            }

            if (editNoteTextArea.getText().equals("") && editAuthorTextField.getText().equals("")) {
                notes.remove(index);
                model.removeElement(note);
                return;
            }


            //Edits existing note
            task.editNote(editNoteTextArea.getText(), index, false);
            task.editNote(editAuthorTextField.getText(), index, true);
            model.setElementAt(note, index);
        });

        deleteButton.setText("Delete");
        deleteButton.setVisible(false);
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(evt -> {
            Note note = listOfNotes.getSelectedValue();
            int index = listOfNotes.getSelectedIndex();
            if (note == null || index < 0) {
                return;
            }
            int choice = presentConfirmMessage("Are you sure you want to delete this note?", "Delete Note?");
            if (choice == JOptionPane.YES_OPTION) {
                task.deleteNote(index);
                model.removeElement(note);
                //Clear the input fields
                editNoteTextArea.setText("");
                editAuthorTextField.setText("");
                if (this.task.getNotes().isEmpty()) {
                    listOfNotes.setVisible(false);
                    listOfNotesScrollPane.setVisible(false);
                    deleteButton.setVisible(false);
                    deleteButton.setEnabled(false);
                    notesLabel.setVisible(false);
                } else {
                    listOfNotes.setVisibleRowCount(model.getSize() >= 5 ? 5 : model.getSize());
                }
                pack();
                repaint();
            }
        });


        addNoteCheckbox.setText("Add Note");
        addNoteCheckbox.addItemListener(evt -> addNote = evt.getStateChange() == ItemEvent.SELECTED);

        //EVERYTHING BELOW THIS COMMENT WAS NOT WRITTEN BY US; IT WAS ALL GENERATED BY THE GUI BUILDER IN NETBEANS.
        //EVERYTHING ELSE, WAS WRITTEN BY US (E.G. ADDING EVENT LISTENERS, SETTING TEXT, ENABLING/DISABLING FIELDS)

        //NOTE: All of the code pertaining to how these components are laid out were generated by the GUI builder in the Netbeans IDE.
        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cancelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton)
                                .addGap(31, 31, 31)
                                .addComponent(addNoteCheckbox)
                                .addGap(70, 70, 70)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
                buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(saveButton)
                                        .addComponent(cancelButton)
                                        .addComponent(deleteButton)
                                        .addComponent(addNoteCheckbox))
                                .addContainerGap())
        );

        editNoteTextArea.setColumns(20);
        editNoteTextArea.setRows(5);
        editNoteTextAreaScrollPane.setViewportView(editNoteTextArea);

        editAuthorLabel.setText("Edit Author:");

        GroupLayout editPanelLayout = new GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanelLayout.setHorizontalGroup(
                editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(editPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(editPanelLayout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(editNoteTextAreaScrollPane)
                                                        .addGroup(GroupLayout.Alignment.TRAILING, editPanelLayout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(editNoteLabel)
                                                                        .addComponent(editAuthorLabel))
                                                                .addGap(394, 394, 394))))
                                        .addComponent(editAuthorTextField)
                                        .addComponent(buttonPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        editPanelLayout.setVerticalGroup(
                editPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(editPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(editNoteLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editNoteTextAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editAuthorLabel)
                                .addGap(0, 0, 0)
                                .addComponent(editAuthorTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(notesLabel)
                                        .addComponent(listOfNotesScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(editPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(notesLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(listOfNotesScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        pack();
        setVisible(true);
    }

    private int presentConfirmMessage(String message, String dialogTitle) {
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

}
