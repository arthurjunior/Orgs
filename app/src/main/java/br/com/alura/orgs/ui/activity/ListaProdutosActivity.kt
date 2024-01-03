package br.com.alura.orgs.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscarProd())
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraRecycleView()
        configuraFab()
        setContentView(binding.root)
        AlertDialog.Builder(this)
            .setTitle("Ola Mundo")
            .setMessage("Bem-vindo ao meu primeiro app")
            .setView(R.layout.activity_formulario_imagem)
            .setPositiveButton("Confirma", { _, i ->  })
            .setNegativeButton("Cancelar", { _, i ->  })
            .show()
        }

        override fun onResume() {
            super.onResume()
            adapter.atualiza(dao.buscarProd())
        }

    private fun configuraRecycleView() {
        val recyclerView = binding.activityListaProdutoRecyclerView
        recyclerView.adapter = adapter
    }

    private fun configuraFab() {
        val fab = binding.activityFormularioProdutoFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }
}