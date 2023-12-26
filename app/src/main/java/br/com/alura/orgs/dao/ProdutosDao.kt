package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {
    fun adicionar(produto: Produto) {
        produtos.add(produto)
    }

    fun buscarProd(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
               nome = "Salada de frutas",
                descricao = "Laranjas, maças e uva",
                valor = BigDecimal("19.99"),
            )
        )

    }
}