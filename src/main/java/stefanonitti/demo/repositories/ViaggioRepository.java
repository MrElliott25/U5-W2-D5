package stefanonitti.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stefanonitti.demo.entities.Viaggio;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}
