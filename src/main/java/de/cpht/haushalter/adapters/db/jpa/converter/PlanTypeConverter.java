package de.cpht.haushalter.adapters.db.jpa.converter;

import de.cpht.haushalter.domain.entities.PlanType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlanTypeConverter implements AttributeConverter<PlanType, String> {

    @Override
    public String convertToDatabaseColumn(PlanType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public PlanType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PlanType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
