package ru.roman.bim.server.service.dataws;

import ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsRequest;
import ru.roman.bim.server.service.dataws.dto.settings.RegisterNewAndLoadSettingsResp;
import ru.roman.bim.server.service.dataws.dto.settings.StoreSettingsRequest;
import ru.roman.bim.server.service.dataws.dto.settings.StoreSettingsResp;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskRequest;
import ru.roman.bim.server.service.dataws.dto.system.SystemTaskResp;
import ru.roman.bim.server.service.dataws.dto.word.*;
import ru.roman.bim.server.service.dataws.jaxws.*;

/** @author Roman 07.01.13 23:48 */
public class DataProviderAdapter {

    private DataProvider dataProvider = new DataProvider();

    public SaveResponse save(Save request){
        final SaveRequest req = request.getArg0();
        final SaveResp res = dataProvider.save(req);
        final SaveResponse response = new SaveResponse();
        response.setReturn(res);
        return response;
    }

    public ru.roman.bim.server.service.dataws.jaxws.GetListResponse getList(GetList request){
        final GetListRequest req = request.getArg0();
        final GetListResp res = dataProvider.getList(req);
        final ru.roman.bim.server.service.dataws.jaxws.GetListResponse response = new ru.roman.bim.server.service.dataws.jaxws.GetListResponse();
        response.setReturn(res);
        return response;
    }

    public RenewRatingResponse renewRating(RenewRating request){
        final RenewRatingRequest req = request.getArg0();
        dataProvider.renewRating(req);
        final RenewRatingResponse response = new RenewRatingResponse();
        return response;
    }

    public RegisterNewAndLoadSettingsResponse registerNewAndLoadSettings(RegisterNewAndLoadSettings request){
        final RegisterNewAndLoadSettingsRequest req = request.getArg0();
        final RegisterNewAndLoadSettingsResp res = dataProvider.registerNewAndLoadSettings(req);
        final RegisterNewAndLoadSettingsResponse response = new RegisterNewAndLoadSettingsResponse();
        response.setReturn(res);
        return response;
    }

    public StoreSettingsResponse storeSettings(StoreSettings request){
        final StoreSettingsRequest req = request.getArg0();
        final StoreSettingsResp res = dataProvider.storeSettings(req);
        final StoreSettingsResponse response = new StoreSettingsResponse();
        response.setReturn(res);
        return response;
    }

    public SystemTaskResponse systemTask(SystemTask request){
        final SystemTaskRequest req = request.getArg0();
        final SystemTaskResp res = dataProvider.systemTask(req);
        final SystemTaskResponse response = new SystemTaskResponse();
        response.setReturn(res);
        return response;
    }
}
