package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {
	private Scanner scanner = new Scanner(System.in);
	private ProjectService projectService = new ProjectService();
	//@formatter:off
	private List<String> operations = List.of(
	"1) Create a project."	


	);
	
	//@formatter:on
public static void main(String[] args) {
	new ProjectsApp().displayMenu();

}

private void displayMenu() {
	boolean done = false;

	while(!done) {
		
		try {		
			int selection = getUserSelection();
		
			switch(selection) {
 		     case -1:
			done = exitMenu();
			break;
			
		     case 1:
			createProjects();
			break;
		    
		    default:
			System.out.println("\n" + selection + " is not valid, please try again.");
			}
		} catch (Exception e) {
		System.out.println("\nError: " + e + "Try Again");
		}
	}
}
	private void createProjects() {
	
}

	private int getUserSelection() {
		PrintOperations();
		
		Integer input = getIntInput("Enter a menu selection");
		
		return Objects.isNull(input) ? -1 : input;
}
	private void PrintOperations() {
		System.out.println("\nThese are the available selections.  Press the Enter key to quit:");
		operations.forEach(line -> System.out.println(" " + line));
	}if (Objects.isNull(currentProject)) {
		System.out.println("\nYou do not have an active project.");
	} else {
		System.out.println("\n You are viewing: " + currentProject);
	}
	

/**
*@ return
*/

private boolean exitMenu() {
	System.out.println("\nExiting the menu.  TTFN!");
	return true;
} 


/**
*
*/

private void printOperations() {
	System.out.println();
	System.out.println("Enter a menu selection:");

	operations.forEach(line -> System.out.println("  " + line));

	}

/**
*@param prompt
*@return
*/

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
	/**
	*@param prompt
	*@return
	*/

	private BigDecimal getDecimalInput(String prompt) {
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

/**
*@param prompt
*@return
*/
private String getStringInput(String prompt) {
	System.out.println(prompt + ": ");
	String line = scanner.nextLine();

	return line.isBlank() ? null : line.trim();

	}
}