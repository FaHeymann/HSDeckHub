package controllers;

import models.Deck;
import models.User;
import play.data.Form;
import play.mvc.*;

import views.html.decks.*;

import java.util.List;

import static play.data.Form.form;

public class DeckController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result list() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        List<Deck> decks = Deck.find.where().eq("user", user).findList();

        return ok(list.render(user, decks));
    }

    public Result all() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        List<Deck> decks = Deck.find.where().eq("isPublic", true).findList();

        return ok(all.render(user, decks));
    }

    @Security.Authenticated(Secured.class)
    public Result create() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        return ok(create.render(user, form(DeckData.class)));
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
            deck.setPublic(deckForm.data().containsKey("isPublic") && deckForm.data().get("isPublic").equals("true"));
            deck.setType(deckForm.get().type);
            deck.save();

            return redirect(routes.DeckController.list());
        }
    }

    @Security.Authenticated(Secured.class)
    public Result edit(int deckId) {
        User user = User.find.where().eq("email", request().username()).findUnique();
        Deck deck = Deck.find.byId(deckId);
        Form<DeckData> deckForm = form(DeckData.class);
        deckForm.data().put("name", deck.name);
        deckForm.data().put("description", deck.description);
        deckForm.data().put("isPublic", deck.isPublic ? "true" : "false");
        deckForm.data().put("type", deck.type.name);
        deckForm.data().put("id", "" + deck.id);
        return ok(edit.render(user, deckForm));
    }

    @Security.Authenticated(Secured.class)
    public Result saveEdit() {
        User user = User.find.where().eq("email", request().username()).findUnique();
        Form<DeckData> deckForm = form(DeckData.class).bindFromRequest();
        if (deckForm.hasErrors()) {
            return badRequest(edit.render(user, deckForm));
        } else {
            Deck deck = Deck.find.byId(Integer.parseInt(deckForm.data().get("id")));
            deck.setUser(user);
            deck.setName(deckForm.get().name);
            deck.setDescription(deckForm.get().description);
            deck.setPublic(deckForm.get().isPublic);
            deck.setType(deckForm.get().type);
            deck.save();

            return redirect(routes.DeckController.list());
        }
    }

    public Result detail(int deckId) {
        User user = User.find.where().eq("email", request().username()).findUnique();
        Deck deck = Deck.find.byId(deckId);

        return ok(detail.render(user, deck));
    }

    public static class DeckData {

        public Integer id;
        public String name;
        public String description;
        public Boolean isPublic = false;
        public Deck.Type type = Deck.Type.NONE;

        public String validate() {
            if(this.name == null || this.name.equals("")) {
                return "Name must not be empty";
            }
            return null;
        }
    }
}
