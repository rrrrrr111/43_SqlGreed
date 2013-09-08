package ru.roman.bim.service.translate.rotator;

import ru.roman.bim.gui.common.cbchain.CircularCallBackChain;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.translate.TranslationService;

/** @author Roman 07.09.13 2:29 */
public class RotationServiceImpl implements RotationService {

    private final TranslationService yaTranslator = ServiceFactory.getYandexService();
    private final TranslationService gooTranslator = ServiceFactory.getGoogleService();

    private CircularCallBackChain circularChain;

    {
        circularChain =
                new CircularCallBackChain(
                    new CircularCallBackChain(
                        new CircularCallBackChain(
                            new CircularCallBackChain() {
                            @Override
                            protected void onSuccess(Object result) {
                                throw new RuntimeException("not implemented");
                            }
                        }) {
                    @Override
                    protected void onSuccess(Object result) {
                        throw new RuntimeException("not implemented");
                    }
                }) {
            @Override
            protected void onSuccess(Object result) {
                throw new RuntimeException("not implemented");
            }
        }) {
            @Override
            protected void onSuccess(Object result) {
                throw new RuntimeException("not implemented");
            }
                    {
                        closeChain();
                    }
        };
    }




    @Override
    public String getFirstValidTranslation() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getNextTranslation() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean wasLastTranslation() {
        throw new RuntimeException("not implemented");
    }
}
