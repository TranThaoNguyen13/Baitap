package controller;

import dao.CategoryDao;
import dao.CosmeticDao;
import dao.InvoiceDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.BillDetail;
import view.KhachHangView;
import model.Category;
import model.Cosmetic;
import model.Invoice;
import model.User;
import view.DangnhapView;


public class KhachHangController {
    private KhachHangView view;
    private DefaultListModel<Object> categoryListModel;
    private JList<Object> categoryList;
    private CosmeticDao cosmeticDao;
    private InvoiceDao invoiceDao;
    private List<BillDetail> billDetails;
    private DefaultTableModel cartTableModel;
    private JTable cartTable;
    private JPanel pnSanPham;
    
    private User loggedInUser;
    
    public KhachHangController(KhachHangView view, User loggedInUser) {
        this.view = view;
        cosmeticDao = new CosmeticDao();
        invoiceDao = new InvoiceDao();
        this.loggedInUser = loggedInUser;
        
        billDetails = new ArrayList<>();
        
        createProductListPanel();
        
        // Cart panel
        createCartPanel();
        
        
        loadData();
        initEvent();
    }
    
    
    private void createProductListPanel() {
        view.getPnDSSanPham().setLayout(new BorderLayout());
        view.getPnDSSanPham().setPreferredSize(new Dimension(600, 600));
        pnSanPham = new JPanel();
        JScrollPane scrollPaneProductList = new JScrollPane(pnSanPham);
        view.getPnDSSanPham().add(scrollPaneProductList, BorderLayout.CENTER);
    }
    
    private void createCartPanel() {
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("Tên sản phẩm");
        cartTableModel.addColumn("Số lượng");
        cartTableModel.addColumn("Giá");
        cartTableModel.addColumn("Thành tiền");
        cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        loadDanhMuc();
    }

