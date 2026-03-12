package com.services;

import com.domains.*;
import com.domains.enums.CategoriaMedicamento;
import com.domains.enums.Funcao;
import com.domains.enums.TipoPagamento;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class DBService {

    @Autowired
    private FornecedorRepository fornecedorRepo;

    @Autowired
    private MedicamentoRepository medicamentoRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private VendaRepository vendaRepo;

    @Autowired
    private ItemVendaRepository itemVendaRepo;

    public void initDB(){

        Fornecedor forn01 = new Fornecedor(
                null,
                "Distribuidora Farma LTDA",
                "11111111000100",
                "17999990000",
                "fornecedor1@email.com",
                new ArrayList<>()
        );

        Fornecedor forn02 = new Fornecedor(
                null,
                "MedDistribuidora",
                "22222222000100",
                "17999991111",
                "fornecedor2@email.com",
                new ArrayList<>()
        );

        Medicamento med01 = new Medicamento(
                null,
                "Paracetamol",
                "Comprimido",
                "EMS",
                "500mg",
                new BigDecimal("5.50"),
                new BigDecimal("100"),
                CategoriaMedicamento.ANALGESICO,
                forn01,
                new ArrayList<>()
        );

        Medicamento med02 = new Medicamento(
                null,
                "Amoxicilina",
                "Comprimido",
                "Neo Química",
                "500mg",
                new BigDecimal("18.90"),
                new BigDecimal("80"),
                CategoriaMedicamento.ANTIBIOTICO,
                forn02,
                new ArrayList<>()
        );

        Medicamento med03 = new Medicamento(
                null,
                "Loratadina",
                "Comprimido",
                "Medley",
                "10mg",
                new BigDecimal("9.50"),
                new BigDecimal("120"),
                CategoriaMedicamento.ANTIALERGICO,
                forn01,
                new ArrayList<>()
        );

        Cliente cliente01 = new Cliente(
                null,
                "João da Silva",
                "11111111111",
                "17999992222",
                "cliente@email.com",
                new ArrayList<>()
        );

        Usuario usuario01 = new Usuario(
                null,
                "Maria Farmacêutica",
                "maria",
                "123456",
                Funcao.FARMACEUTICO,
                new ArrayList<>()
        );

        Venda venda01 = new Venda(
                null,
                LocalDateTime.now(),
                BigDecimal.ZERO,
                TipoPagamento.PIX,
                cliente01,
                usuario01,
                new ArrayList<>()
        );

        ItemVenda item01 = new ItemVenda(
                null,
                venda01,
                med01,
                new BigDecimal("2"),
                med01.getPreco()
        );

        ItemVenda item02 = new ItemVenda(
                null,
                venda01,
                med02,
                new BigDecimal("1"),
                med02.getPreco()
        );

        forn01.addMedicamento(med01);
        forn02.addMedicamento(med02);
        forn01.addMedicamento(med03);

        venda01.addItem(item01);
        venda01.addItem(item02);

        fornecedorRepo.save(forn01);
        fornecedorRepo.save(forn02);

        medicamentoRepo.save(med01);
        medicamentoRepo.save(med02);
        medicamentoRepo.save(med03);

        clienteRepo.save(cliente01);
        usuarioRepo.save(usuario01);

        vendaRepo.save(venda01);
    }
}