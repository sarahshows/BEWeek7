package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	//@formatter:off
	private List<String> operations = List.of( 
			"1) Add a project",	
			"2) List projects",
			"3) Select a project",
			"4) Update the project details",
			"5) Delete a project"
		);
	
public static void main(String[] args) {
	
	new ProjectsApp().processUserSelections();

}

//Method obtains user selection, validates selection
private void processUserSelections() {
		
	boolean done = false;
	
	while(!done) {
		
		try {
			int selection = getUserSelection();
			
			switch(selection) {
			case 1:
				createProject();
				break;
		
			case 2:
				listProjects();
				break;
			
			case 3:
				selectProject();
				break;
				
			case 4:
				updateProjectDetails();
				break;
				
			case 5: 
				deleteProject();
				break;
	
			case -1:
				done = exitMenu();
				break;
		
			
			default:
				System.out.println("\n " + selection + " is not a valid option, please try again.");
				break;
		
		
	    }
		} catch (Exception e) {
			System.out.println("\nError: " + e + " Try again.");
			
		}
	  }
	}

private void deleteProject() {
	listProjects();
	
	Integer projectId = getIntInput("Enter the project ID you wish to delete");
	
	projectService.deleteProject(projectId);
	//if(delete successful) {
		System.out.println("Project " + projectId + " was successfully deleted.");
//	} else {
//		throws Exception(e);
	
//	}
						
	if (Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			curProject = null;
		}
		
}

private List<Project> updateProjectDetails () {
if (Objects.isNull(curProject)) {
	System.out.println("\nSelect a project to update.");
	selectProject();
}

System.out.println();
			
		String projectName = 
				getStringInput("Enter the project name [" 
						+ curProject.getProjectName() + "]");
		BigDecimal estimatedHours =
				getBigDecimalInput("Enter the estimated hours [" 
						+ curProject.getEstimatedHours() + "]");
		BigDecimal actualHours =
				getBigDecimalInput("Enter the actual hours [" 
						+ curProject.getActualHours() + "]");
		Integer difficulty = 
				getIntInput("Enter the difficulty level [" 
						+ curProject.getDifficulty() + "]");
		String notes = 
				getStringInput("Enter the project notes [" 
						+ curProject.getNotes() + "]");
		
		
				
	Project updatedProject = new Project();	
	
	updatedProject.setProjectId(curProject.getProjectId());
	updatedProject.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
	
	updatedProject.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);

	updatedProject.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
	
	updatedProject.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
	
	updatedProject.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
projectService.updateProjectDetails(updatedProject);

curProject = projectService.fetchProjectById(updatedProject.getProjectId());

System.out.println("Project has been updated successfully.");

return List.of(curProject);
} 
	


	
private void selectProject() {
	listProjects();
	
	Integer projectId = getIntInput("Select a project ID: ");
	
	curProject = null;
	curProject = projectService.fetchProjectById(projectId);
	
	
	
	if(Objects.isNull(curProject)) {
		System.out.println("\nInvalid project selected. ");
	}
}		 


private void listProjects() {
	List<Project> projects = projectService.fetchAllProjects();
	
	System.out.println("\nProjects:");
	
	projects.forEach(project -> 
		System.out.println(
			"  " + project.getProjectId()
			+ ": " + project.getProjectName()));
	return;
}

private void createProject() {
	
	String projectName = getStringInput("Enter the project name:");
	BigDecimal estimatedHours = getBigDecimalInput("Enter the estimated hours");
	BigDecimal actualHours = getBigDecimalInput("Enter the actual hours");
	Integer difficulty = getIntInput("Enter the project difficulty(1-5)");
	String notes  = getStringInput("Enter the project notes");
	
	Project project = new Project();
	
	project.setProjectName(projectName);
	project.setEstimatedHours(estimatedHours);
	project.setActualHours(actualHours);
	project.setDifficulty(difficulty);
	project.setNotes(notes);
	
	Project dbProject = projectService.createProject(project);
	System.out.println("You have successfully created project: " + dbProject.getProjectName());
	
}


	private int getUserSelection() {
		printOperations();
		Integer menuSelection = getIntInput("Please enter your menu selection: ");
		return menuSelection;
}
	/* prints to console the available option to be selected. */
		private void printOperations() {
			
			if (Objects.isNull(curProject) ) {
				System.out.println("\nYou do not have an active project selected.");
			} else {
				System.out.println("\n You are working with project: " + curProject);
			}	
			
		System.out.println("\nThese are the available selections.  Press the Enter key to quit:");
		
		
		operations.forEach(line -> System.out.println(" " + line));
		
	
}


private boolean exitMenu() {
	System.out.println("\nExiting the menu.");
	return true;
} 



private BigDecimal getBigDecimalInput(String prompt) {
		String input = getStringInput(prompt);

		if (Objects.isNull(input)) {
		return null;
		}
		
		try {
			return new BigDecimal(input).setScale(2);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal number.");
	}
}


	private Integer getIntInput(String prompt) {
		String input = getStringInput(prompt);
	
		if (Objects.isNull(input)) {
			return null;
		}
		
		try {
			return Integer.parseInt(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}  
	}

private String getStringInput(String prompt) {
	System.out.print(prompt);
	String line = scanner.nextLine();
	return line.isBlank() ? null : line.trim();
	}
}
