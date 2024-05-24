package myFoodora.clui;

import java.util.function.Consumer;

import myFoodora.enums.PermissionType;

class Command {
	private String name;
	private String description;
	private PermissionType permission;
	private Integer inputParameters;
	private Consumer<String[]> command;
	
	Command(String name, String description, PermissionType permission, Integer inputParameters, Consumer<String[]> command) {
		super();
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.inputParameters = inputParameters;
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public PermissionType getPermission() {
		return permission;
	}

	public Consumer<String[]> getCommand() {
		return command;
	}
	
	public Integer getInputParameters() {
		return inputParameters;
	}
	
	public String toString() {
		return name+"\n\t"+description+"\n";
	}
}
