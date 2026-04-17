package stefanonitti.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanonitti.demo.entities.Dipendente;
import stefanonitti.demo.repositories.DipendenteRepository;

import java.util.UUID;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Dipendente save(Dipendente dipendente) {
        return dipendenteRepository.save(dipendente);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dipendente con id " + id + " non trovato"));
    }

    public Dipendente update(UUID id, Dipendente dipendenteDettagli) {
        Dipendente dipendente = findById(id);
        dipendente.setNome(dipendenteDettagli.getNome());
        dipendente.setCognome(dipendenteDettagli.getCognome());
        dipendente.setEmail(dipendenteDettagli.getEmail());
        dipendente.setUsername(dipendenteDettagli.getUsername());
        return dipendenteRepository.save(dipendente);
    }

    public void deleteById(UUID id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: dipendente non trovato");
        }
        dipendenteRepository.deleteById(id);
    }
}
