package kea.eksamen.repository;

import kea.eksamen.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepositoryInterface {

    /**
     * Testing Java documentation, adding CRUD to the database
     *
     * @param project The project to add, edit , delete and view.
     * @return The projects with the generated ID.
     * @throws SQLException if a database error occurs.
     */

    Project addProject (Project project);
    Project updateProject(Project project, int id);
    Boolean deleteProject(Project project);
    Project findProjectById(int id);
    List<Project> findAllProjects();









}
