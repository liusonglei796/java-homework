package step2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils {
    
    /**
     * 获取数据库连接
     */
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/mysql_db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "123123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 增加数据 (泛型封装)
     */
    public void insert(Object obj) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            Class<?> c = obj.getClass();
            Field[] fields = c.getDeclaredFields();
            
            StringBuffer sql = new StringBuffer("insert into " + c.getSimpleName() + "(");
            StringBuffer values = new StringBuffer("values(");
            
            for (int i = 0; i < fields.length; i++) {
                sql.append(fields[i].getName());
                values.append("?");
                if (i != fields.length - 1) {
                    sql.append(",");
                    values.append(",");
                }
            }
            sql.append(") ").append(values).append(")");
            
            ps = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                ps.setObject(i + 1, fields[i].get(obj)); 
            }
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }
    }

    /**
     * 删除数据 (泛型封装)
     */
    public void delete(Object obj) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            Class<?> c = obj.getClass();
            Field[] fields = c.getDeclaredFields();
            fields[0].setAccessible(true);
            
            String sql = "delete from " + c.getSimpleName() + " where " + fields[0].getName() + "=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, fields[0].get(obj));
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }
    }

    /**
     * 更新数据 (泛型封装)
     */
    public void update(Object obj) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try {
            Class<?> c = obj.getClass();
            StringBuffer sb = new StringBuffer("update " + c.getSimpleName() + " set ");
            Field[] fields = c.getDeclaredFields();
            
            for (int i = 1; i < fields.length; i++) {
                if (i != fields.length - 1) {
                    sb.append(fields[i].getName()).append("=?,");
                } else {
                    sb.append(fields[i].getName()).append("=? where ");
                }
            }
            sb.append(fields[0].getName()).append("=?");
            
            ps = conn.prepareStatement(sb.toString());
            
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                ps.setObject(i, fields[i].get(obj));
            }
            fields[0].setAccessible(true);
            ps.setObject(fields.length, fields[0].get(obj));
            ps.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, ps, conn);
        }
    }

    /**
     * 查询所有数据 (注意：此处方法名已根据报错修改为 selectAll)
     */
    public <T> List<T> selectAll(Class<T> c) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        
        try {
            String sql = "select * from " + c.getSimpleName();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            Field[] fields = c.getDeclaredFields();
            
            while (rs.next()) {
                T obj = c.newInstance(); 
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }
                list.add(obj);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }
        return list;
    }

    /**
     * 根据ID查询数据 (根据报错信息新增的方法)
     */
    public Object selectById(Class<?> c, int id) {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object obj = null;
        
        try {
            Field[] fields = c.getDeclaredFields();
            // 假设类的第一个属性就是主键（如 id）
            String idFieldName = fields[0].getName();
            
            String sql = "select * from " + c.getSimpleName() + " where " + idFieldName + "=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                obj = c.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, ps, conn);
        }
        return obj;
    }

    /**
     * 关闭资源方法
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}