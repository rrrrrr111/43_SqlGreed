package ru.roman.bim.server.service.right;

import ru.roman.bim.server.service.dataws.dto.AbstractRequest;
import ru.roman.bim.server.service.dataws.dto.word.SaveRequest;

/** @author Roman 03.02.13 13:45 */
public interface RightsService {
    void checkSavingRight(SaveRequest req);

    void checkAuthority(AbstractRequest req);

    void checkMaster(AbstractRequest req);
}
