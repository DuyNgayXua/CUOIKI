package iuh.fit.se.dtos;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import iuh.fit.se.entities.Category;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	private int id;	
	@NotNull(message = "Code must not be Null")
	@NotEmpty(message = "Code must not be Empty")
	@Size(min = 10, max = 10, message = "Code must be exactly 10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]{10}$", message = "Code must be alphanumeric and exactly 10 characters")
	private String code;	
	@NotNull(message = "Name must not be Null")
	@NotEmpty(message = "Name must not be Empty")
	@Size(max = 50, message = "Name must not exceed 50 characters")
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name must be alphanumeric")
	private String name;	
	@Size(max = 1000, message = "Description must not exceed 1000 characters")
	private String description;	
	@NotNull(message = "Price must not be null")
	@Min(value = 0, message = "Price must not be less than 0")
	private double price;	
	@FutureOrPresent(message = "RegisterDate must be today or in the future")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registerDate;	
	@JsonIgnore
	private Category categoryID;	
	@NotNull(message = "isActive must not be null")
	private Boolean isActive;
	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductDTO(int id,
			@NotNull(message = "Code must not be Null") @NotEmpty(message = "Code must not be Empty") String code,
			@NotNull(message = "Name must not be Null") @NotEmpty(message = "Name must not be Empty") String name,
			String description,
			@NotNull(message = "Price must not be null") @Min(value = 0, message = "Price must not be less than 0") double price,
			@FutureOrPresent(message = "RegisterDate must be today or in the future") LocalDate registerDate,
			Category categoryID, @NotNull(message = "isActive must not be null") Boolean isActive) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.price = price;
		this.registerDate = registerDate;
		this.categoryID = categoryID;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public Category getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Category categoryID) {
		this.categoryID = categoryID;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", registerDate=" + registerDate + ", categoryID=" + categoryID + ", isActive="
				+ isActive + "]";
	}
	
	
	
	

	
	

}
