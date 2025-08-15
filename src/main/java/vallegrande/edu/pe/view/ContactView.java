package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.ContactController;
import vallegrande.edu.pe.model.Contact;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Vista principal con Swing que muestra la lista de contactos y botones.
 */
public class ContactView extends JFrame {
    private final ContactController controller;
    private DefaultTableModel tableModel;
    private JTable table;

    public ContactView(ContactController controller) {
        super("Agenda MVC Swing - Vallegrande");
        this.controller = controller;
        initUI();
        loadContacts();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 🎨 Paleta de colores moderna
        Color primaryBlue = new Color(33, 150, 243);
        Color dangerRed = new Color(244, 67, 54);
        Color background = new Color(245, 245, 245);
        Color textColor = new Color(33, 33, 33);

        Font baseFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(background);
        setContentPane(contentPanel);

        // 🧓 Tabla con columnas extendidas
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Email", "Teléfono", "Edad", "Lugar"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(baseFont);
        table.setRowHeight(30);
        table.getTableHeader().setFont(baseFont.deriveFont(Font.BOLD, 18f));
        table.setForeground(textColor);
        table.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // 🔘 Panel de botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonsPanel.setBackground(background);

        JButton addBtn = new JButton("Agregar");
        styleButton(addBtn, primaryBlue, "src/resources/add.png");

        JButton deleteBtn = new JButton("Eliminar");
        styleButton(deleteBtn, dangerRed, "src/resources/delete.png");

        buttonsPanel.add(addBtn);
        buttonsPanel.add(deleteBtn);

        contentPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // 🎯 Eventos
        addBtn.addActionListener(e -> showAddContactDialog());
        deleteBtn.addActionListener(e -> deleteSelectedContact());
    }

    /**
     * Aplica estilos modernos y hover a los botones, con íconos.
     */
    private void styleButton(JButton button, Color baseColor, String iconPath) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(baseColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Icono si existe
        ImageIcon icon = new ImageIcon(iconPath);
        if (icon.getIconWidth() > 0) {
            button.setIcon(icon);
        }

        // Hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }

    /**
     * Carga los contactos en la tabla.
     */
    private void loadContacts() {
        tableModel.setRowCount(0);
        List<Contact> contacts = controller.list();
        for (Contact c : contacts) {
            tableModel.addRow(new Object[]{
                    c.id(), c.name(), c.email(), c.phone(), c.age(), c.location()
            });
        }
    }

    /**
     * Muestra el diálogo para agregar un nuevo contacto.
     */
    private void showAddContactDialog() {
        AddContactDialog dialog = new AddContactDialog(this, controller);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            loadContacts();
        }
    }

    /**
     * Elimina el contacto seleccionado en la tabla.
     */
    private void deleteSelectedContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un contacto para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar este contacto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.delete(id);
            loadContacts();
        }
    }
}
