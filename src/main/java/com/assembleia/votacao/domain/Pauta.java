package com.assembleia.votacao.domain;

import com.assembleia.votacao.controller.PautaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;


@Table(name = "pautas")
@Entity(name = "pautas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pauta {
    @Id
    @SequenceGenerator(name="pauta_seq", sequenceName="pauta_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pauta_seq")
    public  Long id;
    public String descricao;

    @Column(columnDefinition = "integer default 0")
    private Long favor;

    @Column(columnDefinition = "integer default 0")
    private Long contra;

    @Column(columnDefinition = "integer default 1")
    private Long timeout;

    public Date openSessao;
    public Pauta(PautaDTO pautaDTO){
        this.descricao = pautaDTO.descricao();
    }

    public void incrementaFavor(){
        this.favor = this.favor + 1L;
    }

    public void incrementaContra(){
        this.contra = this.contra + 1L;
    }

}
