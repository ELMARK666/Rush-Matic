/**
 *
 * @author BONG20230935
 */
import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
import java.time.YearMonth;

@WebServlet("/Datacheck")
public class Datacheck extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter write = response.getWriter();

        String m1 = request.getParameter("monthStart");
        String yy = request.getParameter("year");
        String zone = request.getParameter("zone");
        String reg = request.getParameter("region");
        String branch = request.getParameter("branch");
        String data = request.getParameter("data");
        String criteria = request.getParameter("criteria");

        String sqls = "";
        double total = 0.0;

        int month = Integer.parseInt(m1);
        int year = Integer.parseInt(yy);
        YearMonth ym = YearMonth.of(year, month);
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-%02d", year, month, ym.lengthOfMonth());
        
        try (Connection conn = matic_fs.getConnection()) {

            Statement stmt = conn.createStatement();
            stmt.execute("SET SESSION sql_mode = ''");
            
            if(data.equalsIgnoreCase("sp")){
                CallableStatement st = conn.prepareCall("{CALL DataChecking_sp(?, ?, ?, ?)}");
                st.setString(1, startDate);
                st.setString(2, endDate);
                st.setString(3, zone);
                st.setString(4, reg);

                try (ResultSet rs = st.executeQuery()) {

                    while (rs.next()) {
                        double sfp = rs.getDouble("sfp"), stp  = rs.getDouble("stp"), sbk = rs.getDouble("sbk");  
                        double ssp  = rs.getDouble("ssp"), srw = rs.getDouble("srw"), sep = rs.getDouble("sep");
                        double sphp = rs.getDouble("sphp"), sair = rs.getDouble("sair"), sst = rs.getDouble("sst");  
                        double sml  = rs.getDouble("sml"), sp = rs.getDouble("sp"), sli = rs.getDouble("sli");
                        double cfp = rs.getDouble("cfp"), ctp  = rs.getDouble("ctp"), cbk = rs.getDouble("cbk");  
                        double csp  = rs.getDouble("csp"), crw = rs.getDouble("crw"), cep = rs.getDouble("cep");
                        double cphp = rs.getDouble("cphp"), cair = rs.getDouble("cair"), cst = rs.getDouble("cst");  
                        double cml  = rs.getDouble("cml"), cp = rs.getDouble("cp"), cli = rs.getDouble("cli");
                        
                        String cost1 = (cfp > sfp) ? "<span style='color:red;'>" + format(cfp) + "</span>" : format(cfp);
                        String cost2 = (ctp > stp) ? "<span style='color:red;'>" + format(ctp) + "</span>" : format(ctp);
                        String cost3 = (cbk > sbk) ? "<span style='color:red;'>" + format(cbk) + "</span>" : format(cbk);
                        String cost4 = (csp > ssp) ? "<span style='color:red;'>" + format(csp) + "</span>" : format(csp);
                        String cost5 = (crw > srw) ? "<span style='color:red;'>" + format(crw) + "</span>" : format(crw);
                        String cost6 = (cep > sep) ? "<span style='color:red;'>" + format(cep) + "</span>" : format(cep);
                        String cost7 = (cphp > sphp) ? "<span style='color:red;'>" + format(cphp) + "</span>" : format(cphp);
                        String cost8 = (cair > sair) ? "<span style='color:red;'>" + format(cair) + "</span>" : format(cair);
                        String cost9 = (cst > sst) ? "<span style='color:red;'>" + format(cst) + "</span>" : format(cst);
                        String cost10 = (cml > sml) ? "<span style='color:red;'>" + format(cml) + "</span>" : format(cml);
                        String cost11 = (cp > sp) ? "<span style='color:red;'>" + format(cp) + "</span>" : format(cp);
                        String cost12 = (cli > sli) ? "<span style='color:red;'>" + format(cli) + "</span>" : format(cli);


                        sqls += "<tr>"
                                + "<td style='text-align: center;'>" + rs.getString("date") + "</td>"
                                + "<td>" + rs.getString("Branch") + "</td>"
                                + "<td style='text-align: center;'>" + format(sfp) + "</td>"
                                + "<td style='text-align: center;'>" + cost1 + "</td>"
                                + "<td style='text-align: center;'>" + format(stp) + "</td>"
                                + "<td style='text-align: center;'>" + cost2 + "</td>"
                                + "<td style='text-align: center;'>" + format(sbk) + "</td>"
                                + "<td style='text-align: center;'>" + cost3 + "</td>"
                                + "<td style='text-align: center;'>" + format(ssp) + "</td>"
                                + "<td style='text-align: center;'>" + cost4 + "</td>"
                                + "<td style='text-align: center;'>" + format(srw) + "</td>"
                                + "<td style='text-align: center;'>" + cost5 + "</td>"
                                + "<td style='text-align: center;'>" + format(sep) + "</td>"
                                + "<td style='text-align: center;'>" + cost6 + "</td>"
                                + "<td style='text-align: center;'>" + format(sphp) + "</td>"
                                + "<td style='text-align: center;'>" + cost7 + "</td>"
                                + "<td style='text-align: center;'>" + format(sair) + "</td>"
                                + "<td style='text-align: center;'>" + cost8 + "</td>"
                                + "<td style='text-align: center;'>" + format(sst) + "</td>"
                                + "<td style='text-align: center;'>" + cost9 + "</td>"
                                + "<td style='text-align: center;'>" + format(sml) + "</td>"
                                + "<td style='text-align: center;'>" + cost10 + "</td>"
                                + "<td style='text-align: center;'>" + format(sp) + "</td>"
                                + "<td style='text-align: center;'>" + cost11 + "</td>"
                                + "<td style='text-align: center;'>" + format(sli) + "</td>"
                                + "<td style='text-align: center;'>" + cost12 + "</td>"
                                + "</tr>";
                    }
                }
            }else if (data.equalsIgnoreCase("iea")){
                String in = "";
                if (criteria.equalsIgnoreCase("in")){
                    in = "SELECT a.date as Date, vw.ZoneName, vw.RegionName, vw.Branch, a.branch_id, c.code, a.`desc`, SUM(a.total_amount) AS Amount, a.category "
                            + "FROM fs a "
                            + "LEFT JOIN vw_new_branch_record vw ON vw.BranchID = a.branch_id  "
                            + "LEFT JOIN codes c ON c.code_id = a.code_id "
                            + "WHERE c.code like '4%' AND a.date BETWEEN ? AND ? AND a.zone_id = ? AND a.region_id = ? AND a.branch_id = ? "
                            + "GROUP BY a.date, vw.Branch, a.code_id "
                            + "ORDER BY  vw.ZoneName, vw.RegionName, vw.Branch, a.date;";
                }else{
                    in = "SELECT a.date as Date, vw.ZoneName, vw.RegionName, vw.Branch, a.branch_id, c.code, a.`desc`, SUM(a.total_amount) AS Amount, a.category "
                            + "FROM fs a "
                            + "LEFT JOIN vw_new_branch_record vw ON vw.BranchID = a.branch_id  "
                            + "LEFT JOIN codes c ON c.code_id = a.code_id "
                            + "WHERE c.code BETWEEN '500000' AND '611101' AND a.date BETWEEN ? AND ? AND a.zone_id = ? AND a.region_id = ? AND a.branch_id = ? "
                            + "GROUP BY a.date, vw.Branch, a.code_id "
                            + "ORDER BY  vw.ZoneName, vw.RegionName, vw.Branch, a.date;";
                }
                try (PreparedStatement pst = conn.prepareStatement(in)) {
                    pst.setDate(1, java.sql.Date.valueOf(startDate));
                    pst.setDate(2, java.sql.Date.valueOf(endDate));
                    pst.setString(3, zone);
                    pst.setString(4, reg);
                    pst.setString(5, branch);
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            double amount = rs.getDouble("Amount");
                            String date = rs.getString("Date");
                            sqls += "<tr>"
                            + "<td>" + date + "</td>"
                            + "<td>" + rs.getString("ZoneName") + "</td>"
                            + "<td>" + rs.getString("RegionName") + "</td>"
                            + "<td>" + rs.getString("Branch") + "</td>"
                            + "<td>" + rs.getString("desc") + "</td>"
                            + "<td>" + rs.getString("code") + "</td>"
                            + "<td style='text-align: center;'>" + format(amount) + "</td>"
                            + "<td>" + rs.getString("category") + "</td>"
                            + "</tr>";
                        }
                    }
                }
            }else if (data.equalsIgnoreCase("sa")){
                String sundry = "SELECT a.Date, vw.ZoneName, vw.RegionName, vw.AreaName, a.Branch, a.Sundry "
                        + "FROM crrf_tbl a "
                        + "INNER JOIN vw_new_branch_record vw ON vw.Region = a.Region AND vw.Branch = a.Branch "
                        + "WHERE a.Date BETWEEN ? AND ? AND a.Region = ? AND a.Sundry <> 0 "
                        + "GROUP BY a.Date, vw.ZoneName, vw.RegionName, vw.Branch  "
                        + "ORDER BY  vw.ZoneName, vw.RegionName, vw.Branch, a.Date;";
                
                try (PreparedStatement pst = conn.prepareStatement(sundry)) {
                    pst.setString(1, startDate);
                    pst.setString(2, endDate);
                    pst.setString(3, reg);
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            double amount = rs.getDouble("Sundry");
                            sqls += "<tr>"
                            + "<td>" + rs.getString("Date") + "</td>"
                            + "<td>" + rs.getString("ZoneName") + "</td>"
                            + "<td>" + rs.getString("RegionName") + "</td>"
                            + "<td>" + rs.getString("AreaName") + "</td>"
                            + "<td>" + rs.getString("Branch") + "</td>"
                            + "<td style='text-align: center;'>" + format(amount) + "</td>"
                            + "</tr>";
                        }
                    }
                }
            }
            
            write.print(sqls);

        } catch (SQLException | NumberFormatException e) {
            write.print(
                "<tr>" +
                    "<td colspan='28' style='color:red; text-align:left;'>" + "ERROR: " + e.getMessage() + "</td>" +
                "</tr>"
            );

        }
    }

    private String format(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(amount);
    }
}
