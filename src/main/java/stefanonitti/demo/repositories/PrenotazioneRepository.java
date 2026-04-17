package stefanonitti.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stefanonitti.demo.entities.Prenotazione;

import java.time.LocalDate;
import java.util.UUID;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndViaggioData(UUID dipendenteId, LocalDate data);
}
