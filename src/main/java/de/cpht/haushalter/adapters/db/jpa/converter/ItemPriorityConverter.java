package de.cpht.haushalter.adapters.db.jpa.converter;

import de.cpht.haushalter.domain.entities.ItemPriority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemPriorityConverter implements AttributeConverter<ItemPriority, String> {

    @Override
    public String convertToDatabaseColumn(ItemPriority type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public ItemPriority convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ItemPriority.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
