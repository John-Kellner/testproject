package com.project.presentation.server.tx.dom;

import com.project.presentation.shared.dto.AnlageDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 13.05.2016.
 */

@Entity
@Table(name = "Anlagen", uniqueConstraints = {@UniqueConstraint(columnNames = {"filename","FK_einstellung"})})
public class AnlageDOM {

    @Id
    @GeneratedValue
    @Column(name = "PK_Anlagen")
    private long id;

    private String filename;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_einstellung", nullable = false)
    private EinstellungDOM einstellung;

    public long getId() {
        return id;
    }

    public void setId(long zeugnisId) {
        this.id = zeugnisId;
    }

    public String getPDFFileName() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public EinstellungDOM getEinstellung() {
        return einstellung;
    }

    public void setEinstellung(EinstellungDOM einstellung) {
        this.einstellung = einstellung;
    }

    /**
     * Convert List<DOM> to List<DTO>
     * @param anlagen
     * @return
     */
    public static List<AnlageDTO> convert(final List<AnlageDOM> anlagen) {
        if (anlagen == null) return new ArrayList<>();

        List<AnlageDTO> anlagenDTOs = new ArrayList<>();
        for (AnlageDOM anlageDOM : anlagen) {
            final AnlageDTO anlage = new AnlageDTO();
            anlage.setId(anlageDOM.getId());
            anlage.setFilename(anlageDOM.getPDFFileName());
            anlage.setEinstellungID(anlageDOM.getEinstellung().getEinstellungId());
            anlagenDTOs.add(anlage);
        }
        return anlagenDTOs;
    }

    /**
     * Convert a single DOM to DTO
     * @param anlageDOM
     * @return
     */
    public static AnlageDTO convert(final AnlageDOM anlageDOM) {
        if (anlageDOM == null) return null;

        final AnlageDTO anlageDTO = new AnlageDTO();
        anlageDTO.setId(anlageDOM.getId());
        anlageDTO.setFilename(anlageDOM.getPDFFileName());
        anlageDTO.setEinstellungID(anlageDOM.getEinstellung().getEinstellungId());

        return anlageDTO;
    }
}
