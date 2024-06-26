package controller;

import view.QuanlysanphamView;
import dao.CosmeticDao;
import dao.CategoryDao;
import model.Category;
import model.Cosmetic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.TrangchuView;

public class QuanLySanPhamController {

    private QuanlysanphamView view;
    private CosmeticDao cosmeticDao;
    private CategoryDao categoryDao;
    private List<Category> categories;

    public QuanLySanPhamController(QuanlysanphamView view) {
        this.view = view;
        this.cosmeticDao = new CosmeticDao();
        this.categoryDao = new CategoryDao();

        loadData();
        initEvent();
    }

    private void initEvent() {
        view.getBtnThemSP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCosmetic();
            }
        });

        view.getBtnSuaSP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCosmetic();
            }
        });

        view.getBtnXoaSP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCosmetic();
            }
        });

        view.getBtnChonAnh().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });
        
        view.getTblSanPham().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && view.getTblSanPham().getSelectedRow() != -1) {
                    int selectedRow = view.getTblSanPham().getSelectedRow();
                    int cosmeticId = (int) view.getTblSanPham().getValueAt(selectedRow, 0);
                    loadCosmeticDetails(cosmeticId);
                }
            }
        });
        
        view.getBtnTroVe().addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    TrangchuView view = new TrangchuView();
                    view.setLocationRelativeTo(null);
                    view.setVisible(true);
                    disposeCurrentView();
                }
        });
    }

    private void loadData() {
        // Load danh sách danh mục vào combobox
        categories = categoryDao.getAllCategories();
        view.getCbbDanhMuc().removeAllItems();
        for (Category category : categories) {
            view.getCbbDanhMuc().addItem(category);
        }

//        // Load danh sách sản phẩm vào bảng
//        List<Cosmetic> cosmetics = cosmeticDao.getAllCosmetics();
//        DefaultTableModel model = (DefaultTableModel) view.getTblSanPham().getModel();
//        model.setRowCount(0);
//        for (Cosmetic cosmetic : cosmetics) {
//            model.addRow(new Object[]{
//                cosmetic.getCosmeticID(),
//                cosmetic.getCosmeticName(),
//                cosmetic.getCategoryID(),
//                cosmetic.getDescription(),
//                cosmetic.getManufactureDate(),
//                cosmetic.getExpiryDate(),
//                cosmetic.getPrice(),
//                cosmetic.getQuantity()
//            });
//        }

        // Load danh sách sản phẩm vào bảng
        List<Cosmetic> cosmetics = cosmeticDao.getAllCosmetics();

        String[] columnNames = {"ID", "Name", "Category ID", "Description", "Manufacture Date", "Expiry Date", "Price", "Quantity"};
        Object[][] data = new Object[cosmetics.size()][columnNames.length];

        for (int i = 0; i < cosmetics.size(); i++) {
            Cosmetic cosmetic = cosmetics.get(i);
            data[i][0] = cosmetic.getCosmeticID();
            data[i][1] = cosmetic.getCosmeticName();
            data[i][2] = cosmetic.getCategoryID();
            data[i][3] = cosmetic.getDescription();
            data[i][4] = cosmetic.getManufactureDate();
            data[i][5] = cosmetic.getExpiryDate();
            data[i][6] = cosmetic.getPrice();
            data[i][7] = cosmetic.getQuantity();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa các ô trong bảng
            }
        };

        view.getTblSanPham().setModel(model);
    }

    private void addCosmetic() {
        try {
            String name = view.getTxtTenSP().getText();
            Category category = (Category) view.getCbbDanhMuc().getSelectedItem();
            String description = view.getTxtMoTa().getText();
            Date manufactureDate = new SimpleDateFormat("yyyy-MM-dd").parse(view.getTxtNgaySanXuat().getText());
            Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(view.getTxtNgayHetHan().getText());
            float price = Float.parseFloat(view.getTxtGia().getText());
            int quantity = Integer.parseInt(view.getTxtSoLuong().getText());
            String image = view.getLblLuuAnh().getText(); // Assuming the image is stored as Base64 string in lblLuuAnh

            Cosmetic cosmetic = new Cosmetic(name, category.getCategoryId(), description, manufactureDate, expiryDate, price, quantity, image);
            if (cosmeticDao.addCosmetic(cosmetic)) {
                JOptionPane.showMessageDialog(view, "Thêm mỹ phẩm thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm mỹ phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra. Vui lòng kiểm tra lại thông tin!");
        }
    }

    private void updateCosmetic() {
        int selectedRow = view.getTblSanPham().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn Sản phẩm cần sửa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(view.getTxtMaSP().getText());
            String name = view.getTxtTenSP().getText();
            Category category = (Category) view.getCbbDanhMuc().getSelectedItem();
            String description = view.getTxtMoTa().getText();
            Date manufactureDate = new SimpleDateFormat("yyyy-MM-dd").parse(view.getTxtNgaySanXuat().getText());
            Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(view.getTxtNgayHetHan().getText());
            float price = Float.parseFloat(view.getTxtGia().getText());
            int quantity = Integer.parseInt(view.getTxtSoLuong().getText());
            String image = view.getLblLuuAnh().getText(); // Assuming the image is stored as Base64 string in lblLuuAnh

            Cosmetic cosmetic = new Cosmetic(id, name, category.getCategoryId(), description, manufactureDate, expiryDate, price, quantity, image);
            if (cosmeticDao.updateCosmetic(cosmetic)) {
                JOptionPane.showMessageDialog(view, "Cập nhật mỹ phẩm thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật mỹ phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra. Vui lòng kiểm tra lại thông tin!");
        }
    }

    private void deleteCosmetic() {
        int selectedRow = view.getTblSanPham().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn Sản phẩm cần xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(view.getTxtMaSP().getText());
            if (cosmeticDao.deleteCosmetic(id)) {
                JOptionPane.showMessageDialog(view, "Xóa mỹ phẩm thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa mỹ phẩm thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Có lỗi xảy ra. Vui lòng kiểm tra lại thông tin!");
        }
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(file);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                view.getLblLuuAnh().setText(imageBase64);
                ImageIcon icon = new ImageIcon(image.getScaledInstance(view.getLblLuuAnh().getWidth(), view.getLblLuuAnh().getHeight(), Image.SCALE_SMOOTH));
                view.getLblLuuAnh().setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Có lỗi xảy ra khi chọn ảnh!");
            }
        }
    }
    
    private void loadCosmeticDetails(int cosmeticId) {
        Cosmetic cosmetic = cosmeticDao.getCosmeticById(cosmeticId);
        if (cosmetic != null) {
            view.getTxtMaSP().setText(String.valueOf(cosmetic.getCosmeticID()));
            view.getTxtTenSP().setText(cosmetic.getCosmeticName());
            for (int i = 0; i < view.getCbbDanhMuc().getItemCount(); i++) {
                if (view.getCbbDanhMuc().getItemAt(i).getCategoryId() == cosmetic.getCategoryID()) {
                    view.getCbbDanhMuc().setSelectedIndex(i);
                    break;
                }
            }
            view.getTxtMoTa().setText(cosmetic.getDescription());
            view.getTxtNgaySanXuat().setText(new SimpleDateFormat("yyyy-MM-dd").format(cosmetic.getManufactureDate()));
            view.getTxtNgayHetHan().setText(new SimpleDateFormat("yyyy-MM-dd").format(cosmetic.getExpiryDate()));
            view.getTxtGia().setText(String.valueOf(cosmetic.getPrice()));
            view.getTxtSoLuong().setText(String.valueOf(cosmetic.getQuantity()));

            if (cosmetic.getImage() != null && !cosmetic.getImage().isEmpty()) {
                try {
                    byte[] imageBytes = Base64.getDecoder().decode(cosmetic.getImage());
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(view.getLblLuuAnh().getWidth(), view.getLblLuuAnh().getHeight(), Image.SCALE_SMOOTH));
                    view.getLblLuuAnh().setIcon(icon);
                    view.getLblLuuAnh().setText(cosmetic.getImage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                view.getLblLuuAnh().setIcon(null);
                view.getLblLuuAnh().setText("");
            }
        }
    }
    
    private void disposeCurrentView() {
        if (this.view != null) {
            this.view.dispose();
        }
    }
}
