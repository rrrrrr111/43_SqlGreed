package ru.roman.bim.gui.custom.widget;

import ru.roman.bim.gui.common.cbchain.CallBackChain;
import ru.roman.bim.util.GuiUtil;

/** @author Roman 30.03.13 11:15 */
public class TransparentWindowSupportTest {


    public static void main(String[] args) {
        GuiUtil.startSwingApp(new CallBackChain<Void>() {
            @Override
            public void onSuccess(Void result) {
                new TransparentWindowSupport();
            }
        });
    }
}
