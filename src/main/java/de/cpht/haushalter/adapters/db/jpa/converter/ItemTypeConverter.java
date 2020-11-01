package de.cpht.haushalter.adapters.db.jpa.converter;

import de.cpht.haushalter.domain.entities.ItemType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {

    @Override
    public String convertToDatabaseColumn(ItemType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public ItemType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ItemType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
