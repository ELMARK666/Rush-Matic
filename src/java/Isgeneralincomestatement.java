
import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Isgeneralincomestatement")
public class Isgeneralincomestatement extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String m1 = request.getParameter("monthStart");
        String yy = request.getParameter("yearStart");
        String z = request.getParameter("zone");
        String r = request.getParameter("region");
        String b = request.getParameter("branch");
        String f = request.getParameter("group");
   
        int month = Integer.parseInt(m1);
        int year = Integer.parseInt(yy);
        YearMonth ym = YearMonth.of(year, month);
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-%02d", year, month, ym.lengthOfMonth());
        
        try
        {
                String sql = "";
                String sqls = "";
                
                conn = matic_fs.getConnection();
                
                Statement stmt = conn.createStatement();
                stmt.execute("SET sql_mode = ''");
                
                if(request.getParameter("action").equals("filter"))
                {
                    switch (f) {
                        case "Branch":
                            sql = "SELECT gl.code as Code, gl.description, FORMAT(IFNULL(w.Amount,0), 2) AS Amount, "
                                + "FORMAT(IFNULL((revenue.TotalRevenue),0),2) as TotalRevenue "
                                + "FROM (SELECT DISTINCT code_id, code, description FROM codes WHERE code LIKE '4%') gl "
                                + "LEFT JOIN ("
                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) as Amount "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "GROUP BY f.code_id "
                                + ") w ON w.code_id = gl.code_id "
                                + "LEFT JOIN ("
                                    + "SELECT "
                                    + "SUM(CASE WHEN f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.category <> 'Balance' AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "THEN f.total_amount ELSE 0 END) as TotalRevenue "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + ") revenue ON 1=1 "
                                + "WHERE w.Amount IS NOT NULL "
                                + "GROUP BY gl.code_id "
                                + "ORDER BY gl.code_id;";

                            break;
                        case "Region":
                            sql = "SELECT gl.code as Code, gl.description, FORMAT(IFNULL(w.Amount,0), 2) AS Amount, "
                                + "FORMAT(IFNULL((revenue.TotalRevenue),0),2) as TotalRevenue "
                                + "FROM (SELECT DISTINCT code_id, code, description FROM codes WHERE code LIKE '4%') gl "
                                + "LEFT JOIN ("
                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) as Amount "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"'  "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "GROUP BY f.code_id "
                                + ") w ON w.code_id = gl.code_id "
                                + "LEFT JOIN ("
                                    + "SELECT "
                                    + "SUM(CASE WHEN f.region_id = '"+r+"'  "
                                    + "AND f.category <> 'Balance' AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "THEN f.total_amount ELSE 0 END) as TotalRevenue "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"'  "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + ") revenue ON 1=1 "
                                + "WHERE w.Amount IS NOT NULL "
                                + "GROUP BY gl.code_id "
                                + "ORDER BY gl.code_id;";
                            break;
