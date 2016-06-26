package controllers;

import models.App;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;
import play.data.Form;
import play.data.FormFactory;

import javax.inject.Inject;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class AppController extends Controller {

    private FormFactory formFactory;

    @Inject
    AppController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result create() {
        Form<App> appForm = formFactory.form(App.class);
        return ok(views.html.createFormApp.render(appForm));
    }

    public Result save() {
        Form<App> appForm = formFactory.form(App.class).bindFromRequest();
        String name = appForm.get().getName();
        return ok(index.render("App created: " + name ));
    }
}
