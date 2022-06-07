package com.adiomiDev.store;

import com.adiomiDev.store.controller.ProductController;
import com.adiomiDev.store.dao.ProductRepository;
import com.adiomiDev.store.dto.CreateProductDto;
import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class StoreApplicationTests {

	@Autowired
	ProductController controller;

	@MockBean
	ProductRepository productRepository;

	@MockBean
	ProductService service;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void postTestPositiveCase() throws  Exception{

		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		String product = mapper.writeValueAsString(buildProduct());

	 CreateProductDto createProductDto = new CreateProductDto();
		createProductDto.setQuantity(buildProduct().getQuantity());
		createProductDto.setPrice(buildProduct().getPrice());
		createProductDto.setName(buildProduct().getName());

		ProductDto productDto = new ProductDto();
		productDto.setId(buildProduct().getId());

		given(this.productRepository.save(buildProduct())).willReturn(buildProduct());
		given(this.service.create(createProductDto)).willReturn(productDto);
		given(this.controller.addProduct(any())).willReturn(productDto);

		this.mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(product))
						.andDo(print())
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.id").value(1));

	}





	public Product buildProduct(){
		Product product = new Product();
		product.setId(1);
		product.setName("Particles");
		product.setPrice(100.989);
		product.setQuantity(100);
		product.setCreatedAt(new Date());
		product.setUpdatedAt(new Date());

		return product;
	}
}
