package ru.roman.bim.server.service.data.dto.system;

import ru.roman.bim.server.service.data.dto.AbstractRequest;

/** @author Roman 29.01.13 23:04 */
public class SystemTaskRequest extends AbstractRequest {

    private int taskNum;


    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }
}
