package com.example.component;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

public class CursorLoaderListFragment extends ListFragment implements
		OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {

	// ����������ʾ�б����ݵ�Adapter
	SimpleCursorAdapter mAdapter;

	// �����null�����ǵ�ǰ������������
	String mCurFilter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// ����б���û�����ݣ��͸��ؼ�һЩ����ȥ��ʾ����һ��������Ӧ��
		// ����Ӧ����Դ��ȡ�ã�
		setEmptyText("No phone numbers");

		// �����ڶ���������һ���˵��
		setHasOptionsMenu(true);

		// ����һ���յ�adapter�����ǽ�������ʾ���غ������
		mAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, null, new String[] {
						Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS },
				new int[] { android.R.id.text1, android.R.id.text2 }, 0);
		setListAdapter(mAdapter);

		// ׼��loader.������������һ���Ѵ��ڵĻ�ʼһ���µ�
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// ����һ��������������������
		MenuItem item = menu.add("Search");
		item.setIcon(android.R.drawable.ic_menu_search);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		SearchView sv = new SearchView(getActivity());
		sv.setOnQueryTextListener(this);
		item.setActionView(sv);
	}

	public boolean onQueryTextChange(String newText) {
		// �ڶ������ϵ������ִ��ı�ʱ�����ã�����
		// ������������������loader��ִ��һ���µĲ�ѯ
		mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
		getLoaderManager().restartLoader(0, null, this);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// ���ǲ������������
		return true;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// д������д�Ĵ���
		Log.i("FragmentComplexList", "Item clicked: " + id);
	}

	// �����������ȡ����ϵ����һ�е����ݣ�
	static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
			Contacts._ID, Contacts.DISPLAY_NAME, Contacts.CONTACT_STATUS,
			Contacts.CONTACT_PRESENCE, Contacts.PHOTO_ID, Contacts.LOOKUP_KEY, };

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// ��һ���µ�loader�豻����ʱ���ã���������һ��Loader��
		// �������ǲ������ID����������base URI��URIָ�������ϵ��
		Uri baseUri;
		if (mCurFilter != null) {
			baseUri = Uri.withAppendedPath(Contacts.CONTENT_FILTER_URI,
					Uri.encode(mCurFilter));
		} else {
			baseUri = Contacts.CONTENT_URI;
		}

		// ���ڴ���������һ��CursorLoader���������𴴽�һ��
		// Cursor������ʾ����
		String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
				+ Contacts.HAS_PHONE_NUMBER + "=1) AND ("
				+ Contacts.DISPLAY_NAME + " != '' ))";
		return new CursorLoader(getActivity(), baseUri,
				CONTACTS_SUMMARY_PROJECTION, select, null,
				Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// ���µ�cursor��������(��ܽ������Ƿ���ʱ����һ�¾�cursor�Ĺر�)
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// �����һ��Cursor׼�����������onLoadFinished()֮ǰ��
		// CursorҪ���ر��ˣ�������Ҫȷ������ʹ������
		mAdapter.swapCursor(null);
	}
}