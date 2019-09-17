package com.lifestorm.learn.jsp;

import static java.lang.System.out;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Created by engle on 2019/7/15.
 */
public class JndiTest {

  public String test() {
    String jndi_name = "java:jdbc/eciq_operation";
    String strSql = "SELECT D.GOODS_PLACE,DECL_NO FROM DCL_IO_DECL D WHERE D.DECL_NO='219000000000338'";
    Context ctx = null;
    DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      ctx = new InitialContext();
      if (ctx == null) {
        out.println("no context");
      }
      ds = (DataSource) ctx.lookup(jndi_name);
      if (ds == null) {
        out.println("no datasource");
      }
      conn = ds.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      rs = stmt.executeQuery(strSql);
      while (rs.next()) {
        out.println("GOODS_PLACE:" + rs.getString(1) + "<br />");
        out.println("DECL_NO：" + rs.getString(2) + "<br />");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      out.println(ex.toString());
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
    return super.toString();
  }
}
