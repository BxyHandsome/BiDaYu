import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class loginServlet extends HttpServlet {
	static String yh, mm;

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PrintWriter out = response.getWriter();

		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("���سɹ�");
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/userss", "root", "bxy3367158");
			System.out.println("sql���ӳɹ�");

			Statement sta = (Statement) con.createStatement();
			String user = request.getParameter("user");
			String pwd = request.getParameter("pwd");
			ResultSet rs = (ResultSet) sta
					.executeQuery("select * from user where user='" + user
							+ "' and pwd='" + pwd + "'");
			if (rs.next()) {
				yh = rs.getString("user");
				mm = rs.getString("pwd");
				if (yh.equals(user) && mm.equals(pwd)) {
					HttpSession session = request.getSession();
					session.setAttribute("username", user);
					session.setAttribute("pwd", pwd);
					session.setMaxInactiveInterval(10);
					System.out.println("�����Ϣ�Ѵ���");
					request.getRequestDispatcher("home").forward(request,
							response);
				}

			}else {
                 
				response.sendRedirect("/bidayu/register.html");

			}
 
			
			
		} catch (ClassNotFoundException e) {

			System.out.println("û���ҵ�class.forname");

		} catch (SQLException sqle) {

			System.out.println("sql����ʧ��");

		}
		response.setContentType("text/html");
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");

		out.println("  </form>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();

	}

}