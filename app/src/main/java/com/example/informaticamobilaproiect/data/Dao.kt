package com.example.informaticamobilaproiect.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) pentru operatii CRUD pe tabela 'produse'
 */
@Dao
interface ProdusDao {
    
    @Query("SELECT * FROM produse")
    fun getAllProduse(): Flow<List<Produs>>
    
    @Query("SELECT * FROM produse WHERE id = :id")
    suspend fun getProdusById(id: Long): Produs?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(produs: Produs): Long
    
    @Update
    suspend fun update(produs: Produs)
    
    @Delete
    suspend fun delete(produs: Produs)
    
    @Query("DELETE FROM produse WHERE id = :id")
    suspend fun deleteById(id: Long)
}

/**
 * DAO (Data Access Object) pentru operatii CRUD pe tabela 'comenzi'
 */
@Dao
interface ComandaDao {
    
    @Query("SELECT * FROM comenzi")
    fun getAllComenzi(): Flow<List<Comanda>>
    
    @Query("SELECT * FROM comenzi WHERE id = :id")
    suspend fun getComandaById(id: Long): Comanda?
    
    @Query("SELECT * FROM comenzi WHERE produsId = :produsId")
    fun getComenziByProdusId(produsId: Long): Flow<List<Comanda>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comanda: Comanda): Long
    
    @Update
    suspend fun update(comanda: Comanda)
    
    @Delete
    suspend fun delete(comanda: Comanda)
    
    @Query("DELETE FROM comenzi WHERE id = :id")
    suspend fun deleteById(id: Long)
}
