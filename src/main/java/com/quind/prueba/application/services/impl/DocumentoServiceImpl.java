package com.quind.prueba.application.services.impl;

import com.quind.prueba.application.services.DocumentoService;
import com.quind.prueba.domain.model.Documento;
import com.quind.prueba.domain.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;

    @Override
    public List<Documento> findAll() {
        return documentoRepository.findAll();
    }
}
