package com.example.informaticamobilaproiect.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Baza de date Room pentru Task 9
 * Contine 2 tabele: produse si comenzi
 */
@Database(
    entities = [Produs::class, Comanda::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun produsDao(): ProdusDao
    abstract fun comandaDao(): ComandaDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task9_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.produsDao(), database.comandaDao())
                }
            }
        }

        private suspend fun populateDatabase(produsDao: ProdusDao, comandaDao: ComandaDao) {
            // Seed produse
            produsDao.insert(Produs(nume = "Laptop Dell XPS 15", pret = 4999.99, categorie = "Electronice"))
            produsDao.insert(Produs(nume = "Telefon Samsung S24", pret = 3499.00, categorie = "Electronice"))
            produsDao.insert(Produs(nume = "Casti AirPods Pro", pret = 899.00, categorie = "Audio"))
            produsDao.insert(Produs(nume = "Monitor LG 27 inch", pret = 1299.00, categorie = "Electronice"))
            produsDao.insert(Produs(nume = "Tastatura Mecanica", pret = 450.00, categorie = "Periferice"))
            produsDao.insert(Produs(nume = "Mouse Wireless", pret = 199.00, categorie = "Periferice"))
            produsDao.insert(Produs(nume = "Webcam HD", pret = 350.00, categorie = "Periferice"))

            // Seed comenzi
            comandaDao.insert(Comanda(produsId = 1, cantitate = 1, dataComanda = "2024-01-15", status = "Livrat"))
            comandaDao.insert(Comanda(produsId = 2, cantitate = 2, dataComanda = "2024-02-20", status = "In curs"))
            comandaDao.insert(Comanda(produsId = 3, cantitate = 1, dataComanda = "2024-03-10", status = "Confirmat"))
            comandaDao.insert(Comanda(produsId = 1, cantitate = 1, dataComanda = "2024-03-25", status = "Livrat"))
            comandaDao.insert(Comanda(produsId = 4, cantitate = 1, dataComanda = "2024-04-01", status = "Anulat"))
            comandaDao.insert(Comanda(produsId = 5, cantitate = 3, dataComanda = "2024-04-15", status = "In curs"))
            comandaDao.insert(Comanda(produsId = 6, cantitate = 2, dataComanda = "2024-05-01", status = "Confirmat"))
        }
    }
}