

import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/fs_general_ledger_percategorys")
public class fs_general_ledger_percategorys extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Database connection parameters
    Connection conn = null;

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    // ===== GET PARAMETERS =====
    String dateFrom = request.getParameter("per_categorys_date_gl_from");
    String dateTo = request.getParameter("per_categorys_date_gl_to");
    String filter = request.getParameter("per_categorys_filter");
    String zone = request.getParameter("per_categorys_zones");
    String region = request.getParameter("per_categorys_region");
    String glFrom = request.getParameter("per_categorys_gl_from");
    String category = request.getParameter("per_categorys_category");
    String branch = request.getParameter("per_categorys_branch");
    String action = request.getParameter("action");

    // ===== DEBUG PARAMETERS =====
    System.out.println("===== AJAX PARAMETERS =====");
    System.out.println("dateFrom: " + dateFrom);
    System.out.println("dateTo: " + dateTo);
    System.out.println("filter: " + filter);
    System.out.println("zone: " + zone);
    System.out.println("region: " + region);
    System.out.println("glFrom: " + glFrom);
    System.out.println("category: " + category);
    System.out.println("branch: " + branch);
    System.out.println("action: " + action);
    System.out.println("===========================");

    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection conn = null;

    try {
        conn = matic_fs.getConnection();

        // Disable strict mode (optional, remove if not needed)
        Statement stmt = conn.createStatement();
        stmt.execute("SET sql_mode = ''");

        // ===== BUILD SQL =====
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ")
           .append("f.date, ")
           .append("v.RegionName AS Region, ")
           .append("gl.code AS GLCode, ")
           .append("gl.description AS Description, ")
           .append("SUM(f.total_amount) AS Amount ")
           .append("FROM fs.fs f ")
           .append("INNER JOIN vw_new_branch_record v ON f.branch_id = v.BranchID ")
           .append("INNER JOIN fs.codes gl ON f.code_id = gl.code_id ")
           .append("WHERE f.date BETWEEN ? AND ? ")
           .append("AND gl.description = ? ")
           .append("AND f.category = ? ");

        // ===== DYNAMIC FILTER =====
        if ("3".equals(filter) && branch != null && !branch.isEmpty()) {
            sql.append("AND f.branch_id = ? ");
        } 
        else if ("2".equals(filter) && region != null && !region.isEmpty()) {
            sql.append("AND v.RegionName = ? ");
        } 
        else if ("1".equals(filter) && zone != null && !zone.isEmpty()) {
            sql.append("AND f.zone_id = ? ");
        }

        // FIXED: include f.date in GROUP BY
        sql.append("GROUP BY f.date, v.RegionName, gl.code, gl.description ")
           .append("ORDER BY f.date ASC, v.RegionName ASC");

        ps = conn.prepareStatement(sql.toString());

        // ===== BIND PARAMETERS =====
        int paramIndex = 1;

        ps.setString(paramIndex++, dateFrom);
        ps.setString(paramIndex++, dateTo);
        ps.setString(paramIndex++, glFrom);
        ps.setString(paramIndex++, category);

        if ("3".equals(filter) && branch != null && !branch.isEmpty()) {
            ps.setString(paramIndex++, branch);
        } 
        else if ("2".equals(filter) && region != null && !region.isEmpty()) {
            ps.setString(paramIndex++, region);
        } 
        else if ("1".equals(filter) && zone != null && !zone.isEmpty()) {
            ps.setString(paramIndex++, zone);
        }

        // ===== DEBUG FINAL SQL =====
        System.out.println("Executing SQL: " + ps.toString());

        rs = ps.executeQuery();

        boolean hasData = false;

        while (rs.next()) {
            hasData = true;

            String date = rs.getString("date");
            String regionName = rs.getString("Region");
            String glCode = rs.getString("GLCode");
            String description = rs.getString("Description");
            String amount = rs.getString("Amount");

            System.out.println("Row: " + date + ", " + regionName + ", "
                    + glCode + ", " + description + ", " + amount);

            out.println("<tr>");
            out.println("<td>" + glCode + "</td>");
            out.println("<td>" + description + "</td>");
            out.println("<td>" + date + "</td>");
            out.println("<td>" + regionName + "</td>");
            out.println("<td>-</td>"); // Cost Center placeholder
            out.println("<td>-</td>"); // Branch placeholder
            out.println("<td>" + amount + "</td>");
            out.println("</tr>");
        }

        if (!hasData) {
            out.println("<tr><td colspan='7' style='text-align:center;'>No data found</td></tr>");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        out.println("<tr><td colspan='7' style='text-align:center;color:red;'>Error: "
                + e.getMessage() + "</td></tr>");
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException ignored) {
        }
    }
}
}