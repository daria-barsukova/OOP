package dbarsukova.controller;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;


/**
 * utility class for managing Git repositories.
 */

public class GitManager {

    /**
     * clones Git repository into specified directory.
     *
     * @param repoUrl   URL of Git repository to clone.
     * @param studentId unique identifier of student.
     * @param labsPath  path where repository will be cloned.
     * @throws IllegalStateException if error occurs during cloning process.
     */
    public static void cloneRepository(String repoUrl, String studentId, String labsPath) {
        try {
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(new File(labsPath, studentId))
                    .call();
        } catch (GitAPIException e) {
            String errorMessage = String.format("Failed to clone repository from %s", repoUrl);
            throw new IllegalStateException(errorMessage, e);
        } catch (Exception e) {
            throw new IllegalStateException("An unexpected error occurred", e);
        }
    }
}
