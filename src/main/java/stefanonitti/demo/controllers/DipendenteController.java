package stefanonitti.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stefanonitti.demo.entities.Dipendente;
import stefanonitti.demo.payloads.DipendenteDTO;
import stefanonitti.demo.services.DipendenteService;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class DipendenteController {

    private final DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "cognome") String sortBy) {
        return dipendenteService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createEmployee(@RequestBody @Validated DipendenteDTO request) {
        Dipendente d = new Dipendente();
        d.setUsername(request.username());
        d.setNome(request.nome());
        d.setCognome(request.cognome());
        d.setEmail(request.email());

        return dipendenteService.save(d);
    }

    @GetMapping("/{id}")
    public Dipendente getById(@PathVariable UUID id) {
        return dipendenteService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        dipendenteService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Dipendente update(@PathVariable UUID id, @RequestBody @Validated DipendenteDTO request) {
        Dipendente dettagli = new Dipendente();
        dettagli.setUsername(request.username());
        dettagli.setNome(request.nome());
        dettagli.setCognome(request.cognome());
        dettagli.setEmail(request.email());

        return dipendenteService.update(id, dettagli);
    }
}
