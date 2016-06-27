package controllers;

import models.App;
import models.GitProject;
import org.eclipse.jgit.api.errors.GitAPIException;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createFormApp;
import views.html.index;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class AppController extends Controller {

    private FormFactory formFactory;

    @Inject
    AppController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    @Inject
    GitProject gitProject;

    public Result create() {
        Form<App> appForm = formFactory.form(App.class);
        return ok(createFormApp.render(appForm));
    }

    public Result save() throws IOException, GitAPIException {

        Form<App> appForm = formFactory.form(App.class).bindFromRequest();

        String name = appForm.get().getName();
        String id = appForm.get().getId();
        String password = appForm.get().getPassword();
        String vMajor = appForm.get().getvMajor();
        String vMinor = appForm.get().getvMinor();
        String vMinorMinor = appForm.get().getvMinorMinor();

        App app = new App();
        app.setId(id);
        app.setName(name);
        app.setPassword(password);
        app.setvMajor(vMajor);
        app.setvMinor(vMinor);
        app.setvMinorMinor(vMinorMinor);

        app.adaptAppFile(name, id, password, vMajor, vMinor, vMinorMinor, gitProject.getLocalPath());

        gitProject.addCommitPush(App.APP_FILE_TO_ADAPT, "New app: " + name);

        return ok(index.render("App created: " + name ));
    }

    public Result list() throws IOException, GitAPIException {
        App app = new App();
        List<App> apps = app.readExistingApps(gitProject.getLocalPath().getPath() + "/app/build.gradle");
        return ok(views.html.applist.render("Apps ", apps));
    }

}
