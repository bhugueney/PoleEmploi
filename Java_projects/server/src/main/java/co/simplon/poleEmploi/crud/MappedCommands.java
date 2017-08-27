package co.simplon.poleEmploi.crud;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MappedCommands implements ICommand {
	private Map<String, ICommand> keywordToCommand;
	public MappedCommands() {
		keywordToCommand= new HashMap<String, ICommand>();
	}
	public MappedCommands(Map<String, ICommand> k2C) {
		keywordToCommand= k2C;
	}
	public MappedCommands add(String keyword, ICommand command) {
		keywordToCommand.put(keyword, command);
		return this;
	}
	public String process(Scanner sc) {
		String keyword= sc.next().toUpperCase();
		return keywordToCommand.get(keyword).process(sc);
	}

}
