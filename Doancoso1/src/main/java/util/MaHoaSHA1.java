/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author ADMIN
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class MaHoaSHA1 {
    public static String maHoaSHA1(String input) {
        try {
            // Tạo một instance của SHA-1 message digest
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            // Tính toán hash SHA-1
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte hash thành chuỗi hexadecimal
            BigInteger hash = new BigInteger(1, hashBytes);
            StringBuilder hexString = new StringBuilder(hash.toString(16));

            // Thêm các ký tự 0 ở đầu nếu cần thiết để đảm bảo chuỗi hash có độ dài 40 ký tự
            while (hexString.length() < 40) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String code = "package controller;\n" +
                      "import dao.UserDao;\n" +
                      "import java.awt.event.MouseAdapter;\n" +
                      "import java.awt.event.MouseEvent;\n" +
                      "import java.io.BufferedReader;\n" +
                      "import java.io.IOException;\n" +
                      "import java.io.InputStreamReader;\n" +
                      "import java.io.PrintWriter;\n" +
                      "import java.net.Socket;\n" +
                      "import javax.swing.JOptionPane;\n" +
                      "import view.DangnhapView;\n" +
                      "import model.User;\n" +
                      "import view.DangkyView;\n" +
                      "import view.TrangchuView;\n" +
                      "import view.KhachHangView;\n" +
                      "public class DangNhapController {\n" +
                      "    private DangnhapView view;\n" +
                      "    private UserDao userDao;\n" +
                      "    private static User loggedInUser;\n" +
                      "    public static User getLoggedInUser() {\n" +
                      "        return loggedInUser;\n" +
                      "    }\n" +
                      "    public static void setLoggedInUser(User user) {\n" +
                      "        loggedInUser = user;\n" +
                      "    }\n" +
                      "    public DangNhapController(DangnhapView view) {\n" +
                      "        this.view = view;\n" +
                      "        userDao = new UserDao();\n" +
                      "        initEvent();\n" +
                      "    }\n" +
                      "    private void initEvent() {\n" +
                      "        view.getBtnDangNhap().addActionListener(e -> xuLyDangNhap());\n" +
                      "        view.getLblDangKy().addMouseListener(new MouseAdapter() {\n" +
                      "            @Override\n" +
                      "            public void mouseClicked(MouseEvent e) {\n" +
                      "                moManHinhDangKy();\n" +
                      "            }\n" +
                      "        });\n" +
                      "    }\n" +
                      "    private void xuLyDangNhap() {\n" +
                      "    String email = view.getTxtUsername().getText();\n" +
                      "    String password = view.getTxtPassword().getText();\n" +
                      "    User user = userDao.loginUser(email, password);\n" +
                      "    if (user != null) {\n" +
                      "        DangNhapController.setLoggedInUser(user);\n" +
                      "        try {\n" +
                      "            Socket socket = new Socket(\"localhost\", 12345);\n" +
                      "            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);\n" +
                      "            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));\n" +
                      "            out.println(email);\n" +
                      "            out.println(password);\n" +
                      "            String response = in.readLine();\n" +
                      "            if (\"Login successful\".equals(response)) {\n" +
                      "                int roleId = user.getRoleID();\n" +
                      "                if (roleId == 1) {\n" +
                      "                    TrangchuView adminView = new TrangchuView(socket);\n" +
                      "                    adminView.setLocationRelativeTo(null);\n" +
                      "                    adminView.setVisible(true);\n" +
                      "                } else if (roleId == 2) {\n" +
                      "                    KhachHangView homeView = new KhachHangView(user, socket);\n" +
                      "                    homeView.setLocationRelativeTo(null);\n" +
                      "                    homeView.setVisible(true);\n" +
                      "                } else {\n" +
                      "                    JOptionPane.showMessageDialog(view, \"RoleId không hợp lệ\", \"Lỗi\", JOptionPane.ERROR_MESSAGE);\n" +
                      "                }\n" +
                      "                view.dispose();\n" +
                      "            } else {\n" +
                      "                JOptionPane.showMessageDialog(view, \"Email hoặc mật khẩu không đúng\", \"Lỗi\", JOptionPane.ERROR_MESSAGE);\n" +
                      "            }\n" +
                      "        } catch (IOException e) {\n" +
                      "            e.printStackTrace();\n" +
                      "            JOptionPane.showMessageDialog(view, \"Không thể kết nối tới server\", \"Lỗi\", JOptionPane.ERROR_MESSAGE);\n" +
                      "        }\n" +
                      "    } else {\n" +
                      "        JOptionPane.showMessageDialog(view, \"Email hoặc mật khẩu không đúng\", \"Lỗi\", JOptionPane.ERROR_MESSAGE);\n" +
                      "    }\n" +
                      "}\n" +
                      "    private void moManHinhDangKy() {\n" +
                      "        DangkyView dangKyView = new DangkyView();\n" +
                      "        dangKyView.setLocationRelativeTo(null);\n" +
                      "        dangKyView.setVisible(true);\n" +
                      "        view.dispose();\n" +
                      "    }\n" +
                      "}";

        String hash = maHoaSHA1(code);
        System.out.println("Mã bam SHA-1: " + hash);
    }
}
