package com.infra;

import com.domains.enums.Funcao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FuncaoConverter implements AttributeConverter<Funcao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Funcao funcao) {
        return funcao == null ? null : funcao.getId();
    }

    @Override
    public Funcao convertToEntityAttribute(Integer dbValue) {
        return Funcao.toEnum(dbValue);
    }

}
