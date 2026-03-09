package ru.cbr.koh.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigurationSchemaValidatorTest {

    @Test
    void shouldRejectInvalidWindowSize() {
        ApplicationProperties properties = new StubApplicationProperties(0, 900, 'i', 11);
        assertThrows(IllegalStateException.class, () -> ConfigurationSchemaValidator.validate(properties));
    }

    @Test
    void shouldRejectInvalidExcelRowSelector() {
        ApplicationProperties properties = new StubApplicationProperties(900, 900, '\0', 11);
        assertThrows(IllegalStateException.class, () -> ConfigurationSchemaValidator.validate(properties));
    }

    @Test
    void shouldAcceptValidConfiguration() {
        ApplicationProperties properties = new StubApplicationProperties(900, 900, 'i', 11);
        assertDoesNotThrow(() -> ConfigurationSchemaValidator.validate(properties));
    }

    private static class StubApplicationProperties implements ApplicationProperties {

        private final int horizontalSize;
        private final int verticalSize;
        private final char rowSelector;
        private final int profileStartColumn;

        private StubApplicationProperties(int horizontalSize, int verticalSize, char rowSelector, int profileStartColumn) {
            this.horizontalSize = horizontalSize;
            this.verticalSize = verticalSize;
            this.rowSelector = rowSelector;
            this.profileStartColumn = profileStartColumn;
        }

        @Override
        public String get(String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(String key, String value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getHorizontalSize() {
            return horizontalSize;
        }

        @Override
        public int getVerticalSize() {
            return verticalSize;
        }

        @Override
        public String getTitle() {
            return "KOH";
        }

        @Override
        public String getAuthor() {
            return "author";
        }

        @Override
        public String getStoryNumber() {
            return "123";
        }

        @Override
        public String getStoryName() {
            return "story";
        }

        @Override
        public String getStoryKey() {
            return "key";
        }

        @Override
        public boolean getSaveAbacPolitics() {
            return true;
        }

        @Override
        public boolean getSaveAbacAttributeCode() {
            return true;
        }

        @Override
        public String getAbacFileName() {
            return "AbacProfiles.txt";
        }

        @Override
        public String getAbacAttributeCodeFilePath() {
            return "attributeCodeFilePath.txt";
        }

        @Override
        public boolean getFromExcel() {
            return true;
        }

        @Override
        public String getPathExcel() {
            return "/tmp";
        }

        @Override
        public char getExcelRowSelector() {
            return rowSelector;
        }

        @Override
        public void setExcelRowSelector(char rowSelector) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getExcelProfileStartColumn() {
            return profileStartColumn;
        }

        @Override
        public void setExcelProfileStartColumn(int profileStartColumn) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPathExcel(String pathExcel) {
            throw new UnsupportedOperationException();
        }
    }
}
