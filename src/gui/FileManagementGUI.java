package gui; 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entidades.Administrador;
import entidades.Usuario;

public class FileManagementGUI extends JFrame {
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private File currentDirectory;
    private List<File> clipboardFiles;
    private JButton copyButton, pasteButton, deleteButton, parentButton;
    private JButton userManagementButton; // Botão para gestão de usuários

    private Usuario usuario;

    public FileManagementGUI(Usuario usuario) {
        this.usuario = usuario;

        setTitle("File Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();

        currentDirectory = new File(System.getProperty("user.home"));
        if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
            currentDirectory = new File("/");
        }

        refreshList(currentDirectory);

        if (usuario instanceof Administrador) {
            userManagementButton.setVisible(true);
        } else {
            userManagementButton.setVisible(false);
        }

        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Name", "Type", "Size"}, 0);
        fileTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(fileTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        copyButton = new JButton("Copy");
        pasteButton = new JButton("Paste");
        deleteButton = new JButton("Delete");
        parentButton = new JButton("Up");
        userManagementButton = new JButton("User Management");

        buttonPanel.add(copyButton);
        buttonPanel.add(pasteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(parentButton);
        buttonPanel.add(userManagementButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        addListeners();
    }

    private void addListeners() {
        fileTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = fileTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        String selectedFileName = (String) tableModel.getValueAt(selectedRow, 0);
                        File selectedFile = new File(currentDirectory, selectedFileName);
                        if (selectedFile.isDirectory()) {
                            currentDirectory = selectedFile;
                            refreshList(currentDirectory);
                        }
                    }
                }
            }
        });

        parentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File parentDirectory = currentDirectory.getParentFile();
                if (parentDirectory != null && parentDirectory.isDirectory()) {
                    currentDirectory = parentDirectory;
                    refreshList(parentDirectory);
                }
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = fileTable.getSelectedRows();
                clipboardFiles = new ArrayList<>();

                for (int row : selectedRows) {
                    String fileName = (String) tableModel.getValueAt(row, 0);
                    File fileToCopy = new File(currentDirectory, fileName);
                    clipboardFiles.add(fileToCopy);
                }

                pasteButton.setEnabled(true);
            }
        });

        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clipboardFiles != null) {
                    for (File file : clipboardFiles) {
                        if (file.isDirectory()) {
                            String folderName = file.getName();
                            File destFolder = generateUniqueFileName(currentDirectory, folderName);

                            try {
                                copyDirectory(file.toPath().toString(), destFolder.toPath().toString());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else if (file.isFile()) {
                            String fileName = file.getName();
                            File destFile = generateUniqueFileName(currentDirectory, fileName);

                            try {
                                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    refreshList(currentDirectory);
                }

                pasteButton.setEnabled(false);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = fileTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedFileName = (String) tableModel.getValueAt(selectedRow, 0);
                    File fileToDelete = new File(currentDirectory, selectedFileName);
                    if (fileToDelete.exists()) {
                        try {
                            if (fileToDelete.isDirectory()) {
                                deleteDirectory(fileToDelete);
                            } else {
                                Files.delete(fileToDelete.toPath());
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        refreshList(currentDirectory);
                    }
                }
            }
        });

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserManagementGUI();
            }
        });
    }

    private void refreshList(File directory) {
        if (directory.isDirectory()) {
            tableModel.setRowCount(0);

            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    tableModel.addRow(new Object[]{
                            file.getName(),
                            file.isDirectory() ? "Directory" : "File",
                            file.length()
                    });
                }
            }
        }
    }

    private File generateUniqueFileName(File directory, String fileName) {
        File file = new File(directory, fileName);
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        int counter = 1;
        while (file.exists()) {
            file = new File(directory, name + "(" + counter + ")" + extension);
            counter++;
        }
        return file;
    }

    private void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    try {
                        Files.copy(source, Paths.get(destinationDirectoryLocation).resolve(Paths.get(sourceDirectoryLocation).relativize(source)), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void deleteDirectory(File directoryToBeDeleted) throws IOException {
        Files.walk(directoryToBeDeleted.toPath())
                .sorted((path1, path2) -> path2.compareTo(path1)) // Reverse order to delete files before directories
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
