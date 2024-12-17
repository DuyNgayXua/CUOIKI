package iuh.fit.se.repositories;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import iuh.fit.se.entities.Product;
import jakarta.websocket.server.PathParam;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query(value = "SELECT p.* FROM Product p WHERE p.code LIKE  %:keyword%"
            + " OR p.name LIKE %:keyword%"
            + " OR p.description LIKE  %:keyword%"
            + " OR p.categoryid LIKE  %:keyword%", nativeQuery = true)
    List<Product> search(@Param("keyword") String keyword);	
	@Query(value = "SELECT p.* FROM Product p WHERE p.categoryID = :categoryID", nativeQuery = true)
	List<Product> findByCategoryID(@Param("categoryID")  int categoryID);	
	// Tìm sản phẩm theo categoryID, isActive và khoảng thời gian
	@Query(value = "SELECT p.* FROM Product p WHERE p.categoryid = :categoryID AND p.is_active = :isActive "
			+ "AND p.register_date BETWEEN GETDATE() AND :registerDate", nativeQuery = true)
	List<Product> findByCategoryIdAndStatusAndCreatedDateBetween(@Param("categoryID") int categoryID,
	                                                             @Param("isActive") Boolean isActive,
	                                                             @Param("registerDate") LocalDate registerDate);
	@Query(value = "SELECT p.* FROM Product p WHERE p.categoryid = :categoryID AND p.register_date BETWEEN GETDATE() "
			+ "AND :registerDate", nativeQuery = true)
	List<Product> findByCategoryIdAndCreatedDateBetween(@Param("categoryID") int categoryID,	                                                   
	                                                    @Param("registerDate") LocalDate registerDate);
	@Query(value = "SELECT p.* FROM Product p WHERE p.is_active = :isActive AND p.register_date BETWEEN GETDATE() "
			+ "AND :registerDate", nativeQuery = true)
	List<Product> findByStatusAndCreatedDateBetween(@Param("isActive") Boolean isActive,	                                            
	                                                @Param("registerDate") LocalDate registerDate);
    // Tìm sản phẩm theo chỉ trạng thái
    @Query(value = "SELECT p.* FROM Product p WHERE p.is_active = :isActive and p.categoryID = :categoryID", nativeQuery = true)
    List<Product> findCategoryIDandByStatus(@Param("isActive") Boolean isActive, @Param("categoryID")  int categoryID);
}
