package personal.zaytiri.taskerlyze.ui.logic.globals;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class UiGlobalFilter {
    private static UiGlobalFilter INSTANCE;
    private final PropertyChangeSupport support;
    private LocalDate activeDay = null;
    private int activeCategoryId = 0;
    private int activeProfileId;

    private UiGlobalFilter() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public int getActiveCategoryId() {
        return activeCategoryId;
    }

    public void setActiveCategoryId(int currentActiveCategoryId) {
        activeCategoryId = currentActiveCategoryId;
    }

    public LocalDate getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(LocalDate currentActiveDay) {
        activeDay = currentActiveDay;
        support.firePropertyChange("toReload", true, false);
    }

    public int getActiveProfileId() {
        return activeProfileId;
    }

    public void setActiveProfileId(int activeProfileId) {
        this.activeProfileId = activeProfileId;
        support.firePropertyChange("toReset", false, true);
    }

    public static UiGlobalFilter getUiGlobalFilter() {
        if (INSTANCE == null) {
            INSTANCE = new UiGlobalFilter();
        }
        return INSTANCE;
    }
}
