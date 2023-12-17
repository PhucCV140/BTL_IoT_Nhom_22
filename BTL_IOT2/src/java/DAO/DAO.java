/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Parameter;
import Model.WaterUsed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author XUAN CUONG
 */
public class DAO {
    Connection conn = null; // ket noi toi sql server
    PreparedStatement ps = null; // nem cau lenh query tu netbean sang sql server
    ResultSet rs=null; //nhan ket qua tra ve
    
    public List<WaterUsed> getAll(){
        try
            {
                String query = "select Ngay,Water from WaterPump";
                conn = new DBContext().getConnection(); //mo ket noi toi SQL server
                ps=conn.prepareStatement(query);
                rs=ps.executeQuery();
                List<WaterUsed> list;
                list = new ArrayList<>();
                while(rs.next()){
                    WaterUsed a;
                    a = new WaterUsed(rs.getString(1), rs.getDouble(2));
                    list.add(a);
                }
                return list;
            } catch (Exception e){
    }
        return null;
    }
    
    public boolean Save(WaterUsed wt) {
        String query ="Insert WaterPump (Ngay, Water) values (?,?)"; 
        try {
            conn = new DBContext().getConnection(); //mo ket noi toi SQL server
            ps=conn.prepareStatement(query);
            ps.setString(1, wt.getDate());
            ps.setDouble(2,wt.getWaterused());
            int check = ps.executeUpdate();
            return check > 0;
    } catch (Exception e) {
        }
        return false;
    }
    
    public Parameter getParameter(){
        try
            {
                String query = "select * from Parameter where Id=1";
                conn = new DBContext().getConnection(); //mo ket noi toi SQL server
                ps=conn.prepareStatement(query);
                rs=ps.executeQuery();
                Parameter a = new Parameter();
                while(rs.next()){
                    a = new Parameter(rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5));
                }
                return a;
            } catch (Exception e){
    }
        return null;
    }
    
    public boolean Update(Parameter p) {
        String query ="Update Parameter set [Min]=?, [Max]=?, [Height]=?, Radius=? where [Id]=1;";
        try {
            conn = new DBContext().getConnection(); //mo ket noi toi SQL server
            ps=conn.prepareStatement(query);
            ps.setInt(1,p.getMin());
            ps.setInt(2,p.getMax());
            ps.setInt(3,p.getHeight());
            ps.setInt(4,p.getRadius());
            int check = ps.executeUpdate();
            return check > 0;
        } catch (Exception e) {
        }
        return false;
    }
    
    
}