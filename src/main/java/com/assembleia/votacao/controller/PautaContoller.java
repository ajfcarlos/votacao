package com.assembleia.votacao.controller;

import com.assembleia.votacao.domain.Pauta;
import com.assembleia.votacao.service.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("pauta")
public class PautaContoller {

    @Autowired
    PautaRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("novaPauta")
    public PautaResponseDTO novaPauta(@RequestBody PautaDTO pautaDTO) {

        Pauta pauta = new Pauta(pautaDTO);
        Pauta saved = repository.save(pauta);
        return  new PautaResponseDTO(saved.getId(), saved.getDescricao());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("openSession")
    public String openSession(@RequestBody SessionDTO sessionDTO) {
        ///open sessinon
       Optional<Pauta> pauta = repository.findById(sessionDTO.pautaId());
       if(pauta.isPresent() && pauta.get().getOpenSessao() == null){
           Pauta current = pauta.get();
           current.setOpenSessao(new Date());
           if(sessionDTO.timeout() != null){
               current.setTimeout(sessionDTO.timeout());
           }else{
               current.setTimeout(1L);
           }
           current.setContra(0L);
           current.setFavor(0L);
           repository.save(current);
           return "Sessão aberta";
       }else{
           return "Pauta não existe ou sessão já fechada";
       }
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("vota")
    public String vota(@RequestBody VotacaoDTO votacaoDTO) {
        Optional<Pauta> pauta = repository.findById(votacaoDTO.pautaId());
        if (pauta.isPresent() && pauta.get().getOpenSessao() != null) {
            long diferenca = new Date().getTime() - pauta.get().getOpenSessao().getTime();
            long diferenca_minutos = (diferenca / (1000 * 60)) % 60;
            if (pauta.get().getTimeout() >= diferenca_minutos) {
                Pauta current = pauta.get();
                if (votacaoDTO.voto().equals("S")) {
                    current.incrementaFavor();
                } else if (votacaoDTO.voto().equals("N")) {
                    current.incrementaContra();
                }
                repository.save(current);
                return "Vota computado";
            } else {
                return "timeout session";
            }
        }
        return "Pauta invalida";
    }
}