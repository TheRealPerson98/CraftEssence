package com.person98.commonsessence.invtory.menu;

import com.person98.commonsessence.invtory.menu.EssenceMenuElement;
import com.person98.craftessence.util.builder.IBuilder;

import java.util.ArrayList;
import java.util.List;

public class ConfigurableMenu {
    private final String menuTitle;
    private final String name;
    private final List<String> menuDesign;
    private final List<EssenceMenuElement> elements;
    private final int updateTicks;

    // Private constructor to ensure the use of the builder
    private ConfigurableMenu(Builder builder) {
        this.menuTitle = builder.menuTitle;
        this.name = builder.name;
        this.menuDesign = builder.menuDesign;
        this.elements = builder.elements;
        this.updateTicks = builder.updateTicks;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getName() {
        return name;
    }

    public List<String> getMenuDesign() {
        return menuDesign;
    }

    public List<EssenceMenuElement> getElements() {
        return elements;
    }

    public int getUpdateTicks() {
        return updateTicks;
    }

    public static class Builder implements IBuilder<ConfigurableMenu> {
        private String menuTitle;
        private String name;
        private List<String> menuDesign = new ArrayList<>();
        private List<EssenceMenuElement> elements = new ArrayList<>();
        private int updateTicks;

        public Builder menuTitle(String menuTitle) {
            this.menuTitle = menuTitle;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder menuDesign(String... layout) {
            for (String row : layout) {
                this.menuDesign.add(row);
            }
            return this;
        }

        public Builder item(EssenceMenuElement element) {
            this.elements.add(element);
            return this;
        }

        public Builder updateTicks(int updateTicks) {
            this.updateTicks = updateTicks;
            return this;
        }

        public ConfigurableMenu build() {
            return new ConfigurableMenu(this);
        }
    }

}
