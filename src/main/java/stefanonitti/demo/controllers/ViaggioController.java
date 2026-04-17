package stefanonitti.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import stefanonitti.demo.entities.Viaggio;
import stefanonitti.demo.payloads.ViaggioDTO;
import stefanonitti.demo.services.ViaggioService;

import java.util.UUID;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class ViaggioController {

    private final ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> getAllTravels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "data") String sortBy) {
        return viaggioService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createTravel(@RequestBody ViaggioDTO request) {
        Viaggio v = new Viaggio();
        v.setDestinazione(request.destinazione());
        v.setData(request.data());
        v.setStato(request.stato());

        return viaggioService.save(v);
    }

    @PutMapping("/{id}")
    public Viaggio update(@PathVariable Long id, @RequestBody ViaggioDTO request) {
        Viaggio dettagli = new Viaggio();
        dettagli.setDestinazione(request.destinazione());
        dettagli.setData(request.data());
        dettagli.setStato(request.stato());

        return viaggioService.update(id, dettagli);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        viaggioService.deleteById(id);
    }
}
