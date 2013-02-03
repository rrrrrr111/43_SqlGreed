package ru.roman.bim.server.service.systask;

import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskResp;

/** @author Roman 29.01.13 23:08 */
public interface SystemService {


    SystemTaskResp systemTask(SystemTaskRequest req);
}
