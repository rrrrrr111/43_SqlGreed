package ru.roman.greet.service.config.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roman 15.09.12 22:25
 */
public class CommandsModel implements Serializable {

    private List<String> commandList;
    private String commandsCount;

    public CommandsModel() {
    }

    public CommandsModel(List<String> commandList, String commandsCount) {
        this.commandList = commandList;
        this.commandsCount = commandsCount;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<String> commandList) {
        this.commandList = commandList;
    }

    public String getCommandsCount() {
        return commandsCount;
    }

    public void setCommandsCount(String commandsCount) {
        this.commandsCount = commandsCount;
    }
}
