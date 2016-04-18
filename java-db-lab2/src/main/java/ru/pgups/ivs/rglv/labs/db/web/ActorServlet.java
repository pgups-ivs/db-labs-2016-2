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

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.print(String.format("<html><head><title>Детальная информация об актере %s %s</title></head>", actor.getFirstName(), actor.getLastName()));

        out.print("<body>");

        out.print("<h1>" + actor.getFirstName() + " " + actor.getLastName() + "</h1>");

        List<Film> films = filmsDAO.listForActor(id);

        if (films != null && films.size() > 0) {
            out.print("<div><h3>Список фильмов:</h3>");
            for (Film film : films) {
                out.print(String.format("<a href='film?id=%d'>%s (%d)</a><br/>", film.getId(), film.getTitle(), film.getReleaseYear()));
            }
            out.print("</div>");
        }

        out.print("</body>");
    }
}
