package envelopes.yodlee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import envelopes.data.Constants;
import envelopes.data.Envelope;
import envelopes.data.EnvelopeHandler;

/**
 * Servlet implementation class GetEnvelopes
 */
@WebServlet("/AssignAmt")
public class AssignAmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignAmt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EnvelopeHandler eh = new EnvelopeHandler();
		
		String username = (String) request.getSession().getAttribute(Constants.USERNAME);

		// TODO: Change this to get only the envelopes for the correct user.
		List<Envelope> envelopes = eh.getEnvelopes();
		
		List<Envelope> userEnvelopes = new ArrayList<Envelope>();
		
		for (Envelope envelope : envelopes) {
			if (envelope.getCustomer_username() != null && envelope.getCustomer_username().equals(username)) {
				userEnvelopes.add(envelope);
			}
		}
		
		request.getSession().setAttribute("enves", userEnvelopes);
		request.getRequestDispatcher("assign.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnvelopeHandler eh = new EnvelopeHandler(); 
		String updateEnvelopesAmount = request.getParameter("envelope_update");
		String updateEnvelopesId = request.getParameter("envelope_id");
		eh.updateEnvelope(updateEnvelopesId, updateEnvelopesAmount);
		
		response.sendRedirect("AssignAmt");
	}

}
