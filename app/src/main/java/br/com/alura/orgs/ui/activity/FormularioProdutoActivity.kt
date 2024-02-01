package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.database.appDataBase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L
    val produtosDao by lazy {
        Room.databaseBuilder(
            applicationContext,
            appDataBase::class.java,
            "orgs.db"
        ).allowMainThreadQueries()
            .build()
            .produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()

        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .dialog(url) { imagem ->
                    url = imagem
                    binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
                }
        }
        tentaCarregarProduto()

    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        produtosDao.buscarPorId(produtoId)?.let {
            title = "Editar Produto"
            preencherCampos(it)
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRDUTO_ID, 0L)
    }

    private fun preencherCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem
            .tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome
            .setText(produto.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormularioProdutoValor
            .setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            /*if (produtoId > 0){
                produtosDao.altera(produtoNovo)
            }else{
                produtosDao.salvar(produtoNovo)
            }*/
            produtosDao.salvar(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }
}
