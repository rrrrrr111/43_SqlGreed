package ru.roman.bim.server.service.complex;

import ru.roman.bim.server.service.data.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.data.dto.system.SystemTaskResp;

/** @author Roman 29.01.13 23:08 */
public interface SystemService {


    SystemTaskResp systemTask(SystemTaskRequest req);
}
