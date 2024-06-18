package com.senai.pa3.services;

import com.senai.pa3.dto.ProductDTO;
import com.senai.pa3.entities.Product;
import com.senai.pa3.exceptions.ResourceNotFoundException;
import com.senai.pa3.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


//    public List<ProductDTO> findAll2() {
//        public byte[] gerarRelatorioPDF() {
//            List<Product> produtos = productRepository.findAll();
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            org.w3c.dom.Document document = new Document();
//            try {
//                PdfWriter.getInstance(document, outputStream);
//                document.open();
//                for (Produto produto : produtos) {
//                    document.add(new Paragraph(produto.toString()));
//                }
//                document.close();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//            return outputStream.toByteArray();
//        }
//    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
        return new ProductDTO(product);
    }
    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product product = new Product();
        copyDtoToEntity(productDTO, product);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try {
            var entity = productRepository.getOne(id);
            copyDtoToEntity(productDTO, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e ) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
        productRepository.deleteById(id);
    }

    protected void copyDtoToEntity(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
    }
}
