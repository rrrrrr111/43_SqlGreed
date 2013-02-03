package ru.roman.bim.server.service.dataws;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.settings.*;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskResp;
import ru.roman.bim.server.service.dataws.dto.word.GetListRequest;
import ru.roman.bim.server.service.dataws.dto.word.GetListResp;
import ru.roman.bim.server.service.dataws.dto.word.RenewRatingRequest;
import ru.roman.bim.server.service.dataws.dto.word.SaveRequest;
import ru.roman.bim.server.service.right.RightsService;
import ru.roman.bim.server.service.right.RightsServiceImpl;
import ru.roman.bim.server.service.systask.SystemService;
import ru.roman.bim.server.service.systask.SystemServiceImpl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Roman 07.01.13 17:22 */
@WebService
public class DataProvider {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final RightsService rightsService = new RightsServiceImpl();
    private final SystemService systemService = new SystemServiceImpl();

    @WebMethod
    public Long save(SaveRequest req){
        try {
            log.log(Level.INFO, "Saving item, params : " + ToStringBuilder.reflectionToString(req.getModel()));
            rightsService.checkAuthority(req);
            rightsService.checkSavingRight(req);
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
            rightsService.checkAuthority(req);
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
            rightsService.checkAuthority(req);
            WordDao.renewRating(req.getId(), req.getRating(), req.getRequestInfo().getUserId());
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
            log.log(Level.INFO, "Save settings, params : " + ToStringBuilder.reflectionToString(req.getUserSettingsModel()));
            rightsService.checkAuthority(req);
            UserSettingsDao.storeSettings(req.getUserSettingsModel());
            return new StoreSettingsResp();
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }

    @WebMethod
    public SystemTaskResp systemTask(SystemTaskRequest req){
        try {
            log.log(Level.INFO, "System task, params : " + ToStringBuilder.reflectionToString(req));
            rightsService.checkAuthority(req);
            rightsService.checkMaster(req);
            return systemService.systemTask(req);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }


}
