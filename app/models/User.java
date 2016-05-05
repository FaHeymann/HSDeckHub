package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import helpers.PasswordHelper;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Entity
public class User extends Model {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;

    @Column(unique=true)
    public String email;

    public String name;

    public String password;

    @OneToMany(mappedBy = "user")
    public List<Deck> decks;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.setPassword(password);
    }

    public void setPassword(String password) {
        try {
            this.password = PasswordHelper.createPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public static User authenticate(String email, String password) {
        User user = find.where().eq("email", email).findUnique();
        return PasswordHelper.checkPassword(password, user.password) ? user : null;
    }

    public static Finder<Integer, User> find = new Finder<Integer, User>(User.class);
}