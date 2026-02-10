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
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Otherreports")
public class Otherreports extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        
        String start = request.getParameter("start");
        String start1 = request.getParameter("start");
        String end = request.getParameter("end");
        String zone = request.getParameter("zone");
        String reg = request.getParameter("reg");
        String branch = request.getParameter("branch");
        String data = request.getParameter("data");
        String criteria1 = request.getParameter("c1");
        String criteria2 = request.getParameter("c2");
        
        
        String started = null;

        if ("cb".equalsIgnoreCase(data) || "bb".equalsIgnoreCase(data) || "r".equalsIgnoreCase(data) || "lb".equalsIgnoreCase(data)) {

            if (start == null || start.isEmpty()) {
                throw new IllegalArgumentException("Date is required.");
            }

            LocalDate selectedDate = LocalDate.parse(start);
            LocalDate startOfYear = LocalDate.of(selectedDate.getYear(), 1, 1);
            started = startOfYear.toString();

        }

        String sqls = "";
        try {
            
            conn = matic_fs.getConnection();
            conn.setAutoCommit(false);
            
            Statement stmt = conn.createStatement();
            stmt.execute("SET sql_mode = ''");
        
            Double total = 0.0;
            if (data.equalsIgnoreCase("gr")){
                String sql = "SELECT f.date, c.code, v.ZoneName, v.RegionName, v.Branch, f.desc, "
                        + "SUM(CASE WHEN c.code LIKE '4%' THEN f.total_amount ELSE 0 END) AS total_4, "
                        + "SUM(CASE WHEN c.code BETWEEN '511101' AND '513201' THEN f.total_amount ELSE 0 END) AS total_between, "
                        + "SUM(CASE WHEN c.code LIKE '4%' THEN f.total_amount ELSE 0 END) - "
                        + "SUM(CASE WHEN c.code BETWEEN '511101' AND '513201' THEN f.total_amount ELSE 0 END) AS net_result "
                        + "FROM fs f "
                        + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                        + "LEFT JOIN codes c ON c.code_id = f.code_id "
                        + "WHERE f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.Region_id = ? "
                        + "GROUP BY  f.date, v.ZoneName, v.RegionName, v.Branch "
                        + "ORDER BY f.date, v.Branch, c.code;"; 
                pst = conn.prepareStatement(sql);
                pst.setString(1, start);
                pst.setString(2, end);
                pst.setString(3, zone); 
                pst.setString(4, reg);

                rs = pst.executeQuery();

                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    double net_result = rs.getDouble("net_result");
                    double total_4 = rs.getDouble("total_4");
                    double total_between = rs.getDouble("total_between");
                    String result = df.format(net_result);
                    String gl4 = df.format(total_4);
                    String gl5 = df.format(total_between);

                    if (net_result < 0) {
                        result = "<span style='color: red;'>(" + df.format(Math.abs(net_result)) + ")</span>";
                    }
                    if (total_4 < 0) {
                        gl4 = "<span style='color: red;'>(" + df.format(Math.abs(total_4)) + ")</span>";
                    }
                    if (total_between < 0) {
                        gl5 = "<span style='color: red;'>(" + df.format(Math.abs(total_between)) + ")</span>";
                    }
                        sqls += "<tr>"
                            + "<td>" + rs.getString("date") + "</td>"
                            + "<td>" + rs.getString("ZoneName") + "</td>"
                            + "<td>" + rs.getString("RegionName") + "</td>"
                            + "<td>" + rs.getString("Branch") + "</td>"
                            + "<td style='text-align: center;'>" + gl4 + "</td>"
                            + "<td style='text-align: center;'>" + gl5 + "</td>"
                            + "<td style='text-align: center;'>" + result + "</td>"
                            + "</tr>";
                }

                write.print(sqls);
            }else if (data.equalsIgnoreCase("is")){
                String sql = "SELECT f.date, v.ZoneName, v.RegionName, v.Branch, c.code, SUM(f.total_amount) AS amount, c.description "
                            + "FROM fs f "
                            + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                            + "LEFT JOIN codes c ON c.code_id = f.code_id "
                            + "WHERE f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.region_id = ? AND (c.code LIKE '4%' OR (c.code BETWEEN '511101' AND '611101')) "
                            + "GROUP BY f.date, v.RegionName, c.code "
                            + "ORDER BY f.date, c.code;";

                pst = conn.prepareStatement(sql);
                pst.setString(1, start);
                pst.setString(2, end); 
                pst.setString(3, zone);
                pst.setString(4, reg); 

                rs = pst.executeQuery();

                DecimalFormat df = new DecimalFormat("#,##0.00");

                double total4 = 0;
                double total56 = 0;
                double net = 0;
                boolean printedTotal4 = false;

                while (rs.next()) {

                    String code = rs.getString("code");
                    double amount = rs.getDouble("amount");

                    String amtFormatted = df.format(amount);
                    if (amount < 0) {
                        amtFormatted = "<span style='color:red;'>(" + df.format(Math.abs(amount)) + ")</span>";
                    }

                    if (code.startsWith("4")) {

                        total4 += amount;

                        sqls += "<tr>"
                              + "<td>" + rs.getString("date") + "</td>"
                              + "<td>" + code + "</td>"
                              + "<td>" + rs.getString("description") + "</td>"
                              + "<td style='text-align:center;'>" + amtFormatted + "</td>"
                              + "</tr>";
                    }else if (!printedTotal4 && (code.startsWith("5") || code.startsWith("6"))) {

                        printedTotal4 = true;

                        sqls += "<tr style='font-weight:bold;background:#eef;'>"
                              + "<td colspan='3' style='text-align:center;'>TOTAL REVENUE:</td>"
                              + "<td style='text-align:right;'>" + df.format(total4) + "</td>"
                              + "</tr>";

                        total56 += amount;

                        sqls += "<tr>"
                              + "<td>" + rs.getString("date") + "</td>"
                              + "<td>" + code + "</td>"
                              + "<td>" + rs.getString("description") + "</td>"
                              + "<td style='text-align:center;'>" + amtFormatted + "</td>"
                              + "</tr>";
                    }else if (code.startsWith("5") || code.startsWith("6")) {

                        total56 += amount;

                        sqls += "<tr>"
                              + "<td>" + rs.getString("date") + "</td>"
                              + "<td>" + code + "</td>"
                              + "<td>" + rs.getString("description") + "</td>"
                              + "<td style='text-align:center;'>" + amtFormatted + "</td>"
                              + "</tr>";
                    }
                }

                if (!printedTotal4) {
                    sqls += "<tr style='font-weight:bold;background:#eef;'>"
                          + "<td colspan='3' style='text-align:center;'>TOTAL REVENUE:</td>"
                          + "<td style='text-align:right;'>" + df.format(total4) + "</td>"
                          + "</tr>";
                }
                
                net = total4-total56;
                sqls += "<tr style='font-weight:bold;background:#eef;'>"
                      + "<td colspan='3' style='text-align:center;'>TOTAL EXPENSE:</td>"
                      + "<td style='text-align:right;'>" + df.format(total56) + "</td>"
                      + "</tr>"
                      + "<tr style='font-weight:bold;background:#eef;'>"
                      + "<td colspan='3' style='text-align:center;'>NET TOTAL:</td>"
                      + "<td style='text-align:right;'>" + df.format(net) + "</td>"
                      + "</tr>";


                write.print(sqls);
            }else if (data.equalsIgnoreCase("lb")){
                String sql = "SELECT f.date, v.ZoneName, v.RegionName, v.Branch, v.BranchId, "
                                + "CASE "
                                + "WHEN t.code BETWEEN '411101' AND '411105' THEN 'QCL Income' "
                                + "WHEN t.code BETWEEN '411201' AND '411205' THEN 'OPI Income' "
                                + "WHEN t.code BETWEEN '411301' AND '411604' THEN 'ML Loans Income' "
                                + "WHEN t.code BETWEEN '491101' AND '491199' THEN 'Other Income' "
                                + "WHEN t.code BETWEEN '421101' AND '421104' THEN 'KP Domestic Transactions Income' "
                                + "WHEN t.code BETWEEN '422101' AND '423101' THEN 'MCash/MLX Income' "
                                + "WHEN t.code = '424101' THEN 'Corporate Transactions Commission' "
                                + "WHEN t.code = '425101' THEN 'Payment Solution Commission' "
                                + "WHEN t.code = '426101' THEN 'POS Commission' "
                                + "WHEN t.code = '428101' THEN 'Domestic Partners Commission' "
                                + "WHEN t.code = '429101' THEN 'EPAY Transactions Commission' "
                                + "WHEN t.code BETWEEN '431101' AND '431110' THEN 'Insurance Commission' "
                                + "WHEN t.code BETWEEN '441101' AND '441102' THEN 'Sales - Jewelries and OPI' "
                                + "WHEN t.code BETWEEN '442101' AND '442902' THEN 'Sales - ISPD Products' "
                                + "WHEN t.code BETWEEN '443101' AND '443201' THEN 'Service Revenue - Logistics' "
                                + "WHEN t.code BETWEEN '449101' AND '449102' THEN 'Sales Returns, Allowances and Discounts' "
                                + "WHEN t.code = '461101' THEN 'MCFX Gain/Loss' "
                                + "WHEN t.code IN ('513101','513201','511101') OR t.code BETWEEN '521102' AND '521902' THEN 'Cost of Sales' "
                                + "WHEN t.code BETWEEN '521101' AND '521103' THEN 'Salaries and Wages' "
                                + "WHEN t.code BETWEEN '522101' AND '522315' THEN 'Staff Benefits' "
                                + "WHEN t.code BETWEEN '522401' AND '522413' THEN 'Taxes, Permits, and Licenses' "
                                + "WHEN t.code BETWEEN '522501' AND '522601' THEN 'Interest Expense' "
                                + "WHEN t.code BETWEEN '522701' AND '522706' THEN 'Marketing Expenses' "
                                + "WHEN t.code BETWEEN '522801' AND '522805' THEN 'Professional Fees & Training Fee' "
                                + "WHEN t.code IN ('522901','522902') THEN 'Rent Expenses' "
                                + "WHEN t.code BETWEEN '523101' AND '523107' THEN 'Security, Janitorial & Messengerial Services' "
                                + "WHEN t.code BETWEEN '523201' AND '523206' THEN 'Utitlites and Communication Expenses' "
                                + "WHEN t.code BETWEEN '523301' AND '523302' THEN 'Supplies Expenses' "
                                + "WHEN t.code = '523401' THEN 'Repairs and Maintenance' "
                                + "WHEN t.code = '523402' THEN 'Software Subscriptions and Maintenance' "
                                + "WHEN t.code IN ('523501','523502') THEN 'Fuel and Oil' "
                                + "WHEN t.code = '523601' THEN 'Meal Expense' "
                                + "WHEN t.code BETWEEN '523701' AND '523702' THEN 'Traveling and Transportation Expenses' "
                                + "WHEN t.code = '523801' THEN 'Delivery and Freight Charges' "
                                + "WHEN t.code = '523901' THEN 'Representation and Entertainment' "
                                + "WHEN t.code IN ('524101','524102') THEN 'Loss on Calamity, Theft and Robbery' "
                                + "WHEN t.code BETWEEN '524201' AND '524203' THEN 'Bank Charges and Other Fees' "
                                + "WHEN t.code = '524301' THEN 'Insurance Expense' "
                                + "WHEN t.code = '524401' THEN 'Bad Debts Expense' "
                                + "WHEN t.code = '524501' THEN 'Provision for Income Taxes' "
                                + "WHEN t.code = '524601' THEN 'Cost of Franchise' "
                                + "WHEN t.code = '524801' THEN 'Miscellaneous Expense' "
                                + "WHEN t.code BETWEEN '524901' AND '524912' THEN 'Depreciation and Amortization Expense' "
                                + "WHEN t.code = '611101' THEN 'HO Expenses' END AS label, "
                                + "FORMAT(SUM(f.total_amount), 2) AS amount "
                            + "FROM fs f "
                            + "INNER JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                            + "INNER JOIN codes t ON t.code_id = f.code_id "
                            + "WHERE f.category <> 'Balance' AND f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.region_id = ? AND f.branch_id = ? "
                            + "GROUP BY v.ZoneName, v.RegionName, v.Branch, v.BranchId, label "
                            + "HAVING SUM(f.total_amount) < 0 "
                            + "ORDER BY label;";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, started);
                    pst.setString(2, start1);
                    pst.setString(3, zone);
                    pst.setString(4, reg);
                    pst.setString(5, branch);
                    rs = pst.executeQuery();
                    
                    DecimalFormat df = new DecimalFormat("#,##0.00");

                    while (rs.next()) {
                         
                        String z = rs.getString("ZoneName");
                        String r = rs.getString("RegionName");
                        String b = rs.getString("Branch");
                        String c = rs.getString("label");
                        double amount = rs.getDouble("amount");
                        
                        sqls += "<tr>"
                            + "<td style='text-align:center;'>" + z + "</td>"
                            + "<td style='text-align:left;'>" + r + "</td>"
                            + "<td style='text-align:left;'>" + b + "</td>"
                            + "<td style='text-align:left;'>" + c + "</td>"
                            + "<td style='text-align:center;'>" + red(amount) + "</td>"
                            + "</tr>";
                    }
                    write.print(sqls);
                    
            }else if (data.equalsIgnoreCase("cb")){
                String main = "SELECT f.date, v.ZoneName, f.zone_id, v.RegionName, f.region_id, v.Branch, f.branch_id, sum(f.total_amount) AS Amount "
                    + "FROM fs f "
                    + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                    + "LEFT JOIN codes c ON c.code_id = f.code_id "
                    + "WHERE f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.region_id = ? AND f.total_amount <> 0 AND ";

                String codeFilter = "";
                
                boolean isTeller = "teller".equalsIgnoreCase(criteria1);
                if ("php".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111101'" : "c.code='111151'";
                else if ("usd".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111102'" : "c.code='111152'";
                else if ("jpy".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111103'" : "c.code='111155'";
                else if ("eur".equalsIgnoreCase(criteria2)) codeFilter = isTeller ? "c.code='111105'" : "c.code='111153'";
                
                String sql = main + codeFilter + " GROUP BY f.region_id";
                
                pst = conn.prepareStatement(sql);
                pst.setString(1, started);
                pst.setString(2, start);
                pst.setString(3, zone);
                pst.setString(4, reg);
                rs = pst.executeQuery();
                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    String date = rs.getString("date");
                    String z = rs.getString("ZoneName");
                    String r = rs.getString("RegionName");
                    double amount = rs.getDouble("Amount");
                    
                    sqls += "<tr>"
                        + "<td>" + date + "</td>"
                        + "<td style='text-align:center;'>" + z + "</td>"
                        + "<td style='text-align:left;'>" + r + "</td>"
                        + "<td style='text-align:center;'>" + df.format(amount) + "</td>"
                        + "</tr>";
                }
                write.print(sqls);
            }else if (data.equalsIgnoreCase("bb")){
                String main = "SELECT f.date, v.ZoneName, f.zone_id, v.RegionName, f.region_id, v.Branch, f.branch_id, sum(f.total_amount) AS Amount "
                    + "FROM fs f "
                    + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                    + "LEFT JOIN codes c ON c.code_id = f.code_id "
                    + "WHERE f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.region_id = ? AND f.total_amount <> 0 AND c.code BETWEEN '111201' AND '111231' GROUP BY f.region_id";
                
                pst = conn.prepareStatement(main);
                pst.setString(1, started);
                pst.setString(2, start);
                pst.setString(3, zone);
                pst.setString(4, reg);
                rs = pst.executeQuery();
                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    String date = rs.getString("date");
                    String z = rs.getString("ZoneName");
                    String r = rs.getString("RegionName");
                    double amount = rs.getDouble("Amount");
                    
                    sqls += "<tr>"
                        + "<td>" + date + "</td>"
                        + "<td style='text-align:center;'>" + z + "</td>"
                        + "<td style='text-align:left;'>" + r + "</td>"
                        + "<td style='text-align:center;'>" + df.format(amount) + "</td>"
                        + "</tr>";
                }
                write.print(sqls);
            }else if (data.equalsIgnoreCase("r")){
                String main = "SELECT f.date, v.ZoneName, f.zone_id, v.RegionName, f.region_id, v.Branch, f.branch_id, sum(f.total_amount) AS Amount "
                    + "FROM fs f "
                    + "LEFT JOIN vw_new_branch_record v ON v.BranchId = f.branch_id "
                    + "LEFT JOIN codes c ON c.code_id = f.code_id "
                    + "WHERE f.date BETWEEN ? AND ? AND f.zone_id = ? AND f.region_id = ? AND f.total_amount <> 0 AND c.code IN ('112101','112102') GROUP BY f.region_id";
                
                pst = conn.prepareStatement(main);
                pst.setString(1, started);
                pst.setString(2, start);
                pst.setString(3, zone);
                pst.setString(4, reg);
                rs = pst.executeQuery();
                DecimalFormat df = new DecimalFormat("#,##0.00");

                while (rs.next()) {
                    String date = rs.getString("date");
                    String z = rs.getString("ZoneName");
                    String r = rs.getString("RegionName");
                    double amount = rs.getDouble("Amount");
                    
                    sqls += "<tr>"
                        + "<td>" + date + "</td>"
                        + "<td style='text-align:center;'>" + z + "</td>"
                        + "<td style='text-align:left;'>" + r + "</td>"
                        + "<td style='text-align:center;'>" + df.format(amount) + "</td>"
                        + "</tr>";
                }
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