import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.YearMonth;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Levelbalance_comparative")
public class Levelbalance_comparative extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();

        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        String m1 = request.getParameter("month1");
        String yy = request.getParameter("year1");
        String m2 = request.getParameter("month2");
        String yy2 = request.getParameter("year2");
        String m3 = request.getParameter("month3");
        String yy3 = request.getParameter("year3");
        String z = request.getParameter("zone");
        String r = request.getParameter("region");
        String b = request.getParameter("branch");
        String c = request.getParameter("code");
        String f = request.getParameter("group");
        String level = request.getParameter("level");

        int month = Integer.parseInt(m1);
        int year = Integer.parseInt(yy);
        YearMonth ym = YearMonth.of(year, month);
        String startDate = String.format("%04d-%02d-01", year, month);
        String endDate = String.format("%04d-%02d-%02d", year, month, ym.lengthOfMonth());
        
        int month2 = Integer.parseInt(m2);
        int year2 = Integer.parseInt(yy2);
        YearMonth ym2 = YearMonth.of(year2, month2);
        String startDate2 = String.format("%04d-%02d-01", year2, month2);
        String endDate2 = String.format("%04d-%02d-%02d", year2, month2, ym2.lengthOfMonth());
        
        int month3 = Integer.parseInt(m3);
        int year3 = Integer.parseInt(yy3);
        YearMonth ym3 = YearMonth.of(year3, month3);
        String startDate3 = String.format("%04d-%02d-01", year3, month3);
        String endDate3 = String.format("%04d-%02d-%02d", year3, month3, ym3.lengthOfMonth());
        
        try {
            Double totalExp3 = 0.0;
            Double totalExp2 = 0.0;
            Double totalExp1 = 0.0;
            Double totalRev3 = 0.0;
            Double totalRev2 = 0.0;
            Double totalRev1 = 0.0;
            Double ltotalRev3 = 0.0;
            Double ltotalRev2 = 0.0;
            Double ltotalRev1 = 0.0;
            Double totalVs2 = 0.0;
            Double totalPercent2 = 0.0;
            Double totalVs1 = 0.0;
            Double totalPercent1 = 0.0;
            
            conn = matic_fs.getConnection();
            
            String sqlQuery = "{CALL LevelbalanceRevenue(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = conn.prepareCall(sqlQuery);
            cs.setString(1, f);
            cs.setString(2, r);
            cs.setString(3, b);
            cs.setString(4, startDate);
            cs.setString(5, endDate);
            cs.setString(6, startDate2);
            cs.setString(7, endDate2);
            cs.setString(8, startDate3);
            cs.setString(9, endDate3);
            cs.setString(10, level);
            cs.setString(11, z);

            rs = cs.executeQuery();

            while (rs.next()) {
                double rev1 = rs.getDouble("Expense_m1");
                double rev2 = rs.getDouble("Expense_m2");
                double rev3 = rs.getDouble("Expense_m3");

                double revpercent2 = 0.0;
                double revpercent1 = 0.0;
                
                double revvs2 = rev3 - rev2;
                double revvs1 = rev3 - rev1;
                revpercent2 = (revvs2 != 0) ? (revvs2 / rev2) * 100 : 0.0;
                revpercent1 = (revvs1 != 0) ? (revvs1 / rev1) * 100 : 0.0;

                if (Double.isNaN(revpercent2) || Double.isInfinite(revpercent2)) {
                    revpercent2 = 0.0;
                }
                if (Double.isNaN(revpercent1) || Double.isInfinite(revpercent1)) {
                    revpercent1 = 0.0;
                }
                String formattedRev1 = formatAmountWithColor(rev1);
                String formattedRev2 = formatAmountWithColor(rev2);
                String formattedRev3 = formatAmountWithColor(rev3);
                String vs1Formatted = formatAmountWithColor(revvs1);
                String vs2Formatted = formatAmountWithColor(revvs2);
                String pc2Formatted = formatPercentWithColor(revpercent2);
                String pc1Formatted = formatPercentWithColor(revpercent1);

                if (null != level) switch (level) {
                    case "1":
                        write.print("<tr>"
                                + "<td style='text-align:right;font-weight: bold;'></td>"
                                + "<td>" + rs.getString("name") + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev3 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev2 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev1 + "</td>"
                                + "<td style='text-align:right;'>" + vs2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + vs1Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc1Formatted + "</td>"
                                + "</tr>");
                        break;
                    case "2":
                        write.print("<tr>"
                                + "<td style='text-align:right;font-weight: bold;'></td>"
                                + "<td>" + rs.getString("name") + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev3 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev2 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev1 + "</td>"
                                + "<td style='text-align:right;'>" + vs2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + vs1Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc1Formatted + "</td>"
                                + "</tr>");
                        break;
                    case "3":
                        write.print("<tr>"
                                + "<td style='text-align:right;font-weight: bold;'></td>"
                                + "<td>" + rs.getString("name") + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev3 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev2 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev1 + "</td>"
                                + "<td style='text-align:right;'>" + vs2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + vs1Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc1Formatted + "</td>"
                                + "</tr>");
                        break;
                    case "4":
                        write.print("<tr>"
                                + "<td style='text-align:right;font-weight: bold;'></td>"
                                + "<td>" + rs.getString("name") + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev3 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev2 + "</td>"
                                + "<td style='text-align:right;'>" + formattedRev1 + "</td>"
                                + "<td style='text-align:right;'>" + vs2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc2Formatted + "</td>"
                                + "<td style='text-align:right;'>" + vs1Formatted + "</td>"
                                + "<td style='text-align:right;'>" + pc1Formatted + "</td>"
                                + "</tr>");
                        break;
                    default:
                        break;
                }
                totalRev3 += rev3;
                totalRev2 += rev2;
                totalRev1 += rev1;
                totalVs2 += revvs2;
                totalPercent2 += revpercent2;
                totalVs1 += revvs1;
                totalPercent1 += revpercent1;
            }

            write.print("<tr><td><b>Total Revenue</b></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalRev3) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalRev2) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalRev1) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "</tr>");
            
            //cost of sales
            if (level.equals("2") || "3".equals(level)|| "4".equals(level)) {
                String sqlQuery2 = "{CALL LevelbalanceCost(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
                cs = conn.prepareCall(sqlQuery2);
                cs.setString(1, f);
                cs.setString(2, r);
                cs.setString(3, b);
                cs.setString(4, startDate);
                cs.setString(5, endDate);
                cs.setString(6, startDate2);
                cs.setString(7, endDate2);
                cs.setString(8, startDate3);
                cs.setString(9, endDate3);
                cs.setString(10, level);
            cs.setString(11, z);

                rs = cs.executeQuery();

                double total3 = 0.0;
                double total2 = 0.0;
                double total1 = 0.0;
            while (rs.next()) {
                double lrev1 = rs.getDouble("Expense_m1");
                double lrev2 = rs.getDouble("Expense_m2");
                double lrev3 = rs.getDouble("Expense_m3");

                double lrevvs2 = 0.0;
                double lrevvs1 = 0.0;
                double lrevpercent2 = 0.0;
                double lrevpercent1 = 0.0;

                lrevvs2 = lrev3 - lrev2;
                lrevvs1 = lrev3 - lrev1;
                lrevpercent2 = (lrevvs2 != 0) ? (lrevvs2 / lrev2) * 100 : 0.0;
                lrevpercent1 = (lrevvs1 != 0) ? (lrevvs1 / lrev1) * 100 : 0.0;

                if (Double.isNaN(lrevpercent2) || Double.isInfinite(lrevpercent2)) {
                    lrevpercent2 = 0.0;
                }
                if (Double.isNaN(lrevpercent1) || Double.isInfinite(lrevpercent1)) {
                    lrevpercent1 = 0.0;
                }
                String lformattedRev1 = formatAmountWithColor(lrev1);
                String lformattedRev2 = formatAmountWithColor(lrev2);
                String lformattedRev3 = formatAmountWithColor(lrev3);
                String lvs1Formatted = formatAmountWithColor(lrevvs1);
                String lvs2Formatted = formatAmountWithColor(lrevvs2);
                String lpc2Formatted = formatPercentWithColor(lrevpercent2);
                String lpc1Formatted = formatPercentWithColor(lrevpercent1);

                if ("2".equals(level)) {
                    write.print("<tr>"
                            + "<td style='text-align:right;font-weight: bold;'></td>"
                            + "<td>" + rs.getString("name") + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev3 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev2 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev1 + "</td>"
                            + "<td style='text-align:right;'>" + lvs2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lvs1Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc1Formatted + "</td>"
                            + "</tr>");
                } else if ("3".equals(level)) {
                    write.print("<tr>"
                            + "<td style='text-align:right;font-weight: bold;'></td>"
                            + "<td>" + rs.getString("name") + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev3 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev2 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev1 + "</td>"
                            + "<td style='text-align:right;'>" + lvs2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lvs1Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc1Formatted + "</td>"
                            + "</tr>");
                }else if ("4".equals(level)) {
                    write.print("<tr>"
                            + "<td style='text-align:right;font-weight: bold;'></td>"
                            + "<td>" + rs.getString("name") + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev3 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev2 + "</td>"
                            + "<td style='text-align:right;'>" + lformattedRev1 + "</td>"
                            + "<td style='text-align:right;'>" + lvs2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc2Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lvs1Formatted + "</td>"
                            + "<td style='text-align:right;'>" + lpc1Formatted + "</td>"
                            + "</tr>");
                }
                total3 += lrev3;
                total2 += lrev2;
                total1 += lrev1;
                }
                
                ltotalRev3 = totalRev3 - total3;
                ltotalRev2 = totalRev2 - total2;
                ltotalRev1 = totalRev1 - total1;
    
                write.print("<tr><td><b>Gross Revenue</b></td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", ltotalRev3) + "</td>"
                        + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", ltotalRev2) + "</td>"
                        + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", ltotalRev1) + "</td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "</tr>");

            }
            
            //expenses
            
            String sqlQuery1 = "{CALL LevelbalanceExpense(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            cs = conn.prepareCall(sqlQuery1);
            cs.setString(1, f);
            cs.setString(2, r);
            cs.setString(3, b);
            cs.setString(4, startDate);
            cs.setString(5, endDate);
            cs.setString(6, startDate2);
            cs.setString(7, endDate2);
            cs.setString(8, startDate3);
            cs.setString(9, endDate3);
            cs.setString(10, level);
            cs.setString(11, z);

            rs = cs.executeQuery();

        while (rs.next()) {
            double exp1 = rs.getDouble("Expense_m1");
            double exp2 = rs.getDouble("Expense_m2");
            double exp3 = rs.getDouble("Expense_m3");

            double expvs2 = 0.0;
            double expvs1 = 0.0;
            double exppercent2 = 0.0;
            double exppercent1 = 0.0;

            expvs2 = exp3 - exp2;
            expvs1 = exp3 - exp1;
            exppercent2 = (expvs2 != 0) ? (expvs2 / exp2) * 100 : 0.0;
            exppercent1 = (expvs1 != 0) ? (expvs1 / exp1) * 100 : 0.0;

            if (Double.isNaN(exppercent2) || Double.isInfinite(exppercent2)) {
                exppercent2 = 0.0;
            }
            if (Double.isNaN(exppercent1) || Double.isInfinite(exppercent1)) {
                exppercent1 = 0.0;
            }
            String formattedExp1 = formatAmountWithColor(exp1);
            String formattedExp2 = formatAmountWithColor(exp2);
            String formattedExp3 = formatAmountWithColor(exp3);
            String Expvs1Formatted = formatAmountWithColor(expvs1);
            String Expvs2Formatted = formatAmountWithColor(expvs2);
            String Exppc2Formatted = formatPercentWithColor(exppercent2);
            String Exppc1Formatted = formatPercentWithColor(exppercent1);

            if ("1".equals(level)) {
                write.print("<tr>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td>" + rs.getString("name") + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp3 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp2 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp1 + "</td>"
                        + "<td style='text-align:right;'>" + Expvs2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Expvs1Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc1Formatted + "</td>"
                        + "</tr>");
            } else if ("2".equals(level)) {
                write.print("<tr>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td>" + rs.getString("name") + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp3 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp2 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp1 + "</td>"
                        + "<td style='text-align:right;'>" + Expvs2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Expvs1Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc1Formatted + "</td>"
                        + "</tr>");
            } else if ("3".equals(level)) {
                write.print("<tr>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td>" + rs.getString("name") + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp3 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp2 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp1 + "</td>"
                        + "<td style='text-align:right;'>" + Expvs2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Expvs1Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc1Formatted + "</td>"
                        + "</tr>");
            } else if ("4".equals(level)) {
                write.print("<tr>"
                        + "<td style='text-align:right;font-weight: bold;'></td>"
                        + "<td>" + rs.getString("name") + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp3 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp2 + "</td>"
                        + "<td style='text-align:right;'>" + formattedExp1 + "</td>"
                        + "<td style='text-align:right;'>" + Expvs2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc2Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Expvs1Formatted + "</td>"
                        + "<td style='text-align:right;'>" + Exppc1Formatted + "</td>"
                        + "</tr>");
            }
            totalExp3 += exp3;
            totalExp2 += exp2;
            totalExp1 += exp1;
            }
            
            write.print("<tr><td><b>Total Expenses</b></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalExp3) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalExp2) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + String.format("%,.2f", totalExp1) + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "</tr>");
            
            Double net1 = ltotalRev1 - totalExp1;
            Double net2 = ltotalRev2 - totalExp2;
            Double net3 = ltotalRev3 - totalExp3;
            Double net1a = totalRev1 - totalExp1;
            Double net2a = totalRev2 - totalExp2;
            Double net3a = totalRev3 - totalExp3;
            
            String netIncome1 = formatAmountWithColor(net1);
            String netIncome2 = formatAmountWithColor(net2);
            String netIncome3 = formatAmountWithColor(net3);
            String netIncome1a = formatAmountWithColor(net1a);
            String netIncome2a = formatAmountWithColor(net2a);
            String netIncome3a = formatAmountWithColor(net3a);
            
            if(level.equals("1")){
                write.print("<tr><td><b>Net Income</b></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome3a + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome2a + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome1a + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "</tr>");
            } else{    
                write.print("<tr><td><b>Net Income</b></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome3 + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome2 + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'>" + netIncome1 + "</td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "<td style='text-align:right;font-weight: bold;'></td>"
                    + "</tr>");
            }
        } catch (SQLException | NumberFormatException e) {
            write.print("<tr><td class='alert alert-danger alert-dismissible fade in' colspan='4'>"
                    + "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
                    + "Sorry. Unable to process data. Please contact IT Support immediately. Issue : " + e.getMessage()
                    + "</td></tr>");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String formatPercentWithColor(double percent) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (percent < 0) {
            return "<font color='red'>(" + formatter.format(-percent) + "%)</font>";
        } else {
            return formatter.format(percent) + "%";
        }
    }
    private String formatAmountWithColor(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (amount < 0) {
            return "<font color='red'>(" + formatter.format(-amount) + ")</font>";
        } else {
            return formatter.format(amount);
        }
    }
}
