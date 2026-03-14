package com.services;

import com.domains.Cliente;
import com.domains.ItemVenda;
import com.domains.Usuario;
import com.domains.Venda;
import com.domains.dtos.VendaDTO;
import com.mappers.ItemVendaMapper;
import com.mappers.VendaMapper;
import com.repositories.ClienteRepository;
import com.repositories.MedicamentoRepository;
import com.repositories.UsuarioRepository;
import com.repositories.VendaRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VendaService {

    private static final int MAX_PAGE_SIZE = 200;

    private final VendaRepository vendaRepo;
    private final ClienteRepository clienteRepo;
    private final UsuarioRepository usuarioRepo;
    private final MedicamentoRepository medicamentoRepo;

    public VendaService(VendaRepository vendaRepo,
                        ClienteRepository clienteRepo,
                        UsuarioRepository usuarioRepo,
                        MedicamentoRepository medicamentoRepo) {
        this.vendaRepo = vendaRepo;
        this.clienteRepo = clienteRepo;
        this.usuarioRepo = usuarioRepo;
        this.medicamentoRepo = medicamentoRepo;
    }

    /* =================== READ =================== */

    @Transactional
    public List<VendaDTO> findAll() {
        return VendaMapper.toDtoList(vendaRepo.findAll());
    }

    @Transactional
    public Page<VendaDTO> findAll(Pageable pageable) {

        final Pageable effective;

        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        } else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<Venda> page = vendaRepo.findAll(effective);
        return VendaMapper.toDtoPage(page);
    }

    @Transactional
    public VendaDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da venda é obrigatório");
        }

        return vendaRepo.findById(id)
                .map(VendaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Venda não encontrada: id=" + id));
    }

    /* =================== CREATE =================== */

    @Transactional
    public VendaDTO create(VendaDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da venda são obrigatórios");
        }

        dto.setId(null);

        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cliente não encontrado: id=" + dto.getClienteId()));

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + dto.getUsuarioId()));

        List<ItemVenda> itens = ItemVendaMapper.toEntityList(
                dto.getItens(),
                id -> medicamentoRepo.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException("Medicamento não encontrado: id=" + id))
        );

        Venda venda = VendaMapper.toEntity(dto, cliente, usuario, itens);

        return VendaMapper.toDto(vendaRepo.save(venda));
    }

    /* =================== UPDATE =================== */

    @Transactional
    public VendaDTO update(Long id, VendaDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da venda são obrigatórios");
        }

        Venda venda = vendaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Venda não encontrada: id=" + id));

        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cliente não encontrado: id=" + dto.getClienteId()));

        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + dto.getUsuarioId()));

        List<ItemVenda> itens = ItemVendaMapper.toEntityList(
                dto.getItens(),
                mid -> medicamentoRepo.findById(mid)
                        .orElseThrow(() -> new ObjectNotFoundException("Medicamento não encontrado: id=" + mid))
        );

        VendaMapper.copyToEntity(dto, venda, cliente, usuario, itens);

        return VendaMapper.toDto(vendaRepo.save(venda));
    }

    /* =================== DELETE =================== */

    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Venda venda = vendaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Venda não encontrada: id=" + id));

        vendaRepo.delete(venda);
    }
}
