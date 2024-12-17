package iuh.fit.se.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import iuh.fit.se.entities.Category;
import iuh.fit.se.entities.Product;

@RepositoryRestResource(collectionResourceRel = "category", path = "category", exported = false)
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query(value = "SELECT c FROM Category c WHERE c.name LIKE  %:keyword%")
    List<Category> searchCategory(@Param("keyword") String keyword);

}
