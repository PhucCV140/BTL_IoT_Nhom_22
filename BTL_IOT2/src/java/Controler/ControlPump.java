/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controler;

import DAO.DAO;
import Model.Parameter;
import Model.WaterUsed;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author XUAN CUONG
 */
public class ControlPump extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControlPump</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControlPump at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        List<WaterUsed> list = dao.getAll();
        request.setAttribute("Water", list);
        Parameter p = dao.getParameter();
        request.setAttribute("parameter", p);
        request.getRequestDispatcher("IOT.jsp").forward(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    int count=0;
    int temp=0, temp2=0;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String distance = request.getParameter("distance");
        if (!"-".equals(distance)) {
            if (count == 0) temp=Integer.parseInt(distance);
            if (count == 1) temp2=Integer.parseInt(distance);
            count++;
        }
        
        WaterUsed wt = new WaterUsed();
        DAO dao = new DAO();
        Parameter p = dao.getParameter();
        if (count>=2) {
            String ngay = NgayThang();
            wt.setDate(ngay);
            Double nuoc = Water(temp, temp2, p.getRadius());
            wt.setWaterused(nuoc);
            if (dao.Save(wt)) System.out.println("Luu thanh cong");
            count=0;
        }
        
        List<WaterUsed> list = dao.getAll();
        request.setAttribute("Water", list);
        
        request.setAttribute("Parameter", p);
      
        request.getRequestDispatcher("IOT.jsp").forward(request, response);
    }
    
    public static Double Water(int tmp1, int tmp2, int r){
        Double temp = Math.abs(tmp2-tmp1)*r*r*3.14;
        return temp;
    }
    
    public static String NgayThang() {
        // Sử dụng thư viện java.time để lấy ngày tháng hiện tại
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return today.format(formatter);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
