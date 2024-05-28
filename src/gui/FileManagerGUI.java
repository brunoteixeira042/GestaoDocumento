package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class FileManagerGUI extends JFrame {
    private File currentDirectory;
    private JTable fileTable;
    private DefaultTableModel tableModel;
    private JButton copyButton, pasteButton, deleteButton, parentButton;
    private List<File> clipboardFiles;

    public FileManagerGUI() {
        currentDirectory = new File(System.getProperty("user.home"));
        initUI();
        refreshList(currentDirectory);
    }

    private void initUI() {
        setTitle("File Manager");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] columnNames = {"Name", "Type", "Size"};
        tableModel = new DefaultTableModel(columnNames, 0);
        fileTable = new JTable(tableModel);
        add(new JScrollPane(fileTable), "Center");

        JPanel buttonPanel = new JPanel();
        copyButton = new JButton("Copy");
        pasteButton = new JButton("Paste");
        deleteButton = new JButton("Delete");
        parentButton = new JButton("Up");
        buttonPanel.add(copyButton);
        buttonPanel.add(pasteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(parentButton);
        add(buttonPanel, "South");

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
                        try {
                            if (file.isDirectory()) {
                                File destFolder = generateUniqueFileName(currentDirectory, file.getName());
                                copyDirectory(file.toPath().toString(), destFolder.toPath().toString());
                            } else if (file.isFile()) {
                                File destFile = generateUniqueFileName(currentDirectory, file.getName());
                                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
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
    }

    private void refreshList(File directory) {
        if (directory.isDirectory()) {
            tableModel.setRowCount(0);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();
                    String type = file.isDirectory() ? "Folder" : "File";
                    String size = file.isDirectory() ? "-" : String.valueOf(file.length());
                    tableModel.addRow(new Object[]{name, type, size});
                }
            }
        }
    }

    private void deleteDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    Files.delete(file.toPath());
                }
            }
            Files.delete(directory.toPath());
        }
    }

    public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
            Path destination = Paths.get(destinationDirectoryLocation, source.toString().substring(sourceDirectoryLocation.length()));
            try {
                Files.copy(source, destination);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private File generateUniqueFileName(File directory, String fileName) {
        String baseName = fileName;
        String extension = "";

        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex != -1) {
            baseName = fileName.substring(0, extensionIndex);
            extension = fileName.substring(extensionIndex);
        }

        int count = 1;
        File destFile = new File(directory, fileName);
        while (destFile.exists()) {
            destFile = new File(directory, baseName + "(" + count + ")" + extension);
            count++;
        }

        return destFile;
    }
}
