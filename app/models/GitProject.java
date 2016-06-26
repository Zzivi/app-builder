package models;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dgarcia on 26/06/2016.
 */
public class GitProject {
    private static final String LOCAL_REPO = "LocalGitRepository";

    private String url;

    private File localPath;

    private Git gitProject;

    public File getLocalPath() {
        return localPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void cloneRemoteRepository(String branch) throws IOException, GitAPIException {
        localPath = File.createTempFile(LOCAL_REPO, "");
        localPath.delete();

        ArrayList<String> branchesToClone = new ArrayList<String>();
        branchesToClone.add("master");
        branchesToClone.add(branch);

        // then clone
        System.out.println("Cloning from " + url + " to " + localPath);
        gitProject = Git.cloneRepository()
                .setURI(url)
                .setDirectory(localPath)
                //.setBranchesToClone(branchesToClone)
                .call();

        System.out.println("Having repository: " + gitProject.getRepository().getDirectory());
    }

    public void addCommitPush(String nameFileToAdd, String message) throws IOException, GitAPIException {
		System.out.println("git add file: " + nameFileToAdd);
        gitProject.add().addFilepattern(nameFileToAdd).call();
        CommitCommand commit = gitProject.commit();
        commit.setMessage(message).call();
		gitProject.push().call();
	}
}
