/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.weld.servlet.SessionHolder;

/**
 *
 * @author fahmy
 */
@WebServlet(urlPatterns = {"/updateUser"})
public class updateUser extends HttpServlet {

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
        User newUser = new User();
        newUser.name = request.getParameter("newName");
        newUser.password = request.getParameter("newPassword");

        HttpSession session = request.getSession(true);
        User u = (User)session.getAttribute("user");
        newUser.email = u.email;
        Connection con = new DBConnection().getConnection();
        Statement stmt;
        try {
            stmt = con.createStatement();
            String update = "";
            if (newUser.name == null && newUser.password != null){
                 update = "update user set password='"+newUser.password+"' where email='" + newUser.email + "'";  
            }else if(newUser.name != null && newUser.password == null){
                 update = "update user set name='"+newUser.name+"' where email='" + newUser.email + "'";
            }else if(newUser.name != null && newUser.password != null){
                 update = "update user set name = '" +newUser.name+ "' and password='"+
                        newUser.password+"' where email='" + newUser.email + "'";
             }
        stmt.executeUpdate(update);
        } catch (SQLException ex) {
            Logger.getLogger(signUp.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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