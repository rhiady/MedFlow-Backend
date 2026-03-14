package com.resources;

import com.domains.dtos.ItemVendaDTO;
import com.services.ItemVendaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/item-venda")
public class ItemVendaResource {

    private final ItemVendaService service;

    public ItemVendaResource(ItemVendaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ItemVendaDTO>> list(
            @PageableDefault(size = 20) Pageable pageable) {

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemVendaDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVendaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemVendaDTO> create(@RequestBody ItemVendaDTO dto) {

        ItemVendaDTO created = service.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVendaDTO> update(@PathVariable Long id,
                                               @RequestBody ItemVendaDTO dto) {

        dto.setId(id);

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
