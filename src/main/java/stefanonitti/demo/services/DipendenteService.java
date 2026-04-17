package stefanonitti.demo.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stefanonitti.demo.entities.Dipendente;
import stefanonitti.demo.exceptions.EmailInUseException;
import stefanonitti.demo.exceptions.NotFoundException;
import stefanonitti.demo.repositories.DipendenteRepository;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente save(Dipendente dipendente) {
        if (dipendenteRepository.existsByEmail(dipendente.getEmail())) {
            throw new EmailInUseException("L'email " + dipendente.getEmail() + " è già in uso!");
        }
        return dipendenteRepository.save(dipendente);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    public Dipendente update(UUID id, Dipendente dipendenteDettagli) {
        Dipendente dipendente = findById(id);
        dipendente.setNome(dipendenteDettagli.getNome());
        dipendente.setCognome(dipendenteDettagli.getCognome());
        dipendente.setEmail(dipendenteDettagli.getEmail());
        dipendente.setUsername(dipendenteDettagli.getUsername());
        dipendente.setPropicUrl(dipendenteDettagli.getPropicUrl());
        return dipendenteRepository.save(dipendente);
    }

    public void deleteById(UUID id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: dipendente non trovato");
        }
        dipendenteRepository.deleteById(id);
    }

    public void avatarUpload(MultipartFile file, UUID id) {

        try {
            Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");
            System.out.println(url);
            Dipendente found = dipendenteRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(id));
            found.setPropicUrl(url);
            this.update(id, found);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
