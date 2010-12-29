package br.com.agendatech.cadastro;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import br.com.caelum.cadastro.modelo.Evento;
import br.com.caelum.cadastro.parser.EventoParser;

public class ListaEventos extends ListActivity implements OnItemLongClickListener {
	private static final int MENU_NOVO = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);

		getListView().setOnItemLongClickListener(this);
		
		// para a mensagem não ser exibida enquanto a lista é carregada
		findViewById(android.R.id.empty).setVisibility(View.GONE);

		new CarregarListaTask().execute();
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		Toast.makeText(ListaEventos.this,
				"Aguarde pr�ximas vers�es do agendatech para detalhes do evento." , Toast.LENGTH_LONG)
				.show();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
		Toast.makeText(ListaEventos.this,
				"Aguarde pr�ximas vers�es do agendatech para detalhes do evento." , Toast.LENGTH_LONG)
				.show();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_NOVO, 0, "Cadastrar Evento").setIcon(R.drawable.mais);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == MENU_NOVO) {
			startActivity(new Intent(this, Formulario.class));
			return true;
		}

		return false;
	}
	
	private class CarregarListaTask extends AsyncTask<Object, Object, List<Evento>> {
		ProgressDialog progress;

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(ListaEventos.this, "Aguarde...",
					"Carregando lista de eventos...", true);
		}

		@Override
		protected List<Evento> doInBackground(Object... params) {
			return new EventoParser().parse();
		}

		@Override
		protected void onPostExecute(List<Evento> eventos) {
			setListAdapter(new ArrayAdapter<Evento>(ListaEventos.this,
					android.R.layout.simple_list_item_1, eventos));
			progress.dismiss();
		}
	}
}