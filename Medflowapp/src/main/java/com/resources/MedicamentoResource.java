package com.resources;

import com.domains.dtos.MedicamentoDTO;
import com.services.MedicamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicamento")
public class MedicamentoResource {

    private final MedicamentoService service;

    public MedicamentoResource(MedicamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<MedicamentoDTO>> list(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicamentoDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MedicamentoDTO> create(@RequestBody MedicamentoDTO dto) {

        MedicamentoDTO created = service.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoDTO> update(@PathVariable Long id, @RequestBody MedicamentoDTO dto) {

        dto.setId(id);

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
