package stefanonitti.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stefanonitti.demo.entities.Dipendente;

import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {
}
