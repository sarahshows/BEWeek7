package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	
	//@formatter:off
	private List<String> operations = List.of( 
			"1) Add a project",	
			"2) List projects",
			"3) Select a project",
			"4) Update the project details"
		);
	//@formatter:on

public static void main(String[] args) {
	new ProjectsApp().processUserSelections();
}
//Method obtains user selection, validates selection
@SuppressWarnings("unused")
private void processUserSelections() {
	boolean done = false;
	
	while(!done) {
		
		try {
			int selection = getUserSelection();
		
		switch(selection) {
			case -1:
				done = exitMenu();
				break;
		
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
			
			default:
				System.out.println("\n " + selection + " is not a valid option, please try again.");
				break;
		
		
	    }
		} catch (Exception e) {
			System.out.println("\nError: " + e + " Try again.");
			
		}
	  }
	}

private void updateProjectDetails() {
	if (curProject == null) {
		System.out.println("\nPlease select a project");
	}
		String projectName = getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
		BigDecimal estimatedHours = getBigDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
		BigDecimal actualHours = getBigDecimalInput("Enter the actual hours [" + curProject.getActualHours() + "]");
		Integer difficulty = getIntInput("Enter the difficulty level [" + curProject.getDifficulty() + "]");
		String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
		Project project = new Project();
		project.setProjectId(curProject.getProjectId());
		project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
		project.setEstimatedHours(Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
		project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
		project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty(): difficulty);
		project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		curProject = projectService.fetchProjectById(curProject.getProjectId());
		
	}

private void selectProject() {
	listProjects();
	Integer projectId = getIntInput("Enter a project ID to select a project");

	curProject = null;
	
	curProject = projectService.fetchProjectById(projectId);
} 
		
	
private void listProjects() {
	List<Project> projects = projectService.fetchAllProjects();
	
	System.out.println("\nProjects:");
	
	projects.forEach(project -> System.out.println(
			" " + project.getProjectId()
			+ ": " + project.getProjectName()));
} 

private void createProject() {
	String projectName = getStringInput("Enter the project name");
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
	
	Project dbProject = projectService.addProject(project);
	System.out.println("You have successfully created project: " + dbProject);
	
}


	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
}
	
	private void printOperations() {
		System.out.println("\nThese are the available selections.  Press the Enter key to quit:");
		
		operations.forEach(line -> System.out.println(" " + line));
		
	if (Objects.isNull(curProject)) {
		System.out.println("\nYou do not have an active project.");
	} else {

		System.out.println("\n You are viewing: " + curProject);
	}
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
			return Integer.valueOf(input);
		}
		catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid number.");
		}  
	}

private String getStringInput(String prompt) {
	System.out.print(prompt + ": ");
	String input = scanner.nextLine();

	return input.isBlank() ? null : input.trim();

	}
}
