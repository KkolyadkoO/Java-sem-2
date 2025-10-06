package org.lab5.servlets.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.lab5.servlets.service.SortService;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/sort")
public class SortServlet extends HttpServlet {
    private final SortService sortService = new SortService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String numbersInput = request.getParameter("numbers");
        int[] sorted = sortService.sortNumbers(numbersInput);

        request.setAttribute("input", numbersInput);
        request.setAttribute("sorted", Arrays.toString(sorted));

        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
        dispatcher.forward(request, response);
    }
}
