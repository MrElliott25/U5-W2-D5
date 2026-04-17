package stefanonitti.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private String email;
    @Column(name = "profile_picture_url")
    private String propicUrl;
}
