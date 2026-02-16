import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Datarecon")
public class Datarecon extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();

        String startDate = request.getParameter("monthStart");
        String zone = request.getParameter("zone");
        String region = request.getParameter("region");
        String branch = request.getParameter("branch");
        String cri = request.getParameter("cri");          // frontend sends "r - Principal" etc
        String criteria1 = request.getParameter("criteria1");
        String criteria2 = request.getParameter("criteria2");
        String criteria3 = request.getParameter("criteria3");
        String data = request.getParameter("data");

        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        String sqls = "";

        try {
            conn = matic_fs.getConnection();
            conn.createStatement().execute("SET sql_mode=''"); 

            if ("adj".equalsIgnoreCase(data)) {
                handleAdjustment(request, conn, cri);
                return; 
            }

            String main = "SELECT f.date, v.ZoneName, f.zone_id, v.RegionName, f.region_id, v.Branch, f.branch_id, sum(f.total_amount) AS Amount "
                    + "FROM fs f "
                    + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                    + "LEFT JOIN codes c ON c.code_id = f.code_id "
                    + "WHERE f.date = ? AND f.zone_id = ? AND f.region_id = ? AND f.total_amount <> 0 AND ";

            String codeFilter = getCodeFilter(data, criteria1, criteria2);

            String sql = main + codeFilter + " GROUP BY f.branch_id";
            pst = conn.prepareStatement(sql);
            pst.setString(1, startDate);
            pst.setString(2, zone);
            pst.setString(3, region);

            rs = pst.executeQuery();
            DecimalFormat df = new DecimalFormat("#,##0.00");

            while (rs.next()) {
                String date = rs.getString("date");
                String z = rs.getString("ZoneName");
                String r = rs.getString("RegionName");
                String b = rs.getString("Branch");
                String zid = rs.getString("zone_id");
                String rid = rs.getString("region_id");
                String bid = rs.getString("branch_id");
                double amount = rs.getDouble("Amount");

                double adj = getAdjustment(conn, date, zid, rid, bid, cri);

                double total = amount + adj; 

                String amounted = df.format(amount);
                String adjed = df.format(adj);
                String totaled = df.format(total);

                String button = "";
                if (total != 0) {
                    button = "<button class='btn btn-sm' onclick=\"openReceivableModal('"+ date + "','" + z + "','" + r + "','" + b + "','" + zid + "','" + rid + "','" + bid + "','"+ amounted + "','" + adjed + "','" + cri + "', this)\"><i class='fa-regular fa-pen-to-square'></i></button>";
                }

                sqls += "<tr>"
                        + "<td>" + date + "</td>"
                        + "<td style='text-align:center;'>" + z + "</td>"
                        + "<td style='text-align:left;'>" + r + "</td>"
                        + "<td style='text-align:left;'>" + b + "</td>"
                        + "<td style='text-align:center;'>" + amounted + "</td>"
                        + "<td style='text-align:center;'>" + adjed + "</td>"
                        + "<td style='text-align:center;'>" + totaled + " " + button + "</td>"
                        + "<td></td><td></td><td></td><td></td>"
                        + "</tr>";
            }

            write.print(sqls);
            write.flush();

        } catch (SQLException | NumberFormatException e) {
            write.print("<tr><td colspan='11' style='color:red; text-align:left;'>Error: "
                    + e.getMessage() + "</td></tr>");
            e.printStackTrace();
        } finally {
            try { if (rs1 != null) rs1.close(); } catch (Exception e) {}
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pst2 != null) pst2.close(); } catch (Exception e) {}
            try { if (pst1 != null) pst1.close(); } catch (Exception e) {}
            try { if (pst != null) pst.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    private void handleAdjustment(HttpServletRequest request, Connection conn, String cri) throws SQLException {
        String dated = request.getParameter("date");
        String zoneid = request.getParameter("zoneid");
        String regionid = request.getParameter("regionid");
        String branchid = request.getParameter("branchid");
        String branchname = request.getParameter("branch");
        String remarks = request.getParameter("remarks");
        double adjustment = Double.parseDouble(request.getParameter("adjustment"));
        String category = request.getParameter("category");

        String checkSql = "SELECT id FROM datarecon WHERE date = ? AND zone = ? AND region = ? AND branch = ? AND criteria = ?";
        try (PreparedStatement pst1 = conn.prepareStatement(checkSql)) {
            pst1.setString(1, dated);
            pst1.setString(2, zoneid);
            pst1.setString(3, regionid);
            pst1.setString(4, branchid);
            pst1.setString(5, cri);

            ResultSet rsCheck = pst1.executeQuery();
            if (!rsCheck.next()) {
                String insert = "INSERT INTO datarecon (date, zone, region, branch, branchname, description, amount, category, criteria) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstInsert = conn.prepareStatement(insert)) {
                    pstInsert.setString(1, dated);
                    pstInsert.setString(2, zoneid);
                    pstInsert.setString(3, regionid);
                    pstInsert.setString(4, branchid);
                    pstInsert.setString(5, branchname);
                    pstInsert.setString(6, remarks);
                    pstInsert.setDouble(7, adjustment);
                    pstInsert.setString(8, category);
                    pstInsert.setString(9, cri);
                    pstInsert.executeUpdate();
                }
            }
        }
    }

    private double getAdjustment(Connection conn, String date, String zone, String region, String branch, String cri) throws SQLException {
        String getadj = "SELECT amount FROM Datarecon WHERE date = ? AND zone = ? AND region = ? AND branch = ? AND criteria = ?";
        try (PreparedStatement pst2 = conn.prepareStatement(getadj)) {
            pst2.setString(1, date);
            pst2.setString(2, zone);
            pst2.setString(3, region);
            pst2.setString(4, branch);
            pst2.setString(5, cri);
            ResultSet rs1 = pst2.executeQuery();
            if (rs1.next()) {
                return rs1.getDouble("amount");
            }
        }
        return 0;
    }

    private String getCodeFilter(String data, String criteria1, String criteria2) {
        String codeFilter = "1=1"; 

        if (data == null) return codeFilter;

        switch (data.toLowerCase()) {
            case "cb":
                boolean isTeller = "teller".equalsIgnoreCase(criteria1);
                if ("php".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111101'" : "c.code='111151'";
                else if ("usd".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111102'" : "c.code='111152'";
                else if ("jpy".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111103'" : "c.code='111155'";
                else if ("eur".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111105'" : "c.code='111153'";
                break;
            case "bb":
                codeFilter = "c.code BETWEEN '111201' AND '111231'";
                break;
            case "r":
                codeFilter = "c.code IN ('112101','112102')";
                break;
            case "ql":
                switch (criteria1.toLowerCase()) {
                    case "in": codeFilter = "c.code IN ('112101','112102')"; break;
                    case "ad": codeFilter = "c.code IN ('411102','411202')"; break;
                    case "or": codeFilter = "c.code IN ('411104','411204')"; break;
                    case "li": codeFilter = "c.code IN ('411105','411205')"; break;
                    case "se": codeFilter = "c.code IN ('411201','411101')"; break;
                }
                break;
            case "bp":
                if ("p".equalsIgnoreCase(criteria1)) codeFilter = "c.code = '112401'";
                else if ("c".equalsIgnoreCase(criteria1)) codeFilter = "c.code IN ('428101')";
                break;
            case "cp":
                if ("p".equalsIgnoreCase(criteria1)) codeFilter = "c.code IN ('211107')";
                else if ("c".equalsIgnoreCase(criteria1)) codeFilter = "c.code IN ('424101')";
                break;
            case "ps":
                if ("p".equalsIgnoreCase(criteria1)) codeFilter = "c.code IN ('211108')";
                else if ("c".equalsIgnoreCase(criteria1)) codeFilter = "c.code IN ('425101')";
                break;
            default:
                codeFilter = "1=1";
        }
        return codeFilter;
    }
}
