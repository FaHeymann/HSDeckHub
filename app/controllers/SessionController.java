package controllers;

import models.User;
import play.data.*;
import play.mvc.*;
import views.html.session.login;

import static play.data.Form.form;

public class SessionController extends Controller {

    public Result login() {
        return ok(
                login.render("Login", form(Login.class))
        );
    }

    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render("Login", loginForm));
        } else {
            session().clear();
            session("email", loginForm.get().email);
            return redirect(
                    routes.DashboardController.index()
            );
        }
    }

    public Result logout() {
        session().clear();
        return redirect(
                routes.SessionController.login()
        );
    }

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

}
