/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.DAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author XUAN CUONG
 */
public class MainTest {
    public static void main(String[] args) {
        DAO dao = new DAO();
//        List<WaterUsed> list = dao.getAll();
//        for (WaterUsed x : list){
//            System.out.println(x.getDate() + "/" + x.getWaterused());
//        }

        Parameter p = dao.getParameter();
        System.out.println(p.getMin()+ " " + p.getMax() + " " + p.getHeight() + " " + p.getRadius());
    }
    
    public static Double Water(int tmp1, int tmp2){
        Double temp = Math.abs(tmp2-tmp1)*5*5*3.14;
        return temp;
    }
    
    public static String NgayThang() {
        // Sử dụng thư viện java.time để lấy ngày tháng hiện tại
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return today.format(formatter);
    }
}
