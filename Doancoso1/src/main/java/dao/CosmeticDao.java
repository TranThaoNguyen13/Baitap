package dao;

import database.SQLServerConnection;
import model.Cosmetic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CosmeticDao {

    // Thêm một mỹ phẩm mới vào CSDL
    public boolean addCosmetic(Cosmetic cosmetic) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để chèn dữ liệu mỹ phẩm mới vào CSDL
                String query = "INSERT INTO Cosmetic (CosmeticName, CategoryID, Description, ManufactureDate, ExpiryDate, Price, Quantity, Image) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                statement = connection.prepareStatement(query);
                statement.setString(1, cosmetic.getCosmeticName());
                statement.setInt(2, cosmetic.getCategoryID());
                statement.setString(3, cosmetic.getDescription());
                statement.setDate(4, new java.sql.Date(cosmetic.getManufactureDate().getTime()));
                statement.setDate(5, new java.sql.Date(cosmetic.getExpiryDate().getTime()));
                statement.setFloat(6, cosmetic.getPrice());
                statement.setInt(7, cosmetic.getQuantity());
                statement.setString(8, cosmetic.getImage());

                // Thực hiện câu truy vấn
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Thêm mỹ phẩm thành công!");
                    success = true;
                } else {
                    System.out.println("Thêm mỹ phẩm thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Sửa thông tin của một mỹ phẩm trong CSDL
    public boolean updateCosmetic(Cosmetic cosmetic) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để cập nhật thông tin của mỹ phẩm trong CSDL
                String query = "UPDATE Cosmetic SET CosmeticName = ?, CategoryID = ?, Description = ?, ManufactureDate = ?, ExpiryDate = ?, Price = ?, Quantity = ?, Image = ? WHERE CosmeticID = ?";

                statement = connection.prepareStatement(query);
                statement.setString(1, cosmetic.getCosmeticName());
                statement.setInt(2, cosmetic.getCategoryID());
                statement.setString(3, cosmetic.getDescription());
                statement.setDate(4, new java.sql.Date(cosmetic.getManufactureDate().getTime()));
                statement.setDate(5, new java.sql.Date(cosmetic.getExpiryDate().getTime()));
                statement.setFloat(6, cosmetic.getPrice());
                statement.setInt(7, cosmetic.getQuantity());
                statement.setString(8, cosmetic.getImage());
                statement.setInt(9, cosmetic.getCosmeticID());

                // Thực hiện câu truy vấn
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Cập nhật mỹ phẩm thành công!");
                    success = true;
                } else {
                    System.out.println("Cập nhật mỹ phẩm thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Xóa một mỹ phẩm khỏi CSDL dựa trên ID
    public boolean deleteCosmetic(int cosmeticId) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để xóa mỹ phẩm khỏi CSDL
                String query = "DELETE FROM Cosmetic WHERE CosmeticID = ?";

                statement = connection.prepareStatement(query);
                statement.setInt(1, cosmeticId);

                // Thực hiện câu truy vấn
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Xóa mỹ phẩm thành công!");
                    success = true;
                } else {
                    System.out.println("Xóa mỹ phẩm thất bại!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Lấy danh sách các mỹ phẩm từ CSDL
    public List<Cosmetic> getAllCosmetics() {
        List<Cosmetic> cosmetics = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để lấy danh sách các mỹ phẩm từ CSDL
                String query = "SELECT CosmeticID, CosmeticName, CategoryID, Description, ManufactureDate, ExpiryDate, Price, Quantity FROM Cosmetic";

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                // Duyệt qua các dòng kết quả và tạo danh sách mỹ phẩm
                while (resultSet.next()) {
                    Cosmetic cosmetic = new Cosmetic();
                    cosmetic.setCosmeticID(resultSet.getInt("CosmeticID"));
                    cosmetic.setCosmeticName(resultSet.getString("CosmeticName"));
                    cosmetic.setCategoryID(resultSet.getInt("CategoryID"));
                    cosmetic.setDescription(resultSet.getString("Description"));
                    cosmetic.setManufactureDate(resultSet.getDate("ManufactureDate"));
                    cosmetic.setExpiryDate(resultSet.getDate("ExpiryDate"));
                    cosmetic.setPrice(resultSet.getFloat("Price"));
                    cosmetic.setQuantity(resultSet.getInt("Quantity"));
                    cosmetics.add(cosmetic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cosmetics;
    }
    
    public Cosmetic getCosmeticById(int cosmeticId) {
        Cosmetic cosmetic = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để lấy thông tin của một mỹ phẩm từ CSDL dựa trên ID
                String query = "SELECT * FROM Cosmetic WHERE CosmeticID = ?";
//String query = "SELECT * FROM Cosmetic WHERE CosmeticName = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, cosmeticId);
                resultSet = statement.executeQuery();

                // Nếu có kết quả, tạo đối tượng mỹ phẩm từ dữ liệu kết quả
                if (resultSet.next()) {
                    cosmetic = new Cosmetic();
                    cosmetic.setCosmeticID(resultSet.getInt("CosmeticID"));
                    cosmetic.setCosmeticName(resultSet.getString("CosmeticName"));
                    cosmetic.setCategoryID(resultSet.getInt("CategoryID"));
                    cosmetic.setDescription(resultSet.getString("Description"));
                    cosmetic.setManufactureDate(resultSet.getDate("ManufactureDate"));
                    cosmetic.setExpiryDate(resultSet.getDate("ExpiryDate"));
                    cosmetic.setPrice(resultSet.getFloat("Price"));
                    cosmetic.setQuantity(resultSet.getInt("Quantity"));
                    cosmetic.setImage(resultSet.getString("Image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cosmetic;
    }
    
 // Lấy danh sách các mỹ phẩm theo ID danh mục từ CSDL
    public List<Cosmetic> getCosmeticsByCategoryId(int categoryId) {
        List<Cosmetic> cosmetics = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để lấy danh sách các mỹ phẩm từ CSDL theo ID danh mục
                String query = "SELECT * FROM Cosmetic WHERE CategoryID = ?";

                statement = connection.prepareStatement(query);
                statement.setInt(1, categoryId);
                resultSet = statement.executeQuery();

                // Duyệt qua các dòng kết quả và tạo danh sách mỹ phẩm
                while (resultSet.next()) {
                    Cosmetic cosmetic = new Cosmetic();
                    cosmetic.setCosmeticID(resultSet.getInt("CosmeticID"));
                    cosmetic.setCosmeticName(resultSet.getString("CosmeticName"));
                    cosmetic.setCategoryID(resultSet.getInt("CategoryID"));
                    cosmetic.setDescription(resultSet.getString("Description"));
                    cosmetic.setManufactureDate(resultSet.getDate("ManufactureDate"));
                    cosmetic.setExpiryDate(resultSet.getDate("ExpiryDate"));
                    cosmetic.setPrice(resultSet.getFloat("Price"));
                    cosmetic.setQuantity(resultSet.getInt("Quantity"));
                    cosmetic.setImage(resultSet.getString("Image"));
                    cosmetics.add(cosmetic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cosmetics;
    }
    
    
    public List<Cosmetic> getCosmeticsByName(String cosmeticName) {
    List<Cosmetic> cosmetics = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        connection = SQLServerConnection.getConnection();
        if (connection != null) {
            // SQL query to fetch cosmetics based on their name
            String query = "SELECT * FROM Cosmetic WHERE CosmeticName LIKE ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + cosmeticName + "%");
            resultSet = statement.executeQuery();

            // Iterate through the result set and populate the cosmetics list
            while (resultSet.next()) {
                Cosmetic cosmetic = new Cosmetic();
                cosmetic.setCosmeticID(resultSet.getInt("CosmeticID"));
                cosmetic.setCosmeticName(resultSet.getString("CosmeticName"));
                cosmetic.setCategoryID(resultSet.getInt("CategoryID"));
                cosmetic.setDescription(resultSet.getString("Description"));
                cosmetic.setManufactureDate(resultSet.getDate("ManufactureDate"));
                cosmetic.setExpiryDate(resultSet.getDate("ExpiryDate"));
                cosmetic.setPrice(resultSet.getFloat("Price"));
                cosmetic.setQuantity(resultSet.getInt("Quantity"));
                cosmetic.setImage(resultSet.getString("Image"));
                cosmetics.add(cosmetic);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close connection and statement
        SQLServerConnection.closeConnection();
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return cosmetics;
}
    // Tìm kiếm mỹ phẩm theo tên sử dụng từ khóa LIKE
    public List<Cosmetic> searchCosmeticsByName(String keyword) {
        List<Cosmetic> cosmetics = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = SQLServerConnection.getConnection();
            if (connection != null) {
                // Câu truy vấn SQL để tìm kiếm mỹ phẩm theo tên sử dụng từ khóa LIKE
                String query = "SELECT * FROM Cosmetic WHERE CosmeticName LIKE ?";

                statement = connection.prepareStatement(query);
                // Sử dụng ký tự % để tìm kiếm theo từ khóa
                statement.setString(1, "%" + keyword + "%");
                resultSet = statement.executeQuery();

                // Duyệt qua các dòng kết quả và tạo danh sách mỹ phẩm
                while (resultSet.next()) {
                    Cosmetic cosmetic = new Cosmetic();
                    cosmetic.setCosmeticID(resultSet.getInt("CosmeticID"));
                    cosmetic.setCosmeticName(resultSet.getString("CosmeticName"));
                    cosmetic.setCategoryID(resultSet.getInt("CategoryID"));
                    cosmetic.setDescription(resultSet.getString("Description"));
                    cosmetic.setManufactureDate(resultSet.getDate("ManufactureDate"));
                    cosmetic.setExpiryDate(resultSet.getDate("ExpiryDate"));
                    cosmetic.setPrice(resultSet.getFloat("Price"));
                    cosmetic.setQuantity(resultSet.getInt("Quantity"));
                    cosmetic.setImage(resultSet.getString("Image"));
                    cosmetics.add(cosmetic);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và tuyên bố
            SQLServerConnection.closeConnection();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cosmetics;
    }

    
}
