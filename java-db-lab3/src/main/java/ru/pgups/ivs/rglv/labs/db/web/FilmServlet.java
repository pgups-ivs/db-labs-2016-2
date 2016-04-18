package ru.pgups.ivs.rglv.labs.db.web;

import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;
import ru.pgups.ivs.rglv.labs.db.model.Actor;
import ru.pgups.ivs.rglv.labs.db.model.Film;
import ru.pgups.ivs.rglv.labs.db.model.FilmCategory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FilmServlet", urlPatterns = {"/film"})
public class FilmServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmsDAO filmsDAO = (FilmsDAO) this.getServletContext().getAttribute("filmsDAO");
        if (request.getParameter("id") == null) {
            response.sendError(404);
            return;
        }
        long id = Long.parseLong(request.getParameter("id"));
        Film film = filmsDAO.get(id);
        if (film == null) {
            response.sendError(404);
            return;
        }
        request.setAttribute("film", film);
        this.getServletContext().getRequestDispatcher("/film.jsp").forward(request, response);
    }
}
