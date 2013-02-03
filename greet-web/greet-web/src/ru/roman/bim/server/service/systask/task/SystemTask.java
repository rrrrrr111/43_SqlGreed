package ru.roman.bim.server.service.systask.task;

import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;

/** @author Roman 03.02.13 12:17 */
public interface SystemTask {

    int executeTask(int num, String params, SystemTaskRequest req);

}
