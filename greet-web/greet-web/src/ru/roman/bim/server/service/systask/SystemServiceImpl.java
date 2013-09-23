package ru.roman.bim.server.service.systask;

import ru.roman.bim.server.service.systask.task.*;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskResp;

import java.util.HashMap;
import java.util.Map;

/** @author Roman 29.01.13 23:08 */
public class SystemServiceImpl implements SystemService{

    private final static Map<Integer, SystemTask> TASKS = new HashMap<Integer, SystemTask>();
    static {
        TASKS.put(1, new NormalizeWordsTask());
        TASKS.put(2, new CreateCustomChecks());
        TASKS.put(3, new PrepareWordsExamplesTask());
        TASKS.put(4, new CheckUserRatingsConsistencyTask());
    }

    @Override
    public SystemTaskResp systemTask(final SystemTaskRequest req) {

        if(TASKS.containsKey(req.getTaskNum())) {
            final int res = TASKS.get(req.getTaskNum()).executeTask(req.getTaskNum(), req.getParams(), req);
            final SystemTaskResp resp = new SystemTaskResp();
            resp.setResultCount(res);
            return resp;
        } else {
            throw new IllegalArgumentException(String.format("Task %s doesn't exists", req.getTaskNum()));
        }
    }
}
