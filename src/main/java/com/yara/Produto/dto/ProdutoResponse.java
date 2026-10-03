package com.yara.Produto.dto;

import com.yara.Produto.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponse(Long id,String codigo, String marca, String tipo, String categoria, BigDecimal precoUnitario, BigDecimal custo, String obs) {


    public static ProdutoResponse de(Produto p){
        return new ProdutoResponse(
        p.getId(),
                p.getCodigo(),
        p.getMarca(),
        p.getTipo(),
        p.getCategoria(),
        p.getPrecoUnitario(),
        p.getCusto(),
        p.getObs()
);
    }
}
