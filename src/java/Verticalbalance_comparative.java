import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.YearMonth;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Verticalbalance_comparative")
public class Verticalbalance_comparative extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter write = response.getWriter();
        
        Connection conn = null;
        PreparedStatement pstRev = null;
        PreparedStatement pstExp = null;
        ResultSet rsRev = null;
        ResultSet rsExp = null;
        
        String m1 = request.getParameter("month1");
        String yy = request.getParameter("year1");
        String m2 = request.getParameter("month2");
        String yy2 = request.getParameter("year2");
        String m3 = request.getParameter("month3");
        String yy3 = request.getParameter("year3");
        String z = request.getParameter("zone");
        String r = request.getParameter("region");
        String b = request.getParameter("branch");
        String f = request.getParameter("group");
        
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
        
        try
        {
            StringBuilder sqls = new StringBuilder();
            Double rev3 = 0.0, rev2 = 0.0, rev1 = 0.0;
            Double exp3 = 0.0, exp2 = 0.0, exp1 = 0.0;
            Double exp3p = 0.0, exp2p = 0.0, exp1p = 0.0;
            Double rev3p = 0.0, rev2p = 0.0, rev1p = 0.0;
            
            conn = matic_fs.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("SET sql_mode = ''");
                
            String sql = "CALL VbalanceRevenue(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstRev = conn.prepareStatement(sql);
            pstRev.setString(1, r);
            pstRev.setString(2, b);
            pstRev.setString(3, f);
            pstRev.setString(4, z);
            pstRev.setString(5, startDate);
            pstRev.setString(6, endDate);
            pstRev.setString(7, startDate2);
            pstRev.setString(8, endDate2);
            pstRev.setString(9, startDate3);
            pstRev.setString(10, endDate3);
            
            rsRev = pstRev.executeQuery();
            
            while (rsRev.next()) {
                String code = rsRev.getString("Code").replace(".0", "");
                String description = rsRev.getString("Description");
                String amountM1 = rsRev.getString("AmountM1");
                String amountM2 = rsRev.getString("AmountM2");
                String amountM3 = rsRev.getString("AmountM3");
                String totalRevenue1 = rsRev.getString("TotalRevenue1");
                String totalRevenue2 = rsRev.getString("TotalRevenue2");
                String totalRevenue3 = rsRev.getString("TotalRevenue3");

                double amount1 = roundToTwoDecimals(Double.parseDouble(amountM1.replace(",", "")));
                double amount2 = roundToTwoDecimals(Double.parseDouble(amountM2.replace(",", "")));
                double amount3 = roundToTwoDecimals(Double.parseDouble(amountM3.replace(",", "")));
                double total1 = Double.parseDouble(totalRevenue1.replace(",", ""));
                double total2 = Double.parseDouble(totalRevenue2.replace(",", ""));
                double total3 = Double.parseDouble(totalRevenue3.replace(",", ""));

                double percent1 = (total1 != 0) ? (amount1 / total1) * 100 : 0.0;
                double percent2 = (total2 != 0) ? (amount2 / total2) * 100 : 0.0;
                double percent3 = (total3 != 0) ? (amount3 / total3) * 100 : 0.0;

                if (Double.isNaN(percent1) || Double.isInfinite(percent1)) {
                    percent1 = 0.0;
                }
                if (Double.isNaN(percent2) || Double.isInfinite(percent2)) {
                    percent2 = 0.0;
                }
                if (Double.isNaN(percent3) || Double.isInfinite(percent3)) {
                    percent3 = 0.0;
                }

                String formattedAmount3 = formatAmountWithColor(amount3);
                String formattedAmount2 = formatAmountWithColor(amount2);
                String formattedAmount1 = formatAmountWithColor(amount1);
                String formattedPercent1 = formatPercentWithColor(percent1);
                String formattedPercent2 = formatPercentWithColor(percent2);
                String formattedPercent3 = formatPercentWithColor(percent3);
    
                sqls.append("<tr><td>&nbsp;</td>")
                    .append("<td>").append(code).append("</td>")
                    .append("<td>").append(description).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedAmount3).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedPercent3).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedAmount2).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedPercent2).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedAmount1).append("</td>")
                    .append("<td style='text-align:right;'>").append(formattedPercent1).append("</td>")
                    .append("</tr>");
              
                rev3 += amount3;
                rev2 += amount2;
                rev1 += amount1;
                
                rev3p = (rev3 / rev3) * 100;
                rev2p = (rev2 / rev2) * 100;
                rev1p = (rev1 / rev1) * 100;
                if (Double.isNaN(rev3p) || Double.isInfinite(rev3p)) {
                    rev3p = 0.0;
                }
                if (Double.isNaN(rev2p) || Double.isInfinite(rev2p)) {
                    rev2p = 0.0;
                }
                if (Double.isNaN(rev1p) || Double.isInfinite(rev1p)) {
                    rev1p = 0.0;
                }
            } 
                sqls.append("<tr><td><b>Revenue</b></td>")
                    .append("<td></td>")
                    .append("<td></td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(rev3)).append("</td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercentWithColor(rev3p)).append("</td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(rev2)).append("</td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercentWithColor(rev2p)).append("</td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(rev1)).append("</td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercentWithColor(rev1p)).append("</td>")
                    .append("</tr>");
            
                String sql1 = "CALL VbalanceExpense(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstExp = conn.prepareStatement(sql1);
                pstExp.setString(1, r);
                pstExp.setString(2, b);
                pstExp.setString(3, f);
                pstExp.setString(4, z);
                pstExp.setString(5, startDate);
                pstExp.setString(6, endDate);
                pstExp.setString(7, startDate2);
                pstExp.setString(8, endDate2);
                pstExp.setString(9, startDate3);
                pstExp.setString(10, endDate3);

                rsExp = pstExp.executeQuery();

                while (rsExp.next()) {
                    String code = rsExp.getString("Code").replace(".0", "");
                    String description = rsExp.getString("Description");
                    String amountStr1 = rsExp.getString("Amount_m1");
                    String amountStr2 = rsExp.getString("Amount_m2");
                    String amountStr3 = rsExp.getString("Amount_m3");
                    String tot1 = rsExp.getString("Totalexp_m1");
                    String tot2 = rsExp.getString("Totalexp_m2");
                    String tot3 = rsExp.getString("Totalexp_m3");
                    
                    double amounts1 = roundToTwoDecimals(Double.parseDouble(amountStr1.replace(",", "")));
                    double amounts2 = roundToTwoDecimals(Double.parseDouble(amountStr2.replace(",", "")));
                    double amounts3 = roundToTwoDecimals(Double.parseDouble(amountStr3.replace(",", "")));
                    
                    double totals1 = Double.parseDouble(tot1.replace(",", ""));
                    double totals2 = Double.parseDouble(tot2.replace(",", ""));
                    double totals3 = Double.parseDouble(tot3.replace(",", ""));
                    
                    double percents1 = (totals1 != 0) ? (amounts1 / rev1) * 100 : 0.0;
                    double percents2 = (totals2 != 0) ? (amounts2 / rev2) * 100 : 0.0;
                    double percents3 = (totals3 != 0) ? (amounts3 / rev3) * 100 : 0.0;
                    
                    if (Double.isNaN(percents1) || Double.isInfinite(percents1)) {
                        percents1 = 0.0;
                    }
                    if (Double.isNaN(percents2) || Double.isInfinite(percents2)) {
                        percents2 = 0.0;
                    }
                    if (Double.isNaN(percents3) || Double.isInfinite(percents3)) {
                        percents3 = 0.0;
                    }
                    
                    String formattedAmountStr3 = formatAmount1WithColor(amounts3);
                    String formattedAmountStr2 = formatAmount1WithColor(amounts2);
                    String formattedAmountStr1 = formatAmount1WithColor(amounts1);
                    String formattedPercentage1 = formatPercent1WithColor(percents1);
                    String formattedPercentage2 = formatPercent1WithColor(percents2);
                    String formattedPercentage3 = formatPercent1WithColor(percents3);
                
                    sqls.append("<tr><td>&nbsp;</td>")
                        .append("<td>").append(code).append("</td>")
                        .append("<td>").append(description).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedAmountStr3).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedPercentage3).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedAmountStr2).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedPercentage2).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedAmountStr1).append("</td>")
                        .append("<td style='text-align:right;'>").append(formattedPercentage1).append("</td>")
                        .append("</tr>");
              
                exp3 += amounts3;
                exp2 += amounts2;
                exp1 += amounts1;
                
                exp3p = (exp3 / rev3) * 100;
                exp2p = (exp2 / rev2) * 100;
                exp1p = (exp1 / rev1) * 100;
                if (Double.isNaN(exp3p) || Double.isInfinite(exp3p)) {
                    exp3p = 0.0;
                }
                if (Double.isNaN(exp2p) || Double.isInfinite(exp2p)) {
                    exp2p = 0.0;
                }
                if (Double.isNaN(exp1p) || Double.isInfinite(exp1p)) {
                    exp1p = 0.0;
                }
                }
                sqls.append("<tr><td><b>Expense</b></td>")
                        .append("<td></td>")
                        .append("<td></td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(exp3)).append("</td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercent1WithColor(exp3p)).append("</td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(exp2)).append("</td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercent1WithColor(exp2p)).append("</td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatAmountWithCommas(exp1)).append("</td>")
                        .append("<td style='text-align:right;font-weight: bold;'>").append(formatPercent1WithColor(exp1p)).append("</td>")
                        .append("</tr>");
               Double net1 = rev1-exp1;
               Double net2 = rev2-exp2;
               Double net3 = rev3-exp3;

               String netIncome1 = formatAmountWithColor(net1);
               String netIncome2 = formatAmountWithColor(net2);
               String netIncome3 = formatAmountWithColor(net3);

                sqls.append("<tr><td><b>Net Income</b></td>")
                    .append("<td></td>")
                    .append("<td></td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(netIncome3).append("</td>")
                    .append("<td style='text-align:right;'></td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(netIncome2).append("</td>")
                    .append("<td style='text-align:right;'></td>")
                    .append("<td style='text-align:right;font-weight: bold;'>").append(netIncome1).append("</td>")
                    .append("<td style='text-align:right;'></td>")
                    .append("</tr>");

                write.print(sqls);
        }catch (SQLException | NumberFormatException e) {
            write.print("<tr><td class='alert alert-danger alert-dismissible fade in' colspan='4'>"
                    + "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>"
                    + "Sorry. Unable to process data. Please contact IT Support immediately. Issue : " + e.getMessage()
                    + "</td></tr>");
            e.printStackTrace();
        } finally {
            if (rsExp != null) {
                try {
                    rsExp.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstExp != null) {
                try {
                    pstExp.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } if (rsRev != null) {
                try {
                    rsRev.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstRev != null) {
                try {
                    pstRev.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    private String formatAmountWithCommas(double amount) {
            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            return formatter.format(amount);
        }
    private String formatPercentWithColor(double percent) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (percent < 0) {
            return "<font color='red'>(" + formatter.format(-percent) + "%)</font>";
        } else {
            return formatter.format(percent) + "%";
        }
    }
    private String formatAmount1WithColor(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (amount < 0) {
            return "<font color='red'>(" + formatter.format(-amount) + ")</font>";
        } else {
            return formatter.format(amount);
        }
    }

    private String formatPercent1WithColor(double percent) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        if (percent < 0) {
            return "<font color='red'>(" + formatter.format(-percent) + "%)</font>";
        } else {
            return formatter.format(percent) + "%";
        }
    }
    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}