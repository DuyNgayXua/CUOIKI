package iuh.fit.se.services.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iuh.fit.se.dtos.CategoryDTO;
import iuh.fit.se.entities.Category;
import iuh.fit.se.repositories.CategoryRepository;
import iuh.fit.se.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;	
	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return this.categoryRepository.save(category);
	}
	@Override
	public List<CategoryDTO> getAll() {
		List<Category> categories = categoryRepository.findAll();		
		return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName())).collect(Collectors.toList());
	}
}
