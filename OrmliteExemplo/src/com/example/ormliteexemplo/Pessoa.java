package com.example.ormliteexemplo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//anotação que indica que a classe representa uma tabela no bd
@DatabaseTable
public class Pessoa {

	//anotação que indica que o atributo é um campo no bd de id auto increment
	@DatabaseField(generatedId = true)
	private Integer id;

	//anotação que indica que o atributo é um campo no bd
	@DatabaseField
	private String nome;

	//anotação que indica que o atributo é um campo no bd
	@DatabaseField
	private Integer idade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "Pessoa [nome = " + nome + ", idade = " + idade + "]";
	}
	
	

}
