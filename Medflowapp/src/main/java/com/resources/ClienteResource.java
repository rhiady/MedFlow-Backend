package com.resources;

import com.domains.dtos.ClienteDTO;
import com.services.ClienteService;
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
@RequestMapping("/api/cliente")
public class ClienteResource {

    private final ClienteService service;

    public ClienteResource(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> list(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(
            @RequestBody @Validated(ClienteDTO.Create.class) ClienteDTO dto) {

        ClienteDTO created = service.create(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(ClienteDTO.Update.class) ClienteDTO dto) {

        dto.setId(id);

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
