package stefanonitti.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stefanonitti.demo.entities.Dipendente;
import stefanonitti.demo.exceptions.ValidationException;
import stefanonitti.demo.payloads.DipendenteDTO;
import stefanonitti.demo.services.DipendenteService;

import java.util.List;
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
    public Dipendente createEmployee(@RequestBody @Validated DipendenteDTO request, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors);
        }
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

    @PatchMapping("/{id}/avatar")
    @ResponseStatus(HttpStatus.OK)
    public void uploadAvatar(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        dipendenteService.avatarUpload(file, id);
    }
}
