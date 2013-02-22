package com.example.ormliteexemplo;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class MainActivity extends Activity {
	
	private EditText etNome, etIdade;
	private Button btGravar, btVer;
	
	//metodo que retorna uma conexao com o bd
	private ConnectionSource getConnectionSource(){
		return new DataBaseUtil(MainActivity.this).getConnectionSource();
	}
	
	//metodo que retorna um dao do tipo Pessoa
	//recebe o tipo e o tipo da pk
	private Dao<Pessoa, Integer> getPessoaDao() throws SQLException{
		Dao<Pessoa, Integer> dao = DaoManager.createDao(getConnectionSource(), Pessoa.class);
		return dao;
	}
	
	//metodo que instancia os componentes de tela
	private void instantiateViews(){
		etNome = (EditText) findViewById(R.id.etNome);
		etIdade = (EditText) findViewById(R.id.etIdade);
		btGravar = (Button) findViewById(R.id.btGravar);
		btVer = (Button) findViewById(R.id.btVer);
	}
	
	//metodo que checa os campos vazios
	private String checkFields(){
		StringBuilder sb = new StringBuilder("");
		if (etNome.toString().length() < 1 || etIdade.toString().length() < 1){
			sb.append("\nPreencha os campos corretamente");
		}
		return sb.toString();
	}
	
	//metodo que sera chamado ao gravar os dados
	private boolean gravar() throws SQLException{
		int x = 0;
		if (checkFields().equals("")){
			Pessoa pessoa = new Pessoa();
			pessoa.setNome(etNome.getText().toString());
			pessoa.setIdade(Integer.parseInt(etIdade.getText().toString()));
			//create é o insert do objeto no bd, retorna  a qtd de linhas inseridas
			x = getPessoaDao().create(pessoa);
		}else{
			showDialog("Erro", checkFields(), "OK");
		}
		return x > 0;
	}
	
	//metodo chamado para mostrar as pessoas gravadas no bd
	private void showPessoas(){
		List<Pessoa> pessoas = null;
		try {
			pessoas = getPessoaDao().queryForAll();
		} catch (SQLException e) {
			showDialog("Erro", "Detalhes:\n" + e.getMessage() , "OK");
			e.printStackTrace();
		}
		if (pessoas != null && pessoas.size() > 0){
			for (int i = 0; i < pessoas.size(); i++){
				Toast toast = Toast.makeText(MainActivity.this, pessoas.get(i).toString(), Toast.LENGTH_SHORT);
				toast.show();
			}
		}else{
			Toast toast = Toast.makeText(MainActivity.this, "Nenhuma pessoa gravado no banco", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	

	//metodo auxiliar para mostrar uma caixa de dialogo
	private void showDialog(String title, String message, String positive) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setMessage(message);
		dialog.setPositiveButton(positive,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface di, int arg) {

					}
				});
		dialog.setTitle(title);
		dialog.show();
	}
	
	//adicionando ações aos botoes
	private void addButtonsLinsteners(){
		btGravar.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					if(gravar()){
						showDialog("Aviso", "Dados gravados com sucesso!", "OK");
					}else{
						showDialog("Erro", "Dados não foram gravados!", "OK");
					}
				} catch (SQLException e) {
					showDialog("Erro", "Dados não foram gravados! Detalhes:\n" + e.getMessage() , "OK");
					e.printStackTrace();
				}
			}

		});
		
		btVer.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showPessoas();
			}

		});
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instantiateViews();
        addButtonsLinsteners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
