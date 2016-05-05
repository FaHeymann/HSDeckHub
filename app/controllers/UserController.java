package controllers;


import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.users.index;

public class UserController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result index(int userId) {
        User sessionUser = User.find.where().eq("email", request().username()).findUnique();
        User user = User.find.byId(userId);

        return ok(
                index.render(sessionUser, user)
        );
    }

}
