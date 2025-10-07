package com.firsatbilisim.spacialnote.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.firsatbilisim.spacialnote.data.model.CategoryModel

@Dao
interface CategoryDao {


    // Kategori eklemek için insert fonksiyonu
    @Insert
    suspend fun insertCategory(category: CategoryModel)

    // Kategorileri listelemek için (isteğe bağlı)
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryModel>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoryModel?

    @Update
    suspend fun updateCategory(category: CategoryModel)

    @Delete
    suspend fun deleteCategory(category: CategoryModel)

    @Query("SELECT * FROM categories WHERE title LIKE '%' || :title || '%'")
    suspend fun searchCategoryByTitle(title: String): List<CategoryModel>

}