package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Utils.Companion.formataParaMoedaBrasileira


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
        }
    }
  //Adicionando um menu, junto com inflate
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_detalhes_produto_remover -> {}
            R.id.menu_detalhes_produto_editar -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    private fun exibirDetalhesProduto(binding: ActivityDetalhesProdutoBinding, produto: Produto) {
        binding.txtNome.text = produto.nome
        binding.txtDescricao.text = produto.descricao
        binding.txtValor.text = formataParaMoedaBrasileira(produto.valor)
        binding.detalheImagemImagemview.tentaCarregarImagem(produto.imagem)
    }

}