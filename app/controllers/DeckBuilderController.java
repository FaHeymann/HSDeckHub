package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Card;
import models.Deck;
import models.User;
import play.mvc.*;

import views.html.deckBuilder.index;

import java.util.Iterator;
import java.util.List;

import static play.libs.Json.toJson;

public class DeckBuilderController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result index(int deckId) {
        User user = User.find.where().eq("email", request().username()).findUnique();
        return ok(
                index.render(user, deckId)
        );
    }

    @Security.Authenticated(Secured.class)
    public Result getCards() {
        List<Card> cards = Card.find.where().orderBy("name asc").findList();
        return ok(toJson(cards));
    }

    @Security.Authenticated(Secured.class)
    public Result loadDeck(int deckId) {
        Deck deck = Deck.find.byId(deckId);

        try {
            JsonNodeFactory factory = new JsonNodeFactory(false);
            ObjectNode jsonRoot = factory.objectNode();
            ArrayNode first = factory.arrayNode();
            for(Card card : deck.first) {
                first.add(card.getId());
            }
            ArrayNode second = factory.arrayNode();
            for(Card card : deck.second) {
                second.add(card.getId());
            }
            jsonRoot.set("first", first);
            jsonRoot.set("second", second);


            return ok(jsonRoot);
        } catch(Exception e) {
            return internalServerError();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result saveDeck(int deckId) {
        Deck deck = Deck.find.byId(deckId);
        JsonNode json = request().body().asJson();
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            deck.removeCards();
            ObjectNode first = (ObjectNode)json.findPath("first");
            Iterator<JsonNode> firstIterator = first.elements();
            while(firstIterator.hasNext()) {
                ObjectNode card = (ObjectNode)firstIterator.next();
                deck.addCard(Card.find.byId(card.get("id").asInt()));
            }
            ObjectNode second = (ObjectNode)json.findPath("second");
            Iterator<JsonNode> secondIterator = second.elements();
            while(secondIterator.hasNext()) {
                ObjectNode card = (ObjectNode)secondIterator.next();
                deck.addCard(Card.find.byId(card.get("id").asInt()));
            }
            deck.save();
        }
        return ok(toJson(deck.first));
    }
}
