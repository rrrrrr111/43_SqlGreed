package ru.roman.bim.server.service.dataws;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.roman.bim.server.dss.dao.UserSettingsDao;
import ru.roman.bim.server.dss.dao.WordDao;
import ru.roman.bim.server.service.dataws.dto.settings.*;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskResp;
import ru.roman.bim.server.service.dataws.dto.word.*;
import ru.roman.bim.server.service.right.RightsService;
import ru.roman.bim.server.service.right.RightsServiceImpl;
import ru.roman.bim.server.service.systask.SystemService;
import ru.roman.bim.server.service.systask.SystemServiceImpl;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Roman 07.01.13 17:22
 *
 * ������� ������ Web-�������:
 * 1. ������ ������ � ���� ������ � � ��������� ��������� ����, ��� DTO ������ � ����� dto
 *    ��� ������� ������ ������ ...Resp � ...Request, ����� Response �� ����������,
 *    �.�. ������������� ����������� ��� ���������� ��� ����� ���� ����.
 * 2. �� ������������ ��� � DataProviderAdapter ��������� � �������� ���.
 * 3. ��������� RunWsGen.cmd
 * 4. ���������� ����� DataProviderAdapter � ����� ������ ������ DataProviderSOAPHandler ���� ����� ������.
 * 5. Web ������ ������������ � �������� �� ������ �������, �.�. WsGen ������ ���������� ������������ ��������� Java.
 * 6. ��������� Web ������ ��� Ant, ������� runserver, ������� ��� � ���� � ����.
 * 7. ��������� RunWsImport.cmd
 * 8  �������� URL � greet-web\war\wsdl\DataProvider.wsdl
 * 9. ������ ��� �� ������� ��� ����� ���������.
 * 10. ����������� ������ �� ������ �����, �� �� ����� ��������� �������, ��������� ����������������.
 * 11. ���� �������� ������ ����������� ����� Castor, ������ ������ CastorMappings.xml
 *
 * */
@WebService
public class DataProvider {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final RightsService rightsService = new RightsServiceImpl();
    private final SystemService systemService = new SystemServiceImpl();

    @WebMethod
    public SaveResp save(SaveRequest req){
        try {
            log.log(Level.INFO, "Saving item, params : " + ToStringBuilder.reflectionToString(req.getModel()));
            rightsService.checkAuthority(req);
            rightsService.checkSavingRight(req);
            final SaveResp resp = WordDao.createOrUpdate(req.getModel());
            log.log(Level.INFO, "Saving item complete : " + ToStringBuilder.reflectionToString(resp));
            return resp;
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
            final GetListResp resp = WordDao.getWords(req);
            log.log(Level.INFO, "Getting list complete : recordsCount=" + resp.getRecordsCount() + ", " +
                    "listSize=" + resp.getList().size());
            return resp;
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
            log.log(Level.INFO, "Renew complete");
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
            log.log(Level.INFO, "Load settings complete : " + ToStringBuilder.reflectionToString(res));
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
            log.log(Level.INFO, "Save settings complete");
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
            final SystemTaskResp resp = systemService.systemTask(req);
            log.log(Level.INFO, "System task complete : result=" + resp.getResultCount());
            return resp;
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "", e);
            throw e;
        }
    }
}
