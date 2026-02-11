/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/**
 *
 * @author MADR20220271
 */
import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/live_search_gl2")
public class live_search_gl2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String q = request.getParameter("q");
        String fn = request.getParameter("fn");

        // Default JS function if not passed
        if (fn == null || fn.trim().isEmpty()) {
            fn = "selectCategoryItem";
        }

        if (q == null || q.trim().isEmpty()) {
            out.print("");
            return;
        }

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = matic_fs.getConnection();

            String sql = "SELECT code_id, code, description FROM codes "
                       + "WHERE code LIKE ? OR description LIKE ? LIMIT 10";

            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + q + "%");
            pst.setString(2, "%" + q + "%");

            rs = pst.executeQuery();

            out.println("<ul style='list-style:none; padding:0; margin:0;'>");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                String code = rs.getString("code_id");
                String name = rs.getString("code");
                String desc = rs.getString("description");

                out.println(
                    "<li style='padding:8px; border-bottom:1px solid #ddd; cursor:pointer;' "
                    + "onclick=\"" + fn + "('" + desc + "')\">"
                    + "<b>" + name + "</b><br>"
                    + "<small>" + desc + "</small>"
                    + "</li>"
                );
            }

            if (!hasData) {
                out.println("<li style='padding:8px;'>No results found</li>");
            }

            out.println("</ul>");

        } catch (Exception e) {
            out.println("<li>Error: " + e.getMessage() + "</li>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pst != null) pst.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
