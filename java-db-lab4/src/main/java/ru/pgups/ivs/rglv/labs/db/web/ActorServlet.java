package ru.pgups.ivs.rglv.labs.db.web;

import ru.pgups.ivs.rglv.labs.db.dao.ActorsDAO;
import ru.pgups.ivs.rglv.labs.db.dao.FilmsDAO;
import ru.pgups.ivs.rglv.labs.db.model.Actor;
import ru.pgups.ivs.rglv.labs.db.model.Film;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ActorServlet", urlPatterns = {"/actor"})
public class ActorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActorsDAO actorsDAO = (ActorsDAO) this.getServletContext().getAttribute("actorsDAO");
        FilmsDAO filmsDAO = (FilmsDAO) this.getServletContext().getAttribute("filmsDAO");

        if (request.getParameter("id") == null) {
            response.sendError(404);
            return;
        }
        long id = Long.parseLong(request.getParameter("id"));
        Actor actor = actorsDAO.get(id);
        if (actor == null) {
            response.sendError(404);
            return;
        }
        List<Film> films = filmsDAO.listForActor(id);
        request.setAttribute("actor", actor);
        request.setAttribute("films", films);
        this.getServletContext().getRequestDispatcher("/actor.jsp").forward(request, response);
    }
}
