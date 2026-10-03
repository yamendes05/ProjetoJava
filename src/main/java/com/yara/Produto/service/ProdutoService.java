package com.yara.Produto.service;

import com.yara.Produto.dto.ProdutoRequest;
import com.yara.Produto.dto.ProdutoResponse;
import com.yara.Produto.exception.CodigoDuplicadoException;
import com.yara.Produto.exception.ProdutoNaoEncontradoException;
import com.yara.Produto.model.Produto;
import com.yara.Produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest req) {

        if (produtoRepository.existsByCodigo(req.codigo())) {
            throw new CodigoDuplicadoException(
                    "Código já cadastrado: " + req.codigo()
            );
        }

        Produto produto = new Produto(
                req.codigo(),
                req.marca(),
                req.tipo(),
                req.categoria(),
                req.precoUnitario(),
                req.custo(),
                req.obs()
        );

        Produto salvo = produtoRepository.save(produto);

        return ProdutoResponse.de(salvo);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscar(Long id) {
        Produto produto = buscarPorId(id);
        return ProdutoResponse.de(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponse::de)
                .toList();
    }

    private Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "Produto não encontrado com o id: " + id
                ));
    }
}