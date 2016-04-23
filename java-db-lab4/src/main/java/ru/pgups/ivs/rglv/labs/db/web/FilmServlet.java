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
import java.sql.SQLException;

@WebServlet(name = "FilmServlet", urlPatterns = {"/film", "/film/edit"})
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
        String route = request.getRequestURI().endsWith("edit") ? "/filmEdit.jsp" : "/film.jsp";
        this.getServletContext().getRequestDispatcher(route).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilmsDAO filmsDAO = (FilmsDAO) this.getServletContext().getAttribute("filmsDAO");
        Film film;
        if (request.getParameter("id") != null) {
            long id = Long.parseLong(request.getParameter("id"));
            film = filmsDAO.get(id);
        } else {
            film = new Film();
        }

        if (film == null) {
            response.sendError(404);
            return;
        }

        String title = request.getParameter("title");
        if (title != null) {
            film.setTitle(title);
        }

        String description = request.getParameter("description");
        if (description != null) {
            film.setDescription(description);
        }

        String releaseYear = request.getParameter("release_year");
        if (releaseYear != null) {
            film.setReleaseYear(Integer.parseInt(releaseYear));
        }

        try {
            filmsDAO.save(film);
        } catch (SQLException e) {
            log("Failed to save film", e);
            response.sendError(500, "Failed to save film: " + e.getMessage());
        }

        response.sendRedirect("/films");
    }
}
