package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.appDataBase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {


    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Inicio"
        configuraRecyclerView()
        configuraFab()

    }

    override fun onResume() {
        super.onResume()
        val db = appDataBase.instancia(this)

        val produtosDao = db.produtoDao()
        adapter.atualiza(produtosDao.buscarTodos())
    }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun vaiParaDetalheProduto(produto: Produto) {
        val navegarDetalhe = Intent(this, DetalhesProdutoActivity::class.java)
        navegarDetalhe.putExtra("INFOR_PRODUTO", produto)
        startActivity(navegarDetalhe)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        val db = appDataBase.instancia(this)
        val produtosDao = db.produtoDao()
        // Configura o adaptador e atualiza a lista de produtos
        recyclerView.adapter = adapter
        adapter.atualiza(produtosDao.buscarTodos())
        // Configura o clique nos produtos
        adapter.setOnProdutoClickListener { produto ->
            vaiParaDetalheProduto(produto)
        }
    }
}





