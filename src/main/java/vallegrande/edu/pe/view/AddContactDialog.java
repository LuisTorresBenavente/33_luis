package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.ContactController;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo modal para agregar un nuevo contacto.
 */
public class AddContactDialog extends JDialog {
    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField phoneField = new JTextField(20);
    private final JTextField ageField = new JTextField(5);
    private final JTextField locationField = new JTextField(20);

    private final ContactController controller;
    private boolean succeeded;

    public AddContactDialog(Frame parent, ContactController controller) {
        super(parent, "Agregar Contacto", true);
        this.controller = controller;
        initUI();
    }

    // Inicializar componentes UI
    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Teléfono:"));
        panel.add(phoneField);

        panel.add(new JLabel("Edad:"));
        panel.add(ageField);

        panel.add(new JLabel("Lugar:"));
        panel.add(locationField);

        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancelar");

        okBtn.addActionListener(e -> onOK());
        cancelBtn.addActionListener(e -> onCancel());

        JPanel btnPanel = new JPanel();
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    // Acción al presionar OK
    private void onOK() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String ageText = ageField.getText().trim();
        String location = locationField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || ageText.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            controller.create(name, email, phone, age, location);
            succeeded = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Acción al presionar Cancelar
    private void onCancel() {
        succeeded = false;
        dispose();
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
