package com.example.informaticamobilaproiect.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository pentru operatii CRUD pe tabela 'produse'
 */
class ProdusRepository(private val dao: ProdusDao) {
    
    val allProduse: Flow<List<Produs>> = dao.getAllProduse()
    
    suspend fun insert(nume: String, pret: Double, categorie: String): Long {
        val produs = Produs(nume = nume, pret = pret, categorie = categorie)
        return dao.insert(produs)
    }
    
    suspend fun update(id: Long, nume: String, pret: Double, categorie: String) {
        val produs = Produs(id = id, nume = nume, pret = pret, categorie = categorie)
        dao.update(produs)
    }
    
    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
    
    suspend fun getProdusById(id: Long): Produs? {
        return dao.getProdusById(id)
    }
}

/**
 * Repository pentru operatii CRUD pe tabela 'comenzi'
 */
class ComandaRepository(private val dao: ComandaDao) {
    
    val allComenzi: Flow<List<Comanda>> = dao.getAllComenzi()
    
    fun getComenziByProdusId(produsId: Long): Flow<List<Comanda>> {
        return dao.getComenziByProdusId(produsId)
    }
    
    suspend fun insert(produsId: Long, cantitate: Int, dataComanda: String, status: String): Long {
        val comanda = Comanda(
            produsId = produsId,
            cantitate = cantitate,
            dataComanda = dataComanda,
            status = status
        )
        return dao.insert(comanda)
    }
    
    suspend fun update(id: Long, produsId: Long, cantitate: Int, dataComanda: String, status: String) {
        val comanda = Comanda(
            id = id,
            produsId = produsId,
            cantitate = cantitate,
            dataComanda = dataComanda,
            status = status
        )
        dao.update(comanda)
    }
    
    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
    
    suspend fun getComandaById(id: Long): Comanda? {
        return dao.getComandaById(id)
    }
}
