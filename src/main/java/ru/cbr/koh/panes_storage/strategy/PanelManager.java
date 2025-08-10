package ru.cbr.koh.panes_storage.strategy;

import ru.cbr.koh.exceptions.SerializationException;
import ru.cbr.koh.main_window.SaveablePanel;
import ru.cbr.koh.panes_storage.PaneInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Менеджер для управления панелями
 */
public class PanelManager {
    
    private static final Logger logger = LogManager.getLogger(PanelManager.class);
    
    /**
     * Сохранить данные всех панелей, которые поддерживают сохранение
     * @param panels список панелей
     */
    public static void saveAllPanels(List<PaneInterface> panels) {
        panels.stream()
                .filter(panel -> panel instanceof SaveablePanel)
                .map(panel -> (SaveablePanel) panel)
                .forEach(saveablePanel -> {
                    try {
                        saveablePanel.saveData();
                        logger.info("Данные панели {} успешно сохранены", saveablePanel.getClass().getSimpleName());
                    } catch (SerializationException e) {
                        logger.error("Ошибка при сохранении данных панели {}", saveablePanel.getClass().getSimpleName(), e);
                    }
                });
    }
    
    /**
     * Найти панель по типу
     * @param panels список панелей
     * @param panelType тип панели
     * @return панель, если найдена
     */
    public static Optional<PanelContext> findPanelByType(List<PaneInterface> panels, PanelType panelType) {
        return panels.stream()
                .filter(panel -> panel instanceof PanelContext)
                .map(panel -> (PanelContext) panel)
                .filter(context -> context.getPanelType() == panelType)
                .findFirst();
    }
    
    /**
     * Проверить, содержит ли список панель указанного типа
     * @param panels список панелей
     * @param panelType тип панели
     * @return true, если панель найдена
     */
    public static boolean containsPanelType(List<PaneInterface> panels, PanelType panelType) {
        return findPanelByType(panels, panelType).isPresent();
    }
    
    /**
     * Получить количество панелей каждого типа
     * @param panels список панелей
     * @return количество панелей по типам
     */
    public static long countPanelsByType(List<PaneInterface> panels, PanelType panelType) {
        return panels.stream()
                .filter(panel -> panel instanceof PanelContext)
                .map(panel -> (PanelContext) panel)
                .filter(context -> context.getPanelType() == panelType)
                .count();
    }
}
