package stefanonitti.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanonitti.demo.entities.Viaggio;
import stefanonitti.demo.repositories.ViaggioRepository;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepository;

    public Viaggio save(Viaggio viaggio) {
        return viaggioRepository.save(viaggio);
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(Long id) {
        return viaggioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaggio con id " + id + " non trovato"));
    }

    public Viaggio update(Long id, Viaggio viaggioDettagli) {
        Viaggio viaggio = findById(id);
        viaggio.setDestinazione(viaggioDettagli.getDestinazione());
        viaggio.setData(viaggioDettagli.getData());
        viaggio.setStato(viaggioDettagli.getStato());
        return viaggioRepository.save(viaggio);
    }

    public void deleteById(Long id) {
        if (!viaggioRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: viaggio non trovato");
        }
        viaggioRepository.deleteById(id);
    }
}
