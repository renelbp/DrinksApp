package com.example.drinksapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drinksapp.data.model.DrinksEntity
import com.example.drinksapp.domain.DrinksDao

@Database(entities = [DrinksEntity::class], version = 1, exportSchema = false )
abstract class DrinksAppDatabase: RoomDatabase() {

    abstract fun drinksDao(): DrinksDao

    companion object{
        private var INSTANCE: DrinksAppDatabase? = null

        fun getDatabase(context: Context): DrinksAppDatabase{
            INSTANCE = INSTANCE ?:  Room.databaseBuilder(
                context.applicationContext,
                DrinksAppDatabase::class.java,
                "Drinks_table")
                .build()
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }

}