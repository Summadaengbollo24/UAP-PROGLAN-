import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

// Exception class for validation
class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) {
        super(message);
    }
}

class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

// Class untuk menyimpan data mahasiswa
class Mahasiswa {
    String nama;
    String nim;
    String foto;

    public Mahasiswa(String nama, String nim, String foto) {
        this.nama = nama;
        this.nim = nim;
        this.foto = foto;
    }
}

public class Main {
    private static Map<String, Mahasiswa> mahasiswaMap = new HashMap<>(); // Menyimpan data mahasiswa

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Login Sistem Akademik");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeLoginComponents(panel, loginFrame);

        loginFrame.setVisible(true);  // Menampilkan frame login
    }

    // Menyusun komponen untuk login
    private static void placeLoginComponents(JPanel panel, JFrame loginFrame) {
        panel.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel("");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(messageLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                try {
                    validateLogin(username, password);  // Validasi login
                    loginFrame.dispose();  // Tutup frame login jika berhasil login
                    showMainMenu();  // Tampilkan menu utama
                } catch (Exception ex) {
                    messageLabel.setText(ex.getMessage());  // Tampilkan pesan error
                }
            }
        });
    }

    // Validasi username dan password
    private static void validateLogin(String username, String password) throws Exception {
        if (!"ADMIN".equals(username)) {
            throw new InvalidUsernameException("Username invalid");
        }
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password minimal 8 digit");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password harus mengandung huruf besar");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("Password harus mengandung huruf kecil");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password harus mengandung angka");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            throw new InvalidPasswordException("Password harus mengandung simbol");
        }
    }

    // Menampilkan menu utama setelah login berhasil
    private static void showMainMenu() {
        JFrame mainFrame = new JFrame("Sistem Akademik");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        mainFrame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addStudentButton = new JButton("Tambah Data Mahasiswa");
        JButton addCourseButton = new JButton("Tambah Mata Kuliah dan Nilai");
        JButton searchStudentButton = new JButton("Cari Mahasiswa");
        JButton showAllStudentsButton = new JButton("Tampilkan Semua Mahasiswa");
        JButton deleteStudentButton = new JButton("Hapus Mahasiswa");
        JButton updateStudentButton = new JButton("Update Data Mahasiswa");

        panel.add(addStudentButton);
        panel.add(addCourseButton);
        panel.add(searchStudentButton);
        panel.add(showAllStudentsButton);
        panel.add(deleteStudentButton);
        panel.add(updateStudentButton);

        // Action listeners untuk tombol-tombol menu
        addStudentButton.addActionListener(e -> showAddStudentForm());
        addCourseButton.addActionListener(e -> showAddCourseForm());  // Action listener untuk menambah mata kuliah
        searchStudentButton.addActionListener(e -> showSearchStudentForm());  // Action listener untuk mencari mahasiswa
        showAllStudentsButton.addActionListener(e -> showAllStudents());
        deleteStudentButton.addActionListener(e -> showDeleteStudentForm());
        updateStudentButton.addActionListener(e -> showUpdateStudentForm());  // Action listener untuk update data mahasiswa

        mainFrame.setVisible(true);  // Menampilkan menu utama
    }

    // Menampilkan form untuk menambah mahasiswa
    private static void showAddStudentForm() {
        JFrame frame = new JFrame("Tambah Data Mahasiswa");
        frame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Nama:");
        JTextField nameField = new JTextField();
        JLabel nimLabel = new JLabel("NIM:");
        JTextField nimField = new JTextField();
        JLabel photoLabel = new JLabel("Foto (Path):");
        JTextField photoField = new JTextField();
        JButton browseButton = new JButton("Browse");
        JButton saveButton = new JButton("Simpan");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(nimLabel);
        panel.add(nimField);
        panel.add(photoLabel);
        panel.add(photoField);
        panel.add(new JLabel());
        panel.add(browseButton);
        panel.add(saveButton);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener untuk tombol browse
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                photoField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Action listener untuk tombol simpan
        saveButton.addActionListener(e -> {
            String nama = nameField.getText();
            String nim = nimField.getText();
            String foto = photoField.getText();
            try {
                // Validasi input
                if (nama.isEmpty() || nim.isEmpty() || foto.isEmpty()) {
                    throw new Exception("Semua field harus diisi!");
                }
                if (mahasiswaMap.containsKey(nim)) {
                    throw new Exception("Mahasiswa dengan NIM tersebut sudah ada!");
                }
                // Validasi ekstensi foto
                if (!foto.toLowerCase().endsWith(".jpg") && !foto.toLowerCase().endsWith(".png")) {
                    throw new Exception("File foto harus berupa .jpg atau .png");
                }
                File fotoFile = new File(foto);
                if (!fotoFile.exists()) {
                    throw new Exception("File foto tidak ditemukan!");
                }
                // Tentukan folder baru tempat foto disalin
                String destinationPath = "new_images/" + fotoFile.getName();
                // Pastikan folder baru sudah ada atau buat folder tersebut
                File destinationFolder = new File("new_images");
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }
                // Salin foto ke folder baru
                Files.copy(fotoFile.toPath(), new File(destinationPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                mahasiswaMap.put(nim, new Mahasiswa(nama, nim, destinationPath));
                JOptionPane.showMessageDialog(frame, "Data Mahasiswa Disimpan!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Menampilkan form untuk menambah mata kuliah dan nilai
    private static void showAddCourseForm() {
        JFrame frame = new JFrame("Tambah Mata Kuliah dan Nilai");
        frame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel courseLabel = new JLabel("Mata Kuliah:");
        JTextField courseField = new JTextField();
        JLabel gradeLabel = new JLabel("Nilai:");
        JTextField gradeField = new JTextField();
        JButton saveButton = new JButton("Simpan");

        panel.add(courseLabel);
        panel.add(courseField);
        panel.add(gradeLabel);
        panel.add(gradeField);
        panel.add(new JLabel());
        panel.add(saveButton);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener untuk tombol simpan
        saveButton.addActionListener(e -> {
            String course = courseField.getText();
            String grade = gradeField.getText();
            try {
                // Validasi input
                if (course.isEmpty() || grade.isEmpty()) {
                    throw new Exception("Semua field harus diisi!");
                }
                JOptionPane.showMessageDialog(frame, "Mata Kuliah dan Nilai Disimpan!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Menampilkan form untuk mencari mahasiswa
    private static void showSearchStudentForm() {
        JFrame frame = new JFrame("Cari Mahasiswa");
        frame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nimLabel = new JLabel("Masukkan NIM:");
        JTextField nimField = new JTextField();
        JButton searchButton = new JButton("Cari");

        panel.add(nimLabel);
        panel.add(nimField);
        panel.add(searchButton);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener untuk tombol cari
        searchButton.addActionListener(e -> {
            String nim = nimField.getText();
            try {
                if (mahasiswaMap.containsKey(nim)) {
                    Mahasiswa mhs = mahasiswaMap.get(nim);
                    JOptionPane.showMessageDialog(frame, "Nama: " + mhs.nama + "\nNIM: " + mhs.nim + "\nFoto: " + mhs.foto);
                } else {
                    throw new Exception("Mahasiswa Tidak Ditemukan!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Menampilkan form untuk menghapus mahasiswa
    private static void showDeleteStudentForm() {
        JFrame frame = new JFrame("Hapus Mahasiswa");
        frame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel nimLabel = new JLabel("Masukkan NIM:");
        JTextField nimField = new JTextField();
        JButton deleteButton = new JButton("Hapus");

        panel.add(nimLabel);
        panel.add(nimField);
        panel.add(deleteButton);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener untuk tombol hapus
        deleteButton.addActionListener(e -> {
            String nim = nimField.getText();
            try {
                if (mahasiswaMap.containsKey(nim)) {
                    int confirmation = JOptionPane.showConfirmDialog(frame, "Apakah Anda yakin ingin menghapus mahasiswa ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        mahasiswaMap.remove(nim);  // Hapus mahasiswa dari map
                        JOptionPane.showMessageDialog(frame, "Mahasiswa Berhasil Dihapus!");
                        frame.dispose();
                    }
                } else {
                    throw new Exception("Mahasiswa Tidak Ditemukan!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Menampilkan daftar semua mahasiswa
    private static void showAllStudents() {
        JFrame frame = new JFrame("Tampilkan Semua Mahasiswa");
        frame.setSize(600, 400);

        String[] columnNames = {"Nama", "NIM", "Foto"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        table.setRowHeight(100);  // Menyesuaikan tinggi baris untuk menampilkan foto

        for (Mahasiswa mhs : mahasiswaMap.values()) {
            // Membuat ImageIcon untuk kolom foto
            ImageIcon photoIcon = new ImageIcon(mhs.foto);
            model.addRow(new Object[]{mhs.nama, mhs.nim, photoIcon});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    // Menampilkan form untuk update mahasiswa
    private static void showUpdateStudentForm() {
        JFrame frame = new JFrame("Update Data Mahasiswa");
        frame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel nimLabel = new JLabel("Masukkan NIM:");
        JTextField nimField = new JTextField();
        JLabel nameLabel = new JLabel("Nama Baru:");
        JTextField nameField = new JTextField();
        JLabel photoLabel = new JLabel("Foto Baru (Path):");
        JTextField photoField = new JTextField();
        JButton browseButton = new JButton("Browse");
        JButton saveButton = new JButton("Update");

        panel.add(nimLabel);
        panel.add(nimField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(photoLabel);
        panel.add(photoField);
        panel.add(new JLabel());
        panel.add(browseButton);
        panel.add(saveButton);

        frame.add(panel);
        frame.setVisible(true);

        // Action listener untuk tombol browse
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                photoField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Action listener untuk tombol update
        saveButton.addActionListener(e -> {
            String nim = nimField.getText();
            String newName = nameField.getText();
            String newPhoto = photoField.getText();
            try {
                if (mahasiswaMap.containsKey(nim)) {
                    Mahasiswa mhs = mahasiswaMap.get(nim);
                    mhs.nama = newName.isEmpty() ? mhs.nama : newName;
                    mhs.foto = newPhoto.isEmpty() ? mhs.foto : newPhoto;

                    JOptionPane.showMessageDialog(frame, "Data Mahasiswa Berhasil Diupdate!");
                    frame.dispose();
                } else {
                    throw new Exception("Mahasiswa Tidak Ditemukan!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
