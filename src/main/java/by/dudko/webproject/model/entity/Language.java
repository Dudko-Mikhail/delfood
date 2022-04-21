package by.dudko.webproject.model.entity;

import java.util.Locale;

public class Language extends RootEntity<Integer> {
    private final String name;

    public Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Locale toLocale() {
        return Locale.forLanguageTag(name.replace("_", "-"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Language language = (Language) o;

        return name != null ? name.equals(language.name) : language.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Language{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
