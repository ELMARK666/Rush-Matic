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

@SuppressWarnings("unchecked")
@WebServlet("/DataCheck_Jew")
public class DataCheck_Jew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
            
        String dates = request.getParameter("start");
        String zone = request.getParameter("zone");
        String reg = request.getParameter("reg");

        String sqls = "";
        try {
            conn = matic_fs.getConnection();
            conn.setAutoCommit(false);
            
            Statement stmt = conn.createStatement();
            stmt.execute("SET sql_mode = ''");
        
            Double total = 0.0;
            Double totals = 0.0;
            String sql = "CALL DataCheck_Jew(?, ?, ?)"; 
            pst = conn.prepareStatement(sql);
            pst.setString(1, zone);
            pst.setString(2, reg);
            pst.setString(3, dates); 
            
            rs = pst.executeQuery();
            
            DecimalFormat df = new DecimalFormat("#,##0.00");

            while (rs.next()) {
                double present = rs.getDouble("present");
                double presents = rs.getDouble("presents");
                String formattedpresent = df.format(present);
                String formattedpresents = df.format(presents);

                if (present < 0) {
                    formattedpresent = "<span style='color: red;'>(" + df.format(Math.abs(present)) + ")</span>";
                }
                if (presents < 0) {
                    formattedpresents = "<span style='color: red;'>(" + df.format(Math.abs(presents)) + ")</span>";
                }
                
                double per = ((presents / present) * 100);
                String perc = df.format(per);
                
                if (per < 0) {
                    perc = "<span style='color: red;'>(" + df.format(Math.abs(per)) + ")</span>";
                }
                    sqls += "<tr>"
                        + "<td>" + rs.getString("Date") + "</td>"
                        + "<td>" + rs.getString("Zone") + "</td>"
                        + "<td>" + rs.getString("Region") + "</td>"
                        + "<td>" + rs.getString("Branch") + "</td>"
                        + "<td style='text-align: center;'>" + formattedpresent + "</td>"
                        + "<td style='text-align: center;'>" + formattedpresents + "</td>"
                        + "<td style='text-align: center;'>" + perc + "%</td>"
                        + "</tr>";
                    total += present;
                    totals += presents;
            }
                sqls += "<tr>"
                        + "<td style='color: white;'>za</td>"
                        + "<td></td>"
                        + "<td></td>"
                        + "<td><b><center>Total</center></b></td>"
                        + "<td style='text-align:right;font-weight: bold;'>" + red(total) + "</td>"
                        + "<td style='text-align:right;font-weight: bold;'>" + red(totals) + "</td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "</tr>";

            write.print(sqls); 
            
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