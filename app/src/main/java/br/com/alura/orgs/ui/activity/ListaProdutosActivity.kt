package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.DetalhesProdutoActivity
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())
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
        adapter.atualiza(dao.buscaTodos())
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
        recyclerView.adapter = adapter

        dao.buscaLista { produtos ->
            adapter.atualiza(produtos)
        }

        adapter.setOnProdutoClickListener { produto ->
            vaiParaDetalheProduto(produto)
        }
    }
}





