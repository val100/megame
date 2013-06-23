/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.nadeem.battleship.Board;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author val
 */
public class DigitalClock extends HttpServlet {

    public static boolean boardStatus = false;
    public Board userBoard;
    public Board enemyBoard;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (boardStatus == false) {
                userBoard = new Board();
                userBoard.placeShipRandomly();
                enemyBoard = new Board();
                out.println("<table style='align:center'>");
                out.println("<tr>");
                out.println("<td>");
                out.println(userBoard.showBoardHTML());
                out.println("</td>");
                out.println("<td>");
                out.println(enemyBoard.showBoardHTML());
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td colspan=12>");
                out.println("Your field");
                out.println("</td>");
                out.println("<td colspan=12>");
                out.println("Reval's' field");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                boardStatus = true;
            } else {
                out.println("<table>");
                out.println("<tr>");
                out.println("<td>");
                out.println(userBoard.showBoardHTML());
                out.println("</td>");
                out.println("<td>");
                out.println(enemyBoard.showBoardHTML());
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align='center'>");
                out.println("Your field");
                out.println("</td>");
                out.println("<td align='center'>");
                out.println("Reval's' field");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
