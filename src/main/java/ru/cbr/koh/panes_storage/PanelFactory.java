package ru.cbr.koh.panes_storage;

import ru.cbr.koh.panes_storage.strategy.PanelType;
import ru.cbr.koh.panes_storage.strategy.PanelStrategy;
import ru.cbr.koh.panes_storage.strategy.PanelStrategyFactory;
import ru.cbr.koh.panes_storage.strategy.PanelContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Фабрика для создания панелей
 */
public class PanelFactory {
    
    /**
     * Создать все панели по умолчанию
     */
    public static List<PaneInterface> createDefaultPanels() {
        return Stream.of(PanelType.values())
                .map(PanelStrategyFactory::createStrategy)
                .map(PanelContext::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Создать панель по типу
     */
    public static PaneInterface createPanel(PanelType panelType) {
        PanelStrategy strategy = PanelStrategyFactory.createStrategy(panelType);
        return new PanelContext(strategy);
    }
    
    /**
     * Создать панель по стратегии
     */
    public static PaneInterface createPanel(PanelStrategy strategy) {
        return new PanelContext(strategy);
    }
}

