package ru.roman.bim.gui.pane.edit;

import ru.roman.bim.gui.pane.PaineFactory;
import ru.roman.bim.service.cache.LocalCacheFactory;
import ru.roman.bim.util.GuiUtils;

/** @author Roman 22.12.12 13:30 */
public class EditViewTest {




    public static void main(String args[]) {

        GuiUtils.startSwingApp(new GuiUtils.Starter() {
            @Override
            public void onStart() {
                PaineFactory.getEditViewController().show(
                    LocalCacheFactory.createLocalCacheInstance(0, 0));
            }
        });

    }
}
