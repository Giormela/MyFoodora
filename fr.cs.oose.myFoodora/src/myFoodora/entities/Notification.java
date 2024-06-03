package myFoodora.entities;

import myFoodora.clui.Display;

public class Notification implements Display{
	private String message;

	public Notification(String message) {
		this.message = message;
	}

	@Override
	public String display() {
		int length = message.length();
		String framedMessage = 
				  "****"+"*".repeat(length)+"****\n"
                + "*   "+" ".repeat(length)+"   *\n"
                + "*   "+message+"   *\n"
                + "*   "+" ".repeat(length)+"   *\n"
                + "****"+"*".repeat(length)+"****\n";
		return framedMessage;
	}
	
}
