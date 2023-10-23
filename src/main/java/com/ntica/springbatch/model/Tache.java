package com.ntica.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "tache")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String priorite;
    private String etat;
    private String dateDebut;
    private String dateEcheance;
    private String pourcentage;
    private String note;
}
