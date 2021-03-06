import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


/**
 * The <code>NoteCellRenderer</code> class dictates how the notes of a task are displayed.
 * This tutorial was referenced during the construction of this class.
 * Link: https://www.codejava.net/java-se/swing/jlist-custom-renderer-example
 */
public class NoteCellRenderer extends JPanel implements ListCellRenderer<Note> {

    private Font noteTextAreaFont;
    private Font noteAuthorFont;
    private JTextArea noteTextArea;
    private JTextField noteAuthorTextField;

    public NoteCellRenderer() {
        setOpaque(true);
        noteTextAreaFont = new Font("TimesRoman", Font.PLAIN, 14);
        noteAuthorFont = new Font("TimesRoman", Font.ITALIC, 12);

        //noteTextArea
        noteTextArea = new JTextArea(5, 30);
        noteTextArea.setFont(noteTextAreaFont);
        noteTextArea.setVisible(true);
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);
        noteTextArea.setBackground(Color.red);

        //Setting rounder border
        noteTextArea.setBorder(new LineBorder(Color.BLACK, 4, true));


        //noteAuthorTextField
        noteAuthorTextField = new JTextField();
        noteAuthorTextField.setFont(noteAuthorFont);
        noteAuthorTextField.setVisible(true);
        noteAuthorTextField.setBackground(Color.GREEN);
        setBorder(BorderFactory.createEtchedBorder());
        setVisible(true);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Note> notes, Note note, int index, boolean isSelected, boolean cellHasFocus) {

        noteTextArea.setText(note.getText());
        noteAuthorTextField.setText("Written by: " + note.getAuthor());
        add(noteTextArea);
        add(noteAuthorTextField);

        if (isSelected) {
            setBackground(notes.getSelectionBackground());
            setForeground(notes.getSelectionForeground());
        } else {
            setBackground(notes.getBackground());
            setForeground(notes.getForeground());
        }

        return this;
    }

}
