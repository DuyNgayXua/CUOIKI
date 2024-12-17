package iuh.fit.se.services.impl;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import iuh.fit.se.dtos.ProductDTO;
import iuh.fit.se.entities.Product;
import iuh.fit.se.exceptions.ItemNotFoundException;
import iuh.fit.se.repositories.ProductRepository;
import iuh.fit.se.services.ProductService;
import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository productRepository;	
	@Autowired
	ModelMapper modelMapper;
	private ProductDTO covertToDTO (Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}
	private Product covertToEntity (ProductDTO productDTO) {
		Product product = modelMapper.map(productDTO, Product.class);
		return product;
	}
	@Override
	public ProductDTO findById(int id) {
		Product product = productRepository.findById(id)
							.orElseThrow(()-> new ItemNotFoundException("Khong tim thay Product: " +id));
		return this.covertToDTO(product);
	}
	@Override
	@Transactional
	public List<ProductDTO> findAll() {
		return productRepository.findAll().stream()
				.map(this::covertToDTO)
				.collect(Collectors.toList());
	}
	@Override
	public Page<ProductDTO> findAllWithPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return productRepository.findAll(pageable).map(this::covertToDTO);		
	}	
	@Transactional
	@Override
	public ProductDTO save(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		Product product = this.covertToEntity(productDTO);
		product = productRepository.save(product);
		 System.out.println("Product saved: " + product);  // Log thông tin sản phẩm sau khi lưu
		return this.covertToDTO(product);
	}
	@Override
	public ProductDTO update(int id, ProductDTO productDTO) {
		this.findById(id);
		productRepository.save(this.covertToEntity(productDTO));
		return productDTO;
	}
	@Override
	public boolean delete(int id) {
		this.findById(id);
		productRepository.deleteById(id);
		return true;
	}
	@Transactional
	@Override
	public List<ProductDTO> search(String keyword) {
		return productRepository.search(keyword).stream()
				.map(this::covertToDTO)
				.collect(Collectors.toList());
	}
	@Override
	@Transactional
	public List<ProductDTO> searchByCategory(int categoryID) {
	    return productRepository.findByCategoryID(categoryID).stream()
	            .map(this::covertToDTO)
	            .collect(Collectors.toList());
	}
	@Override
    public List<ProductDTO> searchProducts(int categoryID, Boolean isActive,  LocalDate registerDate, String keyword) {
        List<Product> products;
        if (categoryID > 0 && isActive != null && registerDate != null) {
            products = productRepository.findByCategoryIdAndStatusAndCreatedDateBetween(categoryID, isActive, registerDate);
        } 
        else if (categoryID > 0 && registerDate != null) {
            products = productRepository.findByCategoryIdAndCreatedDateBetween(categoryID, registerDate);
        } 
        else if (isActive != null && registerDate != null) {
            products = productRepository.findByStatusAndCreatedDateBetween(isActive, registerDate);
        } 
        else if (categoryID > 0 && isActive != null) {
            products = productRepository.findCategoryIDandByStatus(isActive, categoryID);
        } 
        else if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.search(keyword);
        }
        else if (categoryID > 0) {
            products = productRepository.findByCategoryID(categoryID);
        } 
        else {
            products = productRepository.findAll();
        }
        return products.stream()
                       .map(this::covertToDTO)
                       .collect(Collectors.toList());
    }
}