    private void initEvent() {
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Category selectedCategory = (Category) categoryList.getSelectedValue();
                if (selectedCategory != null) {
                    System.err.println("111111111");
                    loadCosmeticsByCategory(selectedCategory.getCategoryId());
                }
            }
        });
        
        view.getBtnGioHang().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartDialog();
            }
        });
        
        view.getBtnDangXuat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    handleLogout();
            }
        });
        
        view.getBtnTimKiem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    handleTimKiemMyPham();
            }
        });
        
        view.getBtnChat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.moHopThoaiChat();
            }
        });
    }
    
    
    private void handleTimKiemMyPham() {
        String key = view.getTxtTimKiem().getText();
        if (key != null && !key.trim().isEmpty()) {
            List<Cosmetic> searchResults = cosmeticDao.searchCosmeticsByName(key.trim());
            displayCosmetics(searchResults);
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập từ khóa tìm kiếm.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void displayCosmetics(List<Cosmetic> cosmetics) {
        pnSanPham.removeAll();
        GridBagLayout gridBagLayout = new GridBagLayout();
        pnSanPham.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        for (Cosmetic cosmetic : cosmetics) {
            JPanel productPanel = createProductPanel(cosmetic);
            pnSanPham.add(productPanel, constraints);
            constraints.gridx++; // Di chuyển sang cột tiếp theo
            if (constraints.gridx == 3) { // Nếu đã đến cột thứ 3, chuyển sang hàng mới
                constraints.gridx = 0;
                constraints.gridy++;
            }
        }
        view.revalidate();
        view.repaint();
    }

    private void loadDanhMuc() {
        view.getPnDanhMuc().setLayout(new BorderLayout());
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        view.getPnDanhMuc().add(categoryScrollPane, BorderLayout.CENTER);
        view.getPnDanhMuc().setPreferredSize(new Dimension(200, 600));
        
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAllCategories();
        for (Category category : categories) {
            categoryListModel.addElement(category);
        }
    }
    
    private void loadCosmeticsByCategory(int categoryId) {
         System.err.println("22222");
        pnSanPham.removeAll();
        List<Cosmetic> cosmetics = cosmeticDao.getCosmeticsByCategoryId(categoryId);
        GridBagLayout gridBagLayout = new GridBagLayout();
        pnSanPham.setLayout(gridBagLayout);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(20, 20, 20, 20);
        for (Cosmetic cosmetic : cosmetics) {
            JPanel productPanel = createProductPanel(cosmetic);
            pnSanPham.add(productPanel, constraints);
            constraints.gridx++; // Di chuyển sang cột tiếp theo
            if (constraints.gridx == 3) { // Nếu đã đến cột thứ 3, chuyển sang hàng mới
                constraints.gridx = 0;
                constraints.gridy++;
            }
        }
        view.revalidate();
        view.repaint();
    }
    
    private JPanel createProductPanel(Cosmetic cosmetic) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setPreferredSize(new Dimension(250, 300));

        JLabel imageLabel = new JLabel();
        String base64Image = cosmetic.getImage();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                byte[] imageData = Base64.getDecoder().decode(base64Image);
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
                if (bufferedImage != null) {
                    ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(imageIcon);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.add(imageLabel);
        panel.add(imagePanel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.add(new JLabel("Tên: " + cosmetic.getCosmeticName()));
        detailsPanel.add(new JLabel("Ngày sản xuất: " + cosmetic.getManufactureDate()));
        detailsPanel.add(new JLabel("Ngày hết hạn: " + cosmetic.getExpiryDate()));
        detailsPanel.add(new JLabel("Giá: " + cosmetic.getPrice()));
        panel.add(detailsPanel, BorderLayout.CENTER);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedQuantity = showQuantityDialog();
                if (selectedQuantity > 0) {
                    float price = cosmetic.getPrice();
                    BillDetail billDetail = new BillDetail(cosmetic.getCosmeticID(), selectedQuantity, price);
                    billDetails.add(billDetail);
                    updateCartTable();
                    updateTotalPrice();
                }
            }
        });
        panel.add(addToCartButton, BorderLayout.SOUTH);

        return panel;
    }
    
    private int showQuantityDialog() {
        JTextField quantityField = new JTextField(5);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Số lượng:"));
        myPanel.add(quantityField);
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Chọn số lượng", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                return Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException e) {
                return -1; // Invalid quantity
            }
        }
        return -1; // Cancelled
    }
    
    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (BillDetail billDetail : billDetails) {
            Object[] row = {billDetail.getCosmeticID(), billDetail.getQuantity(), billDetail.getPrice(), billDetail.getQuantity() * billDetail.getPrice()}; // Calculate total price
            cartTableModel.addRow(row);
        }
    }

    private void updateTotalPrice() {
        float totalPrice = 0;
        for (BillDetail billDetail : billDetails) {
            totalPrice += billDetail.getPrice();
        }
        // Update total price label or text field in your UI
    }

    private void showCartDialog() {
        // Create a dialog to display the cart
        JDialog cartDialog = new JDialog(view, "Giỏ hàng", true);
        cartDialog.setSize(400, 300);
        cartDialog.setLocationRelativeTo(view);
        
        // Cart table for the dialog
        JTable cartDialogTable = new JTable(cartTableModel);
        JScrollPane cartDialogScrollPane = new JScrollPane(cartDialogTable);
        cartDialog.add(cartDialogScrollPane, BorderLayout.CENTER);
        
        // Order button
        JButton orderButton = new JButton("Đặt hàng");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder(); // Call method to place order
                cartDialog.dispose(); // Close dialog after placing order
            }
        });
        
        // Close button
        JButton closeButton = new JButton("Đóng");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cartDialog.dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(orderButton);
        buttonPanel.add(closeButton);
        cartDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        cartDialog.setVisible(true);
    }

    private void placeOrder() { // Renamed method
    	if(DangNhapController.getLoggedInUser() == null) {
    		JOptionPane.showMessageDialog(view, "Bạn cần đăng nhập để đặt hàng!");
    		return;
    	}
        if (billDetails.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng trống!");
            return;
        }

        Date currentDate = new Date();
        int userID = DangNhapController.getLoggedInUser().getUserID();
        float totalPrice = 0;
        for (BillDetail billDetail : billDetails) {
            totalPrice += billDetail.getPrice() * billDetail.getQuantity();
        }
        Invoice invoice = new Invoice(userID, currentDate, totalPrice);
        boolean success = invoiceDao.saveInvoice(invoice, billDetails);
        if (success) {
            JOptionPane.showMessageDialog(view, "Đặt hàng thành công!");
            billDetails.clear();
            updateCartTable();
            updateTotalPrice();
        } else {
            JOptionPane.showMessageDialog(view, "Đặt hàng thất bại! Vui lòng thử lại sau.");
        }
    }
    
    
    
    private void handleLogout() {
    	int option = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            DangnhapView loginView = new DangnhapView();
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            view.dispose();
        }
    }
}
