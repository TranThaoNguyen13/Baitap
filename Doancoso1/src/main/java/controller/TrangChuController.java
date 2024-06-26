package controller;

import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import view.DangnhapView;
import view.QuanlydanhmucView;
import view.QuanlynguoidungView;
import view.QuanlysanphamView;
import view.ThongkeView;
import view.TrangchuView;

public class TrangChuController {
    
    private TrangchuView view;
    
    public TrangChuController (TrangchuView view) {
        this.view = view;
        
        initEvent();
    }

    private void initEvent() {
        this.view.getBtnQLDanhMuc().addActionListener(e -> moManHinhQuanLyDanhMuc());
        this.view.getBtnQLSanPham().addActionListener(e -> moManHinhQuanLySanPham());
        this.view.getBtnQLNguoiDung().addActionListener(e -> moManHinhQuanLyNguoiDung());
        this.view.getBtnThongKe().addActionListener(e -> moManHinhThongKe());
        this.view.getBtnDangXuat().addActionListener(e -> xuLyDangXuat());
    }

    private void moManHinhQuanLyDanhMuc() {
        QuanlydanhmucView view = new QuanlydanhmucView();
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        disposeCurrentView();
    }

    private void moManHinhQuanLySanPham() {
        QuanlysanphamView view = new QuanlysanphamView();
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        disposeCurrentView();
    }

    private void moManHinhQuanLyNguoiDung() {
        QuanlynguoidungView view = new QuanlynguoidungView();
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        disposeCurrentView();
    }

    private void moManHinhThongKe() {
        ThongkeView view = new ThongkeView();
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        disposeCurrentView();
    }

    private void xuLyDangXuat() {
        int option = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            DangnhapView loginView = new DangnhapView();
            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
            view.dispose();
        }
    }

    private void disposeCurrentView() {
        if (this.view != null) {
            this.view.dispose();
        }
    }
}
