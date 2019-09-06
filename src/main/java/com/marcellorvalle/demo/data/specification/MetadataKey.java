package com.marcellorvalle.demo.data.specification;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa uma chave capaz de identificar a combinação filtro/builder e pode ser usada em um Map.
 */
class MetadataKey implements Serializable {
    private final String filterName;
    private final Class<? extends SpecificationBuilder> forClass;

    private MetadataKey(
            String filterName,
            Class<? extends SpecificationBuilder> forClass) {
        this.filterName = filterName;
        this.forClass = forClass;
    }

    static MetadataKey create(String filterName, Class<? extends SpecificationBuilder> forClass) {
        return new MetadataKey(filterName, forClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterName, forClass);
    }

    @Override
    public boolean equals(Object other) {
        if (!MetadataKey.class.isInstance(other)) {
            return false;
        }

        final MetadataKey metadataKey = MetadataKey.class.cast(other);

        return filterName.equals(metadataKey.filterName)
                && forClass.equals(metadataKey.forClass);
    }
}
