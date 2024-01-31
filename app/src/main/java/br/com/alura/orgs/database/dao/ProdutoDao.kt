package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM Produto")
    fun buscarTodos(): List<Produto>
    @Insert
    fun salvar(produto: Produto)
    @Delete
    fun excluir(produto: Produto?)

    @Update
    fun altera(produto: Produto)
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscarPorId(id: Long) : Produto?
}