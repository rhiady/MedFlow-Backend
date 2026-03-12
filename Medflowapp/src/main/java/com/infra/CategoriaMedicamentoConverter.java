package com.infra;

import com.domains.enums.CategoriaMedicamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CategoriaMedicamentoConverter implements AttributeConverter<CategoriaMedicamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoriaMedicamento categoria) {
        return categoria == null ? null : categoria.getId();
    }

    @Override
    public CategoriaMedicamento convertToEntityAttribute(Integer dbValue) {
        return CategoriaMedicamento.toEnum(dbValue);
    }
}
