package ru.cbr.koh.panes_storage.panels.logger_proxy;

import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.main_window.SaveablePanel;
import ru.cbr.koh.panes_storage.PaneInterface;
import ru.cbr.koh.panes_storage.strategy.PanelContext;
import ru.cbr.koh.panes_storage.strategy.PanelType;
import ru.cbr.koh.panes_storage.strategy.PanelStrategyFactory;
import ru.cbr.koh.panes_storage.strategy.impl.LoggerProxyPanelStrategy;

import javax.swing.*;

/**
 * Панель Logger Proxy, использующая паттерн Strategy
 * @deprecated Используйте {@link PanelContext} с {@link LoggerProxyPanelStrategy}
 */
@Deprecated
public class LoggerProxyPanel implements PaneInterface, SaveablePanel {

    private final PanelContext context;

    public LoggerProxyPanel() {
        this.context = new PanelContext(PanelStrategyFactory.createStrategy(PanelType.LOGGER_PROXY));
    }

    @Override
    public String getTitle() {
        return context.getTitle();
    }

    @Override
    public JComponent createPanel(JFrame frame) {
        return context.createPanel(frame);
    }

    @Override
    public void saveData() throws SerializationException {
        context.saveData();
    }
    
    /**
     * @deprecated Используйте метод saveData() для сохранения данных панели
     */
    @Deprecated
    public static void saveDossierKoDirectory() {
        // Этот метод оставлен для обратной совместимости, но не должен использоваться
        throw new UnsupportedOperationException("Используйте инстансный метод saveData() вместо статического saveDossierKoDirectory()");
    }
}
