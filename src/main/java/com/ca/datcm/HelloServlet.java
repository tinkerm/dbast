package com.ca.datcm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class HelloServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    Context ctx;
    DataSource ds;
    Connection con = null;
    Statement s;
    ResultSet rs;
    try {
      ctx = new InitialContext();
      ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/datcm");
    } catch (Exception e) {
      out.println("Could not create anything. :(");
      return;
    }

    try {
      con = ds.getConnection();
      s = con.createStatement();
      String query = "SELECT * FROM stocks FETCH FIRST 10 ROWS ONLY;";
      rs = s.executeQuery(query);
      while (rs.next()) {
        out.println(rs.getString(2) + "\t" + rs.getString(5));
      }   
      rs.close();
      con.close();
    } catch (SQLException e) {
      out.println(e);
    } 
  }
}
