package models;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dgarcia on 26/06/2016.
 */

@Singleton
public class GitProject {
    private static final String LOCAL_REPO = "LocalGitRepository";
    private static final String GIT_REPO_URL = "git@github.com:Zzivi/autoapp.git";


    private File localPath;

    private Git gitProject;

    private String branch = "develop";

    public File getLocalPath() {
        return localPath;
    }


    @Inject
    public GitProject() throws IOException, GitAPIException {
        cloneRemoteRepository(branch);
    }

    public void cloneRemoteRepository(String branch) throws IOException, GitAPIException {
        localPath = File.createTempFile(LOCAL_REPO, "");
        localPath.delete();

        ArrayList<String> branchesToClone = new ArrayList<String>();
        branchesToClone.add("master");
        branchesToClone.add(branch);

        // then clone
        System.out.println("Cloning from " + GIT_REPO_URL + " to " + localPath);

        gitProject = Git.cloneRepository()
                .setURI(GIT_REPO_URL)
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
