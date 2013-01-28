package ru.roman.bim.server.service.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import ru.roman.bim.server.dao.UserSettingsDao;
import ru.roman.bim.server.dao.WordDao;
import ru.roman.bim.server.service.data.dto.settings.*;
import ru.roman.bim.server.service.data.dto.word.GetListRequest;
import ru.roman.bim.server.service.data.dto.word.GetListResp;
import ru.roman.bim.server.service.data.dto.word.RenewRatingRequest;
import ru.roman.bim.server.service.data.dto.word.SaveRequest;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Roman 07.01.13 17:22 */
@WebService
public class DataProvider {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @WebMethod
    public Long save(SaveRequest req){
        try {
            log.log(Level.INFO, "Saving item, params : " + ToStringBuilder.reflectionToString(req.getModel()));
            return WordDao.createOrUpdate(req.getModel());
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public GetListResp getList(GetListRequest req){
        try {
            log.log(Level.INFO, "Getting list, params : " + ToStringBuilder.reflectionToString(req));
            return WordDao.getWords(req);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public void renewRating(RenewRatingRequest req){
        try {
            log.log(Level.INFO, "Renew rating, params : " + ToStringBuilder.reflectionToString(req));
            WordDao.renewRating(req.getId(), req.getRating());
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public RegisterNewAndLoadSettingsResp registerNewAndLoadSettings(RegisterNewAndLoadSettingsRequest req){
        try {
            log.log(Level.INFO, "Load settings, params : " + ToStringBuilder.reflectionToString(req.getUserSettingsModel()));
            final UserSettingsModel res = UserSettingsDao.registerNewAndLoadSettings(req.getUserSettingsModel());
            return new RegisterNewAndLoadSettingsResp(res);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public StoreSettingsResp storeSettings(StoreSettingsRequest req){
        try {
            log.log(Level.INFO, "Save settings : " + ToStringBuilder.reflectionToString(req.getUserSettingsModel()));
            UserSettingsDao.storeSettings(req.getUserSettingsModel());
            return new StoreSettingsResp();
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }


}