//                        case "Area":
//                            sql = "SELECT gl.code as Code, gl.description, FORMAT(IFNULL(w.Amount,0), 2) AS Amount, "
//                                + "FORMAT(IFNULL((revenue.TotalRevenue),0),2) as TotalRevenue "
//                                + "FROM (SELECT DISTINCT code_id, code, description FROM codes WHERE code LIKE '4%') gl "
//                                + "LEFT JOIN ("
//                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) as Amount "
//                                    + "FROM fs f "
//                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                    + "GROUP BY f.code_id "
//                                + ") w ON w.code_id = gl.code_id "
//                                + "LEFT JOIN ("
//                                    + "SELECT "
//                                    + "SUM(CASE WHEN f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.category <> 'Balance' AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                    + "THEN f.total_amount ELSE 0 END) as TotalRevenue "
//                                    + "FROM fs f "
//                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                + ") revenue ON 1=1 "
//                                + "WHERE w.Amount IS NOT NULL "
//                                + "GROUP BY gl.code_id "
//                                + "ORDER BY gl.code_id;";
//                            break;
                        default:
                            if(z.equals("ALL"))
                            {
                                
                                sql = "SELECT gl.code as Code, gl.description, FORMAT(IFNULL(w.Amount,0), 2) AS Amount, "
                                    + "FORMAT(IFNULL((revenue.TotalRevenue),0),2) as TotalRevenue "
                                    + "FROM (SELECT DISTINCT code_id, code, description FROM codes WHERE code LIKE '4%') gl "
                                    + "LEFT JOIN ("
                                        + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) as Amount "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance'  "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "GROUP BY f.code_id "
                                    + ") w ON w.code_id = gl.code_id "
                                    + "LEFT JOIN ("
                                        + "SELECT "
                                        + "SUM(CASE WHEN f.category <> 'Balance' AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "THEN f.total_amount ELSE 0 END) as TotalRevenue "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + ") revenue ON 1=1 "
                                    + "WHERE w.Amount IS NOT NULL "
                                    + "GROUP BY gl.code_id "
                                    + "ORDER BY gl.code_id;";
                            }
                            else
                            {
                                sql = "SELECT gl.code as Code, gl.description, FORMAT(IFNULL(w.Amount,0), 2) AS Amount, "
                                    + "FORMAT(IFNULL((revenue.TotalRevenue),0),2) as TotalRevenue "
                                    + "FROM (SELECT DISTINCT code_id, code, description FROM codes WHERE code LIKE '4%') gl "
                                    + "LEFT JOIN ("
                                        + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) as Amount "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "GROUP BY f.code_id "
                                    + ") w ON w.code_id = gl.code_id "
                                    + "LEFT JOIN ("
                                        + "SELECT "
                                        + "SUM(CASE WHEN f.zone_id = '"+z+"' "
                                        + "AND f.category <> 'Balance' AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "THEN f.total_amount ELSE 0 END) as TotalRevenue "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + ") revenue ON 1=1 "
                                    + "WHERE w.Amount IS NOT NULL "
                                    + "GROUP BY gl.code_id "
                                    + "ORDER BY gl.code_id;";
                                
                            }
                            break;
                    }
                    
                }

                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                pst.setFetchSize(5000);
                while(rs.next())
                {
                    
                        String amountStr = rs.getString("Amount");
                        double amount = Double.parseDouble(amountStr.replace(",", ""));
                        String total = rs.getString("TotalRevenue");
                        double totals = Double.parseDouble(total.replace(",", ""));
                    
                    //Double amount = rs.getDouble("Amount");
                    
                    Double percent = amount / totals * 100;
                    String formattedPercentage = String.format("%.2f%%", percent);
                    
                    sqls += "<tr><td>&nbsp;</td>"
                            + "<td>"+rs.getString("Code").replace(".0", "")+"</td>"
                            + "<td>"+rs.getString("Description")+"</td>"
                            + "<td style='text-align:right;'>"+rs.getString("Amount")+"</td>"
                            + "<td style='text-align:right;'>"+formattedPercentage+"</td>"
                            + "</tr>";

                }

                String sql1="";
                Double rev = null;
                Double exp = null;
                if(request.getParameter("action").equals("filter"))
                {
                    switch (f) {
                        case "Branch":
                            sql1 = "SELECT ROUND(SUM(f.total_amount), 2) AS Revenue "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%') "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"';";
                            break;
                        case "Region":
                            sql1 = "SELECT ROUND(SUM(f.total_amount), 2) AS Revenue "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%') "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"';";
                            break;
//                        case "Area":
//                            sql1 = "SELECT ROUND(SUM(f.total_amount), 2) AS Revenue "
//                                    + "FROM fs f "
//                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%') "
//                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"';";
//                            break;
                        default:
                            if(z.equals("ALL"))
                            {
                                sql1 = "SELECT ROUND(SUM(f.total_amount), 2) AS Revenue "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%') "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"';";
                            }
                            else
                            {
                                sql1 = "SELECT ROUND(SUM(f.total_amount), 2) AS Revenue "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%') "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"';";
                            }   break;
                    }
                }
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                pst.setFetchSize(5000);
                if(rs.next())
                {
                    rev = rs.getDouble("Revenue");
                    sqls +="<tr><td colspan='3'><b>Revenue</b></td><td style='text-align:right;font-weight: bold;'>"+String.format("%,.2f",rev)+"</td></tr>";
                }

                String sql2="";
                if(request.getParameter("action").equals("filter"))
                {
                    switch (f) {
                        case "Branch":
                            sql2 = "SELECT gl.code as Code, gl.description, FORMAT(w.Amount,2) as Amount, FORMAT(revenue.Totalexp,2) as Totalexp "
                                + "FROM codes gl "
                                + "LEFT JOIN ("
                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) AS Amount "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101') "
                                    + "GROUP BY f.code_id"
                                + ") w ON w.code_id = gl.code_id "
                                + "LEFT JOIN ("
                                    + "SELECT ROUND(SUM(f.total_amount),2) AS Totalexp "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%')"
                                + ") revenue ON 1=1 "
                                + "WHERE gl.code BETWEEN '500001' AND '611101' "
                                + "AND w.Amount IS NOT NULL "
                                + "GROUP BY gl.code_id "
                                + "ORDER BY gl.code_id;";

                            break;
                        case "Region":
                            sql2 = "SELECT gl.code as Code, gl.description, FORMAT(w.Amount,2) as Amount, FORMAT(revenue.Totalexp,2) as Totalexp "
                                + "FROM codes gl "
                                + "LEFT JOIN ("
                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) AS Amount "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101') "
                                    + "GROUP BY f.code_id"
                                + ") w ON w.code_id = gl.code_id "
                                + "LEFT JOIN ("
                                    + "SELECT ROUND(SUM(f.total_amount),2) AS Totalexp "
                                    + "FROM fs f "
                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' "
                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%')"
                                + ") revenue ON 1=1 "
                                + "WHERE gl.code BETWEEN '500001' AND '611101' "
                                + "AND w.Amount IS NOT NULL "
                                + "GROUP BY gl.code_id "
                                + "ORDER BY gl.code_id;";
                            break;
