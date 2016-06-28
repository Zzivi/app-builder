package models;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

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
        checkoutBranch(branch);
    }

    public void cloneRemoteRepository(String branch) throws IOException, GitAPIException {
        localPath = File.createTempFile(LOCAL_REPO, "");
        localPath.delete();

        ArrayList<String> branchesToClone = new ArrayList<String>();
        branchesToClone.add("master");
        branchesToClone.add(branch);

        // then clone
        System.out.println("Cloning from " + GIT_REPO_URL + " to " + localPath);

        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                // do nothing
            }

            @Override
            protected JSch createDefaultJSch(FS fs ) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch( fs );
                //TODO: generate ssh key in AWS server, add to github account and update this path
                defaultJSch.addIdentity( "/Users/rominaliuzzi/ghost-dev/.ssh/id_rsa" );
                return defaultJSch;
            }

        };

        gitProject = Git.cloneRepository()
                .setURI(GIT_REPO_URL)
                .setDirectory(localPath)
                //.setBranchesToClone(branchesToClone)
                .setTransportConfigCallback( new TransportConfigCallback() {
                    @Override
                    public void configure( Transport transport ) {
                        SshTransport sshTransport = ( SshTransport )transport;
                        sshTransport.setSshSessionFactory( sshSessionFactory );
                    }
                } )
                .call();

        System.out.println("Having repository: " + gitProject.getRepository().getDirectory());
    }


    public void checkoutBranch(String branch) throws IOException, GitAPIException{
        System.out.println("Checkingout branch " + branch);
        Ref ref = gitProject.checkout().
                setCreateBranch(true).
                setName(branch).
                setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
                setStartPoint("origin/" + branch).
                call();
        System.out.println("Currently at branch REF" + ref.getName());
        System.out.println("Currently at branch " + gitProject.getRepository().getFullBranch());
    }

    public void addCommitPush(String nameFileToAdd, String message) throws IOException, GitAPIException {
		System.out.println("git add file: " + nameFileToAdd);
        gitProject.add().addFilepattern(nameFileToAdd).call();
        CommitCommand commit = gitProject.commit();
        commit.setMessage(message).call();
		gitProject.push().call();
	}
}
