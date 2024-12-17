package iuh.fit.se.services;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.query.Page;

import iuh.fit.se.dtos.ProductDTO;

public interface ProductService {
	public ProductDTO findById(int id);
	public List<ProductDTO> findAll();
	public org.springframework.data.domain.Page<ProductDTO> findAllWithPaging (int pageNo, int pageSize, String sortBy, String sortDirection);
	public ProductDTO save (ProductDTO productDTO);
	public ProductDTO update (int id, ProductDTO productDTO);
	public boolean delete (int id);
	public List<ProductDTO> search (String keyword);
	public List<ProductDTO> searchByCategory (int categoryID);
	public List<ProductDTO> searchProducts(int categoryID, Boolean isActive, LocalDate registerDate, String keyword);
}
