package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

public class ProjectService {
	//private static final String SCHEMA_FILE = "projects_schema.sql";
	//private static final String DATA_FILE = "data.sql";
	
	private ProjectDao projectDao = new ProjectDao();
	private Project project;

	public Project fetchProjectById(Integer projectId) {
		return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException("Project with Project Id = " + projectId + " does not exist." ));
		}


	public void updateProjectDetails(Project project) {
		if(!projectDao.updateProjectDetails(project)) {
			throw new DbException("Project with ID= " + project.getProjectId() + " does not exist. Make another selection." );
	}
	}
	
	public void deleteProject(Integer projectId) {
		if(!projectDao.deleteProject(projectId)) {
			throw new DbException("Project with ID= " + projectId + "does not exist.");
	} 
}


	public List<Project> fetchAllProjects() {
		return (projectDao.fetchAllProjects());
		}


	public Project createProject(Project project) {
		return(projectDao.insertProject(project));

	}
}
	

