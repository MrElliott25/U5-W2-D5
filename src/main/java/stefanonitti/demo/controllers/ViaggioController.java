package stefanonitti.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stefanonitti.demo.entities.Viaggio;
import stefanonitti.demo.exceptions.ValidationException;
import stefanonitti.demo.payloads.ViaggioDTO;
import stefanonitti.demo.services.ViaggioService;

import java.util.List;

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
    public Viaggio createTravel(@RequestBody @Validated ViaggioDTO request, BindingResult validationResult) {
        if(validationResult.hasErrors()){
            List<String> errors = validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            throw new ValidationException(errors);
        }
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
