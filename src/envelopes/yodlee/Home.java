package envelopes.yodlee;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.Constants;
import envelopes.data.Transaction;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LogIn helper = new LogIn();
		
		List<Transaction> transactions = helper.transactionSearchService((String) request.getSession().getAttribute(Constants.COB_SESSION_TOKEN), 
				(String) request.getSession().getAttribute(Constants.USER_SESSION_TOKEN));
		
		double total = 0;
		
		for (Transaction transaction : transactions) {
			System.out.println("Transaction type: " + transaction.getTransactionType() + " transaction amount: " + transaction.getAmount());
			if (transaction.getTransactionType().equals("credit") || transaction.getTransactionType().equals("deposit")) {
				total += transaction.getAmount();
			}
		}
		
		request.getSession().setAttribute("lastMonthTotal", total);
		
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
