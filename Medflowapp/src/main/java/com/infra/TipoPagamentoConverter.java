package com.infra;

import com.domains.enums.TipoPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoPagamentoConverter implements AttributeConverter<TipoPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPagamento tipoPagamento) {
        return tipoPagamento == null ? null : tipoPagamento.getId();
    }

    @Override
    public TipoPagamento convertToEntityAttribute(Integer dbValue) {
        return TipoPagamento.toEnum(dbValue);
    }
}