//                        case "Area":
//                            sql2 = "SELECT gl.code as Code, gl.description, FORMAT(w.Amount,2) as Amount, FORMAT(revenue.Totalexp,2) as Totalexp "
//                                + "FROM codes gl "
//                                + "LEFT JOIN ("
//                                    + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) AS Amount "
//                                    + "FROM fs f "
//                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101') "
//                                    + "GROUP BY f.code_id"
//                                + ") w ON w.code_id = gl.code_id "
//                                + "LEFT JOIN ("
//                                    + "SELECT ROUND(SUM(f.total_amount),2) AS Totalexp "
//                                    + "FROM fs f "
//                                    + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                    + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                    + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                    + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%')"
//                                + ") revenue ON 1=1 "
//                                + "WHERE gl.code BETWEEN '500001' AND '611101' "
//                                + "AND w.Amount IS NOT NULL "
//                                + "GROUP BY gl.code_id "
//                                + "ORDER BY gl.code_id;";
//                            break;
                        default:
                            if(z.equals("ALL"))
                            {
                                
                                sql2 = "SELECT gl.code as Code, gl.description, FORMAT(w.Amount,2) as Amount, FORMAT(revenue.Totalexp,2) as Totalexp "
                                    + "FROM codes gl "
                                    + "LEFT JOIN ("
                                        + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) AS Amount "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101') "
                                        + "GROUP BY f.code_id"
                                    + ") w ON w.code_id = gl.code_id "
                                    + "LEFT JOIN ("
                                        + "SELECT ROUND(SUM(f.total_amount),2) AS Totalexp "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%')"
                                    + ") revenue ON 1=1 "
                                    + "WHERE gl.code BETWEEN '500001' AND '611101' "
                                    + "AND w.Amount IS NOT NULL "
                                    + "GROUP BY gl.code_id "
                                    + "ORDER BY gl.code_id;";
                                
