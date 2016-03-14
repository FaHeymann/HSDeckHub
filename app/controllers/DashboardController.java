package controllers;

import models.User;
import play.mvc.*;

import views.html.dashboard.index;

public class DashboardController extends Controller {

    @Security.Authenticated(Secured.class)
    public Result index() {
        User user = User.find.where().eq("email", request().username()).findUnique();

        //
        return ok(
                index.render(user)
        );
    }
}
