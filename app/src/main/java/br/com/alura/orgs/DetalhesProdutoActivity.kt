package br.com.alura.orgs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto


class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetalhesProdutoBinding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detalhes"
        // Recupera o objeto Produto do Intent
        val produto = intent.getSerializableExtra("INFOR_PRODUTO") as? Produto

        if (produto != null) {
            exibirDetalhesProduto(binding, produto)
        } else {
            // Lida com o caso em que o objeto Produto não está presente no Intent
        }
    }
    private fun exibirDetalhesProduto(binding: ActivityDetalhesProdutoBinding, produto: Produto) {
        binding.txtNome.text = produto.nome
        binding.txtDescricao.text = produto.descricao
        binding.txtValor.text = produto.valor.toString()
        binding.detalheImagemImagemview.tentaCarregarImagem(produto.imagem)
    }

}