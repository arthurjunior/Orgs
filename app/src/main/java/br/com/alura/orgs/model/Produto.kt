package br.com.alura.orgs.model

import android.icu.text.NumberFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal
import java.util.Locale
@Entity
data class Produto(
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val imagem: String? = null
) : Serializable

class Utils {
        //classe para criar ultilitarios que pode ser ultilizado em varis partes do codigo
        companion object {
                fun formataParaMoedaBrasileira(valor: BigDecimal): String {
                        val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                        return formatador.format(valor)
                }
        }
}
