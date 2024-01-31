package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.appDataBase
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.model.Utils.Companion.formataParaMoedaBrasileira


class DetalhesProdutoActivity : AppCompatActivity() {

    private lateinit var produtoSelecionado: Produto
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetalhesProdutoBinding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Produtos"
        // Recupera o objeto Produto do Intent
        val produto = intent.getSerializableExtra("INFOR_PRODUTO") as? Produto
        if (produto != null) {
            exibirDetalhesProduto(binding, produto)
            //Passando para uma propeti para poder chamar na função remover
            produtoSelecionado = produto
        }
    }
  //Adicionando um menu, junto com inflate
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //acesso ao banco
        val db = appDataBase.instancia(this)
        val produtoDao = db.produtoDao()
        //acesso ao banco
        when(item.itemId){
            R.id.menu_detalhes_produto_remover -> {
              produtoDao.excluir(produtoSelecionado)
                finish()
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra("INFOR_PRODUTO",produtoSelecionado)
                    startActivity(this)
                }
            }
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