package models;


import com.avaje.ebean.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Deck extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public String name;

    public String description;

    @NotNull
    @ManyToOne
    public User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="card_deck_first")
    public List<Card> first;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="card_deck_second")
    public List<Card> second;

    public static Finder<Integer, Deck> find = new Finder<Integer, Deck>(Deck.class);

    public void addCard(Card card) {
        if(!this.second.contains(card) && !(this.first.contains(card) && card.getQualityId() == Card.QUALITY_LEGENDARY)) {
            if(this.first.contains(card)) {
                this.second.add(card);
            } else {
                this.first.add(card);
            }
        }
    }

    public void removeCard(Card card) {
        if(this.second.contains(card)) {
            this.second.remove(card);
        } else if(this.first.contains(card)) {
            this.first.remove(card);
        }
    }

    public void removeCards() {
        this.first = new ArrayList<>();
        this.second = new ArrayList<>();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
