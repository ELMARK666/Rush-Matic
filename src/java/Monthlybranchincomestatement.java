import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.YearMonth;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Monthlybranchincomestatement")
public class Monthlybranchincomestatement extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter write = response.getWriter();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = matic_fs.getConnection();
            conn.createStatement().execute("SET sql_mode = ''");

            int reportYear = Integer.parseInt(request.getParameter("year"));
            int startMonth = Integer.parseInt(request.getParameter("start"));
            int endMonth = Integer.parseInt(request.getParameter("end"));
            String zone = request.getParameter("zones");
            String region = request.getParameter("region");
            String branch = request.getParameter("branch");
            String level = request.getParameter("level");

            int monthsCount = endMonth - startMonth + 1;
            double[] revTotals = new double[monthsCount];
            double[] expTotals = new double[monthsCount];
            DecimalFormat df = new DecimalFormat("#,##0.00");

            String selectCols = "";
            for (int m = startMonth; m <= endMonth; m++) {
                YearMonth ym = YearMonth.of(reportYear, m);
                String start = String.format("%04d-%02d-01", reportYear, m);
                String end = String.format("%04d-%02d-%02d", reportYear, m, ym.lengthOfMonth());

                selectCols += "SUM(CASE WHEN f.date BETWEEN '" + start + "' AND '" + end + "' THEN f.total_amount ELSE 0 END) AS M"
                        + String.format("%02d", m);

                if (m < endMonth) selectCols += ", ";
            }

            String sqlRevenue = "SELECT tg.name AS label, " + selectCols + " "
                    + "FROM fs f "
                    + "INNER JOIN ( "
                        + "SELECT DISTINCT c.code, c.code_id, c.code_" + level + "_id, c1.name "
                        + "FROM codes c "
                        + "INNER JOIN code"+level+" c1 on c1.code_" + level + "_id = c.code_" + level + "_id "
                    + ") tg ON tg.code_id = f.code_id "
                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                    + "WHERE tg.code LIKE '4%' ";

            if (!"ALL".equalsIgnoreCase(zone)) sqlRevenue += " AND v.Zone = ? ";
            if (!"ALL".equalsIgnoreCase(region)) sqlRevenue += " AND v.Region = ? ";
            if (!"ALL".equalsIgnoreCase(branch)) sqlRevenue += " AND v.BranchID = ? ";

            sqlRevenue += " GROUP BY tg.code_" + level + "_id ORDER BY tg.code_" + level + "_id ASC";

            pst = conn.prepareStatement(sqlRevenue);
            int paramIndex = 1;
            if (!"ALL".equalsIgnoreCase(zone)) pst.setString(paramIndex++, zone);
            if (!"ALL".equalsIgnoreCase(region)) pst.setString(paramIndex++, region);
            if (!"ALL".equalsIgnoreCase(branch)) pst.setString(paramIndex++, branch);

            rs = pst.executeQuery();
            boolean hasRevenue = false;
            while (rs.next()) {
                hasRevenue = true;
                String label = rs.getString("label");
                write.print("<tr><td>" + label + "</td>");
                for (int m = startMonth; m <= endMonth; m++) {
                    int idx = m - startMonth;
                    double val = rs.getDouble("M" + String.format("%02d", m));
                    revTotals[idx] += val;
                    write.print("<td style='text-align:right;'>" + df.format(val) + "</td>");
                }
                write.println("</tr>");
            }
            rs.close();
            pst.close();

            if (hasRevenue) {
                write.print("<tr><td style='text-align: right;'><strong>Total Revenue</strong></td>");
                for (int j = 0; j < monthsCount; j++) {
                    write.print("<td style='text-align:right;'><strong>" + df.format(revTotals[j]) + "</strong></td>");
                }
                write.println("</tr>");
            }

            String sqlExpenses = "SELECT tg.name AS label, " + selectCols + " "
                    + "FROM fs f "
                    + "INNER JOIN ( "
                    + "SELECT DISTINCT c.code, c.code_id, c.code_" + level + "_id, c1.name  "
                    + "FROM codes c  "
                    + "INNER JOIN code"+level+" c1 on c1.code_" + level + "_id = c.code_" + level + "_id "
                    + ") tg ON tg.code_id = f.code_id "
                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                    + "WHERE tg.code BETWEEN '500001' AND '611101' ";

            if (!"ALL".equalsIgnoreCase(zone)) sqlExpenses += " AND v.Zone = ? ";
            if (!"ALL".equalsIgnoreCase(region)) sqlExpenses += " AND v.Region = ? ";
            if (!"ALL".equalsIgnoreCase(branch)) sqlExpenses += " AND v.BranchID = ? ";

            sqlExpenses += " GROUP BY tg.code_" + level + "_id ORDER BY tg.code_" + level + "_id ASC";

            pst = conn.prepareStatement(sqlExpenses);
            paramIndex = 1;
            if (!"ALL".equalsIgnoreCase(zone)) pst.setString(paramIndex++, zone);
            if (!"ALL".equalsIgnoreCase(region)) pst.setString(paramIndex++, region);
            if (!"ALL".equalsIgnoreCase(branch)) pst.setString(paramIndex++, branch);

            rs = pst.executeQuery();
            boolean hasExpenses = false;
            while (rs.next()) {
                hasExpenses = true;
                String label = rs.getString("label");
                write.print("<tr><td>" + label + "</td>");
                for (int m = startMonth; m <= endMonth; m++) {
                    int idx = m - startMonth;
                    double val = rs.getDouble("M" + String.format("%02d", m));
                    expTotals[idx] += val;
                    write.print("<td style='text-align:right;'>" + df.format(val) + "</td>");
                }
                write.println("</tr>");
            }
            rs.close();
            pst.close();

            if (hasExpenses) {
                write.print("<tr><td style='text-align: right;'><strong>Total Expenses</strong></td>");
                for (int j = 0; j < monthsCount; j++) {
                    write.print("<td style='text-align:right;'><strong>" + df.format(expTotals[j]) + "</strong></td>");
                }
                write.println("</tr>");
            }

            boolean anyData = hasRevenue || hasExpenses;
            if (!anyData) {
                write.println("<tr><td colspan='100%' style='text-align:center; font-weight:bold;'>NO DATA FOUND</td></tr>");
            } else {
                write.print("<tr><td style='text-align: right;'><strong>Net Income</strong></td>");
                for (int j = 0; j < monthsCount; j++) {
                    double net = revTotals[j] - expTotals[j];
                    write.print("<td style='text-align:right;'><strong>" + df.format(net) + "</strong></td>");
                }
                write.println("</tr>");
            }

        } catch (NumberFormatException | SQLException ex) {
            write.println("<tr><td colspan='100%' class='alert alert-danger'>");
            write.println("Error: " + ex.getMessage() + "<br/>");
            for (StackTraceElement st : ex.getStackTrace()) {
                write.println(st.toString() + "<br/>");
            }
            write.println("</td></tr>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (pst != null) pst.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }
}
