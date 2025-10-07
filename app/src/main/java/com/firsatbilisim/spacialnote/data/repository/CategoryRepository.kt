package com.firsatbilisim.spacialnote.data.repository

import android.app.Application
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.room.SpacialNoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryRepository(application: Application) {
    private val _categoryList = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categoryList: StateFlow<List<CategoryModel>> get() = _categoryList

    private val db: SpacialNoteDatabase by lazy { SpacialNoteDatabase.invoke(application) }


    suspend fun addCategory(category: CategoryModel) {
        db.categoryDao().insertCategory(category)
    }

    suspend fun getCategories() {
        val categories = db.categoryDao().getAllCategories()
        _categoryList.value = categories
    }

    suspend fun getCategoryById(categoryId: Int): CategoryModel? {
        return db.categoryDao().getCategoryById(categoryId) // Kategorinin detayına gidiyoruz. return ile sonucu döndürmek zorundayım.
    }

    suspend fun updateCategory(category: CategoryModel) {
        db.categoryDao().updateCategory(category)
    }

    suspend fun deleteCategory(category: CategoryModel) {
        db.categoryDao().deleteCategory(category)
    }

    suspend fun searchCategoryByTitle(title: String) {
        val searchCategory = db.categoryDao().searchCategoryByTitle(title)
        _categoryList.value = searchCategory
    }
}
