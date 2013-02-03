package ru.roman.bim.server.service.right;

import com.google.appengine.api.datastore.Entity;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.AbstractRequest;
import ru.roman.bim.server.service.dataws.dto.word.SaveRequest;
import ru.roman.bim.server.util.PropUtil;

/** @author Roman 03.02.13 13:45 */
public class RightsServiceImpl implements RightsService {
    @Override
    public void checkSavingRight(SaveRequest req) {
        if (req.getModel().getId() == null){
            return;
        } else if (checkUserIsMaster(req)) {
            return;
        }
        Entity word = WordDao.getWord(req.getModel().getId());
        if (PropUtil.getProperty(word, WordDao.OWNER) == req.getRequestInfo().getUserId()) {
            return;
        }
        throw createInsufficientException();
    }

    @Override
    public void checkAuthority(AbstractRequest req) {
        Entity user = UserSettingsDao.checkUserAuthority(req.getRequestInfo());
    }

    private boolean checkUserIsMaster(AbstractRequest req) {
        Entity master = UserSettingsDao.getMasterUser();
        if (master.getKey().getId() == req.getRequestInfo().getUserId()) {
            return true;
        }
        return false;
    }

    @Override
    public void checkMaster(AbstractRequest req) {
        if (!checkUserIsMaster(req)) {
            throw createInsufficientException();
        }
    }

    private RuntimeException createInsufficientException() {return new RuntimeException("Insufficient privileges");}

}
