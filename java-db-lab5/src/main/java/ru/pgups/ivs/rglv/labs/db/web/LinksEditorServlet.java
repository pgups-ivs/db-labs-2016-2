package ru.pgups.ivs.rglv.labs.db.web;

import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LinksEditorServlet", urlPatterns = {"/filmCategory", "/filmActor"})
public class LinksEditorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getRequestURI();
        if (action.endsWith("/filmCategory")) {
            FilmsDAO filmsDAO = (FilmsDAO) this.getServletContext().getAttribute("filmsDAO");
            filmsDAO.removeCategoryFromFilm(Long.parseLong(request.getParameter("filmId")), Long.parseLong(request.getParameter("categoryId")));
            response.sendRedirect("/film/edit?id=" + request.getParameter("filmId"));
        }
    }
}
