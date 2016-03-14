package controllers;

import models.Deck;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.decks.create;
import views.html.decks.list;

import java.util.List;

import static play.data.Form.form;

public class DeckController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result list() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        List<Deck> decks = Deck.find.where().eq("user", user).findList();

        return ok(
                list.render(user, decks)
        );
    }

    @Security.Authenticated(Secured.class)
    public Result create() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        return ok(
                create.render(user, form(DeckData.class))
        );
    }

    @Security.Authenticated(Secured.class)
    public Result save() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        Form<DeckData> deckForm = form(DeckData.class).bindFromRequest();
        if (deckForm.hasErrors()) {
            return badRequest(create.render(user, deckForm));
        } else {

            Deck deck = new Deck();
            deck.setUser(user);
            deck.setName(deckForm.data().get("name"));
            deck.setDescription(deckForm.data().get("description"));
            deck.save();

            return redirect(
                    routes.DeckController.list()
            );
        }
    }

    public static class DeckData {

        public String name;
        public String description;

        public String validate() {
            if(this.name == null || this.name.equals("")) {
                return "Name must not be empty";
            }
            return null;
        }
    }
}
