import db.matic_fs;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.YearMonth;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/populate")
public class populate extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        String type = request.getParameter("type");
        String zoneId;
        String region;
        String m1 = request.getParameter("monthStart");
        String yy = request.getParameter("year");

        Integer month = null;
        Integer year = null;

        String startDate = null;
        String endDate = null;

        if (m1 != null && yy != null && !m1.isEmpty() && !yy.isEmpty()) {
            month = Integer.valueOf(m1);
            year = Integer.valueOf(yy);

            YearMonth ym = YearMonth.of(year, month);

            startDate = String.format("%04d-%02d-01", year, month);
            endDate = String.format("%04d-%02d-%02d", year, month, ym.lengthOfMonth());
        }

        try (Connection conn = matic_fs.getConnection();
             PrintWriter out = response.getWriter()) {

            PreparedStatement ps;
            ResultSet rs;
            
            switch (request.getParameter("data")) {
                case "asset":
                    type = request.getParameter("type");
                    zoneId = request.getParameter("zoneId");
                    region = request.getParameter("regionId");

                    if ("zones".equals(type)) {

                        ps = conn.prepareStatement("SELECT zone_id, name FROM zone ORDER BY zone_id");
                        rs = ps.executeQuery();
                        out.println("<option value='ALL'>All</option>");
                        while (rs.next()) {
                            out.println("<option value='" + rs.getString(1) + "'>"+ rs.getString(2) + "</option>");
                        }
                    } else if ("regions".equals(type) && zoneId != null && !zoneId.isEmpty()) {

                        ps = conn.prepareStatement("SELECT region_id, name FROM region WHERE zone_id = ? ORDER BY name");
                        ps.setString(1, zoneId);
                        rs = ps.executeQuery();
                        out.println("<option value='ALL'>All</option>");
                        while (rs.next()) {
                            out.println("<option value='" + rs.getString(1) + "'>"+ rs.getString(2) + "</option>");
                        }
                    } else if ("branches".equals(type) && region != null && !region.isEmpty()) {

                        ps = conn.prepareStatement("SELECT b.branch_id, b.name FROM branches b INNER JOIN area a ON a.area_id = b.area_id "
                          + "INNER JOIN region r ON r.region_id = a.region_id WHERE r.region_id = ? ORDER BY b.name"
                        );
                        ps.setString(1, region);
                        rs = ps.executeQuery();
                        out.println("<option value=''>All</option>");
                        while (rs.next()) {
                            out.println("<option value='" + rs.getString(1) + "'>" + rs.getString(2) + "</option>");
                        }

                    } else if ("request".equals(type) && region != null && !region.isEmpty()) {
                        System.out.println("[DEBUG] Region received: " + region);
                        ps = conn.prepareStatement(
                        "SELECT `desc` FROM fs WHERE region_id = ? and date between ? and ? Group by `desc` ORDER BY `desc`"
                        );
                        ps.setString(1, region);
                        ps.setString(2, startDate);
                        ps.setString(3, endDate);
                        rs = ps.executeQuery();
                        out.println("<option value=''>Request List</option>");
                        while (rs.next()) {
                            out.println("<option value='" + rs.getString(1) + "'>" + rs.getString(1) + "</option>");
                        }

                    }
                    else {
                        return;
                    }

                    rs.close();
                    ps.close();
                    break;

                case "ho":
                    StringBuilder htmlRows = new StringBuilder();

                    String zoneho = request.getParameter("zoneId");
                    String regionId = request.getParameter("regionId");
                    String areaID = request.getParameter("areaID");
                    String codeID = request.getParameter("codeID");

                    if ("zones".equals(type)) {
                        ps = conn.prepareStatement("SELECT zone_id, name FROM zone ORDER BY zone_id");
                        rs = ps.executeQuery();
                        while(rs.next()){
                            htmlRows.append("<tr>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                    .append(rs.getString("zone_id"))
                                    .append("</td>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc;'>")
                                    .append(rs.getString("name"))
                                    .append("</td>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                    .append("<button class='selectItem' data-type='zones' style='padding:5px 10px; background:#28a745; color:white; border:none; border-radius:4px; cursor:pointer;'>Select</button>")
                                    .append("</td>");
                            htmlRows.append("</tr>");
                        }
                    }else if ("regions".equals(type) && zoneho != null && !zoneho.isEmpty()) {
                        ps = conn.prepareStatement("SELECT region_id, name FROM region WHERE zone_id = ? ORDER BY region_id");
                        ps.setString(1, zoneho);
                        rs = ps.executeQuery();
                        while(rs.next()){
                            htmlRows.append("<tr>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                    .append(rs.getString("region_id"))
                                    .append("</td>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc;'>")
                                    .append(rs.getString("name"))
                                    .append("</td>");
                            htmlRows.append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                    .append("<button class='selectItem' data-type='regions' style='padding:5px 10px; background:#28a745; color:white; border:none; border-radius:4px; cursor:pointer;'>Select</button>")
                                    .append("</td>");
                            htmlRows.append("</tr>");
                        }
                    }else if ("areas".equals(type) && regionId != null && !regionId.isEmpty()) {
                        ps = conn.prepareStatement("SELECT area_id, name FROM area WHERE region_id = ? ORDER BY area_id");
                        ps.setString(1, regionId);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            htmlRows.append("<tr>")
                                .append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                .append(rs.getString("area_id")).append("</td>")
                                .append("<td style='padding:8px; border:1px solid #ccc;'>")
                                .append(rs.getString("name")).append("</td>")
                                .append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                .append("<button class='selectItem' data-type='areas' style='padding:5px 10px; background:#28a745; color:white; border:none; border-radius:4px; cursor:pointer;'>Select</button>")
                                .append("</td></tr>");
                        }
                    }else if ("branchCodes".equals(type) && areaID != null && !areaID.isEmpty()) {
                        ps = conn.prepareStatement("SELECT new_branch_code, name FROM branches WHERE area_id = ? ORDER BY new_branch_code");
                        ps.setString(1, areaID);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            htmlRows.append("<tr>")
                                .append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                .append(rs.getString("new_branch_code")).append("</td>")
                                .append("<td style='padding:8px; border:1px solid #ccc;'>")
                                .append(rs.getString("name")).append("</td>")
                                .append("<td style='padding:8px; border:1px solid #ccc; text-align:center;'>")
                                .append("<button class='selectItem' data-type='branchCodes' style='padding:5px 10px; background:#28a745; color:white; border:none; border-radius:4px; cursor:pointer;'>Select</button>")
                                .append("</td></tr>");
                        }
                    }

                    response.setContentType("text/html");
                    response.getWriter().write(htmlRows.toString());
                    break;
                case "cc":
                    String reg = request.getParameter("region");
                    String zone = request.getParameter("zone");
                    String b_ID = request.getParameter("asset_branch_id");
                    String sql = "SELECT UPPER(Branch) as Branch FROM vw_new_branch_record WHERE Zone = ? AND Region = ? AND BranchCode = ? AND Status <> 1";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, zone);
                    ps.setString(2, reg);
                    ps.setString(3, b_ID);
                    rs = ps.executeQuery();
                    if(rs.next())
                    {
                        out.print(rs.getString("Branch"));
                    } else
                    {
                        out.print("No record Found");
                    }
                    rs.close();
                    ps.close();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}