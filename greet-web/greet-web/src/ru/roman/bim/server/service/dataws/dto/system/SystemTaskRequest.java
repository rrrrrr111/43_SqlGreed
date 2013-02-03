package ru.roman.bim.server.service.dataws.dto.system;

import ru.roman.bim.server.service.dataws.dto.AbstractRequest;

/** @author Roman 29.01.13 23:04 */
public class SystemTaskRequest extends AbstractRequest {

    private int taskNum;
    private String params;


    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }


    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
