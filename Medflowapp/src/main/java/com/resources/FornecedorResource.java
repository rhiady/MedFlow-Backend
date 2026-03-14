package com.resources;

import com.domains.dtos.FornecedorDTO;
import com.services.FornecedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedor")
public class FornecedorResource {

    private final FornecedorService service;

    public FornecedorResource(FornecedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> list(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FornecedorDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorDTO> create(@RequestBody FornecedorDTO dto) {

        FornecedorDTO created = service.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> update(@PathVariable Long id, @RequestBody FornecedorDTO dto) {

        dto.setId(id);

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
