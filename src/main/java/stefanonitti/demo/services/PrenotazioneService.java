package stefanonitti.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stefanonitti.demo.entities.Prenotazione;
import stefanonitti.demo.exceptions.NotFoundException;
import stefanonitti.demo.repositories.PrenotazioneRepository;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Prenotazione save(Prenotazione prenotazione) {
        if (prenotazioneRepository.existsByDipendenteIdAndViaggioData(
                prenotazione.getDipendente().getId(),
                prenotazione.getViaggio().getData())) {
            throw new RuntimeException("Hai già una prenotazione per questa data!");
        }
        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Prenotazione update(Long id, Prenotazione prenotazioneDettagli) {
        Prenotazione prenotazione = findById(id);

        prenotazione.setNote(prenotazioneDettagli.getNote());

        return prenotazioneRepository.save(prenotazione);
    }

    public void deleteById(Long id) {
        if (!prenotazioneRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: prenotazione non trovata");
        }
        prenotazioneRepository.deleteById(id);
    }
}
