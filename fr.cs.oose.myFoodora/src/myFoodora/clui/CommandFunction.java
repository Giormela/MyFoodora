package myFoodora.clui;

import myFoodora.exceptions.CommandException;

interface CommandFunction {
	public void run(String[] args) throws CommandException;
}
