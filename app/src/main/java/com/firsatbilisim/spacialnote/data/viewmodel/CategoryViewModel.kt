package com.firsatbilisim.spacialnote.data.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firsatbilisim.spacialnote.data.model.CategoryModel
import com.firsatbilisim.spacialnote.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING")
class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val categoryRepository = CategoryRepository(application)
    // Listeyi tutan StateFlow
    val categoryList: StateFlow<List<CategoryModel>> get() = categoryRepository.categoryList // Burası listeleme yapmak için kullanıyorum. Listeleme yapacağım sayfada çağırıyorum.

    // Kategori detayını tutacak StateFlow
    private val _selectedCategory = MutableStateFlow<CategoryModel?>(null)
    val selectedCategory: StateFlow<CategoryModel?> get() = _selectedCategory

    private val _showDialog = mutableStateOf(false)
    val showDialog: State<Boolean> get() = _showDialog


    fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories()
        }
    }

    fun addCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryRepository.addCategory(category)
        }
    }


    fun loadCategoryDetail(categoryId: Int) {
        viewModelScope.launch {
            _selectedCategory.value = categoryRepository.getCategoryById(categoryId)
        }
    }

    fun updateCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
            loadCategories()
            _showDialog.value = false
        }
    }

    fun searchCategoryByTitle(title: String) {
        viewModelScope.launch {
            categoryRepository.searchCategoryByTitle(title)
        }
    }

    fun showDeleteDialog() {
        _showDialog.value = true
    }

    fun hideDeleteDialog() {
        _showDialog.value = false
    }

}
