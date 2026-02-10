/**
 * @author BONG20230935
 **/
import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DataCheck_ISPDInsurance")
public class DataCheck_ISPDInsurance extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
            
        String dates = request.getParameter("dates");
        String zone = request.getParameter("zone");
        String reg = request.getParameter("reg");
        
        String sqls = "";
        try {
            conn = matic_fs.getConnection();
            conn.setAutoCommit(false);
            
            Statement stmt = conn.createStatement();
            stmt.execute("SET sql_mode = ''");
        
            Double total = 0.0;
            if (request.getParameter("data").equalsIgnoreCase("insu")){
                String sql = "SELECT f.Date, c.code, v.ZoneName, v.RegionName, v.Branch, SUM(f.total_amount) AS Total, f.desc "
                        + "FROM fs f "
                        + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                        + "LEFT JOIN codes c ON c.code_id = f.code_id "
                        + "WHERE f.date = ?  AND c.code BETWEEN '431101' AND '431110' AND f.zone_id = ? AND f.Region_id = ? "
                        + "GROUP BY  f.Date, c.code, v.ZoneName, v.RegionName, v.Branch "
                        + "ORDER BY f.Date, v.Branch, c.code;"; 
                pst = conn.prepareStatement(sql);
                pst.setString(1, dates);
                pst.setString(2, zone);
                pst.setString(3, reg); 

                rs = pst.executeQuery();

                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    double present = rs.getDouble("Total");
                    String formattedpresent = df.format(present);

                    if (present < 0) {
                        formattedpresent = "<span style='color: red;'>(" + df.format(Math.abs(present)) + ")</span>";
                    }
                        sqls += "<tr>"
                            + "<td>" + rs.getString("date") + "</td>"
                            + "<td>" + rs.getString("code") + "</td>"
                            + "<td>" + rs.getString("ZoneName") + "</td>"
                            + "<td>" + rs.getString("RegionName") + "</td>"
                            + "<td>" + rs.getString("Branch") + "</td>"
                            + "<td>" + rs.getString("desc") + "</td>"
                            + "<td style='text-align: center;'>" + formattedpresent + "</td>"
                            + "</tr>";
                        total += present;
                }
                    sqls += "<tr>"
                            + "<td colspan = '6'><b><center>Total</center></b></td>"
                            + "<td style='text-align:right;font-weight: bold;'>" + red(total) + "</td>"
                        + "</tr>";

                write.print(sqls);
            }else if (request.getParameter("data").equalsIgnoreCase("ince")){
                String sql = "SELECT f.Date, c.code, v.ZoneName, v.RegionName, v.Branch, SUM(f.total_amount) AS Total, f.desc "
                        + "FROM fs f "
                        + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                        + "LEFT JOIN codes c ON c.code_id = f.code_id "
                        + "WHERE f.date = ?  AND c.code = '522306' AND f.zone_id = ? AND f.Region_id = ? "
                        + "GROUP BY  f.Date, c.code, v.ZoneName, v.RegionName, v.Branch "
                        + "ORDER BY f.Date, v.Branch, c.code;"; 
                pst = conn.prepareStatement(sql);
                pst.setString(1, dates);
                pst.setString(2, zone);
                pst.setString(3, reg); 

                rs = pst.executeQuery();

                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    double present = rs.getDouble("Total");
                    String formattedpresent = df.format(present);

                    if (present < 0) {
                        formattedpresent = "<span style='color: red;'>(" + df.format(Math.abs(present)) + ")</span>";
                    }
                        sqls += "<tr>"
                            + "<td>" + rs.getString("date") + "</td>"
                            + "<td>" + rs.getString("code") + "</td>"
                            + "<td>" + rs.getString("ZoneName") + "</td>"
                            + "<td>" + rs.getString("RegionName") + "</td>"
                            + "<td>" + rs.getString("Branch") + "</td>"
                            + "<td>" + rs.getString("desc") + "</td>"
                            + "<td style='text-align: center;'>" + formattedpresent + "</td>"
                            + "</tr>";
                        total += present;
                }
                    sqls += "<tr>"
                            + "<td colspan = '6'><b><center>Total</center></b></td>"
                            + "<td style='text-align:right;font-weight: bold;'>" + red(total) + "</td>"
                        + "</tr>";

                write.print(sqls);
            }
             
            
        } catch (SQLException | NumberFormatException e) {
            write.print("<tr><td class='alert alert-danger alert-dismissible fade in' colspan='4'>"
                    + "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
                    + "Sorry. Unable to process data. Please contact IT Support immediately. Issue : " + e.getMessage()
                    + "</td></tr>");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pst != null) try { pst.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    private String red(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (amount < 0) {
            return "<font color='red'>(" + formatter.format(-amount) + ")</font>";
        } else {
            return formatter.format(amount);
        }
    }
}