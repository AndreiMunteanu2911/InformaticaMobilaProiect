package com.example.informaticamobilaproiect.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entitate Room pentru tabela 'produse'
 * Prima tabela cu 3 campuri: nume, pret, categorie
 */
@Entity(tableName = "produse")
data class Produs(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val nume: String,
    
    val pret: Double,
    
    val categorie: String
)

/**
 * Entitate Room pentru tabela 'comenzi'
 * A doua tabela legata de prima cu 3 campuri: produs_id, cantitate, status
 */
@Entity(tableName = "comenzi")
data class Comanda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val produsId: Long,
    
    val cantitate: Int,
    
    val dataComanda: String,
    
    val status: String
)
