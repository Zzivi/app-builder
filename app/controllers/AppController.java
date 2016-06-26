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

/**
 * Created by dgarcia on 26/06/2016.
 */
public class AppController extends Controller {

    private static final String GIT_REPO_URL = "git@github.com:Zzivi/autoapp.git";

    private FormFactory formFactory;

    @Inject
    AppController(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result create() {
        Form<App> appForm = formFactory.form(App.class);
        return ok(createFormApp.render(appForm));
    }

    public Result save() throws IOException, GitAPIException {
        Form<App> appForm = formFactory.form(App.class).bindFromRequest();

        GitProject gitProject = new GitProject();
        gitProject.setUrl(GIT_REPO_URL);
        gitProject.cloneRemoteRepository("develop");

        String name = appForm.get().getName();
        String id = appForm.get().getId();

        App app = new App();
        app.setId(id);
        app.setName(name);
        app.adaptAppFile(name, id, gitProject.getLocalPath());

        gitProject.addCommitPush(App.APP_FILE_TO_ADAPT, "New app: " + name);

        return ok(index.render("App created: " + name ));
    }
}
