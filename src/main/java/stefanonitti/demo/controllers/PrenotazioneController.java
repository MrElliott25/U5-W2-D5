package stefanonitti.demo.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stefanonitti.demo.entities.Dipendente;
import stefanonitti.demo.entities.Prenotazione;
import stefanonitti.demo.entities.Viaggio;
import stefanonitti.demo.exceptions.ValidationException;
import stefanonitti.demo.payloads.PrenotazioneDTO;
import stefanonitti.demo.services.DipendenteService;
import stefanonitti.demo.services.PrenotazioneService;
import stefanonitti.demo.services.ViaggioService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;
    private final DipendenteService dipendenteService;
    private final ViaggioService viaggioService;

    @GetMapping
    public Page<Prenotazione> getAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataRichiesta") String sortBy) {
        return prenotazioneService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione getById(@PathVariable Long id) {
        return prenotazioneService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createReservation(@RequestBody @Validated PrenotazioneDTO request, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors);
        }
        Dipendente d = dipendenteService.findById(request.dipendenteId());
        Viaggio v = viaggioService.findById(request.viaggioId());
        Prenotazione p = new Prenotazione();
        p.setDipendente(d);
        p.setViaggio(v);
        p.setDataRichiesta(LocalDate.now());
        p.setNote(request.note());

        return prenotazioneService.save(p);
    }

    @PutMapping("/{id}")
    public Prenotazione update(@PathVariable Long id, @RequestBody PrenotazioneDTO request) {
        Prenotazione dettagli = new Prenotazione();
        dettagli.setNote(request.note());

        return prenotazioneService.update(id, dettagli);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        prenotazioneService.deleteById(id);
    }
}
