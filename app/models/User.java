package models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import java.util.List;

@Entity
public class User extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;

    @Column(unique=true)
    public String email;

    public String name;

    public String password;

    @OneToMany(mappedBy = "user")
    public List<Deck> decks;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
                .eq("password", password).findUnique();
    }

    public static Finder<Integer, User> find = new Finder<Integer, User>(User.class);
}