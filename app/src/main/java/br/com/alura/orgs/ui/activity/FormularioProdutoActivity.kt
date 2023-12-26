package br.com.alura.orgs.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProdutosDao
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity :
    AppCompatActivity(R.layout.activity_formulario_produto) {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        setContentView(binding.root)
    }
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private fun configuraBotaoSalvar() {

        val botaoSalvar = binding.activityFormularioProdutoSavebutton//findViewById<Button>(R.id.activity_formulario_produto_savebutton)
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adicionar(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()

        val campoDescricao = binding.activityFormularioProdutoDescricaoProduto
        val descricao = campoDescricao.text.toString()

        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()

        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor
        )
    }
}