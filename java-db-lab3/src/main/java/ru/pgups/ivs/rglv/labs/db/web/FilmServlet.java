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
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.print(String.format("<html><head><title>Детальная информация о фильме %s</title></head>", film.getTitle()));
        out.print("<body>");

        out.print("<h1>" + film.getTitle() + "</h1><h4>" + film.getReleaseYear() + "</h4>");

        if (film.getCategories() != null && film.getCategories().size() > 0) {
            out.print("<div>");
            for (FilmCategory category : film.getCategories()) {
                out.print("<span>" + category.getName() + "</span>");
            }
            out.print("</div>");
        }
        out.print("<div>");
        out.print("Rating: " + film.getMpaaRating());
        out.print("</div>");

        out.print("<div>");
        out.print(film.getDescription());
        out.print("</div>");

        if (film.getLanguage() != null) {
            out.print("<div>");
            out.print("Language: " + film.getLanguage().getLanguage());
            out.print("</div>");
        }

        if (film.getActors() != null && film.getActors().size() > 0) {
            out.print("<div>");
            out.print("<h3>Actors</h3>");
            boolean b = false;
            for (Actor actor : film.getActors()) {
                if (b) out.print(", ");
                out.print(String.format("<a href='actor?id=%d'>%s %s</a>", actor.getId(), actor.getFirstName(), actor.getLastName()));
                b =  true;
            }
            out.print("</div>");
        }

        out.print("</body>");
        out.print("</html>");
    }
}
