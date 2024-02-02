package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.appDataBase
import br.com.alura.orgs.databinding.ActivityListaProdutosActivityBinding
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

private const val TAG = "ListaProdutosActivity"
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

    private fun vaiParaDetalheProduto(produtoId: Long) {
        val navegarDetalhe = Intent(this, DetalhesProdutoActivity::class.java)
        navegarDetalhe.putExtra(CHAVE_PRDUTO_ID, produtoId)
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
            vaiParaDetalheProduto(produto.id)
        }

        adapter.quandoClicaEmEditar = {
            Log.i(TAG, "configuraRecyclerView: Editar $it")
        }
        adapter.quandoClicaEmRemover = {
            Log.i(TAG, "configuraRecyclerView: Remover $it")
        }
    }
// inflando filtro e aplicando lista ordenada
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //logica de orndenação do filtro
            R.id.menu_sort_name_asc -> sortBy("name", true)
            R.id.menu_sort_name_desc -> sortBy("name", false)
            R.id.menu_sort_description_asc -> sortBy("description", true)
            R.id.menu_sort_description_desc -> sortBy("description", false)
            R.id.menu_sort_value_asc -> sortBy("value", true)
            R.id.menu_sort_value_desc -> sortBy("value", false)
            R.id.menu_sort_none -> sortBy("none", true)
            // Adicionar outros itens de menu conforme necessário
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun sortBy(attribute: String, ascending: Boolean) {
        val db = appDataBase.instancia(this)
        val produtosDao = db.produtoDao()

        val sortedProducts = when (attribute) {
            "name" -> if (ascending) produtosDao.buscarTodos().sortedBy { it.nome } else produtosDao.buscarTodos().sortedByDescending { it.nome }
            "description" -> if (ascending) produtosDao.buscarTodos().sortedBy { it.descricao } else produtosDao.buscarTodos().sortedByDescending { it.descricao }
            "value" -> if (ascending) produtosDao.buscarTodos().sortedBy { it.valor } else produtosDao.buscarTodos().sortedByDescending { it.valor }
            else -> produtosDao.buscarTodos() // Sem ordenação
        }

        updateAdapter(sortedProducts)
    }

    private fun updateAdapter(sortedProducts: List<Produto>) {
        adapter.atualiza(sortedProducts)
    }
}