//                                sql2 = "SELECT gl.GLCode as Code,gl.Description,FORMAT(IFNULL((w.Amount),0),2) as Amount from gl LEFT JOIN "
//                                + "(Select GLCode,ROUND(SUM(amount),2)Amount from fstable WHERE Category <> 'Balance' AND DATE_FORMAT(Date,'%m') BETWEEN '"+m1+"' AND '"+m2+"' AND DATE_FORMAT(Date,'%Y')='"+yy+"' GROUP BY GLCode) "
//                                + "w ON w.GLCode=gl.GLCode WHERE gl.GLCode BETWEEN '5000001' AND '6000001' AND w.Amount <>0 GROUP BY gl.GLCode ORDER BY gl.GLCode"; 
                            }
                            else
                            {
                                sql2 = "SELECT gl.code as Code, gl.description, FORMAT(w.Amount,2) as Amount, FORMAT(revenue.Totalexp,2) as Totalexp "
                                    + "FROM codes gl "
                                    + "LEFT JOIN ("
                                        + "SELECT f.code_id, ROUND(SUM(f.total_amount),2) AS Amount "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101') "
                                        + "GROUP BY f.code_id"
                                    + ") w ON w.code_id = gl.code_id "
                                    + "LEFT JOIN ("
                                        + "SELECT ROUND(SUM(f.total_amount),2) AS Totalexp "
                                        + "FROM fs f "
                                        + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                        + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                        + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                        + "AND f.code_id IN (SELECT code_id FROM codes WHERE code LIKE '4%')"
                                    + ") revenue ON 1=1 "
                                    + "WHERE gl.code BETWEEN '500001' AND '611101' "
                                    + "AND w.Amount IS NOT NULL "
                                    + "GROUP BY gl.code_id "
                                    + "ORDER BY gl.code_id;";
                                
                                
                                
//                                sql2 = "SELECT gl.GLCode as Code,gl.Description,FORMAT(IFNULL((w.Amount),0),2) as Amount from gl LEFT JOIN "
//                                + "(Select GLCode,ROUND(SUM(amount),2)Amount from fstable WHERE Category <> 'Balance' AND Zone='"+z+"' AND DATE_FORMAT(Date,'%m') BETWEEN '"+m1+"' AND '"+m2+"' AND DATE_FORMAT(Date,'%Y')='"+yy+"' GROUP BY GLCode) "
//                                + "w ON w.GLCode=gl.GLCode WHERE gl.GLCode BETWEEN '5000001' AND '6000001' AND w.Amount <>0 GROUP BY gl.GLCode ORDER BY gl.GLCode"; 
                            }
                            break;
                    }
                }
                pst = conn.prepareStatement(sql2);
                rs = pst.executeQuery();
                pst.setFetchSize(5000);
                while(rs.next())
                {
                    String amountStr = rs.getString("Amount");
                        double amount = Double.parseDouble(amountStr.replace(",", ""));
                        String total = rs.getString("Totalexp");
                        double totals = Double.parseDouble(total.replace(",", ""));
                    
                    
                    //Double amount = rs.getDouble("Amount");
                    
                    Double percent = amount / totals * 100;
                    String formattedPercentage = String.format("%.2f%%", percent);

                    sqls += "<tr><td>&nbsp;</td>"
                            + "<td>"+rs.getString("Code").replace(".0", "")+"</td>"
                            + "<td>"+rs.getString("Description")+"</td>"
                            + "<td style='text-align:right;'>"+rs.getString("Amount")+"</td>"
                            + "<td style='text-align:right;'>"+formattedPercentage+"</td>"
                            + "</tr>";

                }
                String sql3="";
                if(request.getParameter("action").equals("filter"))
                {
                    switch (f) {
                        case "Branch":
                            sql3 = "SELECT ROUND(SUM(f.total_amount),2) AS Expense "
                                + "FROM fs f "
                                + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.branch_id = '"+b+"' "
                                + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101');";

                            break;
                        case "Region":
                            sql3 = "SELECT ROUND(SUM(f.total_amount),2) AS Expense "
                                + "FROM fs f "
                                + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' "
                                + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101');";
                            break;
//                        case "Area":
//                            sql3 = "SELECT ROUND(SUM(f.total_amount),2) AS Expense "
//                                + "FROM fs f "
//                                + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
//                                + "WHERE f.category <> 'Balance' AND f.region_id = '"+r+"' AND f.area_id = '"+area+"' "
//                                + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
//                                + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101');";
//                            break;
                        default:
                            if(z.equals("ALL"))
                            {
                                sql3 = "SELECT ROUND(SUM(f.total_amount),2) AS Expense "
                                + "FROM fs f "
                                + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                + "WHERE f.category <> 'Balance' "
                                + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101');";
                            }
                            else
                            {
                                sql3 = "SELECT ROUND(SUM(f.total_amount),2) AS Expense "
                                + "FROM fs f "
                                + "INNER JOIN vw_new_branch_record v ON v.BranchID = f.branch_id "
                                + "WHERE f.category <> 'Balance' AND f.zone_id = '"+z+"' "
                                + "AND f.date BETWEEN '"+startDate+"' AND '"+endDate+"' "
                                + "AND f.code_id IN (SELECT code_id FROM codes WHERE code BETWEEN '500001' AND '611101');";
                            }   break;
                    }
                }
                pst = conn.prepareStatement(sql3);
                rs = pst.executeQuery();
                pst.setFetchSize(5000);
                if(rs.next())
                {
                    exp = rs.getDouble("Expense");
                    sqls +="<tr><td colspan='3'><b>Expense</b></td>"
                            + "<td style='text-align:right;font-weight: bold;'>"+String.format("%,.2f",exp)+"</td>"
                            + "<td style='text-align:right;'></td>"
                            + "</tr>";
                }
                if(request.getParameter("action").equals("filter"))
                {
                    Double net = rev-exp;
                    if(net<0.00)
                    {
                        String net_income =String.valueOf(net).replace("-","").trim();
                        Double final_net = Double.valueOf(net_income);
                        sqls +="<tr><td colspan='3'><b>NET Income</b></td><td style='text-align:right;font-weight: bold;color:red;'>("+String.format("%,.2f",final_net)+")</td></tr>";
                    }
                    else
                    {
                        sqls +="<tr><td colspan='3'><b>NET Income</b></td><td style='text-align:right;font-weight: bold;'>"+String.format("%,.2f",net)+"</td></tr>";
                    }
                }
                write.print(sqls);
        }
        catch(NumberFormatException | SQLException e)
        {
            write.print("<tr><td class='alert alert-danger alert-dismissible fade in' colspan='4'>"
                                + "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
                                + "Sorry. Unable to process data. Please contact IT Support immediately. Issue : "+e.fillInStackTrace()
                                + "</td></tr>");
        }
        finally
        {
            if (rs != null) try { rs.close(); } catch (SQLException e) {e.printStackTrace();}
            if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
            if (conn != null) try { conn.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


}
