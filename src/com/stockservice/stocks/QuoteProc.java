package com.stockservice.stocks;


import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetQuote
 */
@WebServlet("/GetQuote")
public class QuoteProc extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuoteProc()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		response.sendRedirect("QuoteError.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String stock = request.getParameter("stock").toUpperCase();
		if (stock.isEmpty())
			stock = "IBM";
		Hashtable<String, String> info = GetRemoteQuote.GetQuote(stock);
		ServletContext sc = getServletContext();
		sc.setAttribute("quote", info);
		response.sendRedirect("StockQuote.jsp");
	}
}
