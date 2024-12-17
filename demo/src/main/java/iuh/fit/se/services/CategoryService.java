package iuh.fit.se.services;
import java.util.List;

import iuh.fit.se.dtos.CategoryDTO;
import iuh.fit.se.entities.Category;

public interface CategoryService {
	public Category save (Category category);
	List<CategoryDTO> getAll();
}
