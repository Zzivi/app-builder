package controllers;

import models.GitProject;
import org.eclipse.jgit.api.errors.GitAPIException;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;


import views.html.*;

import java.io.IOException;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class GitProjectController extends Controller {

    public Result index() {
        Form<GitProject> gitProjectForm = Form.form(GitProject.class);
        return ok(views.html.createForm.render(gitProjectForm));
    }

    public Result cloneGit() throws IOException, GitAPIException {
        Form<GitProject> gitProjectForm = Form.form(GitProject.class).bindFromRequest();
        String url = gitProjectForm.get().getUrl();

        GitProject gitProject = new GitProject();
        gitProject.setUrl(url);
        gitProject.cloneRemoteRepository("develop");
        
        return ok(index.render("Git project cloned: " + url ));
    }
}