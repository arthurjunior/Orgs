package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


    private var produtoId: Long = 0L
    private var produtoSelecionado: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        appDataBase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Produtos"
        // Recupera o objeto Produto do Intent
        produtoCarregadoDb(binding)


    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        produtoSelecionado = produtoDao.buscarPorId(produtoId)
        // logs para teste, para saber se esta passando os dados corretamente
        Log.d("DetalhesProdutoActivity", "Produto carregado com ID: $produtoId")
        produtoSelecionado?.let {
            // logs para teste, para saber se esta passando os dados corretamente
            Log.d("DetalhesProdutoActivity", "Exibindo detalhes do produto: ${it.nome}")
            exibirDetalhesProduto(binding, it)
        } ?: finish()
    }

    private fun produtoCarregadoDb(binding: ActivityDetalhesProdutoBinding) {

       produtoId = intent.getLongExtra(CHAVE_PRDUTO_ID,0L)
       // if (produto != null) {
        //    produtoId = produto.id
       // }
    }

    //Adicionando um menu, junto com inflate
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produtoDao.excluir(produtoSelecionado)
                finish()
            }

            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra("INFOR_PRODUTO", produtoSelecionado)
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