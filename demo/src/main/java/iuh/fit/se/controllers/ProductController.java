package iuh.fit.se.controllers;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ch.qos.logback.core.model.Model;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import iuh.fit.se.dtos.CategoryDTO;
import iuh.fit.se.dtos.ProductDTO;
import iuh.fit.se.services.CategoryService;
import iuh.fit.se.services.ProductService;
import jakarta.validation.Valid;
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/products")
	public String getAllProducts(@Valid @ModelAttribute("product") ProductDTO productDTO, BindingResult result,
			org.springframework.ui.Model model) {
		List<ProductDTO> products = productService.findAll();
		List<CategoryDTO> categories = categoryService.getAll();	
		ProductDTO product = new ProductDTO();		
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);		
		return "product-list";
	}
	// Hiển thị form thêm mới, cập nhật sản phẩm
	@GetMapping({"/add", "/edit/{id}"})
	public String showAddProductForm(@PathVariable(value = "id", required = false) Integer id, org.springframework.ui.Model model) {
	    ProductDTO product;
	    // Kiểm tra nếu có id, tìm sản phẩm theo id để sửa
	    if (id != null && id > 0) {
	        product = productService.findById(id);
	        if (product == null) {
	            return "redirect:/products?error=notfound"; // Nếu không tìm thấy sản phẩm
	        }
	    } else {
	        // Tạo mới sản phẩm nếu không có id
	        product = new ProductDTO();
	        product.setIsActive(false); // Thiết lập mặc định
	    }
	    // Truy xuất danh sách các categories từ service
	    List<CategoryDTO> categories = categoryService.getAll();
	    model.addAttribute("product", product);
	    model.addAttribute("categories", categories);
	    return "product-form";  // Trả về form để thêm hoặc sửa sản phẩm
	}	
	@PostMapping({"/add", "/edit/{id}"})
	public String addOrUpdateProduct(@ModelAttribute("product") @Valid ProductDTO product1, BindingResult result, 
	                                  org.springframework.ui.Model model) {
	    // Kiểm tra nếu có lỗi xác thực
	    if (result.hasErrors()) {
	        model.addAttribute("categories", categoryService.getAll());
	        return "product-form"; // Trả lại form với lỗi
	    }
	    // Nếu không có lỗi, xử lý thêm mới hoặc cập nhật sản phẩm
	    if (product1.getId() > 0) {
	        // Cập nhật sản phẩm
	        productService.update(product1.getId(), product1);
	    } else {
	        // Thêm mới sản phẩm
	        productService.save(product1);
	    }
	    return "redirect:/products?success"; // Quay lại danh sách sản phẩm sau khi thêm/sửa thành công
	}
//Xóa sản phẩm
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(value = "id", required = false) Integer id) {
        productService.delete(id);
        return "redirect:/products?success"; // Quay lại trang danh sách sản phẩm sau khi xóa thành công
	}
	 @GetMapping("/search")
	    public String searchProducts(@RequestParam(required = false) int categoryID,
	                                 @RequestParam(required = false) Boolean isActive,
	                                 @RequestParam(required = false) LocalDate registerDate,
	                                 @RequestParam(required = false) String keyword,
	                                 org.springframework.ui.Model model) {
	        // Gọi phương thức tìm kiếm sản phẩm
	        List<ProductDTO> products = productService.searchProducts(categoryID, isActive, registerDate, keyword);
	        List<CategoryDTO> categories = categoryService.getAll();
	    	ProductDTO product = new ProductDTO();
	    	model.addAttribute("product", product);
	        // Thêm danh sách sản phẩm vào model để hiển thị trong view
	        model.addAttribute("products", products);
	        model.addAttribute("categories", categories);     
	        // Trả về tên của view cần render
	        return "product-list";
	    }
}
