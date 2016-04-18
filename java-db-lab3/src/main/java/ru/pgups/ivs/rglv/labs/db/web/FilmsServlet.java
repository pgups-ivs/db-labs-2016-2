package ru.pgups.ivs.rglv.labs.db.web;

import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;
import ru.pgups.ivs.rglv.labs.db.model.Film;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "FilmsServlet", urlPatterns = {"/films","/"})
public class FilmsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        FilmsDAO filmsDAO = (FilmsDAO) this.getServletContext().getAttribute("filmsDAO");

        if (request.getParameter("id") == null) {
            List<Film> films = filmsDAO.list();
            request.setAttribute("films", films);
            this.getServletContext().getRequestDispatcher("/films.jsp").forward(request, response);
        } else {
            response.sendRedirect("film?id=" + request.getParameter("id"));
        }
    }
}
