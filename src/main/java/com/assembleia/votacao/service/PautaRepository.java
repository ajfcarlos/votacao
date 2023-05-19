package com.assembleia.votacao.service;

import com.assembleia.votacao.domain.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta,Long> {
}
