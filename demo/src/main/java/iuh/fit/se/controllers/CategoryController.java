package iuh.fit.se.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import iuh.fit.se.dtos.CategoryDTO;
import iuh.fit.se.dtos.ProductDTO;
import iuh.fit.se.services.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
}
