package myFoodora.clui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import myFoodora.exceptions.CommandException;

class UserInterfaceFile extends UserInterface {
	UserInterfaceFile(String path) throws FileNotFoundException, IOException {
		super(
			new FileReader(path), 
			new FileWriter(path.substring(0, path.length() - 4)+"Output.txt")
		);		
	}

	@Override
	public void renderLoop() {
		while (runnning) {
			updateAllowedCommands();

			try {
				if (input.ready())
					readCommand();
				else {
					flush();
					break;
				}
			} catch (CommandException | IOException e) {
				print(e.getMessage(), Color.RED);
				break;
			}
		}
	}
	
}