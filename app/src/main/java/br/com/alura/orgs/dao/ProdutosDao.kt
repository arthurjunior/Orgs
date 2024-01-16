package br.com.alura.orgs.dao

import android.os.Handler
import android.os.Looper
import br.com.alura.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }
    fun buscaLista(callback: (List<Produto>) -> Unit) {
        // Simula uma chamada assíncrona (por exemplo, de uma API)
        Handler(Looper.getMainLooper()).postDelayed({
            // Chama o callback passando a lista de produtos
            callback(produtos)
        }, 1000) // Delay de 1 segundo (simulação de carregamento)
    }
    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "Salada de frutas",
                descricao = "Laranja, maçãs e uva",
                valor = BigDecimal("19.83")
            )
        )
    }

}