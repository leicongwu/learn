<%@ page language = "java" pageEncoding="utf-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "javax.sql.*" %>
<%@ page import = "javax.naming.*" %>
<html>
<body>
<%
    String strSql = "    SELECT B.DECL_NO,\n"
            + "           B.GOODS_PLACE,\n"
            + "           B.dcl_io_decl_id ID,\n"
            + "           B.DECL_GET_NO,\n"
            + "           B.ENT_UUID,\n"
            + "           B.DECL_WORK_NO,\n"
            + "           B.DECL_REG_NAME,\n"
            + "           B.DECL_PERSON_NAME,\n"
            + "           B.DECL_DATE,\n"
            + "           B.CONSIGNOR_CNAME,\n"
            + "           B.CONSIGNEE_CNAME,\n"
            + "           B.OPER_CODE,\n"
            + "           B.OPER_TIME,\n"
            + "           B.TRADE_MODE_CODE,\n"
            + "           B.ORG_CODE,\n"
            + "           B.BILL_LAD_NO,\n"
            + "           B.PROCESS_STATUS,\n"
            + "           B.DECL_CODE,\n"
            + "           B.PROCESS_LINK,\n"
            + "           B.TRANS_MODE_CODE,\n"
            + "           B.TRANS_MEAN_NO,\n"
            + "           B.TRADE_COUNTRY_CODE,\n"
            + "           B.ENTRY_ID,\n"
            + "           G.PROD_HS_CODE,\n"
            + "           G.DECL_GOODS_CNAME,\n"
            + "           G.ORI_CTRY_CODE\n"
            + "      FROM DCL_IO_DECL B\n"
            + "      JOIN DCL_IO_DECL_GOODS G\n"
            + "        ON B.DECL_NO = G.DECL_NO\n"
            + "     WHERE B.DECL_TYPE = '2'\n"
            + "       AND G.GOODS_NO = (SELECT MIN(GOODS_NO)\n"
            + "                           FROM DCL_IO_DECL_GOODS T\n"
            + "                          WHERE T.DECL_NO = B.DECL_NO)\n"
            + "       and B.decl_no = '216000000999845'";
    Context ctx = null;
    Context envCtx = null;
    DataSource dsOper = null;
    DataSource dsHis = null;
    Connection conn = null;
    Connection connHis = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        ctx = new InitialContext();
        envCtx = (Context) ctx.lookup("java:comp/env");
        dsOper = (DataSource) ctx.lookup("jdbc/eciq_operation");
        if (dsOper == null) {
            out.println("no datasource");
        }else{
            out.println(dsOper+"<br/>");
            conn = dsOper.getConnection();
            out.println("Oper->connection:" + conn.getMetaData().getURL() + "<br />");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                out.println("GOODS_PLACE:" + rs.getString(1) + "<br />");
                out.println("DECL_NO：" + rs.getString(2) + "<br />");
            }
        }
    } catch (Exception ex) {
        out.println("exception:"+ex.toString());
    } finally {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (ctx != null) {
                ctx.close();
            }
        } catch (Exception e) {
            out.println(e.toString());
        }
    }

    try {
        ctx = new InitialContext();
        envCtx = (Context) ctx.lookup("java:comp/env");
        dsHis = (DataSource) ctx.lookup("jdbc/eciq_operation_his");
        if (dsHis == null) {
            out.println("no datasource");
        }else{
            out.println(dsHis+"<br/>");
            conn = dsHis.getConnection();
            out.println("his->connection:" + conn.getMetaData().getURL() + "<br />");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                out.println("GOODS_PLACE:" + rs.getString(1) + "<br />");
                out.println("DECL_NO：" + rs.getString(2) + "<br />");
            }
        }
    } catch (Exception ex) {
        out.println("exception:"+ex.toString());
    } finally {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (ctx != null) {
                ctx.close();
            }
        } catch (Exception e) {
            out.println(e.toString());
        }
    }
%>
</body>
</html>
